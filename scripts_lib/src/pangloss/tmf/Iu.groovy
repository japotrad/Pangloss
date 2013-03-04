package pangloss.tmf

import java.util.List
import java.util.Map
import groovy.xml.MarkupBuilder
import org.freeplane.core.util.LogUtils
/**
 * An Information Unit object stores a piece of information of a Structural Node.
 * Its semantic is similar to an attribute of a Freeplane map node.
 * It is defined in ISO 16642.
 * @author Stephane Aubry
 * @see tmf.Sn
 */
class Iu {
	/** 
	* <code>dataCategory</code> semantic is similar to the attribute name of a Freeplane map node.
	* The main standard regarding the meaning of Data Categories is ISO 12620.
	* Concerning the string value representing a Data Category, the homepage http://www.isocat.org	is inspiring.
	*/	
	private String dataCategory
	/**
	 * <code>type</code> comes from XML Schemas part 2: Datatypes
	 * It depends on the Data Category.
	 * Note: Currently, the MIXED type is not supported.
	 */
	private String type
	/**
	 * <code>value</code> is the actual content of the Information Unit.
	 */
	private String value
	/**
	 * <code>informationUnits</code> contains the list of Information Units which refines the current object.
	 * For example: An Information Unit having Data Category = 'usage note' may refine an Information Unit having Data Category = 'term'.
	 */
	private List<Iu> informationUnits = new ArrayList<Iu>()
	/**
	 * Tests whether two Information Units are equal.
	 * @param obj The equality operand
	 * @return True if the two Information Units are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
	
		if (obj == null || obj.class.name != this.class.name) {
			return false
		}

		Iu iu = (Iu) obj
		boolean mainComparison =( (dataCategory == iu.dataCategory || (dataCategory != null && dataCategory.equals(iu.dataCategory)))
				&& ( type == iu.type || (type != null && type.equals(iu.type)))
				&& (value == iu.value || (value != null && value.equals(iu.value))) )
		if (informationUnits.size()==0 && iu.informationUnits.size()==0) {
			return mainComparison
		}
		if ( mainComparison == false || informationUnits.size()!=iu.informationUnits.size()) {
			return false
		}
		// The order of the refining informationUnits is not significant.
		informationUnits.each{
			// The contain method calls the equals method recursively.
			if (iu.informationUnits.contains(it)==false) {
				mainComparison = false
			}
		}
		return mainComparison
	}

	/**
	 * Adds a Information Unit which refines the current object.
	 * @param iu The refining Information Unit object 
	 */
	def add(Iu iu) {
		informationUnits.add(iu)
	}
	/**
	 * Constructs an Information Unit and sets its 3 members.
	 * @param dataCategory The Data Category, as in ISO 12620
	 * @param type The value type, as in XML Schemas part 2: Datatypes
	 * @param value The content value
	 */
	def Iu(String dataCategory,String type,String value) {
		this.dataCategory=dataCategory
		this.type=type
		this.value=value
	}
	/**
	 * Outputs the Information Unit in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Information Unit is transformed into an XML element named <code>feat</code>.
	 * The <code>xml:lang</code> attribute of the <code>feat</code> element is not handled.
	 * Thus, this method should NOT be used if the value of the Information Unit is expressed in a human language.
	 * This {@link #toGmt(groovy.xml.MarkupBuilder, java.util.Map) method} should be used instead.
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {xml.feat(type:this.dataCategory, this.value)}
	/**
	 * Outputs the Information Unit in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Information Unit is transformed into an XML element named <code>feat</code>.
	 * The <code>xml:lang</code> attribute is inserted in the <code>feat</code> element if necessary.
	 * This method should be used if the value of the Information Unit is expressed in a human language.
	 * For example, values of the <code>partOfSpeech</code> are usually expressed in English (<code>adjective</code>, <code>noun</code>, <code>verb</code>, etc).
	 * Thus, this method should be used to produce the corresponding <code>feat</code> XML element.
	 * @param xml MarkupBuilder
	 * @param attributes Map of contextual attributes. This context is used to set the <code>xml:lang</code> attribute.
	 *   String <code>Iu.dataCategory</code> as key. String <code>Iu.value</code> as value.
	 */
	def toGmt(MarkupBuilder xml, Map attributes) {
	
		if ((this.dataCategory=="term" ) && attributes.containsKey("objectLanguage")){
			xml.feat('xml:lang':attributes["objectLanguage"], type:this.dataCategory) {mkp.yield this.value}
		}
		else if (this.dataCategory=="definition" && attributes.containsKey("languageID")){
			xml.feat('xml:lang':attributes["languageID"], type:this.dataCategory) {mkp.yield this.value}
		}
		else if (this.dataCategory=="context"){
			if (attributes.containsKey("objectLanguage") && attributes.containsKey("term")){
				xml.feat('xml:lang':attributes["objectLanguage"], type:this.dataCategory) {mkp.yield this.value}
			}
			else if (attributes.containsKey("languageID")){
				xml.feat('xml:lang':attributes["languageID"], type:this.dataCategory) {mkp.yield this.value}
			}
		}
		else {
			xml.feat(type:this.dataCategory, this.value)
		}
	}
}
