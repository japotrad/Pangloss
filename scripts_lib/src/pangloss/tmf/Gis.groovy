package pangloss.tmf

import java.util.List
import java.util.ArrayList
import java.util.Map
import java.util.HashMap

import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

import groovy.transform.InheritConstructors;
import groovy.xml.MarkupBuilder
/**
 * A Global Information Section contains technical and administrative information applying to the entire data collection.
 * Example: title of the data collection, revision history...
 * A Terminological Data Collection should contain one (and only one) Global Information Section.
 * @author Stephane Aubry
 * @see tmf.Iu
 * @see tmf.Sn
 * @see tmf.Tdc
 */
@InheritConstructors
class Gis extends Sn {
	/**
	 *<code>attributeNamesForGis</code> is the list of attribute names applicable to the current object.
	 */
	private List<String> attributeNamesForGis= new ArrayList<String>()
	/**
	 *<code>attributesForGis</code> is the list of attributes applicable to the current object. Keys of the map entries are attribute names.
	 */
	private Map<String,String> attributesForGis = new HashMap<String,String>()
	/**
	 *<code>filename</code> is the name of the file associated to the Terminological Data Collection.
	 */
	public String filename
	/**
	 * Outputs the Global Information Section in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Global Information Section is transformed into an XML element named <code>struct</code> with the XML attribute type = "GIS".
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {
		if (this)  {
			xml.struct(type:"GIS"){
			this.informationUnits.each { iu -> iu.toGmt(xml)}
			}
		}
	}
	/**
	 * Fills the Global Information Section with data read from the Freeplane node.
	 * Details of the created Information Units (if an attribute with the same name exists in the Freeplane node): <ul>
	 	* <li><code>customerSubset</code></li>
	 	* <li><code>fileIdentifier</code> = node's text (if the fileIdentifier attribute is not defined).</li>
	 	* <li><code>languageID</code></li>
	 	* <li><code>objectLanguage</code></li>
	 	* <li><code>projectSubset</code></li>
 		* <li><code>sourceLanguage</code></li>
 		* <li><code>targetLanguage</code></li></ul>
	 * @param node The node of the Terminological Data Collection in the Freeplane map.
	 * @param tovas The map node of Transitive OVerwritable Attributes. Keys of the map entries are attribute names.
	 * @see tmf.Iu
	 */
	def populate(Proxy.Node rootNode, Map<String,String> tovas) {
		// The name of the glossary is the text of the node
		// Store it in the Global Info Section
		attributeNamesForGis.addAll(["customerSubset","fileIdentifier","languageID", "objectLanguage", "projectSubset", "sourceLanguage", "targetLanguage"])
		attributesForGis= tovas.clone()
		if (rootNode.attributes.size()>0) {
			for (int i in 0..rootNode.attributes.size()-1) {
				def attributeName = rootNode.attributes.getNames()[i]
				if (attributeNamesForGis.contains(attributeName) && !attributesForGis.containsKey(attributeName) ) {
					attributesForGis.put(attributeName, rootNode.attributes.getValues()[i].toString())
				}
			}	
		}
		// filename has precedence over fileIdentifier attribute
		if (this.filename){
			if (attributesForGis.containsKey("fileIdentifier")){
				LogUtils.info(this.class.name+'	 The value of the attribute fileIdentifier has been ignored because the filename has been defined.')
			}
			attributesForGis.put("fileIdentifier", this.filename)
		}
		this.filename = attributesForGis["fileIdentifier"]
		attributesForGis.each { key, value->
			if (attributeNamesForGis.contains(key)) {
				def iu = new Iu(key,"string", value)
				LogUtils.info(this.class.name+'	Adding the Information Unit: '+key+', string, '+value)
				this.add(iu)
			}
		}	
	}
}