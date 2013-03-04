package pangloss.tmf

import java.util.Map
/**
 * The Config class statically contains the configuration parameters used by the classes of the tmf package.
 * @author Stephane Aubry
 */
class Config {
	/**
	 * A map of configuration values. Here is the lis of configuration parameters:<ul>
	 * <li><code>pangloss_field_separator</code>: String to be inserted as a separator when concatenating the value of the <code>subjectField<code> Information Unit.</li>
	 * </ul>
	 * @see tmf.Iu
	 */
	public static Map<String,String> config = new HashMap<String,String>()
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
}
