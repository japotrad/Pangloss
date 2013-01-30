// @ExecutionModes({ON_SINGLE_NODE})
import tmf.Tdc
import tmf.Config

import org.freeplane.core.ui.components.UITools
import org.freeplane.core.ui.ExampleFileFilter
import org.freeplane.core.util.LogUtils
import org.freeplane.core.util.TextUtils
import org.freeplane.core.util.LogUtils

import org.freeplane.features.export.mindmapmode.IExportEngine
import org.freeplane.features.export.mindmapmode.XsltExportEngine
import org.freeplane.plugin.script.proxy.Proxy

import groovy.xml.MarkupBuilder
import groovy.util.ConfigSlurper

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import javax.swing.filechooser.FileFilter
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

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.HashMap
import java.util.List

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer


/**
 * XslFileFilterFactory handles the file filters of the file chooser dialog.
 * There are two kinds of filters:<ul>
 * <li>The filters genuinely embedded in the program, like the GMT filter.</li>
 * <li>The filters defined externally in .xsl stylesheets and discovered at runtime.</li></ul>
 * This class is inspired from XsltExportEngineFactory.java and ExportController.java of Freeplane source code.
 * @author Stephane Aubry
 */
private class XslFileFilterFactory {
	/**
	 * <code>xslFilterMap</code> is a map of file filters defined externally in .xsl stylesheets. Keys of the map entries are filters. Values of the map entries are the stylesheet files.
	 */
	Map<FileFilter, File> xslFilterMap = new HashMap<ExampleFileFilter, File>()
	
	/**
	 * <code>fileFilters</code> is the complete list of file filters. It contains both internal and external filters.
	 */
	List<ExampleFileFilter> fileFilters = new ArrayList<ExampleFileFilter>()
	
	/**
	 * COMPILED_EXPORT_FILTER_PATTERN should match in the first lines of a valid .xsl stylesheet.
	// Example: <!-- 	TERMBASEEXPORTFILTER csv;tsv 2-col Spreadsheet -->
	*/
	Pattern COMPILED_EXPORT_FILTER_PATTERN = Pattern.compile('^.*TERMBASEEXPORTFILTER\\s+(\\S+)\\s+(.*)(?:\\s+-->)?$')
	/**
	 * Creates the factory and define the filters genuinely embedded in the program, like the GMT filter.
	 * @return The new <code>XslFileFilterFactory</code> object.
	 */
	public XslFileFilterFactory(){
		fileFilters.add(new ExampleFileFilter(['gmt'] as String[], TextUtils.getOptionalTranslation('Generic Mapping Tool')))
		fileFilters.add(new ExampleFileFilter(['csv'] as String[], TextUtils.getOptionalTranslation('Comma-separated Value')))
		fileFilters.add(new ExampleFileFilter(['tsv'] as String[], TextUtils.getOptionalTranslation('Tab-separated Value')))
	}
	
	/** Returns <code>xslFilterMap</code>, a map of file filters defined externally in .xsl stylesheets. Keys of the map entries are filters. Values of the map entries are the stylesheet files.
	* @return The <code>xslFilterMap</code> map.
	*/
	public HashMap<FileFilter, File> getFilterMap() {
		return xslFilterMap
	}
	/** Returns <code>fileFilters</code>, the complete list of file filters, containing both internal and external filters.
	 * @return The <code>fileFilters</code> list.
	 */
	 public List<ExampleFileFilter> getFileFilters() {
		 return fileFilters
	 }
	/** Adds an externally defined filter.
	 * @param extensions The semicolon separated list of file extensions (without the period character) supported by the filter. Example: 'csv;tsv'
	 * @param description A string describing the filter. Example: '2-col Spreadsheet'
	 * @param xslFile The xsl stylesheet implementing the filter transformation from the GMT format.
	 */
	void addXsltFile(List<String> extensions, String description, File xslFile) {
		ExampleFileFilter filter=new ExampleFileFilter(extensions as String[], TextUtils.getOptionalTranslation(description))
		fileFilters.add(filter)
		xslFilterMap.put(filter, xslFile)
	}

	/**
	* Checks all readable files ending in '.xsl' from a given directory,
	* and passes them to the method {@link #extractFilterFromFile}.
	* @param xsltDir The directory where xsl stylesheet files are to be searched for.
	*/
	private void gatherXsltScripts(File xsltDir) {
		List<File> xslFiles = new ArrayList<File>()
		if (!(xsltDir.isDirectory() && xsltDir.canRead())) {
			LogUtils.info('ExportTermbase	There is a problem related to the folder containing the xsl stylesheet conversion files (from GMT format): '+ xsltDir.getPath())
			return
		}
		// we list the files using an anonymous filter class that accepts only files
		// readable by the user and with name ending in .xsl
		xslFiles = xsltDir.listFiles(new java.io.FileFilter() {
			public boolean accept(final File pathname) {
				return (pathname.isFile() && pathname.canRead() && pathname.getPath().toLowerCase().endsWith(".xsl"))
			}
		})
		// For each found file, we check and extract a potentially present filter
		for (int i = 0; i < xslFiles.size(); i++) {
			extractFilterFromFile(xslFiles[i]);
		}
	}
	
	/**
	 * Checks if the COMPILED_EXPORT_FILTER_PATTERN match within the first 5 lines of the file.
	 * @param xsltFile The file where the pattern is to be searched for.
	 */
	void extractFilterFromFile(final File xsltFile) {
		/** Maximum number of lines we read in each XSLT files for performance reasons */
		int MAX_READ_LINES = 5
		BufferedReader xsl = null
		try {
			xsl = new BufferedReader(new FileReader(xsltFile))
			String line
			int l = 0
			boolean keyFound = false
			// ...we open it and check if it contains the right marker
			while ((line = xsl.readLine()) != null && l < MAX_READ_LINES) {
				final Matcher m = COMPILED_EXPORT_FILTER_PATTERN.matcher(line)
				if (m.matches()) { // if it does
					keyFound = true
					List<String> extensions = m.group(1).split("\\s*;\\s*")
					String description = m.group(2).trim()
					if(description.startsWith("%")){
						description = TextUtils.getText(description.substring(1))
					}
					addXsltFile(extensions, description, xsltFile);
					// we want to allow for more than one filter line per XSLT file
					// so we don't exit once we've found one and even account for
					// the fact that we might trespass the MAX_READ_LINES limit
					l--;
				}
				l++;
			}
			if (keyFound) {
				return;
			}
		}
		catch (final IOException e) {
			LogUtils.warn(e);
			UITools.errorMessage(TextUtils.getText("export_failed"));
		}
		finally {
			if (xsl != null) {
				try {
					xsl.close();
				}
				catch (final IOException e) {
					LogUtils.severe(e);
				}
			}
		}
	}
}

/**
 * A FileChooserAccessory defines the GUI to the right of the file viewer panel of the file chooser dialog.
 * A <code>Parameter</code> button is displayed if the selected file filter needs parameters.
 * @author Stephane Aubry
 */
private class FileChooserAccessory extends JPanel implements PropertyChangeListener {
	/**
	 * <code>fileChooser</code> is the dialog to which the <code>FileChooserAccessory</code> applies.
	 */
	JFileChooser fileChooser
	/**
	 * Creates a FileChooser Accessory object.
	 * @param fc The FileChooser to which the <code>FileChooserAccessory</code> applies.
	 * @return The new <code>FileChooserAccessory</code> object
	 */
	public FileChooserAccessory(JFileChooser fc) {
		this.fileChooser=fc
		fileChooser.addPropertyChangeListener(this)
		JButton paramButton = new JButton(TextUtils.getText('addons.exportParameterButton','Parameters...'))
		this.setBorder(BorderFactory.createEmptyBorder(0,10,0,0)) // Add some space to the left of the button
		switch (fileChooser.getFileFilter() as ExampleFileFilter){
			case fileChooser.getChoosableFileFilters()[1]: // CSV
				paramButton.setEnabled(true)
				break
			case fileChooser.getChoosableFileFilters()[2]: // TSV
				  paramButton.setEnabled(true)
				break
			default:
				  paramButton.setEnabled(false)
		}
		this.add(Box.createVerticalGlue()) // Push the button to the bottom of the accessory panel
		this.add(paramButton)
		BoxLayout layout=new BoxLayout(this,BoxLayout.Y_AXIS)
		this.setLayout(layout)
	}
	/**
	 * Enable or disable the <code>Parameter</code> button according to the selected file filter.
	 * @param changeEvent The GUI event of the file chooser dialog causing the interrupt.
	 */
	public void propertyChange(PropertyChangeEvent changeEvent) {
	  String changeName = changeEvent.getPropertyName();
	  if (changeName.equals(JFileChooser.FILE_FILTER_CHANGED_PROPERTY)) {
		ExampleFileFilter selectedFilter = (ExampleFileFilter)changeEvent.getNewValue()
		JButton paramButton = (JButton) this.getComponent(1) // Component # 0 is the glue
		switch (selectedFilter){
			case fileChooser.getChoosableFileFilters()[1]: // CSV
				paramButton.setEnabled(true)
				break
			case fileChooser.getChoosableFileFilters()[2]: // TSV
				paramButton.setEnabled(true)
				break
			default:
				paramButton.setEnabled(false)
		}
	  }
	}
}
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
	setConfig()
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
	fileChooser.setAccessory(new FileChooserAccessory(fileChooser)) // Link the extra panel on the right
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

