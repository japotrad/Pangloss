// @ExecutionModes({ON_SINGLE_NODE})
import pangloss.xsl.XslFileFilterFactory
import pangloss.tmf.Tdc
import pangloss.tmf.Config

import org.freeplane.core.ui.components.UITools
import org.freeplane.core.ui.ExampleFileFilter
import org.freeplane.core.util.LogUtils
import org.freeplane.core.util.TextUtils

import org.freeplane.features.export.mindmapmode.IExportEngine
import org.freeplane.features.export.mindmapmode.XsltExportEngine
import org.freeplane.plugin.script.proxy.Proxy

import groovy.xml.MarkupBuilder
import groovy.util.ConfigSlurper

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import javax.swing.filechooser.FileSystemView
import javax.swing.JFileChooser
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

import java.util.HashMap
import java.util.List

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer


def setConfig(){
	//Set the configuration for tmf package
	String pangloss_field_separator
	if (config.getProperty('pangloss_field_separator')) {
		pangloss_field_separator = config.getProperty('pangloss_field_separator')
	} else {
		pangloss_field_separator = '>'
	}
	Config.set('pangloss_field_separator', pangloss_field_separator)
	LogUtils.info('ExportTermbase	Adding the configuration parameter pangloss_field_separator, '+pangloss_field_separator)
}
def run(Proxy.Node node, Proxy.Controller c){
	File configFile=new File(c.getUserDirectory().toString()+File.separator +'pangloss'+File.separator +'export.properties')
	ConfigSlurper cfgs=new ConfigSlurper()
	ConfigObject cfgo=new ConfigObject()

	if (configFile.exists() && configFile.canRead() && configFile.size()>0){
		cfgo = cfgs.parse(configFile.toURI().toURL())
		LogUtils.info('ExportTermbase	Reading the configuration file '+ configFile.absolutePath)
	}
	
	File xsltDir=new File(c.getUserDirectory().toString()+File.separator +'pangloss')
	
	JFileChooser fileChooser = new JFileChooser()
	fileChooser.setAcceptAllFileFilterUsed(false)
	fileChooser.setDialogTitle(TextUtils.getText('addons.exportTermbase','Export the terms'))
	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY)
	fileChooser.setDialogType(JFileChooser.SAVE_DIALOG)
	XslFileFilterFactory xfff =new XslFileFilterFactory()
	xfff.gatherXsltScripts(xsltDir)
	List<ExampleFileFilter> choosableFileFilters =  xfff.getFileFilters()
	for (ExampleFileFilter filter : choosableFileFilters) {
		fileChooser.addChoosableFileFilter(filter)
	}
	ExampleFileFilter defaultFileFilter // This is the filter which is selected on dialog opening. When possible, the last used filter is selected. Otherwise: GMT
	if (cfgo.containsKey("last_file_filter")){
		String last_file_filter = cfgo.last_file_filter // example: "JPEG and GIF Image Files (*.jpg, *.gif)"
		// Check that the last filter is choosable
		for (ExampleFileFilter filter : choosableFileFilters) {
			if (filter.getDescription()==last_file_filter) {
				defaultFileFilter=filter
			}
		}
		if (!defaultFileFilter) {
			defaultFileFilter = fileChooser.getChoosableFileFilters()[0] as ExampleFileFilter //GMT format is the default
		}
	}
	else
	{
		defaultFileFilter = fileChooser.getChoosableFileFilters()[0] as ExampleFileFilter //GMT format is the default
		for (ExampleFileFilter filter : choosableFileFilters) {
			fileChooser.addChoosableFileFilter(filter)
		}
	}
	fileChooser.setFileFilter(defaultFileFilter)
	Tdc tdc = new Tdc(node.id)
	String defaultFilename=node.plainText // Name of the node
	String defaultDir
	if (cfgo.containsKey("last_export_dir")){
		defaultDir=cfgo.last_export_dir
	}
	else
	{
		FileSystemView fw = fileChooser.getFileSystemView();
		File myDocuments = fw.getDefaultDirectory();
		defaultDir=myDocuments.toString()
	}
	fileChooser.setSelectedFile(new File(defaultDir+File.separator +defaultFilename))
	int returnVal=fileChooser.showSaveDialog() // The GUI appears
	if (returnVal != JFileChooser.APPROVE_OPTION) {
		return
	}
	File chosenFile = fileChooser.getSelectedFile()
	ExampleFileFilter chosenFilter = fileChooser.getFileFilter() as ExampleFileFilter
	cfgo.last_export_dir=fileChooser.getCurrentDirectory().getAbsolutePath().replaceAll("\\\\", $/\\\\/$) // To double the backslash characters (for Windows)
	cfgo.last_file_filter=chosenFilter.getDescription()
	if (!(configFile.parentFile.isDirectory() && configFile.parentFile.canWrite())) {
		LogUtils.info('ExportTermbase	There is a problem related to the folder '+ configFile.parent.absolutePath)
	}
	else {
		configFile.withWriter{ writer ->
			cfgo.writeTo( writer )
		}
	}
		
	if (!chosenFilter.accept(chosenFile)) {
		// Append the correct file extension if the user has not typed it
		chosenFile = new File(chosenFile.getPath()+'.'+chosenFilter.getExtensionProposal())
	}
	tdc.setFilename(chosenFile.name)
	tdc.populate(node) // Process the Freeplane map


	if (chosenFilter==fileChooser.getChoosableFileFilters()[0]){ //gmt
		def writer= new OutputStreamWriter(new FileOutputStream(chosenFile.getPath()),'utf-8')
		def xml = new MarkupBuilder(writer)
		xml.mkp.xmlDeclaration version: '1.0', encoding: 'UTF-8'
		tdc.toGmt(xml)
		writer.close()
		LogUtils.info('ExportTermbase	Saving the GMT file in: '+chosenFile.getPath())
	}
	if (xfff.getFilterMap().containsKey(chosenFilter)){ // xsl transformation
		File xslFile= xfff.getFilterMap()[chosenFilter]
		File tempGMTFile = File.createTempFile(xslFile.name.substring(0,xslFile.name.length()-4),'.gmt') // The name of the temp file is similar to the xsl file. The .xsl extension is replaced with .gmt
		def writer= new OutputStreamWriter(new FileOutputStream(tempGMTFile.getPath()),'utf-8')
		def xml = new MarkupBuilder(writer)
		xml.mkp.xmlDeclaration version: '1.0', encoding: 'UTF-8'
		tdc.toGmt(xml)
		writer.close()
		LogUtils.info('ExportTermbase	Saving the GMT file in: '+tempGMTFile.getPath())
		
		
		// Create transformer
		def transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new StringReader(xslFile.getText())))
		transformer.setOutputProperty(OutputKeys.METHOD, "text")
		transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/tab-separated-values")
		transformer.setOutputProperty(OutputKeys.INDENT, "no")
		// Load xml
		def resultFile = new FileOutputStream(chosenFile.getPath())
		// Perform transformation
		LogUtils.info('ExportTermbase	Transforming the GMT file using '+xslFile.getPath())
		transformer.transform(new StreamSource(new StringReader(tempGMTFile.getText())), new StreamResult(resultFile))
		LogUtils.info('ExportTermbase	Saving the exported file '+chosenFile.getPath())
		resultFile.close()
	}
}
run(node, c)

