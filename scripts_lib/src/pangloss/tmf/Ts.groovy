package pangloss.tmf

import groovy.transform.InheritConstructors
import groovy.xml.MarkupBuilder

import java.util.List
import java.util.Map
import java.util.HashMap

import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

/**
 * A Term Section is the part of a Language Section giving information about a term.
 * It is described in ISO 16642.
 * @author Stephane Aubry
 * @see tmf.Iu
 * @see tmf.Ls
 * @see tmf.Sn
 */
@InheritConstructors
class Ts extends Sn {
	/**
	 *<code>applicableAttributeNames</code> is the list of attributes names applicable to the current Term Section object.
	 */
	private List<String> applicableAttributeNames= new ArrayList<String>()
	/**
	 *<code>attributes</code> is the map of contextual attributes of the Term Section object.
	 * Keys of the map entries are the attribute names.
	 */
	private Map<String,String> attributes = new HashMap<String,String>()
	
	/**
	 * Outputs the Term Section in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Term Section is transformed into an XML element named <code>struct</code> with the XML attribute type = "TS".
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {
		xml.struct(type:"TS"){
			this.informationUnits.each { iu -> iu.toGmt(xml,attributes)}
		}
	}
	/**
	 * Fills the Term Section with data read from the Freeplane node.
	 * Details of the created Information Units: <ul>
		* <li><code>customerSubset</code></li>
		* <li><code>projectSubset</code></li>
		* <li><code>term</code>= node's text (if the Terminological Entry is conceptual)<br/>
		* Note: if the parent Terminological Entry is bilingual, <code>term</code> is just a part of the node's text: substring to the left of = for the source term, and substring to the right of = for the target term.
		* </li></ul>
	 * Details of the created Term Component Sections: No Term Component Section is created.
	 * @param node The node of the Terminological Entry in the Freeplane map.
	 * @param tovas The map node of Transitive OVerwritable Attributes. Keys of the map entries are attribute names.
	 * @param lsMode The type of Language Section to be populated. See {@link tmf.Constants#LsMode}
	 * @see tmf.Iu
	 * @see tmf.Ls
	 */
	def populate(Proxy.Node node, Map<String,String> tovas, Constants.LsMode lsMode) {
		attributes = tovas.clone()
		applicableAttributeNames.addAll(["customerSubset", "projectSubset","term"])
		switch (lsMode) {
			case Constants.LsMode.SOURCE :
				applicableAttributeNames.addAll(["context", "definition"])
				if (node.note) {
					attributes.put("context", node.note.to.plain)
				}
				if (node.details) {
					attributes.put("definition", node.details.to.plain)
				}
				if (node.plainText.indexOf("=")>0) {
					LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\", Language Section '+  lsMode.toString() +', Main term is: '+node.plainText.substring(0,node.plainText.indexOf("=")))
					attributes.put("term",node.plainText.substring(0,node.plainText.indexOf("=")))
				}
				break
			case Constants.LsMode.TARGET:
				if (node.plainText.length() - node.plainText.indexOf("=")>1) {
					LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\", Language Section '+  lsMode.toString()+', Main term is: '+node.plainText.substring(node.plainText.indexOf("=")+1))
					attributes.put("term",node.plainText.substring(node.plainText.indexOf("=")+1))
				}
				break
			case Constants.LsMode.CONCEPT:
				LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\", Language Section '+  lsMode.toString()+', Main term is: '+node.plainText)
				attributes.put("term",node.plainText)
				break
			default:
				break
		}
		attributes.each{ key, value->
			if (applicableAttributeNames.contains(key)) {
				def iu = new Iu(key,"string", value)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", Language Section '+  lsMode.toString()+', Main term. adding the Information Unit: '+key+', string, '+value)
				this.add(iu)
			}
		}
	}
}
