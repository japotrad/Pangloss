package pangloss.tmf

import groovy.xml.MarkupBuilder;

/**
 * A <code>Rel</code> is a directional relationship between two Structural Nodes. It shall be stored in the source (=origin) Structural Node. 
 *  @see tmf.Sn
 *
 * @author Stephane Aubry
 */
class Rel {
	/**
	 * <code>semantic</code> indicates the semantic of the Relationship.
	 * It is typically based on the chapter A.6 "concept relation" of ISO 12620.
	 */
	protected String semantic
	
	/**
	 * <code>target</code> is the id of the target (=destination) Structural Node.
	 */
	protected String target
	@Override
	public boolean equals(Object obj) {
	
		if (obj == null || obj.class.name != this.class.name) {
			return false
		}

		Rel rel = (Rel) obj
		return ( (semantic == rel.semantic || (semantic != null && semantic.equals(rel.semantic)) ) && (target == rel.target || (target != null && target.equals(rel.target)) ) )
	}
	/**
	 * Creates a Relationship object and assign the given parameters to its <code>semantic</code> and <code>target</code>.
	 * @param semantic The <code>semantic</code> of the Relationship to be created.
	 * @param target The <code>id</code> of the target Structural Node.
	 * @return The new <code>Rel</code> object
	 */
	Rel(String semantic, String target){
		this.semantic = semantic
		this.target = target
	}
	/**
	 * Creates a Relationship object and assign the given parameters to its <code>semantic</code> and <code>target</code>.
	 * @param semantic The <code>semantic</code> of the Relationship to be created.
	 * @param target The target Structural Node.
	 * @return The new <code>Rel</code> object
	 */
	Rel(String semantic, Sn target){
		this.semantic = semantic
		this.target = target.id
	}
	/**
	 * Outputs the Relationship in an XML format.
	 * The format is named <code>GMT</code> (Generic Mapping Tool) and is specified in ISO 16642.
	 * The Information Unit is transformed into an XML element named <code>feat</code>.
	 * @param xml MarkupBuilder
	 */
	def toGmt(MarkupBuilder xml) {xml.feat(type:this.semantic, target:this.target )}
}
