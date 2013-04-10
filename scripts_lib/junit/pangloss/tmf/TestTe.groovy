package pangloss.tmf;

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


import org.freeplane.plugin.script.proxy.Proxy
import org.freeplane.plugin.script.proxy.Convertible
import org.freeplane.core.util.LogUtils

import pangloss.tmf.Iu
import pangloss.tmf.Ls
import pangloss.tmf.Te

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
		return te
	}
	// Closures for the nodes
	// Icons
	def static oneIcon = {-> 1}
	def static gohomeIcon = { iconName -> if(iconName=="gohome"){true}else{false}}
	def static otherIcon = { iconName -> if(iconName=="other"){true}else{false}}
	def static partitiveIcons = {->[size:oneIcon,contains:gohomeIcon] as Proxy.Icons}
	def static otherIcons = {->[size:oneIcon,contains:otherIcon] as Proxy.Icons}
	def static noIcon = {->[]}
	// Ids
	def static parentId = {->"ParentID"}
	def static nodeId = {->"NodeID"}
	def static childId1 = {->"ChildID_1"}
	def static childId2 = {->"ChildID_2"}
	def static childId3 = {->"ChildID_3"}
	// Styles
	def static conceptName = {->"Concept"}
	def static otherName = {->"Other"}
	def static conceptStyle = {->[getName:conceptName] as Proxy.NodeStyle}
	def static otherStyle = {->[getName:otherName] as Proxy.NodeStyle}
	// Details
	
	def static details={->new Convertible("A car is an automotive vehicle.")}
	// Note
	def static note={->new Convertible("I have two cars.")}
	// Node text
	def static conceptualPlainText = {->"car"}
	def static bilingualPlainText = {->"car=voiture"}
	def static nullPlainText = {->}
	def static voidPlainText = {->""}
	// Parent Nodes
	def static conceptParent = {->[getId:parentId,getStyle:conceptStyle] as Proxy.Node}
	def static otherParent = {->[getId:parentId,getStyle:otherStyle] as Proxy.Node}
	// Child Nodes
	def static conceptPartitiveChild = [getId:childId1, getIcons:partitiveIcons, getStyle:conceptStyle]
	def static otherChild = [getId:childId2, getIcons:partitiveIcons, getStyle:otherStyle]
	def static conceptGenericChild = {->[getId:childId3, getIcons:otherIcons, getStyle:conceptStyle]}
	def static oneGenericChild = {->[conceptGenericChild]}
	def static noChild = {->}
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
		assertEquals "pangloss.tmf.Ls", te.children[LsFactory.ID].class.name
	}

	@Test
	public final void testToGmtNothing() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		Te voidTe = new Te()
		voidTe.toGmt(xml)
		def xmlDiff = new Diff(writer.toString(), '<struct type="TE"/>')
		// LogUtils.info(this.class.name + "	"+writer.toString())
		assert xmlDiff.similar()
	}
	@Test
	public final void testToGmtIu() {
		Te teIu = new Te()
		def writerIu = new StringWriter()
		def xmlIu = new MarkupBuilder(writerIu)
		// Add an Information Unit
		teIu.add(new Iu("projectSubset","string","my project"))
		teIu.toGmt(xmlIu)
		def xmlDiffIu = new Diff(writerIu.toString(), "<struct type='TE'><feat type='projectSubset'>my project</feat></struct>")
		assert xmlDiffIu.similar()
	}
	@Test
	public final void testToGmtBilingual() {
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
		// println(writerBilingual.toString())
		// The source Language Section shall be output before the target Language Section
		def xmlDiffBilingual = new Diff(writerBilingual.toString(), '<struct type="TE"><struct type="LS" ><feat type="objectLanguage">en</feat></struct><struct type="LS" ><feat type="objectLanguage">fr</feat></struct></struct>')
		assert xmlDiffBilingual.similar()
	}
	@Test
	public final void testToGmtConceptual() {
		// Conceptual Term Entry
		Te t = new Te()
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		t.id="conceptual"
		Ls ls=LsFactory.getTestLs()
		ls.id=t.id+"_"+Constants.LsMode.CONCEPT.toString()
		ls.add(new Iu("objectLanguage","String","en"))
		t.add(ls)
		t.toGmt(xml)
		//println(writer.toString())

		def xmlDiff = new Diff(writer.toString(), '<struct type="TE"><struct type="LS" ><feat type="objectLanguage">en</feat></struct></struct>')
		assert xmlDiff.similar()
	}
	@Test
	public final void testToGmtRelationship() {
		Te t = new Te()
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		t.id="conceptual"
		Rel rel= new Rel("semantic","target")
		t.add(rel)
		t.toGmt(xml)
		// println(writer.toString())
		def xmlDiff = new Diff(writer.toString(), '<struct type="TE"><feat type="semantic" target="target"/></struct>')
		assert xmlDiff.similar()
	}

	@Test
	public final void testPopulateConcept() {
		def node = [getChildren:TeFactory.noChild, getDetails:TeFactory.details, getIcons:TeFactory.otherIcons, getId:TeFactory.nodeId,
		getNote:TeFactory.note, getParent:TeFactory.conceptParent, getPlainText:TeFactory.conceptualPlainText]
		Te voidTe = new Te(TeFactory.ID)
		voidTe.populate(node as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project"],"Transportation")
	
	// Language Sections
		assertEquals 1, voidTe.children.size()
		assertEquals true , voidTe.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.CONCEPT.toString())
		// Informations Units
		assertEquals 6, voidTe.informationUnits.size
		assertEquals true , voidTe.informationUnits.contains(new Iu("entryIdentifier","string",TeFactory.ID))
		assertEquals true , voidTe.informationUnits.contains(new Iu("customerSubset","string","My customer"))
		assertEquals true , voidTe.informationUnits.contains(new Iu("projectSubset","string","The running project"))
		assertEquals true , voidTe.informationUnits.contains(new Iu("definition","string","A car is an automotive vehicle."))
		assertEquals true , voidTe.informationUnits.contains(new Iu("context","string","I have two cars."))
		assertEquals true , voidTe.informationUnits.contains(new Iu("subjectField","string","Transportation"))
	}
	@Test
	public final void testPopulateBilingual() {
		def node = [getChildren:TeFactory.noChild, getDetails:TeFactory.details, getIcons:TeFactory.otherIcons, getId:TeFactory.nodeId,
		getNote:TeFactory.note, getParent:TeFactory.otherParent, getPlainText:TeFactory.bilingualPlainText]
		Te voidTe = new Te(TeFactory.ID)
		voidTe.populate(node as Proxy.Node, ["customerSubset":"My customer", "projectSubset":"The running project","sourceLanguage":"en","targetLanguage":"fr"],"")
	
		// Language Sections
		assertEquals 2, voidTe.children.size()
		assertEquals true , voidTe.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.SOURCE.toString())
		assertEquals true , voidTe.children.containsKey(TeFactory.ID+"_"+Constants.LsMode.TARGET.toString())
		// Informations Units
		assertEquals 5, voidTe.informationUnits.size
		Iu iuId = new Iu("entryIdentifier","string",TeFactory.ID)
		assertEquals true , voidTe.informationUnits.contains(new Iu("entryIdentifier","string",TeFactory.ID))
		assertEquals true , voidTe.informationUnits.contains(new Iu("customerSubset","string","My customer"))
		assertEquals true , voidTe.informationUnits.contains(new Iu("projectSubset","string","The running project"))
		assertEquals true , voidTe.informationUnits.contains(new Iu("sourceLanguage","string","en"))
		assertEquals true , voidTe.informationUnits.contains(new Iu("targetLanguage","string","fr"))
	}
	@Test
	public final void testPopulateGenericParent() {
		def node = [getChildren:TeFactory.noChild, getDetails:TeFactory.details, getIcons:TeFactory.otherIcons, getId:TeFactory.nodeId,
			getNote:TeFactory.note, getParent:TeFactory.conceptParent, getPlainText:TeFactory.conceptualPlainText]
		Te voidTe = new Te(TeFactory.ID)
		voidTe.populate(node as Proxy.Node, [:],"")
		
		assertEquals 1, voidTe.relationships.size()
		assertEquals true , voidTe.relationships[0].target=="ParentID"
		assertEquals true , voidTe.relationships[0].semantic=="superordinateConceptGeneric"
	}
	@Test
	public final void testPopulatePartitiveParent() {
		def node = [getChildren:TeFactory.noChild, getDetails:TeFactory.details, getIcons:TeFactory.partitiveIcons, getId:TeFactory.nodeId,
			getNote:TeFactory.note, getParent:TeFactory.conceptParent, getPlainText:TeFactory.conceptualPlainText]
		Te voidTe = new Te(TeFactory.ID)
		voidTe.populate(node as Proxy.Node, [:],"")
		
		assertEquals 1, voidTe.relationships.size()
		assertEquals true , voidTe.relationships[0].target=="ParentID"
		assertEquals true , voidTe.relationships[0].semantic=="superordinateConceptPartitive"
	}
	@Test
	public final void testPopulateNoChilld() {
		def node = [getChildren:TeFactory.noChild, getDetails:TeFactory.details, getIcons:TeFactory.otherIcons, getId:TeFactory.nodeId,
			getNote:TeFactory.note, getParent:TeFactory.conceptParent, getPlainText:TeFactory.conceptualPlainText]
		Te voidTe = new Te(TeFactory.ID)
		voidTe.populate(node as Proxy.Node, [:],"")
		
		assertEquals 1, voidTe.relationships.size()
		assertEquals true , voidTe.relationships[0].target=="ParentID" // Relation to the parent
	}
	@Test
	public final void testPopulateGenericChilld() {
		fail("I do not know how to test the each closure!")
		
	}

}
