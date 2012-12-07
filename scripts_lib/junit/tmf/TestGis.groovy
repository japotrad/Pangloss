package tmf;

import static org.junit.Assert.*

import java.util.List;
import java.util.Map;

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.Diff
import org.custommonkey.xmlunit.XMLUnit

import static org.easymock.EasyMock.*
import static org.easymock.IMocksControl.*
import org.freeplane.plugin.script.proxy.Proxy // Mocked
import org.freeplane.plugin.script.proxy.Convertible

public class GisFactory {
	public static String ID = "GIS_ID"
	public static List<String> ATTRIBUTENAMESFORGIS= new ArrayList<String>()
	public static Map<String,Ls> ATTRIBUTESFORGIS = new HashMap<String,String>()
	public static String FILENAME = "FILENAME" 
	public static Gis getTestGis(){
		Gis gis = new Gis(ID)
		gis.attributeNamesForGis=ATTRIBUTENAMESFORGIS
		gis.attributesForGis=ATTRIBUTESFORGIS
		gis.filename=FILENAME
		return gis;
	}
	public static void resetTestGis(){
		ID = "GIS_ID"
		ATTRIBUTENAMESFORGIS= new ArrayList<String>()
		ATTRIBUTESFORGIS = new HashMap<String,String>()
		FILENAME = "FILENAME"
	}
}

class TestGis {
	private Gis gis
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		gis = GisFactory.getTestGis()
		XMLUnit.setIgnoreWhitespace(true)
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public final void testGetFilename() {
		assertEquals GisFactory.FILENAME, gis.getFilename()
	}
	
	@Test
	public final void testPopulateFromNode() {
		Proxy.AttributesRO attributesMock = createMock(Proxy.AttributesRO.class)
		expect(attributesMock.size()).andReturn(8).anyTimes()
		expect(attributesMock.getNames()).andReturn(["dummyName","customerSubset","fileIdentifier","languageID","objectLanguage", "projectSubset", "sourceLanguage", "targetLanguage"]).anyTimes()
		expect(attributesMock.getValues()).andReturn([new Convertible("dummyValue"),new Convertible("my customer"),new Convertible("Termbase"),new Convertible("en-US"),new Convertible("en-GB"), new Convertible("my project"), new Convertible("en"), new Convertible("fr")]).anyTimes()
		replay(attributesMock)
		
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(GisFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("My Glossary").anyTimes()
		expect(nodeMock.getAttributes()).andReturn(attributesMock as Proxy.Attributes).anyTimes()
		replay(nodeMock)
		
		gis.populate(nodeMock as Proxy.Node, [:]) // No Tova
		// Test attributesForGis
		assertEquals 7, gis.attributesForGis.size()
		assertEquals false, gis.attributesForGis.containsKey("dummyName")
		assertEquals true, gis.attributesForGis.containsKey("customerSubset")
		assertEquals "my customer", gis.attributesForGis["customerSubset"]
		assertEquals true, gis.attributesForGis.containsKey("fileIdentifier")
		assertEquals "Termbase", gis.attributesForGis["fileIdentifier"] // fileIdentifier attribute has precedence over node name
		assertEquals true, gis.attributesForGis.containsKey("languageID")
		assertEquals "en-US", gis.attributesForGis["languageID"]
		assertEquals true, gis.attributesForGis.containsKey("objectLanguage")
		assertEquals "en-GB", gis.attributesForGis["objectLanguage"]
		assertEquals true, gis.attributesForGis.containsKey("projectSubset")
		assertEquals "my project", gis.attributesForGis["projectSubset"]
		assertEquals true, gis.attributesForGis.containsKey("sourceLanguage")
		assertEquals "en", gis.attributesForGis["sourceLanguage"]
		assertEquals true, gis.attributesForGis.containsKey("targetLanguage")
		assertEquals "fr", gis.attributesForGis["targetLanguage"]
		
		// fileIdentifier attribute has precedence over node name
		assertEquals  "Termbase", gis.filename
		
		// Informations Units
		assertEquals 7, gis.informationUnits.size()
		assertEquals true , gis.informationUnits.contains(new Iu("customerSubset","string","my customer"))
		assertEquals true , gis.informationUnits.contains(new Iu("fileIdentifier", "string", "Termbase"))
		assertEquals true , gis.informationUnits.contains(new Iu("languageID", "string", "en-US"))
		assertEquals true , gis.informationUnits.contains(new Iu("objectLanguage", "string", "en-GB"))
		assertEquals true , gis.informationUnits.contains(new Iu("projectSubset", "string", "my project"))
		assertEquals true , gis.informationUnits.contains(new Iu("sourceLanguage", "string", "en"))
		assertEquals true , gis.informationUnits.contains(new Iu("targetLanguage", "string", "fr"))
	}
	
	@Test
	public final void testPopulateFromTova() {
		Proxy.AttributesRO attributesMock = createMock(Proxy.AttributesRO.class)
		expect(attributesMock.size()).andReturn(0).anyTimes()
		expect(attributesMock.getNames()).andReturn([]).anyTimes() // No attribute in the node
		expect(attributesMock.getValues()).andReturn([]).anyTimes()
		replay(attributesMock)
		
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(GisFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("My Glossary").anyTimes()
		expect(nodeMock.getAttributes()).andReturn(attributesMock as Proxy.Attributes).anyTimes()
		replay(nodeMock)
		
		GisFactory.resetTestGis()
		gis = GisFactory.getTestGis()
		
		gis.populate(nodeMock as Proxy.Node, ["dummyAttribute":"dummyValue","customerSubset":"my customer","fileIdentifier":"Termbase","languageID":"en-US","objectLanguage":"en-GB","projectSubset":"my project","sourceLanguage":"en","targetLanguage":"fr"])
		// AttributesForGis is a clone of the map above
		assertEquals 8, gis.attributesForGis.size()
		assertEquals true, gis.attributesForGis.containsKey("dummyAttribute")
		assertEquals true, gis.attributesForGis.containsKey("customerSubset")
		assertEquals "my customer", gis.attributesForGis["customerSubset"]
		assertEquals true, gis.attributesForGis.containsKey("fileIdentifier")
		assertEquals "Termbase", gis.attributesForGis["fileIdentifier"] // fileIdentifier attribute has precedence over node name
		assertEquals true, gis.attributesForGis.containsKey("languageID")
		assertEquals "en-US", gis.attributesForGis["languageID"]
		assertEquals true, gis.attributesForGis.containsKey("objectLanguage")
		assertEquals "en-GB", gis.attributesForGis["objectLanguage"]
		assertEquals true, gis.attributesForGis.containsKey("projectSubset")
		assertEquals "my project", gis.attributesForGis["projectSubset"]
		assertEquals true, gis.attributesForGis.containsKey("sourceLanguage")
		assertEquals "en", gis.attributesForGis["sourceLanguage"]
		assertEquals true, gis.attributesForGis.containsKey("targetLanguage")
		assertEquals "fr", gis.attributesForGis["targetLanguage"]
		
		// fileIdentifier attribute has precedence over node name
		assertEquals  "Termbase", gis.filename
		
		// Informations Units
		assertEquals 7, gis.informationUnits.size()
		assertEquals true , gis.informationUnits.contains(new Iu("customerSubset","string","my customer"))
		assertEquals true , gis.informationUnits.contains(new Iu("fileIdentifier", "string", "Termbase"))
		assertEquals true , gis.informationUnits.contains(new Iu("languageID", "string", "en-US"))
		assertEquals true , gis.informationUnits.contains(new Iu("objectLanguage", "string", "en-GB"))
		assertEquals true , gis.informationUnits.contains(new Iu("projectSubset", "string", "my project"))
		assertEquals true , gis.informationUnits.contains(new Iu("sourceLanguage", "string", "en"))
		assertEquals true , gis.informationUnits.contains(new Iu("targetLanguage", "string", "fr"))
	}
	
	@Test
	public final void testPopulateFilename() {
		Proxy.AttributesRO attributesMock = createMock(Proxy.AttributesRO.class)
		expect(attributesMock.size()).andReturn(0).anyTimes()
		expect(attributesMock.getNames()).andReturn([]).anyTimes() // No attribute in the node
		expect(attributesMock.getValues()).andReturn([]).anyTimes()
		replay(attributesMock)
		
		Proxy.NodeRO nodeMock = createMock(Proxy.NodeRO.class)
		expect(nodeMock.getId()).andReturn(GisFactory.ID).anyTimes()
		expect(nodeMock.getPlainText()).andReturn("My Glossary").anyTimes()
		expect(nodeMock.getAttributes()).andReturn(attributesMock as Proxy.Attributes).anyTimes()
		replay(nodeMock)
		
		GisFactory.resetTestGis()
		gis = GisFactory.getTestGis()
		
		gis.populate(nodeMock as Proxy.Node, [:])

		assertEquals true, gis.attributesForGis.containsKey("fileIdentifier")
		assertEquals "My Glossary", gis.attributesForGis["fileIdentifier"] // fileIdentifier attribute has precedence over node name
		assertEquals "My Glossary", gis.filename
		assertEquals 1, gis.informationUnits.size()
		assertEquals true , gis.informationUnits.contains(new Iu("fileIdentifier", "string", "My Glossary"))	
	}
	@Test
	public final void testToGmt() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		gis.toGmt(xml)
		def xmlDiff = new Diff(writer.toString(), '<struct type="GIS"/>')
		assert xmlDiff.similar()
		// Add an Information Unit
		Gis gisIu = new Gis()
		def writerIu = new StringWriter()
		def xmlIu = new MarkupBuilder(writerIu)
		gisIu.add(new Iu("fileIdentifier","string",GisFactory.FILENAME))
		gisIu.toGmt(xmlIu)
		def xmlDiffIu = new Diff(writerIu.toString(), "<struct type='GIS'><feat type='fileIdentifier'>"+GisFactory.FILENAME+"</feat></struct>")
		assert xmlDiff.similar()
	}


	

}
