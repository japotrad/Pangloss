<map version="freeplane 1.2.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<node ID="ID_1723255651" CREATED="1283093380553" MODIFIED="1350741909495" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1"><richcontent TYPE="NODE">

<html>
  <head>
    
  </head>
  <body>
    <p style="text-align: center">
      Pangloss
    </p>
    <p style="text-align: center">
      User Manual
    </p>
  </body>
</html>
</richcontent>
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
<hook NAME="MapStyle">
    <properties show_note_icons="true"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<stylenode LOCALIZED_TEXT="default" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.note" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.floating" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="Dialog" SIZE="10" BOLD="true" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="Dialog" SIZE="10" BOLD="true" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="Dialog" SIZE="10" BOLD="true" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<icon BUILTIN="yes"/>
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode TEXT="Standard Attribute" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
</stylenode>
<stylenode TEXT="Transitive OVerwritable Attribute" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
</stylenode>
<stylenode TEXT="Entry" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<edge STYLE="bezier" COLOR="#808080" WIDTH="thin"/>
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="18" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="16" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="14" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="12" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<hook NAME="AutomaticEdgeColor" COUNTER="2"/>
<node TEXT="Attributes" POSITION="right" ID="ID_1776594958" CREATED="1350740702512" MODIFIED="1352131562732" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      There are 3 types of attributes:
    </p>
    <ul>
      <li>
        <font color="#000000"><b>Standard Attribute</b>:</font>&#160;It is valid only in the very node where it is defined.
      </li>
      <li>
        <font color="#009900"><b>Transitive OVerwritable Attribute</b></font>:&#160;It is valid (along with its&#160;value) on the whole branch starting from current node, unless it is overridden in a descendant node.
      </li>
      <li>
        <font color="#003399"><b>Transitive Hierarchical Attribute</b></font>:&#160;It is valid (along with its&#160;value) on the whole branch starting from current node, unless it is refined (specialized) in a descendant node.
      </li>
    </ul>
    <p>
      Basically, in a given node, an attribute has at most one value.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="Term" ID="ID_1679000004" CREATED="1350741000885" MODIFIED="1350741016190" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<node TEXT="term" STYLE_REF="Standard Attribute" ID="ID_974946012" CREATED="1350745227905" MODIFIED="1352131971488" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A verbal designation
    </p>
    <p>
      of a general concept
    </p>
    <p>
      in a specific subject field.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Terms can consist of single words or be composed of multiword strings. The distinguishing characteristic of a term is that it is assigned to a single concept, as opposed to a phraseological unit, which combines more than one concept in a lexicalized fashion to express complex situations. <i>Quality assurance</i>&#160;system is a term, whereas <i>satisfy quality requirements</i>&#160;is a phraseological unit, specifically a collocation.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="Term-related Information" ID="ID_1139164945" CREATED="1350741029091" MODIFIED="1350741062394" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Equivalence" ID="ID_623018934" CREATED="1350741140426" MODIFIED="1350741165016" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Subject Field" ID="ID_1373462221" CREATED="1350741097338" MODIFIED="1350741136239" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Concept-related Description" ID="ID_1176194341" CREATED="1350745548105" MODIFIED="1350745596741" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<node TEXT="context" STYLE_REF="Standard Attribute" ID="ID_1137132553" CREATED="1350749532029" MODIFIED="1352131952713" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A text which illustrates a concept
    </p>
    <p>
      or the use of a designation.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="definition" STYLE_REF="Standard Attribute" ID="ID_979996589" CREATED="1350745562068" MODIFIED="1352131960080" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A representation of a concept by
    </p>
    <p>
      a descriptive statement which serves
    </p>
    <p>
      to differentiate it from related concepts.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="Concept Relation" ID="ID_1625457046" CREATED="1350745599444" MODIFIED="1350745620755" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Conceptual Structures" ID="ID_207312219" CREATED="1350745622965" MODIFIED="1350745643265" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Note" ID="ID_395355943" CREATED="1350741070405" MODIFIED="1350741078539" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Documentary Language" ID="ID_1671673746" CREATED="1350740738423" MODIFIED="1350740994590" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
</node>
<node TEXT="Administrative Information" ID="ID_1373462397" CREATED="1350740807133" MODIFIED="1350740889831" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<node TEXT="customerSubset" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_1932797254" CREATED="1350746066384" MODIFIED="1352131935859" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An identifier assigned to a terminological record
    </p>
    <p>
      indicating that it is associated with a specific customer.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="entryIdentifier" ID="ID_89107168" CREATED="1350746617586" MODIFIED="1352131412713" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An alphanumeric string that
    </p>
    <p>
      serves as the unique identifier
    </p>
    <p>
      of a language resource entry.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      By default, the ID of the map node is used.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="fileIdentifier" STYLE_REF="Standard Attribute" ID="ID_193286151" CREATED="1350746854295" MODIFIED="1352132021424" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A code that serves as the unique
    </p>
    <p>
      identifier of a file in a terminology
    </p>
    <p>
      database management system.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      By default, the ID of the root node of the termbase is used.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="languageID" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_246551296" CREATED="1350834651495" MODIFIED="1366209930531" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Identifier of the language that is included
    </p>
    <p>
      in the resource or supported by the tool/service.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Set your working (native) language in languageID.<br/>Note: The languageID of the terms is automatically computed from<br/>objectLanguage, sourceLanguage and targetLanguage.<br/>
    </p>
    <p>
      <br/>
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="objectLanguage" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_365414323" CREATED="1350834325474" MODIFIED="1352132030975" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Indicator of the language for which information is provided in a term entry.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      For the expert: Default language used when naming a concept.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="projectSubset" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_1743130890" CREATED="1350747157611" MODIFIED="1352131925209" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An identifier assigned to a specific
    </p>
    <p>
      project indicating that it is associated
    </p>
    <p>
      with a term, record or entry.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="sourceLanguage" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_1997619376" CREATED="1350833258661" MODIFIED="1352131982496" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      In a translation-oriented language resource or
    </p>
    <p>
      terminology database, the language that is taken
    </p>
    <p>
      as the language in which the original text is written.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      For the translator.
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="targetLanguage" STYLE_REF="Transitive OVerwritable Attribute" ID="ID_1917149951" CREATED="1350833173146" MODIFIED="1352131986968" COLOR="#009900" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="true" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      In a translation-oriented terminology database,
    </p>
    <p>
      the language into which the original text is translated.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      For the translator.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
<node TEXT="Use cases" POSITION="left" ID="ID_1868000726" CREATED="1350741284402" MODIFIED="1350742536478" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#0000ff" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Pangloss is an freeplane add-on.
    </p>
    <p>
      It makes the input of concepts and terms easy.
    </p>
    <p>
      It targets two main audiences: experts and translators
    </p>
  </body>
</html>
</richcontent>
<node TEXT="Expert paradigm =&gt; Conceptual Entry" ID="ID_441034355" CREATED="1350742542227" MODIFIED="1350744432151" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#0000ff" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      The expert of a subject field masters the concepts.
    </p>
    <p>
      He indicates what are the proper terms to be used.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A Conceptual Entry may be monolingual, bilingual or multilingual.
    </p>
    <p>
      Anyway, the focus is on the concept itself.<br/>
    </p>
  </body>
</html>
</richcontent>
<hook NAME="FirstGroupNode"/>
</node>
<node TEXT="Translator paradigm =&gt; Bilingual Entry" ID="ID_939474742" CREATED="1350742570321" MODIFIED="1350744397259" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#0000ff" WIDTH="3"/>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      The translator encounters a term he doesn't know.
    </p>
    <p>
      He finds an equivalent term in another language.
    </p>
  </body>
</html>
</richcontent>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      A Bilingual Entry may contain variants (e.g. Australian English).
    </p>
    <p>
      The focus is on the term of the source language<br/>(it is the term the translator has to learn and understand).
    </p>
  </body>
</html>
</richcontent>
</node>
<node TEXT="You can mix both Conceptual and&#xa;Bilingual Entries in a map." ID="ID_1944763008" CREATED="1350744432147" MODIFIED="1350744652481" COLOR="#000000" STYLE="as_parent" MAX_WIDTH="600" MIN_WIDTH="1">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
<edge STYLE="bezier" COLOR="#0000ff" WIDTH="3"/>
<hook NAME="SummaryNode"/>
</node>
</node>
</node>
</map>
