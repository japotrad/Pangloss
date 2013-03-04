package tmf

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

import pangloss.tmf.Ci;
import pangloss.tmf.Gis;
import pangloss.tmf.Tdc;
import pangloss.tmf.Te;

public class TdcFactory {
	public static ID = "TDC_ID"
	public static Gis GLOBALINFORMATIONSECTION = new Gis()
	public static Ci COMPLEMENTARYINFORMATION = new Ci()
	public static Map<String,Te> TERMINOLOGICALENTRIES =  new HashMap<String,Te>()
	public static Map<String,String> TOVASFORTDC = new HashMap<String,String>()
	public static List<String> TOVANAMES= new ArrayList()

	public static Tdc getTestTdc(){
		Tdc tdc = new Tdc(ID)
		tdc.globalInformationSection = GLOBALINFORMATIONSECTION
		tdc.complementaryInformation=COMPLEMENTARYINFORMATION
		tdc.children=TERMINOLOGICALENTRIES
		tdc.tovasForTdc=TOVASFORTDC
		tdc.tovaNames=TOVANAMES
		return tdc
	}
	public static void resetTestTdc(){
		ID = "TDC_ID"
		GLOBALINFORMATIONSECTION = new Gis()
		COMPLEMENTARYINFORMATION = new Ci()
		TERMINOLOGICALENTRIES =  new HashMap<String,Te>()
		TOVASFORTDC = new HashMap<String,String>()
		TOVANAMES= new ArrayList()
	}
	
}
	
class TestTdc {
	private Tdc tdc
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tdc = TdcFactory.getTestTdc()
		XMLUnit.setIgnoreWhitespace(true)
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddTe() {
		tdc.add(TeFactory.getTestTe())
		assertEquals 1, tdc.children.size()
		assertEquals true, tdc.children.containsKey(TeFactory.ID)
		assertEquals "tmf.Te", tdc.children[TeFactory.ID].class.name
		
	}
	
	@Test
	public final void setFilename() {
		tdc.setFilename("tutu")
		assertEquals "tutu", TdcFactory.GLOBALINFORMATIONSECTION.filename
	}

	@Test
	public final void testPopulate() {
		Proxy.NodeStyleRO styleMock = createMock(Proxy.NodeStyleRO.class)
		expect(styleMock.getName()).andReturn("other").anyTimes() // This value prevents from recursing. Recursion shall be tested at a functional level.
		replay(styleMock)
		
		Proxy.AttributesRO attributesMock = createMock(Proxy.AttributesRO.class)
		expect(attributesMock.size()).andReturn(7).anyTimes()
		expect(attributesMock.getNames()).andReturn(["dummyName","customerSubset","languageID","objectLanguage", "projectSubset", "sourceLanguage", "targetLanguage"]).anyTimes()
		expect(attributesMock.getValues()).andReturn([new Convertible("dummyValue"),new Convertible("my customer"),new Convertible("en-US"),new Convertible("en-GB"), new Convertible("my project"), new Convertible("en"), new Convertible("fr")]).anyTimes()
		replay(attributesMock)
		
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(TdcFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("My termbase").anyTimes()
		expect(nodeMock.getStyle()).andReturn(styleMock as Proxy.NodeStyle).anyTimes()
		expect(nodeMock.getChildren()).andReturn(new ArrayList() as java.util.List<Proxy.Node>).anyTimes() // No child node
		expect(nodeMock.getAttributes()).andReturn(attributesMock as Proxy.Attributes).anyTimes()
		replay(nodeMock)
		
		tdc.populate(nodeMock as Proxy.Node)
		assertEquals 6, TdcFactory.TOVASFORTDC.size()
		assertEquals false, TdcFactory.TOVASFORTDC.containsKey("dummyName")
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("customerSubset")
		assertEquals "my customer", TdcFactory.TOVASFORTDC["customerSubset"]
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("languageID")
		assertEquals "en-US", TdcFactory.TOVASFORTDC["languageID"]
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("objectLanguage")
		assertEquals "en-GB", TdcFactory.TOVASFORTDC["objectLanguage"]
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("projectSubset")
		assertEquals "my project", TdcFactory.TOVASFORTDC["projectSubset"]
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("sourceLanguage")
		assertEquals "en", TdcFactory.TOVASFORTDC["sourceLanguage"]
		assertEquals true, TdcFactory.TOVASFORTDC.containsKey("targetLanguage")
		assertEquals "fr", TdcFactory.TOVASFORTDC["targetLanguage"]
	}
	@Test
	public final void testToGmt() {
		TdcFactory.resetTestTdc()
		tdc = TdcFactory.getTestTdc()
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		tdc.toGmt(xml)
		def xmlDiff = new Diff('<tmf><struct type="TDC" id="'+TdcFactory.ID+'" ><struct type="GIS"/></struct></tmf>', writer.toString())
		assert xmlDiff.similar()
		
		Tdc tdcTe = new Tdc()
		tdcTe = TdcFactory.getTestTdc()
		def writerTe = new StringWriter()
		def xmlTe = new MarkupBuilder(writerTe)
		// Add an Terminological Entry
		tdcTe.add(new Te())
		tdcTe.toGmt(xmlTe)
		println writerTe.toString()
		def xmlDiffTe = new Diff( "<tmf><struct type='TDC' id='"+TdcFactory.ID+"'><struct type='GIS'/><struct type='TE'/></struct></tmf>", writerTe.toString())
		assert xmlDiffTe.similar()
	}

}
