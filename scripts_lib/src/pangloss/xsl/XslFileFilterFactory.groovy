package pangloss.xsl

import java.io.File
import java.util.HashMap
import java.util.List
import java.util.Map

import org.freeplane.core.util.LogUtils
import org.freeplane.core.util.TextUtils

import javax.swing.filechooser.FileFilter

import org.freeplane.core.ui.ExampleFileFilter


/**
 * XslFileFilterFactory handles the file filters of the file chooser dialog.
 * There are two kinds of filters:<ul>
 * <li>The filters genuinely embedded in the program, like the GMT filter.</li>
 * <li>The filters defined externally in .xsl stylesheets and discovered at runtime.</li></ul>
 * This class is inspired from XsltExportEngineFactory.java and ExportController.java of Freeplane source code.
 * @author Stephane Aubry
 */
class XslFileFilterFactory {
	/**
	 * <code>xslFilterMap</code> is a map of file filters defined externally in .xsl stylesheets. Keys of the map entries are filters. Values of the map entries are the stylesheet files.
	 */
	Map<FileFilter, File> xslFilterMap = new HashMap<ExampleFileFilter, File>()
	
	/**
	 * <code>fileFilters</code> is the complete list of file filters. It contains both internal and external filters.
	 */
	List<ExampleFileFilter> fileFilters = new ArrayList<ExampleFileFilter>()
	
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
			LogUtils.info(this.class.name +'	There is a problem related to the folder containing the xsl stylesheet conversion files (from GMT format): '+ xsltDir.getPath())
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
	 * Read the extensions and description from the XSL file and add the corresponding filter.
	 * @param xslFile The file where the pattern is to be searched for.
	 */
	void extractFilterFromFile(File xslFile) {
		XSLFileProperties xslFileProperties= new XSLFileProperties(xslFile)
		addXsltFile(xslFileProperties.extensions, xslFileProperties.description, xslFile)
	}
}
