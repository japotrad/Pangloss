// @ExecutionModes({ON_SINGLE_NODE})
import groovy.xml.MarkupBuilder

import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer

import tmf.Tdc
def run(Proxy.Node node, Proxy.Controller c){
	
	String directory=c.getUserDirectory().toString()+'\\'
	Tdc tdc = new Tdc(node.id)
	String filename=tdc.populate(node)
	def writer= new OutputStreamWriter(new FileOutputStream(directory+filename+'.gmt'),'utf-8')
	def xml = new MarkupBuilder(writer)
	xml.mkp.xmlDeclaration version: '1.0', encoding: 'UTF-8'
	tdc.toGmt(xml)
	writer.close()
	LogUtils.info('ExportTermbase	Saving the GMT file in: '+directory+filename+'.gmt')
	
	// Load xslt
	def xslt= new File(directory+'resources\\xsl\\gmt2tsv.xsl').getText()
// Create transformer
	def transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new StringReader(xslt)))
	transformer.setOutputProperty(OutputKeys.METHOD, "text")
	transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/tab-separated-values")
	transformer.setOutputProperty(OutputKeys.INDENT, "no")
	// Load xml
	def gmtFile= new File(directory+filename+'.gmt').getText()
	def tsvFile = new FileOutputStream(directory+filename+'.tsv')
	// Perform transformation
	transformer.transform(new StreamSource(new StringReader(gmtFile)), new StreamResult(tsvFile))
	LogUtils.info('ExportTermbase	Saving the TSV file in: '+directory+filename+'.tsv')
	tsvFile.close()
	}
	
run(node, c)
