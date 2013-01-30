package tmf;

import static org.junit.Assert.*
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import java.util.List
import java.util.Map

import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit

import static org.easymock.EasyMock.*
import static org.easymock.IMocksControl.*
import org.freeplane.plugin.script.proxy.Proxy // Mocked
import org.freeplane.plugin.script.proxy.Convertible

public class TeFactory {
	public static final String ID = "12BPOCE"
	public static final Map<String,Ls> LANGUAGESECTIONS = new HashMap<String,Ls>()
	public static final Map<String,String> SASFORTE = ["entryIdentifier":"BPOCE"]
	public static final Map<String,String> TOVASFORTE = ["projectSubset":"my project"]
	public static final List<String> TOVANAMESFORTE= ["projectSubset"]
	public static Te getTestTe(){
		Te te = new Te(ID)
		te.children = LANGUAGESECTIONS
		te.sasForTe=SASFORTE
		te.tovasForTe=TOVASFORTE
		te.tovaNamesForTe=TOVANAMESFORTE
		return te;
	}
}

class TestTe {
	private Te te
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		te = TeFactory.getTestTe()
		XMLUnit.setIgnoreWhitespace(true)
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddLs() {
		te.add(LsFactory.getTestLs())
		assertEquals 1, te.children.size()
		assertEquals true, te.children.containsKey(LsFactory.ID)
		assertEquals "tmf.Ls", te.children[LsFactory.ID].class.name
	}

	@Test
	public final void testToGmt() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		te.toGmt(xml)
		def xmlDiff = new Diff(writer.toString(), '<struct type="TE"><struct type="LS" /></struct>')
		assert xmlDiff.similar()
		// Add an Information Unit
		Te teIu = new Te()
		def writerIu = new StringWriter()
		def xmlIu = new MarkupBuilder(writerIu)
		teIu.add(new Iu("projectSubset","string","my project"))
		teIu.toGmt(xmlIu)
		def xmlDiffIu = new Diff(writerIu.toString(), "<struct type='TE'><feat type='projectSubset'>my project</feat></struct>")
		assert xmlDiffIu.similar()
		// Bilingual Term Entry
		Te teBilingual = new Te()
		def writerBilingual = new StringWriter()
		def xmlBilingual = new MarkupBuilder(writerBilingual)
		teBilingual.id="Bilingual"
		Ls lsSource=LsFactory.getTestLs()
		lsSource.id=teBilingual.id+"_"+Constants.LsMode.SOURCE.toString()
		lsSource.add(new Iu("objectLanguage","String","en"))
		teBilingual.add(lsSource) 
		Ls lsTarget=LsFactory.getTestLs()
		lsTarget.id=teBilingual.id+"_"+Constants.LsMode.TARGET.toString()
		lsTarget.add(new Iu("objectLanguage","String","fr"))
		teBilingual.add(lsTarget)
		teBilingual.toGmt(xmlBilingual)
		println(writerBilingual.toString())
		// The source Language Section shall be output before the target Language Section
		def xmlDiffBilingual = new Diff(writerBilingual.toString(), '<struct type="TE"><struct type="LS" ><feat type="objectLanguage">en</feat></struct><struct type="LS" ><feat type="objectLanguage">fr</feat></struct></struct>')
		assert xmlDiffBilingual.similar()
	}

	@Test
	public final void testPopulateConcept() {
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(TeFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("car").anyTimes()
		expect(nodeMock.getNote()).andReturn(new Convertible("I have two cars.")).anyTimes()
		expect(nodeMock.getDetails()).andReturn(new Convertible("A car is an automotive vehicle.")).anyTimes()
		replay(nodeMock)
		te.children = new HashMap<String,Ls>() // Reset the number of Language Sections
		te.populate(nodeMock as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project"],'Transportation')
		// Language Sections
		assertEquals 1, te.children.size()
		assertEquals true , te.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.CONCEPT.toString())
		// Informations Units
		assertEquals 6, te.informationUnits.size
		Iu iuId = new Iu("entryIdentifier","string",TeFactory.ID)
		assertEquals true , te.informationUnits.contains(iuId)
		Iu iuCustomer = new Iu("customerSubset","string","My customer")
		assertEquals true , te.informationUnits.contains(iuCustomer)
		Iu iuProject = new Iu("projectSubset","string","The running project")
		assertEquals true , te.informationUnits.contains(iuProject)
		Iu iuDefinition = new Iu("definition","string","A car is an automotive vehicle.")
		assertEquals true , te.informationUnits.contains(iuDefinition)
		Iu iuContext = new Iu("context","string","I have two cars.")
		assertEquals true , te.informationUnits.contains(iuContext)
		Iu iuSubjectField = new Iu("subjectField","string","Transportation")
		assertEquals true , te.informationUnits.contains(iuSubjectField)
	}
	
	@Test
	public final void testPopulateBilingual() {
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(TeFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("car=voiture").anyTimes()
		expect(nodeMock.getNote()).andReturn(new Convertible("I have two cars.")).anyTimes()
		expect(nodeMock.getDetails()).andReturn(new Convertible("A car is an automotive vehicle.")).anyTimes()
		replay(nodeMock)
		te.children = new HashMap<String,Ls>() // Reset the Language Sections
		te.sasForTe = ["entryIdentifier":"BPOCE"] // Reset the Standard Attributes
		te.populate(nodeMock as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project","sourceLanguage":"en","targetLanguage":"fr"], '')
		// Language Sections
		assertEquals 2, te.children.size()
		assertEquals true , te.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.SOURCE.toString())
		assertEquals true , te.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.TARGET.toString())
		// Informations Units
		assertEquals 5, te.informationUnits.size
		Iu iuId = new Iu("entryIdentifier","string",TeFactory.ID)
		assertEquals true , te.informationUnits.contains(iuId)
		Iu iuCustomer = new Iu("customerSubset","string","My customer")
		assertEquals true , te.informationUnits.contains(iuCustomer)
		Iu iuProject = new Iu("projectSubset","string","The running project")
		assertEquals true , te.informationUnits.contains(iuProject)
		assertEquals true , te.informationUnits.contains(new Iu("sourceLanguage","string","en"))
		assertEquals true , te.informationUnits.contains(new Iu("targetLanguage","string","fr"))
	}
}
