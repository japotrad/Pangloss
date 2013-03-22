import pangloss.xsl.XSLFileProperties
import pangloss.xsl.XslFileFilterFactory
import pangloss.xsl.XSLController
import java.net.URL
import java.util.Properties
import java.io.File

import javax.swing.filechooser.FileFilter

import org.freeplane.core.resources.components.IValidator
import org.freeplane.core.resources.components.IValidator.ValidationResult

import org.freeplane.core.util.LogUtils
import org.freeplane.core.util.TextUtils

import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.mindmapmode.MModeController
import org.freeplane.plugin.script.ScriptingEngine
import org.freeplane.plugin.script.ScriptingPermissions
import org.freeplane.plugin.script.proxy.Proxy

def run(Proxy.Node node, Proxy.Controller c){
	/* Inspired from ScriptingRegistration.java / addPropertiesToOptionPanel */
	File xsltDir=new File(c.getUserDirectory().toString()+File.separator +'pangloss')
	XslFileFilterFactory xfff =new XslFileFilterFactory()
	xfff.gatherXsltScripts(xsltDir)
	xfff.getFilterMap().each{fileFilter,file ->
		XSLFileProperties xslFileProperties = new XSLFileProperties(file)
		LogUtils.info('RegisterXsl	Process the file '+ file.name)
		if (!xslFileProperties.hasPreferencesFile()){
			LogUtils.info('RegisterXsl	No preference file found. Assuming that the xsl stylsheet cannot handle parameters...')
		}
		else
		{
			XSLController.registerXSLTranslations(xslFileProperties)
			XSLController.registerXSLPreferences(xslFileProperties)
			XSLController.registerXSLPreferenceDefaultValues(xslFileProperties)
		}
	}
}
run(node, c)