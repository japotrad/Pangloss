package tmf

import static org.junit.Assert.*
import groovy.util.GroovyTestCase

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.Diff

class TestIu {

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
	public final void testIu() {
		Iu iu = new Iu("datacategory","string","value")
		assertEquals(iu.dataCategory,"datacategory")
		assertEquals(iu.type,"string")
		assertEquals(iu.value,"value")
	}

	@Test
	public final void testAdd() {
		Iu iu = new Iu("datacategory","string","value")
		assertEquals(iu.informationUnits.size(), 0)
		Iu refiningIu = new Iu("datacat","date","val")
		iu.add(refiningIu)
		assertEquals(iu.informationUnits.size(), 1)
		assertEquals(iu.informationUnits[0].class.name, "tmf.Iu")
		assertEquals(iu.informationUnits[0].dataCategory,"datacat")
		assertEquals(iu.informationUnits[0].type,"date")
		assertEquals(iu.informationUnits[0].value,"val")
		
		Iu refiningIu2 = new Iu("datacat2","date2","val2")
		iu.add(refiningIu2)
		assertEquals(iu.informationUnits.size(), 2)
		assertEquals(iu.informationUnits[0].class.name, "tmf.Iu")
		assertEquals(iu.informationUnits[0].dataCategory,"datacat")
		assertEquals(iu.informationUnits[0].type,"date")
		assertEquals(iu.informationUnits[0].value,"val")
		assertEquals(iu.informationUnits[1].class.name, "tmf.Iu")
		assertEquals(iu.informationUnits[1].dataCategory,"datacat2")
		assertEquals(iu.informationUnits[1].type,"date2")
		assertEquals(iu.informationUnits[1].value,"val2")
	}
	@Test
	public final void testEquals() {
		String stringNull = null
		Iu iu = new Iu("datacategory","string",stringNull)
		assertEquals true, iu.equals(new Iu("datacategory","string",stringNull))
		assertEquals false, iu.equals(new Iu("datacategory","string",''))
		assertEquals false, iu.equals(new Iu("datacategory","string","value"))
		iu.value=""
		assertEquals true, iu.equals(new Iu("datacategory","string",''))
		assertEquals false, iu.equals(new Iu("datacategory","string",stringNull))
		assertEquals false, iu.equals(new Iu("datacategory","string","value"))
		iu.value="value"
		assertEquals true, iu.equals(new Iu("datacategory","string","value"))
		assertEquals false, iu.equals(new Iu("datacategory","string",stringNull))
		assertEquals false, iu.equals(new Iu("datacategory","string",''))
		
		// Refining Information Units
		Iu refiningIu = new Iu("datacat","date","val")
		iu.add(refiningIu)
		Iu iu2 = new Iu("datacategory","string","value")
		assertEquals false, iu.equals(iu2)
		assertEquals false, iu2.equals(iu)
		iu2.add(refiningIu)
		assertEquals true, iu.equals(iu2)
		assertEquals true, iu2.equals(iu)
	}
	
	@Test
	public final void testToGmtMarkupBuilder() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		Iu iu = new Iu("datacategory","string","value")
		iu.toGmt(xml)
		def xmlDiff = new Diff(writer.toString(), '<feat type="datacategory">value</feat>')
		assert xmlDiff.similar()
	}

	@Test
	public final void testToGmtMarkupBuilderMap() {
		// The objectLanguage is not expressed in a given human language. It is a mere code. Thus, no xml:lang attribute shall be generated.
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		Iu iu = new Iu("objectLanguage","string","fr")
		iu.toGmt(xml,["term":"avion"])
		def xmlDiff = new Diff(writer.toString(), '<feat type="objectLanguage">fr</feat>')
		assert xmlDiff.similar()
		
		// A term is expressed in a given human language, documented as objectLanguage (the object is the term)
		def writerTerm = new StringWriter()
		def xmlTerm = new MarkupBuilder(writerTerm)
		Iu iuTerm = new Iu("term","string","avion") // plane
		iuTerm.toGmt(xmlTerm,["objectLanguage":"fr"])
		def xmlDiffTerm = new Diff(writerTerm.toString(), '<feat xml:lang="fr" type="term">avion</feat>')
		assert xmlDiffTerm.similar()
		
		// A definition is expressed in a given human language, documented as languageID (typically, the author's mother tongue)
		def writerDefinition = new StringWriter()
		def xmlDefinition = new MarkupBuilder(writerDefinition)
		Iu iuDefinition = new Iu("definition","string","An avion is a mean of transportation.")
		iuDefinition.toGmt(xmlDefinition,["languageID":"en-GB"])
		def xmlDiffDefinition = new Diff(writerDefinition.toString(), '<feat xml:lang="en-GB" type="definition">An avion is a mean of transportation.</feat>')
		assert xmlDiffDefinition.similar()
		
		// A term context is expressed in the same language as the term, i.e. objectLanguage.
		def writerContext1 = new StringWriter()
		def xmlContext1 = new MarkupBuilder(writerContext1)
		Iu iuContext1 = new Iu("context","string","Un avion s'envole.")
		iuContext1.toGmt(xmlContext1,["objectLanguage":"fr","term":"avion"])
		def xmlDiffContext1 = new Diff(writerContext1.toString(), '<feat xml:lang="fr" type="context">Un avion s\'envole.</feat>')
		assert xmlDiffContext1.similar()
		
		// A concept context is expressed in the author's mother tongue, i.e. languageID.
		// There is no term in its context map
		def writerContext2 = new StringWriter()
		def xmlContext2 = new MarkupBuilder(writerContext2)
		Iu iuContext2 = new Iu("context","string","Men succeeded in designing flying objects when they stopped imitating birds.")
		iuContext2.toGmt(xmlContext2,["languageID":"en-GB"])
		def xmlDiffContext2 = new Diff(writerContext2.toString(), '<feat xml:lang="en-GB" type="context">Men succeeded in designing flying objects when they stopped imitating birds.</feat>')
		assert xmlDiffContext2.similar()


		
	}

}
