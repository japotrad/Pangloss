package pangloss.xsl

import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List
import java.util.Map
import java.util.Properties;
import java.util.regex.Matcher
import java.util.regex.Pattern

import groovy.util.ConfigSlurper

import org.freeplane.plugin.script.proxy.Proxy
import org.freeplane.core.resources.ResourceController;
import org.freeplane.core.ui.components.UITools
import org.freeplane.core.util.FileUtils;
import org.freeplane.core.util.LogUtils
import org.freeplane.core.util.TextUtils


class XSLFileProperties {
	/**
	 * <code>XSL_DIR</code> is the absolute path of the directory where XSL stylesheet files are stored.
	 */
	static String XSL_DIR = ResourceController.getResourceController().getFreeplaneUserDirectory()+File.separator +'pangloss'
	/**
	 * <code>translations</code> is a map of the translations related to the XSL stylesheet.
	 * Keys are language codes. Values are (text_id, translation) maps. 
	 */
	private Map<String, Map<String, String>> translations = new HashMap()
	/**
	 * <code>preferenceDefaultValues</code> is a map of the default values of the preference items.
	 * Keys are preference ids.
	 */
	private Map<String, String> preferenceDefaultValues
	/**
	 * COMPILED_EXPORT_FILTER_PATTERN should match in the first lines of a valid .xsl stylesheet.
	 * Example: <!-- 	TERMBASEEXPORTFILTER csv;tsv 2-col Spreadsheet -->
	*/
	Pattern COMPILED_EXPORT_FILTER_PATTERN = Pattern.compile('^.*TERMBASEEXPORTFILTER\\s+(\\S+)\\s+(.*)(?:\\s+-->)?$')
	/** <code>xslFile</code> is the XSL stylesheet file */
	File xslFile
	/** <code>xslFileName</code> is the name of the XSL stylesheet file */
	String xslFileName
	/** <code>extensions</code> is the list of file extensions the XSL stylesheet supports. The extension is the substring following the last decimal point in the filename. It is typically 3-letter long.<br>
	 * Example: In file.xml, the extension is xml
	 */
	List<String> extensions
	/** <code>xslShortName</code> is the name of the XSL stylesheet file, without the extension.
	 * Example: The xslShortName of gmt2tsv.xsl is gmt2tsv*/
	String xslShortName
	
	/** <code>description</code> is the one line description of the XSL stylesheet. It is typically a description of the target format (as the source format is fixed: GMT)*/
	String description
	/** <code>arePreferenceDefaultValuesAlreadyRead</code> indicates whether the default values of the preference items have already been extracted from the file.*/
	boolean arePreferenceDefaultValuesAlreadyRead = false
	/** <code>areExtensionAndDescriptionAlreadyRead</code> indicates whether <code>extensions</code> and <code>description</code> have already been read from the XSL file.*/
	boolean areExtensionAndDescriptionAlreadyRead = false
	
	/** <code>isTranslationAlreadyRead</code> indicate for each <code>language</code> whether the translation has already been read from the *_<code>language</code>.properties file.
	 * Keys of the map are <code>language</code> values. If a language does not exist in the map, it means that no reading attempt has been performed yet.
	 * If the value is <code>true</code>, it means that a reading attempt has been successful.
	 * If the value is <code>false</code>, it means that a reading attempt has failed.
	 * */
	private isTranslationAlreadyRead = [:]
	
	/** Returns the list of file extensions the XSL stylesheet supports.
	 * @return The list of extensions
	 */
	List<String> getExtensions(){
		if (!areExtensionAndDescriptionAlreadyRead) {
			readExtensionsAndDescriptionFromFile()
		}
		return extensions
	}
	/** Returns the description of the XSL stylesheet. In the XSL file, if the description string starts with a % character, then this methods tries to find an appropriate translation for it.  
	 * @return The description string
	 */
	String getDescription(){
		if (!areExtensionAndDescriptionAlreadyRead) {
			readExtensionsAndDescriptionFromFile()
		}
		return description
	}
	/**
	 * Create a XSLFileProperties object based on a XSL File object.
	 * @param file The XSL stylesheet file
	 */
	XSLFileProperties(File file){
		xslFile = file
		xslFileName=file.name
		xslShortName=file.name.substring(0,file.name.length()-4)
	}
	/** Returns the translations of the text related to the XSL stylesheet.
	 * @param language The language or locale code.
	 * @return A map containing the text ids as keys and the translated string as values.
	 */
	public Map<String,String>getTranslations(String language){
		if (!isTranslationAlreadyRead.containsKey(language)) {
			readTranslation(language)
		}
		if (isTranslationAlreadyRead[language]){
			return translations[language]
		} else {
			return null
		}
	}
	/** Returns the default values of the XSL stylesheet preferences.
	 * @return A map containing the preference ids as keys and the default values as values.
	 */
	public Map<String,String>getPreferenceDefaultValues(){
		if (!arePreferenceDefaultValuesAlreadyRead) {
			readPreferenceDefaultValues()
		}
		if (arePreferenceDefaultValuesAlreadyRead){
			return preferenceDefaultValues
		} else {
			return null
		}
	}
	
	/**
	 * Extracts the translations of the texts supporting the XSL stylesheet.
	 * They are read from a file having the following name:
	 * XSL stylesheet filename (without extension) + underscore + language + .properties
	 * Example: gmt2tsv_en.properties
	 * @param language The language (or locale) code
	 */
	private readTranslation(String language){
		String absolutePath = XSL_DIR+File.separator + xslShortName + "_" + language+".properties"
		URL resourceURL=new File(absolutePath).toURI().toURL()
		if (resourceURL == null) {
			isTranslationAlreadyRead[language] = false
			return null;
		}
		LogUtils.info(this.class.name +'	Reading the translation file '+ absolutePath )
		InputStream is = null
		try {
			is = new BufferedInputStream(resourceURL.openStream());
			Properties bundle = new Properties()
			bundle.load(is)
			translations.put(language, new HashMap(bundle))
			isTranslationAlreadyRead[language] = true
        }
        finally {
        	FileUtils.silentlyClose(is)
        }
	}
	/**
	 * Extracts the default values of the preference items supporting the XSL stylesheet.
	 * They are read from a file having the following name:
	 * XSL stylesheet filename (without extension) + .properties
	 * Example: If the XSL stylesheet is named conv.xsl, then the preference default values shall be stored in conv.properties
	 */
	private readPreferenceDefaultValues(){
		String absolutePath = XSL_DIR+File.separator + xslShortName + ".properties"
		URL resourceURL=new File(absolutePath).toURI().toURL()
		if (resourceURL == null) {
			arePreferenceDefaultValuesAlreadyRead = false
			return null
		}
		LogUtils.info(this.class.name +'	Reading the preference default value file '+ absolutePath )
		InputStream is = null
		try {
			is = new BufferedInputStream(resourceURL.openStream());
			Properties bundle = new Properties()
			bundle.load(is)
			preferenceDefaultValues=new HashMap(bundle)
			arePreferenceDefaultValuesAlreadyRead = true
		}
		finally {
			FileUtils.silentlyClose(is)
		}
	}
	/** Indicates whether a preference file for the XSL stylesheet exists.
	 * Example: If the XSL stylesheet is named conv.xsl, then the preferences file shall be named conv_preferences.xml (in the same directory)
	 * @return <code>true</code> if the preference file exists, else <code>false</code>.
	 */
	public boolean hasPreferencesFile(){
		File preferencesFile = new File(XSL_DIR + File.separator + xslShortName + '_preferences.xml')
		return (preferencesFile.exists())
	}
	/** Indicates whether a preference default values file for the XSL stylesheet exists.
	 * Example: If the XSL stylesheet is named conv.xsl, then the preferences default values file shall be named conv.properties (in the same directory)
	 * @return <code>true</code> if the preference default values file exists, else <code>false</code>.
	 */
	public boolean hasPreferenceDefaultValuesFile(){
		File file = new File(XSL_DIR + File.separator + xslShortName + '.properties')
		return (file.exists())
	}
	
	/**
	 * Returns the preference file associated to the XSL stylesheet. This XML file contains the list of transformation parameter.
	 * @return The preference file as a file object.
	 */
	public File getPreferenceFile(){
		if (hasPreferencesFile()) {
			return  new File(XSL_DIR + File.separator + xslShortName + '_preferences.xml')
		}
		else return null
	}
	/**
	 * Extracts the handled file extension and description from the first lines of the XSL stylesheet file.
	 * In the XSL file, if the description string starts with a % character, then this methods tries to find an appropriate translation for it.
	 */
	private readExtensionsAndDescriptionFromFile(){
		int MAX_READ_LINES = 5
		BufferedReader xsl = null
		try {
			xsl = new BufferedReader(new FileReader(xslFile))
			String line
			int l = 0
			boolean keyFound = false
			// ...we open it and check if it contains the right marker
			while ((line = xsl.readLine()) != null && l < MAX_READ_LINES) {
				final Matcher m = COMPILED_EXPORT_FILTER_PATTERN.matcher(line)
				if (m.matches()) { // if it does
					keyFound = true
					extensions = m.group(1).split("\\s*;\\s*")
					LogUtils.info(this.class.name +'	The xsl stylesheet '+ xslFileName + ' supports the following file extensions: ' + extensions.toString())
					description = m.group(2).trim()
					LogUtils.info(this.class.name +'	The xsl stylesheet '+ xslFileName + ' has the following description: ' + description)
					if(description.startsWith("%")){
						description = TextUtils.getText(description.substring(1))
						LogUtils.info(this.class.name +'	The following translation of the above description has been found '+ description)
					}
					// we want to allow for more than one filter line per XSLT file
					// so we don't exit once we've found one and even account for
					// the fact that we might trespass the MAX_READ_LINES limit
					l--
				}
				l++
			}
			if (keyFound) {
				areExtensionAndDescriptionAlreadyRead=true
				return
			}
		}
		catch (final IOException e) {
			LogUtils.warn(e);
			UITools.errorMessage(TextUtils.getText("export_failed"))
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
