package pangloss.tmf

import java.util.Map
import java.util.HashMap
import java.util.List
import java.util.ArrayList

import groovy.xml.MarkupBuilder
import groovy.transform.InheritConstructors

import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

import static Constants


/**
 * A Terminological Data Collection contains information on concepts of specific subject fields
 * It is the root of a glossary, as described in ISO 16642
 * @author Stephane Aubry
 * @see tmf.Sn
 */
@InheritConstructors
class Tdc extends Sn {
	/**
	 * <code>globalInformationSection</code> contains the Global Information Section.
	 * @see tmf.Gis
	 */
	private globalInformationSection = new Gis()
	/**
	 * <code>complementaryInformation</code> contains the Complementary Information.
	 * @see tmf.Ci
	 */
	private complementaryInformation = new Ci()
	/**
	 * <code>tovasForTdc</code> contains the list of applicable list of Transitive OVerwritable Attributes along with their values.
	 * The value of a tova is valid on the whole branch starting from current node. unless it is overridden in a descendant node.
	 * The superset list of tova names is defined in <code>TovaNames</code>.
	 * For a given map node, a Transitive OVerwritable Attribute has at most one value.
	 * @see tmf.Constants
	 */
	private Map<String,String> tovasForTdc = new HashMap<String,String>()
	/**
	 *<code>tovaNames</code> is the complete list of Transitive OVerwritable Attributes.
	 * The value of a tova is valid on the whole branch starting from current map node, unless it is overridden in a descendant node.
	 */
	private List<String> tovaNames= new ArrayList<String>()

	/**
	 * Fills the <code>Tdc</code>, its <code>Gis</code> with data, and request the creation of a <code>Te</code> collection according to the children nodes of the glossary root node in the Freeplane map. 
	 * Data is read from the root node of the glossary in the Freeplane map.
	 * Usually, this node is the root node of the map itself (One glossary per map).
	 * Details of the created Information Units in the <code>Tdc</code> object:<ul>
	 * <li><code>customerSubset</code></li>
	 * <li><code>languageID</code></li>
	 * <li><code>objectLanguage</code></li>
	 * <li><code>projectSubset</code></li>
	 * <li><code>sourceLanguage</code></li>
	 * <li><code>targetLanguage</code></li></ul>
	 * @param rootNode The root node of the glossary in the Freeplane map.    
	 * @see tmf.Gis
	 */
	void populate(Proxy.Node rootNode) {
		tovaNames.addAll(["customerSubset","languageID","objectLanguage", "projectSubset", "sourceLanguage", "targetLanguage"])
		String subjectField="" // Root subject of the terminology database, if any.
		if (rootNode.attributes.size() > 0) {
			for (int i in 0..rootNode.attributes.size()-1) {
				def attributeName = rootNode.attributes.getNames()[i]
				if (tovaNames.contains(attributeName)) {
						// The attribute does not exist in the tovas map => Add it
						LogUtils.info(this.class.name+'	In root node \"' + rootNode.plainText + '\", there is a new attribute: ' + attributeName + '. Its value is: ' + rootNode.attributes.getValues()[i].toString() +'.' )
						tovasForTdc.put(attributeName, rootNode.attributes.getValues()[i].toString())
				}
				
				if (attributeName=="subjectField"){
					subjectField=rootNode.attributes.getFirst(attributeName).toString()
					LogUtils.info(this.class.name+'	In root node \"' + rootNode.plainText + '\", there is a new attribute: ' + attributeName + '. Its value is: ' + rootNode.attributes.getFirst(attributeName).toString() +'.' )
				}
			}
		}
		globalInformationSection.populate(rootNode, tovasForTdc)
		this.recursivePopulate(rootNode, tovasForTdc, subjectField)
	}
	/**
	 * Sets the filename of the Terminological Data Collection.
	 * Example: 'My termbase.xls'
	 * @param filename Filename of the Terminological Data Collection
	 */
	void setFilename(String filename){
		globalInformationSection.filename=filename
	}
	/**
	 * Create a collection of the <code>Te</code> according to the children nodes of the glossary root node in the Freeplane map, and populate them.
	 * Data is read from the root node of the glossary in the Freeplane map.
	 * Usually, this node is the root node of the map itself (One glossary per map).
	 * The recursion goes through nodes the style of which is either <code>Concept</code> or <code>Term</code>.
	 * A Terminological Entry is created for each valid <code>Concept</code> node.
	 * @param node The root node of the glossary in the Freeplane map.
	 * @param tovas The map node of Transitive OVerwritable Attributes. Keys of the map entries are attribute names.
	 * @param subjectField The value of the parent subjectField.
	 * @see tmf.Te
	 */
	def private recursivePopulate(Proxy.Node node, Map<String,String> tovas, String subjectField) {	
		if (node.style.name=="Concept" && node.plainText.length()>0){
			if(node.plainText.substring(node.plainText.length() - 1)=="="){
				LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\" is ignored because the last character is equal.')
			}
			else{
				def te = new Te(node.id)
				LogUtils.info(this.class.name+'	Adding the Terminological Entry: \"'+node.plainText+'\".')
				te.populate(node, tovas, subjectField)
				this.add(te)
			}
		}
		node.children.each {
			def childNode = it
			if ((childNode.style.name=="Concept") || (childNode.style.name=="Term")){
				def Map<String,String> childTovas = new HashMap<String,String>()
				childTovas=tovas.clone()
				String childSubjectField=subjectField
				if (childNode.attributes.size() > 0) {
					def nodePlainText= childNode.plainText
					for (int i in 0..childNode.attributes.size()-1) {
						def attributeName = childNode.attributes.getNames()[i]
						if (tovaNames.any {it == attributeName}) {
							// The attribute is transitive overwritable
							if (childTovas.containsKey(attributeName)) {
								// The attribute exists in the tovas map => Overwrite the tova value
								LogUtils.info(this.class.name+'	In node \"' + nodePlainText + '\", the value of ' + attributeName + ' is changed from ' + childTovas[attributeName] + ' to ' + childNode.attributes.getValues()[i].toString() +'.' )
								childTovas.put(attributeName, childNode.attributes.getValues()[i].toString())
							} else {
								// The attribute does not exist in the tovas map => Add it
								LogUtils.info(this.class.name+'	In node \"' + nodePlainText + '\", there is a new attribute: ' + attributeName + '. Its value is: ' + childNode.attributes.getValues()[i].toString() +'.' )
								childTovas.put(attributeName, childNode.attributes.getValues()[i].toString())
							}
						}
						if (attributeName=="subjectField") {
							String attributeValue=childNode.attributes.getFirst(attributeName)
							// The subjectField attributes shall be concatenated if it starts with a +
							if (attributeValue[0]=="+") {
								childSubjectField=childSubjectField+Config.get('pangloss_field_separator')+childNode.attributes.getFirst(attributeName).toString().substring(1)
								LogUtils.info(this.class.name+'	In node \"' + nodePlainText + '\", the value of ' + attributeName + ' is changed from ' + childNode.attributes.getFirst(attributeName).toString() + ' to ' + childSubjectField + '.' )
							} else {
								childSubjectField=childNode.attributes.getFirst(attributeName).toString()
								LogUtils.info(this.class.name+'	In node \"' + nodePlainText + '\", the value of ' + attributeName + ' is ' + childSubjectField +'.' )

							}
						}
					}
				}
				// RECURSION LOOP CALL
				this.recursivePopulate(it, childTovas,childSubjectField)
			
			}
		}
		
	}
	/**
	 * Create the XML file conforming to the GMT format
	 * GMT stands for Generic Mapping Tool and is defined in ISO 16442.
	 * Some data loss might occur:
	 *  - if the data categories don't conform to ISO 12620
	 *  @param xml	The builder for the XML structure
	 */
	def toGmt(MarkupBuilder xml) {
		xml.tmf() {
			struct(type:"TDC",id:this.id){
				globalInformationSection.toGmt(xml)
				children.each { id, te->te.toGmt(xml)}
			}
		}	
	}
}
