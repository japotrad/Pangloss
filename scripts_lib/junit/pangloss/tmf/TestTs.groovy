package pangloss.tmf

import static org.junit.Assert.*

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.Diff

import static org.easymock.EasyMock.*
import static org.easymock.IMocksControl.*
import org.freeplane.plugin.script.proxy.Proxy // Mocked
import org.freeplane.plugin.script.proxy.Convertible

import pangloss.tmf.Iu;
import pangloss.tmf.Ts;

public class TsFactory {
	public static final String ID = "12BPO"
	public static Ts getTestTs(){
		Ts ts = new Ts(ID)
		return ts;
	}
}
class TestTs {
	private Ts ts
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ts = TsFactory.getTestTs()
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testToGmt() {
	def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		ts.toGmt(xml)
		def xmlDiff = new Diff(writer.toString(), '<struct type="TS"/>')
		assert xmlDiff.similar()
	}

	@Test
	public final void testPopulateConcept() {
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getPlainText()).andReturn("car").anyTimes()
		replay(nodeMock)
		ts.populate(nodeMock as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project"], Constants.LsMode.CONCEPT)
		assertEquals 3, ts.informationUnits.size
		Iu iuTerm = new Iu("term","string","car")
		assertEquals true , ts.informationUnits.contains(iuTerm)
		Iu iuCustomer = new Iu("customerSubset","string","My customer")
		assertEquals true , ts.informationUnits.contains(iuCustomer)
		Iu iuProject = new Iu("projectSubset","string","The running project")
		assertEquals true , ts.informationUnits.contains(iuProject)
	}
	@Test
	public final void testPopulateSource() {
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getPlainText()).andReturn("car=voiture").anyTimes()
		expect(nodeMock.getNote()).andReturn(new Convertible("I have two cars.")).anyTimes()
		expect(nodeMock.getDetails()).andReturn(new Convertible("A car is an automotive vehicle.")).anyTimes()
		replay(nodeMock)
		ts.populate(nodeMock as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project"], Constants.LsMode.SOURCE)
		assertEquals 5, ts.informationUnits.size
		Iu iuTerm = new Iu("term","string","car")
		assertEquals true , ts.informationUnits.contains(iuTerm)
		Iu iuCustomer = new Iu("customerSubset","string","My customer")
		assertEquals true , ts.informationUnits.contains(iuCustomer)
		Iu iuProject = new Iu("projectSubset","string","The running project")
		assertEquals true , ts.informationUnits.contains(iuProject)
		Iu iuDefinition = new Iu("definition","string","A car is an automotive vehicle.")
		assertEquals true , ts.informationUnits.contains(iuDefinition)
		Iu iuContext = new Iu("context","string","I have two cars.")
		assertEquals true , ts.informationUnits.contains(iuContext)
	}
	@Test
	public final void testPopulateTarget() {
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getPlainText()).andReturn("car=voiture").anyTimes()
		replay(nodeMock)
		ts.populate(nodeMock as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project"], Constants.LsMode.TARGET)
		assertEquals 3, ts.informationUnits.size
		Iu iuTerm = new Iu("term","string","voiture")
		assertEquals true , ts.informationUnits.contains(iuTerm)
		Iu iuCustomer = new Iu("customerSubset","string","My customer")
		assertEquals true , ts.informationUnits.contains(iuCustomer)
		Iu iuProject = new Iu("projectSubset","string","The running project")
		assertEquals true , ts.informationUnits.contains(iuProject)
	}
}
