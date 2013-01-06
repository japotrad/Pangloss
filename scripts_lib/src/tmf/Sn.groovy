package tmf

import groovy.xml.MarkupBuilder

import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;
import java.util.List
import java.util.Map
import java.util.HashMap
import java.util.UUID
/**
 * A Structural Node is the parent class of all the structural nodes (i.e. XML nodes of the GMT format), namely
 * <code>Ci</code>, <code>Gis</code>, <code>Ls</code>, <code>Tdc</code>, <code>Tcs</code>, <code>Te</code> and <code>Ts</code> classes.
 * It is defined in ISO 16642.
 * Note: This class should be considered abstract. No <code>Sn</code> object should be created.
 *  @see tmf.Ci
 *  @see tmf.Gis
 *  @see tmf.Ls
 *  @see tmf.Tdc
 *  @see tmf.Tcs
 *  @see tmf.Te
 *  @see tmf.Ts
 *   
 * @author Stephane Aubry
 */
class Sn {
	/**
	 * <code>id</code> is the identifier of the object.
	 * It is used for links between <code>Sn</code> objects.
	 */
	protected String id
	/**
	 * <code>informationUnits</code> is the content of the object.
	 * e.g. a definition attached to a Terminological Entry.
	 * An instance of an Information Unit is attached to one and only one Structural Node.
	 * @see tmf.Iu
	 */
	protected List<Iu> informationUnits = new ArrayList<Iu>()
	
	/**
	 * <code>children</code> is the list of Structural Nodes which depend upon the current object. Keys of the map entries are ids of the Structural Nodes objects.
	*/
	protected  Map<String,Sn> children = new HashMap<String,Sn>()
	
	/**
	 * Tests whether two Structural Nodes are equal.
	 * @param obj The equality operand
	 * @return True if the two Structural Nodes are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
	
		if (obj == null || obj.class.name != this.class.name) {
			return false
		}

		Sn sn = (Sn) obj
		boolean mainComparison =(id == sn.id || (id != null && id.equals(sn.id)))
		
		if ((informationUnits.size()==0) && (sn.informationUnits.size()==0) && (children.size()==0) && (sn.children.size()==0)) {
			return mainComparison
		}
		if ( mainComparison == false || informationUnits.size()!=sn.informationUnits.size() || children.size()!=sn.children.size() ) {
			return false
		}
		// The order of the Information Units is not significant.
		informationUnits.each{
			// The contain method calls the equals method of tmf.Iu
			if (sn.informationUnits.contains(it)==false) {
				mainComparison = false
			}
		}
		if ( mainComparison == false) {
			return false
		}	
		children.each{k, v-> 
			// The contain method calls the equals method recursively.
			if (sn.children.containsKey(k)==false) {
				mainComparison = false
			}
			if (sn.children[k]!=v) {
				mainComparison = false
			}
		}
		return mainComparison
	}
	
	/**
	 * Adds an Information Unit as content of the current object.
	 * @param iu The content Information Unit object
	 * @see tmf.Iu
	 */
	def add(Iu iu) {
		informationUnits.add(iu)
	}

	/**
	 * Adds a child Structural Node to the current object.
	 * A child is one level lower in the dependency hierarchy.
	 * @param child The child Structural Node object
	 */
	def add(Sn child) {
		children.put(child.id, child)
	}
	/**
	 * Creates a Structural Node object.
	 * @return The new <code>Sn</code> object
	 */
	Sn(){
		// Generate random UUIDs
		id = UUID.randomUUID() as String;
	}
	/**
	 * Creates a Structural Node object and assign the given parameter to its <code>id</code>.
	 * @param id The <code>id</code> of the Structural Node object to be created.
	 * @return The new <code>Sn</code> object
	 */
	Sn(String id){
		this.id = id
	}
}
