package tmf

import static org.junit.Assert.*

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import pangloss.tmf.Rel;
import pangloss.tmf.Sn;

class TestRel {

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
	public final void testRelString() {
		Rel rel = new Rel("auie","ctsrn")
		assertEquals "auie", rel.semantic
		assertEquals "ctsrn", rel.target
	}
	
	@Test
	public final void testRelSn() {
		Rel rel = new Rel("auie", new Sn("ctsrn"))
		assertEquals "auie", rel.semantic
		assertEquals "ctsrn", rel.target
	}
	
	@Test
	public final void testEqualsSemantic() {
		String stringNull = null
		Rel rel = new Rel("auie","ctsrn")
		assertEquals true, rel.equals(new Rel("auie","ctsrn"))
		assertEquals true, rel.equals(new Rel("auie", new Sn("ctsrn")))
		assertEquals false, rel.equals(new Rel("auie","ctsr"))
		assertEquals false, rel.equals(new Rel("aie","ctsrn"))
	}

}
