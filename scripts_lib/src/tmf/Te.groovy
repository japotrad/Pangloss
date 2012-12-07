package tmf

import java.util.List
import java.util.ArrayList
import java.util.Map
import java.util.HashMap

import groovy.transform.InheritConstructors
import groovy.xml.MarkupBuilder

import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

import static Constants

/**
 * A Terminological Entry contains information on terminological units (i.e., subject-specific concepts, terms, etc.)
 * Information pertains to a single concept, as described in ISO 16642.
 * It usually contains, for example, the terms assigned to a concept,
 * descriptive information pertinent to  concept,
 * and administrative information concerning the concept.
 * It can contain one or two Language Sections depending on whether the termbase is conceptual or bilingual
 * Note: Multilingual Terminological Entries (i.e. three or more Language Sections) are not supported.
 * 
 * @author Stephane Aubry
 * @see tmf.Iu
 * @see tmf.Ls
 * @see tmf.Sn
 * @see tmf.Tdc
 */
@InheritConstructors
class Te extends Sn {
	/**
	 *<code>sasForTe</code> is the list of Standard Attributes applicable only to the current Concept Freeplane node. Keys of the map entries are attribute names.
	 */
	private Map<String,String> sasForTe
	/**
	 *<code>tovasForTe</code> is the list of Transitive OVerwritable Attributes applicable to Terminological Entries.
	 * Keys of the map entries are attribute names.
	 */
	private Map<String,String> tovasForTe
	/**
	 *<code>tovaNamesForTe</code> is the list of Transitive OVerwritable Attributes names applicable to Terminological Entries.
	 * It is a subset of <code>tovaNames</code> of <code>Tdc</code> class.
	 * @see tmf.Tdc#tovaNames
	 */
	private List<String> tovaNamesForTe= new ArrayList<String>()
	/**
	 * Adds a Language Section as content of the current object.
	 * @param ls The content Language Section object
	 * @see tmf.Ls
	 */
	def add(Ls ls) {
		children.put(ls.id, ls)
	}
	/**
	 * Outputs the Terminological Entry in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Terminological Entry is transformed into an XML element named <code>struct</code> with the XML attribute type = "TE".
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {
		xml.struct(type:"TE"){
			this.informationUnits.each { iu -> iu.toGmt(xml, tovasForTe)}
			this.children.each { id, ls->ls.toGmt(xml)}
		}
	}
	/**
	 * Fills the Terminological Entry with data read from the Freeplane node.
	 * Details of the created Information Units: <ul>
	 	* <li><code>context</code> = node's note (only if the Terminological Entry is conceptual)</li>
	 	* <li><code>customerSubset</code></li>
	 	* <li><code>definition</code>  =  node's details (only if the Terminological Entry is conceptual)</li>
	 	* <li><code>entryIdentifier</code> = node's id</li>
	 	* <li><code>projectSubset</code></li>
 		* <li><code>sourceLanguage</code> and <code>targetLanguage</code> (only if the Terminological Entry is bilingual)</li></ul>
	 * Details of the created Language Sections:<ul>
	 	* <li>If the node contains a '=' then two Language Sections are created (one SOURCE and one TARGET)</li>
	 	* <li>Else one Language Sections is created (CONCEPT)</li></ul>
	 * @param node The node of the Terminological Entry in the Freeplane map.
	 * @param tovas The map node of Transitive OVerwritable Attributes. Keys of the map entries are attribute names.
	 * @see tmf.Iu
	 * @see tmf.Ls
	 */
	def populate(Proxy.Node node, Map<String,String> tovas) {
		if (this.id) {
			sasForTe.put("entryIdentifier", this.id)
		}
		tovasForTe = tovas.clone()
		tovaNamesForTe.addAll(["customerSubset", "projectSubset"])
		
		if (node.plainText.contains("=")) {
				// The Terminological Entry is bilingual
				LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\" is bilingual.')
				tovaNamesForTe.addAll(["sourceLanguage","targetLanguage"])
				def sourceLs = new Ls(node.id+"_"+Constants.LsMode.SOURCE.toString())
				sourceLs.populate(node, tovasForTe, Constants.LsMode.SOURCE)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", adding the Language Section: '+Constants.LsMode.SOURCE.toString())
				this.add(sourceLs)
				def targetLs = new Ls(node.id+"_"+Constants.LsMode.TARGET.toString())
				targetLs.populate(node, tovasForTe, Constants.LsMode.TARGET)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", adding the Language Section: '+Constants.LsMode.TARGET.toString())
				this.add(targetLs)
		}
		else {
			// The Terminological Entry is conceptual
			LogUtils.info(this.class.name+'	The Terminological Entry \"'+node.plainText+'\" is conceptual.')
			if (node.details) {
				sasForTe.put("definition", node.details.to.plain)
			}
			if (node.note) {
				sasForTe.put("context", node.note.to.plain)
			}
			def ls = new Ls(node.id+"_"+Constants.LsMode.CONCEPT.toString())
			ls.populate(node, tovasForTe, Constants.LsMode.CONCEPT)
			LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", adding the Language Section: '+Constants.LsMode.CONCEPT.toString())
			this.add(ls)
		}
		// Convert the applicable attributes into Information Units
		tovasForTe.each{ key, value->
			if (tovaNamesForTe.contains(key)) {
				def iu = new Iu(key,"string", value)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", adding the Information Unit: '+key+', string, '+value)
				this.add(iu)
			}
		}
		sasForTe.each{ key, value->
				def iu = new Iu(key,"string", value)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", adding the Information Unit: '+key+', string, '+value)
				this.add(iu)
		}
	}
}
