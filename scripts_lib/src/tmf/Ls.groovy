package tmf

import groovy.transform.InheritConstructors
import groovy.xml.MarkupBuilder

import java.util.List
import java.util.Map
import java.util.HashMap


import org.freeplane.core.util.LogUtils
import org.freeplane.plugin.script.proxy.Proxy

import static Constants

/**
 * A Language Section is the part of a Terminological Entry containing information related to one language.
 * It is described in ISO 16642.
 * Note: One Terminological Entry may contain information on one or two languages.
 * @author Stephane Aubry
 * @see tmf.Iu
 * @see tmf.Sn
 * @see tmf.Te
 * @see tmf.Ts
 * @see tmf.Tdc
 */
@InheritConstructors
class Ls extends Sn {
	/**
	 *<code>tovaNamesForLs</code> is the list of Transitive OVerwritable Attributes names applicable to the current Language Section object.
	 * It is a subset of <code>tovaNames</code> of <code>Tdc</code> class.
	 * @see tmf.Tdc#tovaNames
	 */
	private List<String> TovaNamesForLs= new ArrayList<String>()
	
	/**
	 * Outputs the Language Section in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Language Section is transformed into an XML element named <code>struct</code> with the XML attribute type = "LS".
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {
		xml.struct(type:"LS"){
			this.informationUnits.each { iu -> iu.toGmt(xml)}
			this.children.each { id, ts->ts.toGmt(xml)}
		}
	}
	/**
	 * Fills the Language Section with data read from the Freeplane node.
	 * Details of the created Information Units: <ul>
		* <li><code>objectLanguage</code><br/>
		* Note: if the parent Terminological Entry is bilingual, <code>objectLanguage</code> is set to either <code>sourceLanguage</code> or <code>targetLanguage</code> value.
		* </li></ul>
	 * Details of the created Term Sections: One Term Section is created.
	 * @param node The node of the Terminological Entry in the Freeplane map.
	 * @param tovas The map node of Transitive OVerwritable Attributes. Keys of the map entries are attribute names.
	 * @param lsMode The type of Language Section to be populated. See {@link tmf.Constants#LsMode}
	 * @see tmf.Iu
	 * @see tmf.Ls
	 */
	def populate(Proxy.Node node, Map<String,String> tovas, Constants.LsMode lsMode) {
		Map<String,String> tovasForLs = tovas.clone()
		TovaNamesForLs.addAll(["objectLanguage"])
		switch (lsMode) {
			case Constants.LsMode.SOURCE :
				if (tovasForLs.containsKey("sourceLanguage")) {
					tovasForLs.put("objectLanguage", tovasForLs["sourceLanguage"])
				}
				break
			case Constants.LsMode.TARGET:
				if (tovasForLs.containsKey("targetLanguage")) {
					tovasForLs.put("objectLanguage", tovasForLs["targetLanguage"])
				}
				break
			case Constants.LsMode.CONCEPT:
				break
			default:
				break
		}
		tovasForLs.each{ key, value->
			if (TovaNamesForLs.contains(key)) {
				def iu = new Iu(key,"string", value)
				LogUtils.info(this.class.name+'	In Terminological Entry \"' + node.plainText + '\", Language Section '+  lsMode.toString()+', adding the Information Unit: '+key+', string, '+value)
				this.add(iu)
			}
		}
		def ts = new Ts(node.id+"_"+lsMode.toString()+"_MainTerm")
		ts.populate(node, tovasForLs, lsMode)
		this.add(ts)
	}
}

