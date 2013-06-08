package pangloss.tmf

import java.util.Map

import org.freeplane.plugin.script.proxy.Proxy

/**
 * The Config class statically contains the configuration parameters used by the classes of the tmf package.
 * @author Stephane Aubry
 */
class Config {
	/**
	 * A map of configuration values. Here is the list of mandatory configuration parameters:<ul>
	 * <li><code>pangloss_field_separator</code>: Separator between a subjectField and its sub-fields. Fix value: &gt; </li>
	 * </ul>
	 * @see tmf.Iu
	 */
	public static Map<String,String> config =['pangloss_field_separator':' > ']
	/**
	 * Adds a parameter in the config map.
	 * @param key The parameter key
	 * @param value The parameter value
	 */
	def static set(String key, String value) {
		if (config.containsKey(key)) {
			config[key]=value
		} else {
			config.put(key, value)
		}
	}
	/**
	 * Reads a parameter from the config map.
	 * @param key The parameter key
	 */
	def static get(String key) {
		return config[key]
	}

	/**
	 * Browse the siblings of the node having style=Setting and update the config map accordingly.
	 * @param node The map node
	 */
	def static refresh(Proxy.Node node) {
		if (node.isRoot()) {return}
		def parentNode=node.parent
		parentNode.children.each {
			def siblingNode = it
			if (siblingNode.style.name=="Setting"){
				if (siblingNode.plainText=="Parameters"){
					for (int i in 0..siblingNode.attributes.size()-1) {
						def attributeName = siblingNode.attributes.getNames()[i]
						if (attributeName != "pangloss_field_separator"){ // Constant parameters should not be updated.
							Config.set(attributeName, siblingNode.attributes.getValues()[i].toString())
						}
					}
				}
			}
		}	
	}
}
