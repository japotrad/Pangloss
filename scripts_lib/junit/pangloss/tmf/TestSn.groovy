package pangloss.tmf

import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import pangloss.tmf.Iu;
import pangloss.tmf.Rel;
import pangloss.tmf.Sn;

import static org.hamcrest.CoreMatchers.*


class TestSn {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSn() {
		Sn sn = new Sn()
		assertEquals "java.lang.String", sn.id.class.name
		assertThat sn.id.length(), not(0)
	}

	@Test
	public final void testSnString() {
		Sn sn = new Sn("auie,ctsrn")
		assertEquals "java.lang.String", sn.id.class.name
		assertEquals "auie,ctsrn", sn.id
	}
	@Test
	public final void testAddRel() {
		Rel rel = new Rel("semantic","target")
		Sn sn = new Sn("auie,ctsrn")
		
		sn.add(rel)
		assertEquals 1, sn.relationships.size()
		assertEquals "pangloss.tmf.Rel", sn.relationships[0].class.name
		assertEquals "semantic", sn.relationships[0].semantic
		assertEquals "target", sn.relationships[0].target
		
		Rel rel2 = new Rel("semantic2","target2")
		sn.add(rel2)
		assertEquals 2, sn.relationships.size()
		assertEquals "pangloss.tmf.Rel", sn.relationships[0].class.name
		assertEquals "semantic", sn.relationships[0].semantic
		assertEquals "target", sn.relationships[0].target
		
		assertEquals "pangloss.tmf.Rel", sn.relationships[1].class.name
		assertEquals "semantic2", sn.relationships[1].semantic
		assertEquals "target2", sn.relationships[1].target
		}
	@Test
	public final void testAddIu() {
		Iu iu = new Iu("datacategory","string","value")
		Sn sn = new Sn("auie,ctsrn")
		
		sn.add(iu)
		assertEquals 1, sn.informationUnits.size()
		assertEquals "pangloss.tmf.Iu", sn.informationUnits[0].class.name
		assertEquals "datacategory", sn.informationUnits[0].dataCategory
		assertEquals "string", sn.informationUnits[0].type
		assertEquals "value", sn.informationUnits[0].value
		
		Iu iu2 = new Iu("datacat2","date2","val2")
		sn.add(iu2)
		assertEquals 2, sn.informationUnits.size()
		assertEquals "pangloss.tmf.Iu", sn.informationUnits[0].class.name
		assertEquals "datacategory", sn.informationUnits[0].dataCategory
		assertEquals "string", sn.informationUnits[0].type
		assertEquals "value", sn.informationUnits[0].value
		
		assertEquals "pangloss.tmf.Iu", sn.informationUnits[1].class.name
		assertEquals "datacat2", sn.informationUnits[1].dataCategory
		assertEquals "date2", sn.informationUnits[1].type
		assertEquals "val2", sn.informationUnits[1].value
	}

	@Test
	public final void testAddSn() {
		Sn sn = new Sn("auie,ctsrn")
		Sn child = new Sn("child")
		sn.add(child)
		assertEquals 1, sn.children.size() 
		assertEquals "pangloss.tmf.Sn", sn.children["child"].class.name
		assertEquals child, sn.children["child"]
		
		Sn child2 = new Sn("child2")
		sn.add(child2)
		assertEquals 2, sn.children.size()
		assertEquals "pangloss.tmf.Sn", sn.children["child2"].class.name
		assertEquals child2, sn.children["child2"]
	}
	@Test
	public final void testEqualsID() {
		String stringNull = null
		Sn sn = new Sn("id")
		assertEquals true, sn.equals(new Sn("id"))
		assertEquals false, sn.equals(new Sn("id2"))
		assertEquals false, sn.equals(new Sn())
		
		Sn snNull = new Sn("")
		assertEquals true, snNull.equals(new Sn(""))
		assertEquals false, snNull.equals(new Sn("id"))
		assertEquals false, snNull.equals(new Sn())
	}
	
	@Test
	public final void testEqualsRel() {
		Sn sn = new Sn("id")
		Rel rel = new Rel("semantic","target")
		sn.add(rel)
		Sn sn2 = new Sn("id")
		Rel rel2 = new Rel("semantic2","target2")
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(rel2)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(rel)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn.add(rel2)
		assertEquals true, sn.equals(sn2)
		assertEquals true, sn2.equals(sn)
	}
	
	@Test
	public final void testEqualsIu() {
		Sn sn = new Sn("id")
		Iu iu = new Iu("datacat","date","val")
		sn.add(iu)
		Sn sn2 = new Sn("id")
		Iu iu2 = new Iu("datacategory","string","value")
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(iu2)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(iu)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn.add(iu2)
		assertEquals true, sn.equals(sn2)
		assertEquals true, sn2.equals(sn)
	}
	
	@Test
	public final void testEqualsChildSn() {
		Sn sn = new Sn("id")
		Sn child = new Sn("child")
		sn.add(child)
		Sn sn2 = new Sn("id")
		Sn child2 = new Sn("child2")
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(child2)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn2.add(child)
		assertEquals false, sn.equals(sn2)
		assertEquals false, sn2.equals(sn)
		
		sn.add(child2)
		assertEquals true, sn.equals(sn2)
		assertEquals true, sn2.equals(sn)
	}

}
