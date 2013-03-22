package pangloss.xsl

import java.util.HashMap;
import java.util.HashSet
import java.util.Map
import java.util.Properties;
import java.util.Map.Entry;
import org.freeplane.plugin.script.ScriptingEngine
import org.freeplane.plugin.script.ScriptingPermissions

import org.freeplane.plugin.script.proxy.Proxy
import org.freeplane.core.util.TextUtils
import org.freeplane.core.util.LogUtils

import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.mindmapmode.MModeController
import org.freeplane.core.resources.ResourceController
import org.freeplane.core.resources.components.IValidator;
import org.freeplane.core.resources.components.IValidator.ValidationResult;
import org.freeplane.main.addons.AddOnProperties;
// Inspired from AddOnsController.java
class XSLController {
	/**
	 * <code>XSL_DIR</code> is the absolute path of the directory where XSL stylesheet files are stored.
	 */
	static String XSL_DIR = ResourceController.getResourceController().getFreeplaneUserDirectory()+File.separator +'pangloss'
	/** Make the translations of this XSL file properties known system-wide.
	 * @param xslFileProperties The XSLFileProperties object to process */
	static void registerXSLTranslations(XSLFileProperties xslFileProperties) {
		ResourceController resourceController = ResourceController.getResourceController();
		HashSet<String> languages = new HashSet<String>()
		languages.add(resourceController.getLanguageCode())
		languages.add(resourceController.getDefaultLanguageCode())
		
		for (String language : languages) {
			def Map<String, String> resources = xslFileProperties.getTranslations(language)
			if (resources != null) {
				resourceController.addLanguageResources(language, addOptionPanelPrefix(resources))
				//Adds also an entry containing the short name of the XSL stylesheet. This entry is intended to be used as a separator in the Option Panel GUI. 
				resourceController.addLanguageResources(language, [("OptionPanel.separator." + xslFileProperties.xslShortName) : TextUtils.getText("OptionPanel.Files")+ " " + xslFileProperties.description])
			}
		}
	}
	/** Initialize the default values of the preference items so that they are known system-wide.
	 * @param xslFileProperties The XSLFileProperties object to process */
	static void registerXSLPreferenceDefaultValues(XSLFileProperties xslFileProperties) {
		ResourceController resourceController = ResourceController.getResourceController()
		def Map<String, String> resources = xslFileProperties.preferenceDefaultValues
		if (resources != null) {
			resources.each{k,v ->
				if (resourceController.getProperty(k)){
					LogUtils.info(XSLController.class.name +'	The preference property ' + k + ' has already been set (with the value '+ resourceController.getProperty(k) + '). No initialization is needed.')
				}else{
					resourceController.setProperty(k, v)
					LogUtils.info(XSLController.class.name +'	Initialize the preference property ' + k + ' with the value '+ v)
				}
			}
		}
	}
	/** Adds the prefix <code>OptionPanel.</code> to the keys of the map.
	 * The purpose of this method is to reduce the length of the key in the files and to maintain name uniqueness.
	 * @param resources The map to be processed.
	 * @param xslShortName The short name of the XSL stylesheet.
	 * @return Modified map */
	private static Map<String, String> addOptionPanelPrefix(Map<String, String> resources) {
		HashMap<String, String> result = new HashMap<String, String>(resources.size())
		for (Entry<String, String> entry : resources.entrySet()) {
			result.put("OptionPanel." + entry.getKey(), entry.getValue())
		}
		return result;
	}
	/**
	 * Add the XSL stylesheet parameter in the Option Panel GUI
	 */
	static void registerXSLPreferences(XSLFileProperties xslFileProperties) {
		final URL preferences =xslFileProperties.preferenceFile.toURI().toURL()
		Controller.getCurrentController().addOptionValidator(new IValidator() {
			public ValidationResult validate(Properties properties) {
				final ValidationResult result = new ValidationResult()
				final String readAccessString = properties
					.getProperty(ScriptingPermissions.RESOURCES_EXECUTE_SCRIPTS_WITHOUT_READ_RESTRICTION)
				final String writeAccessString = properties
					.getProperty(ScriptingPermissions.RESOURCES_EXECUTE_SCRIPTS_WITHOUT_WRITE_RESTRICTION)
					final String classpath = properties.getProperty(ScriptingEngine.RESOURCES_SCRIPT_CLASSPATH)
					final boolean readAccess = readAccessString != null && Boolean.parseBoolean(readAccessString)
					final boolean writeAccess = writeAccessString != null && Boolean.parseBoolean(writeAccessString)
					final boolean classpathIsSet = classpath != null && classpath.length() > 0;
					if (classpathIsSet && !readAccess) {
						result.addError(TextUtils.getText("OptionPanel.validate_classpath_needs_readaccess"))
					}
					if (writeAccess && !readAccess) {
						result.addWarning(TextUtils.getText("OptionPanel.validate_write_without_read"))
					}
					return result
			}
		})
		final MModeController modeController = (MModeController) Controller.getCurrentModeController()
		modeController.getOptionPanelBuilder().load(preferences)
		LogUtils.info(this.class.name +'	The preferences of the ' + xslFileProperties.preferenceFile.name + ' file have been loaded into the GUI preferences.')
	}	
}
