<map version="freeplane 1.2.0">
<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->
<node TEXT="Pangloss" ID="ID_1723255651" CREATED="1283093380553" MODIFIED="1359209736069" BACKGROUND_COLOR="#97c7dc">
<font SIZE="16" BOLD="true" ITALIC="true"/>
<hook NAME="MapStyle">
    <properties show_icon_for_attributes="true" show_note_icons="true"/>

<map_styles>
<stylenode LOCALIZED_TEXT="styles.root_node">
<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">
<stylenode LOCALIZED_TEXT="default" MAX_WIDTH="600" COLOR="#000000" STYLE="as_parent">
<font NAME="SansSerif" SIZE="10" BOLD="false" ITALIC="false"/>
</stylenode>
<stylenode LOCALIZED_TEXT="defaultstyle.details"/>
<stylenode LOCALIZED_TEXT="defaultstyle.note"/>
<stylenode LOCALIZED_TEXT="defaultstyle.floating">
<edge STYLE="hide_edge"/>
<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">
<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">
<font NAME="Liberation Sans" SIZE="10" BOLD="true"/>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.important">
<icon BUILTIN="yes"/>
</stylenode>
</stylenode>
<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">
<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000">
<font SIZE="18"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">
<font SIZE="16"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">
<font SIZE="14"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">
<font SIZE="12"/>
</stylenode>
<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">
<font SIZE="10"/>
</stylenode>
</stylenode>
</stylenode>
</map_styles>
</hook>
<hook NAME="AutomaticEdgeColor" COUNTER="12"/>
<attribute_layout NAME_WIDTH="135" VALUE_WIDTH="125"/>
<attribute NAME="name" VALUE="Pangloss"/>
<attribute NAME="version" VALUE="v0.3"/>
<attribute NAME="author" VALUE="Stephane Aubry"/>
<attribute NAME="freeplaneVersionFrom" VALUE="1.2.18"/>
<attribute NAME="freeplaneVersionTo" VALUE=""/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      The homepage of this add-on should be set as the link of the root node.
    </p>
    <p>
      The basic properties of this add-on. They can be used in script names and other attributes, e.g. &quot;${name}.groovy&quot;.
    </p>
    <ul>
      <li>
        name: The name of the add-on, normally a technically one (no spaces, no special characters except _.-).
      </li>
      <li>
        author: Author's name(s) and (optionally) email adresses.
      </li>
      <li>
        version: Since it's difficult to protect numbers like 1.0 from Freeplane's number parser it's advised to prepend a 'v' to the number, e.g. 'v1.0'.
      </li>
      <li>
        freeplane-version-from: The oldest compatible Freeplane version. The add-on will not be installed if the Freeplane version is too old.
      </li>
      <li>
        freeplane-version-to: Normally empty: The newest compatible Freeplane version. The add-on will not be installed if the Freeplane version is too new.
      </li>
    </ul>
  </body>
</html>
</richcontent>
<node TEXT="description" POSITION="left" ID="ID_1397745431" CREATED="1354893964317" MODIFIED="1354893964356">
<edge COLOR="#ff0000" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Description would be awkward to edit as an attribute.
    </p>
    <p>
      So you have to put the add-on description as a child of the <i>'description'</i>&#160;node.
    </p>
  </body>
</html>
</richcontent>
<node ID="ID_917638461" CREATED="1354894048441" MODIFIED="1357462802324"><richcontent TYPE="NODE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      &#160;<font size="4">Pangloss adds to Freeplane some terminology-oriented features. </font>
    </p>
    <p>
      <b>Saving a termbase:</b>
    </p>
    <ul>
      <li>
        Supported output formats: GMT, 2-column Tab-separated value,
      </li>
      <li>
        Save dialog (GUI) for selecting the format and path of the file where the termbase should be saved.
      </li>
      <li>
        Support custom xsl converter from the native GMT format. Example: pangloss/gmt2tsv.xsl in the user directory.
      </li>
    </ul>
  </body>
</html>
</richcontent>
<richcontent TYPE="DETAILS">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Version 1 - Exporting a termbase.
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
<node TEXT="changes" POSITION="left" ID="ID_370376835" CREATED="1354893964358" MODIFIED="1354893964367">
<edge COLOR="#0000ff" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      Change log of this add-on: append one node for each noteworthy version and put the details for each version into a child node.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="v0.3" ID="ID_643313470" CREATED="1357461654176" MODIFIED="1359209777612">
<node ID="ID_1333269125" CREATED="1357461713066" MODIFIED="1359210308047"><richcontent TYPE="NODE">

<html>
  <head>
    
  </head>
  <body>
    <ul>
      <li>
        Support subjectField attribute in Concept nodes. If its value starts with a +, then the string is internally concatenated with the subjectField of the parent Concept node.
      </li>
      <li>
        Support superordinate/subordinate relationship in Concept nodes. The default relationship is generic (broader/narrower). To set a partitive relationship, add the Home (House) icon on a Concept node representing a part of its parent Concept node.
      </li>
    </ul>
  </body>
</html>

</richcontent>
</node>
</node>
<node TEXT="v0.2" ID="ID_731500961" CREATED="1357461654176" MODIFIED="1357462082906">
<node ID="ID_1142520000" CREATED="1357461713066" MODIFIED="1357462555848"><richcontent TYPE="NODE">

<html>
  <head>
    
  </head>
  <body>
    <ul>
      <li>
        Save dialog (GUI) for selecting the format and path of the file where the termbase should be saved.
      </li>
      <li>
        Support custom xsl converter from GMT format. Example: pangloss/gmt2tsv.xsl in the user directory.
      </li>
    </ul>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
<node TEXT="license" POSITION="left" ID="ID_465998270" CREATED="1354893964368" MODIFIED="1354893964378">
<edge COLOR="#00ff00" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      The add-ons's license that the user has to accept before she can install it.
    </p>
    <p>
      
    </p>
    <p>
      The License text has to be entered as a child of the <i>'license'</i>&#160;node, either as plain text or as HTML.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="&#xa;This add-on is free software: you can redistribute it and/or modify&#xa;it under the terms of the GNU General Public License as published by&#xa;the Free Software Foundation, either version 2 of the License, or&#xa;(at your option) any later version.&#xa;&#xa;This program is distributed in the hope that it will be useful,&#xa;but WITHOUT ANY WARRANTY; without even the implied warranty of&#xa;MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.&#xa0;&#xa0;See the&#xa;GNU General Public License for more details.&#xa;" ID="ID_162127328" CREATED="1354893964380" MODIFIED="1354893964382"/>
</node>
<node TEXT="preferences.xml" POSITION="left" ID="ID_1001187955" CREATED="1354893964401" MODIFIED="1354893964425">
<edge COLOR="#ff00ff" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      <font color="#000000" face="SansSerif, sans-serif">The child node contains the add-on configuration as an extension to mindmapmodemenu.xml (in Tools-&gt;Preferences-&gt;Add-ons). </font>
    </p>
    <p>
      <font color="#000000" face="SansSerif, sans-serif">Every property in the configuration should receive a default value in <i>default.properties</i>&#160;node.</font>
    </p>
  </body>
</html>
</richcontent>
<node TEXT="&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&#xa;&lt;preferences_structure&gt;&#xa;&lt;tabbed_pane&gt;&#xa;&lt;tab name=&quot;plugins&quot;&gt;&#xa;&lt;separator name=&quot;pangloss&quot;&gt;&#xa;&lt;boolean name=&quot;pangloss_bilingual_notation&quot;/&gt;&#xa;&lt;string name=&quot;pangloss_field_separator&quot; /&gt;&#xa;&lt;/separator&gt;&#xa;&lt;/tab&gt;&#xa;&lt;/tabbed_pane&gt;&#xa;&lt;/preferences_structure&gt;" ID="ID_1560246146" CREATED="1356528131036" MODIFIED="1359208710061"/>
</node>
<node TEXT="default.properties" POSITION="left" ID="ID_829284969" CREATED="1354893964426" MODIFIED="1359208608091">
<edge COLOR="#00ffff" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      These properties play together with the preferences: Each property defined in the preferences should have a default value in the attributes of this node.
    </p>
  </body>
</html>
</richcontent>
<attribute_layout NAME_WIDTH="168"/>
<attribute NAME="pangloss_bilingual_notation" VALUE="true"/>
<attribute NAME="pangloss_field_separator" VALUE="&gt;"/>
</node>
<node TEXT="translations" POSITION="left" ID="ID_301113394" CREATED="1354893964437" MODIFIED="1354893964446">
<edge COLOR="#ffff00" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      The translation keys that this script uses. Define one child node per supported locale. The attributes contain the translations. Define at least 'addons.${name}' for the add-on's name.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="en" ID="ID_1742695989" CREATED="1354893964447" MODIFIED="1359208784013">
<attribute_layout NAME_WIDTH="205" VALUE_WIDTH="191"/>
<attribute NAME="addons.${name}" VALUE="Pangloss"/>
<attribute NAME="addons.exportTermbase" VALUE="Export the terms..."/>
<attribute NAME="addons.exportParameterButton" VALUE="Parameters..."/>
<attribute NAME="OptionPanel.separator.pangloss" VALUE="Pangloss"/>
<attribute NAME="OptionPanel.pangloss_bilingual_notation" VALUE="Simple bilingual notation"/>
<attribute NAME="OptionPanel.pangloss_field_separator" VALUE="S&#xe9;parateur champ / sous-champ"/>
<node TEXT="OptionPanel.separator.pangloss" ID="ID_575741755" CREATED="1356528677384" MODIFIED="1356528677384"/>
</node>
<node TEXT="fr" ID="ID_1609652132" CREATED="1354893964447" MODIFIED="1359208842203">
<attribute_layout NAME_WIDTH="205" VALUE_WIDTH="298"/>
<attribute NAME="addons.${name}" VALUE="Pangloss"/>
<attribute NAME="addons.exportTermbase" VALUE="Exporter les termes..."/>
<attribute NAME="addons.exportParameterButton" VALUE="Param&#xe8;tres..."/>
<attribute NAME="OptionPanel.separator.pangloss" VALUE="Pangloss"/>
<attribute NAME="OptionPanel.pangloss_bilingual_notation" VALUE="Notation bilingue simplifi&#xe9;e"/>
<attribute NAME="OptionPanel.pangloss_field_separator" VALUE="Separator Field / Sub-field"/>
</node>
<node TEXT="es" ID="ID_918771512" CREATED="1354893964447" MODIFIED="1357405415745">
<attribute_layout NAME_WIDTH="205" VALUE_WIDTH="298"/>
<attribute NAME="addons.${name}" VALUE="Pangloss"/>
<attribute NAME="addons.exportTermbase" VALUE="Exportar los t\u00e9rminos..."/>
<attribute NAME="addons.exportParameterButton" VALUE="Par&#xe1;metros..."/>
<attribute NAME="OptionPanel.separator.pangloss" VALUE="Pangloss"/>
<attribute NAME="OptionPanel.pangloss_bilingual_notation" VALUE="Notaci&#xf3;n biling&#xfc;e simple"/>
</node>
<node TEXT="ja" ID="ID_1059175717" CREATED="1354893964447" MODIFIED="1359209605354">
<attribute_layout NAME_WIDTH="205" VALUE_WIDTH="298"/>
<attribute NAME="addons.${name}" VALUE="&#x30d1;&#x30f3;&#x30b0;&#x30ed;&#x30b9;"/>
<attribute NAME="addons.exportTermbase" VALUE="&#x7528;&#x8a9e;&#x3092;&#x30a8;&#x30af;&#x30b9;&#x30dd;&#x30fc;&#x30c8;..."/>
<attribute NAME="addons.exportParameterButton" VALUE="&#x8a2d;&#x5b9a;..."/>
<attribute NAME="OptionPanel.separator.pangloss" VALUE="&#x30d1;&#x30f3;&#x30b0;&#x30ed;&#x30b9;"/>
<attribute NAME="OptionPanel.pangloss_bilingual_notation" VALUE="&#x7c21;&#x5358;&#x306a;&#x30d0;&#x30a4;&#x30ea;&#x30f3;&#x30ac;&#x30eb;&#x8868;&#x8a18;&#x6cd5;"/>
<attribute NAME="OptionPanel.pangloss_field_separator" VALUE="&#x5206;&#x91ce;&#x30fb;&#x4e0b;&#x4f4d;&#x5206;&#x91ce;&#x306e;&#x9593;&#x306e;&#x8a18;&#x53f7;"/>
</node>
</node>
<node TEXT="deinstall" POSITION="left" ID="ID_1889438955" CREATED="1354893964455" MODIFIED="1357462993382">
<edge COLOR="#7c0000" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      List of files and/or directories to remove on deinstall
    </p>
  </body>
</html>
</richcontent>
<attribute_layout NAME_WIDTH="70" VALUE_WIDTH="443"/>
<attribute NAME="delete" VALUE="${installationbase}/addons/${name}.script.xml"/>
<attribute NAME="delete" VALUE="${installationbase}/scripts/ExportTermbase.groovy"/>
<attribute NAME="delete" VALUE="${installationbase}/resources/images/Pangloss.png"/>
<attribute NAME="delete" VALUE="${installationbase}/resources/images/Pangloss-icon.png"/>
<attribute NAME="delete" VALUE="${installationbase}/pangloss/gmt2tsv.xsl"/>
<attribute NAME="delete" VALUE="${installationbase}/pangloss/export.properties"/>
<attribute NAME="delete" VALUE="${installationbase}/pangloss"/>
<attribute NAME="delete" VALUE="${installationbase}/lib/tmf.jar"/>
</node>
<node TEXT="scripts" POSITION="right" ID="ID_1652832201" CREATED="1354893964472" MODIFIED="1354893964493">
<edge COLOR="#00007c" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may contain multiple scripts. The node text defines the script name (e.g. inserInlineImage.groovy). Its properties have to be configured via attributes:
    </p>
    <p>
      
    </p>
    <p>
      * menuLocation: &lt;locationkey&gt;
    </p>
    <p>
      &#160;&#160;&#160;- Defines where the menu location.
    </p>
    <p>
      &#160;&#160;&#160;-&#160;See mindmapmodemenu.xml for how the menu locations look like.
    </p>
    <p>
      &#160;&#160;&#160;- http://freeplane.bzr.sf.net/bzr/freeplane/freeplane_program/trunk/annotate/head%3A/freeplane/resources/xml/mindmapmodemenu.xml
    </p>
    <p>
      &#160;&#160;&#160;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * menuTitleKey: &lt;key&gt;
    </p>
    <p>
      &#160;&#160;&#160;- The menu item title will be looked up under the translation key &lt;key&gt; - don't forget to define its translation.
    </p>
    <p>
      &#160;&#160;&#160;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * executionMode: &lt;mode&gt;
    </p>
    <p>
      &#160;&#160;&#160;- The execution mode as described in the Freeplane wiki (http://freeplane.sourceforge.net/wiki/index.php/Scripting)
    </p>
    <p>
      &#160;&#160;&#160;- ON_SINGLE_NODE: Execute the script once. The <i>node</i>&#160;variable is set to the selected node.
    </p>
    <p>
      &#160;&#160;&#160;- ON_SELECTED_NODE: Execute the script n times for n selected nodes, once for each node.
    </p>
    <p>
      &#160;&#160;&#160;- ON_SELECTED_NODE_RECURSIVELY: Execute the script on every selected node and recursively on all of its children.
    </p>
    <p>
      &#160;&#160;&#160;- In doubt use ON_SINGLE_NODE.
    </p>
    <p>
      &#160;&#160;&#160;- This attribute is mandatory
    </p>
    <p>
      
    </p>
    <p>
      * keyboardShortcut: &lt;shortcut&gt;
    </p>
    <p>
      &#160;&#160;&#160;- Optional: keyboard combination / accelerator for this script, e.g. control alt I
    </p>
    <p>
      &#160;&#160;&#160;- Use lowercase letters for modifiers and uppercase for letters. Use no + signs.
    </p>
    <p>
      &#160;&#160;&#160;- The available key names are listed at http://download.oracle.com/javase/1.4.2/docs/api/java/awt/event/KeyEvent.html#VK_0
    </p>
    <p>
      &#160;&#160;&#160;&#160;&#160;In the list only entries with a 'VK_' prefix count. Omit the prefix in the shortcut definition.
    </p>
    <p>
      
    </p>
    <p>
      * Permissions&#160;that the script(s) require, each either false or true:
    </p>
    <p>
      &#160;&#160;&#160;- execute_scripts_without_asking
    </p>
    <p>
      &#160;&#160;&#160;- execute_scripts_without_file_restriction: permission to read files
    </p>
    <p>
      &#160;&#160;&#160;- execute_scripts_without_write_restriction: permission to create/change/delete files
    </p>
    <p>
      &#160;&#160;&#160;- execute_scripts_without_exec_restriction: permission to execute other programs
    </p>
    <p>
      &#160;&#160;&#160;- execute_scripts_without_network_restriction: permission to access the network
    </p>
    <p>
      &#160;&#160;Notes:
    </p>
    <p>
      &#160;&#160;- The set of permissions is fixed.
    </p>
    <p>
      &#160;&#160;- Don't change the attribute names, don't omit one.
    </p>
    <p>
      &#160;&#160;- Set the values either to true or to false
    </p>
    <p>
      &#160;&#160;- In any case set execute_scripts_without_asking to true unless you want to annoy users.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="ExportTermbase.groovy" ID="ID_499338003" CREATED="1354894330862" MODIFIED="1357405390084">
<attribute_layout NAME_WIDTH="280" VALUE_WIDTH="202"/>
<attribute NAME="menuTitleKey" VALUE="addons.exportTermbase"/>
<attribute NAME="menuLocation" VALUE="/menu_bar/file"/>
<attribute NAME="executionMode" VALUE="on_single_node"/>
<attribute NAME="keyboardShortcut" VALUE=""/>
<attribute NAME="execute_scripts_without_asking" VALUE="true"/>
<attribute NAME="execute_scripts_without_file_restriction" VALUE="true"/>
<attribute NAME="execute_scripts_without_write_restriction" VALUE="true"/>
<attribute NAME="execute_scripts_without_exec_restriction" VALUE="false"/>
<attribute NAME="execute_scripts_without_network_restriction" VALUE="false"/>
</node>
</node>
<node TEXT="zips" POSITION="right" ID="ID_1339660549" CREATED="1354893964507" MODIFIED="1354893964519">
<edge COLOR="#007c00" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may contain any number of nodes containing zip files.
    </p>
    <p>
      
    </p>
    <p>
      &#160;- The immediate child nodes contain a description of the zip. The devtools script releaseAddOn.groovy allows automatic zip creation if the name of this node matches a directory in the current directory.
    </p>
    <p>
      
    </p>
    <p>
      &#160;- The child nodes of these nodes contain the actual zip files.
    </p>
    <p>
      
    </p>
    <p>
      &#160;- Any zip file will be extracted in the &lt;installationbase&gt;. Currently, &lt;installationbase&gt; is always Freeplane's &lt;userhome&gt;, e.g. ~/.freeplane/1.2.
    </p>
    <p>
      
    </p>
    <p>
      &#160;- The files will be processed in the sequence as seen in the map.
    </p>
    <p>
      
    </p>
    <p>
      &#160;- Zip files must be uploaded into the map via the script <i>Tools-&gt;Scripts-&gt;Insert Binary</i>&#160;since they have to be (base64) encoded as simple strings.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="lib" ID="ID_658483344" CREATED="1354903293259" MODIFIED="1354903297192">
<node TEXT="tmf.jar" ID="ID_1311261880" CREATED="1354903299056" MODIFIED="1354903311861">
<node TEXT="" ID="ID_931073285" CREATED="1354903320015" MODIFIED="1354903320015"/>
</node>
</node>
<node TEXT="pangloss" ID="ID_310562204" CREATED="1357404146652" MODIFIED="1357404154889">
<node TEXT="gmt2tsv.xsl" ID="ID_1635419669" CREATED="1354908489879" MODIFIED="1354908492968"/>
</node>
</node>
<node TEXT="images" POSITION="right" ID="ID_1122751729" CREATED="1354893964521" MODIFIED="1354893964533">
<edge COLOR="#7c007c" WIDTH="3"/>
<richcontent TYPE="NOTE">

<html>
  <head>
    
  </head>
  <body>
    <p>
      An add-on may define any number of images as child nodes of the images node. The actual image data has to be placed as base64 encoded binary data into the text of a subnode.
    </p>
    <p>
      The images are saved to the <i>${installationbase}/resources/images</i>&#160;directory.
    </p>
    <p>
      
    </p>
    <p>
      The following images should be present:
    </p>
    <ul>
      <li>
        <i>${name}.png</i>, like <i>oldicons-theme.png</i>. This will be used in the app-on details dialog.
      </li>
      <li>
        <i>${name}-icon.png</i>, like <i>oldicons-theme-icon.png</i>. This will be used in the app-on overview.
      </li>
    </ul>
    <p>
      Images can be added automatically by releaseAddOn.groovy or must be uploaded into the map via the script <i>Tools-&gt;Scripts-&gt;Insert Binary</i>&#160;since they have to be (base64) encoded as simple strings.
    </p>
  </body>
</html>
</richcontent>
<node TEXT="Pangloss.png" ID="ID_587261049" CREATED="1354896703825" MODIFIED="1354896764131">
<node TEXT="iVBORw0KGgoAAAANSUhEUgAAACoAAAArBAMAAAD8sQfNAAAAMFBMVEX2hhr3jSn4lDf1nkr2pl&#xa;X6q2f7tnf7w5D5ypr90az82Lj74cT+6Nf78OP/9+/+//za3yxcAAAACXBIWXMAAArwAAAK8AFC&#xa;rDSYAAACJklEQVQoz43TT2jTUBgA8K9J1rhZtuBZR0567UmGB1tkJyfq0Z0MIjsoLGUeggq2F/&#xa;VSdbLjYO7mobIMRBFnTaFTBGF48zLthqyFiWSzXdI/WT6/l9csQXfwQZKXH1++F773PcDDBrCb&#xa;jV4ZP/vfa95KOdT94rFnroxquW61RAs/5nmsXhhtym2wN580z+IWiDbPsDE4K2STvqbPrfhpgA&#xa;xpC/QdYOMcmO8utmlyhMUW5EKg9PbDdOgpkL53oT8G3qhWk01YrA8HI2Fr5+1PKulMKcLp9iQt&#xa;3iHNxUP962YjzTLUI4XT1zKY5XnTMQarx1f7pkQm3sQq11heOIqrMP6PilYvjw2m7XhakG4jVt&#xa;lqMUuAjt68T/o0psPriBqIf+VNYWmO/8NbLZbBWg2r04xQhbGfWqD72QMdakz+qnkK099RgqFg&#xa;gytMd850+ihI+Nh/sOAwrdqhPlImFtMgLCmkL9aWxRlerw/jd1WAZIF0S8/pDkzBApiIa2w3WW&#xa;zOXrZ35W4CL1+aprXUfiXxlbc70pWwfvJWqYyzfbWLN9yMnd5G5yVAfqOvVmfEH77q3LdwSQW5&#xa;FcbupfBKBqeOI/v8NVcDKxJ+TeE6jOEXgFNca10FLDdh0V7nqQ0FKhJpj6qTd6i0WRjlzZkkZc&#xa;1nbtJUgcFKoCdYfTX6JAsStcfAYqAmU1eRqaukPUoY7NYFfi627z03jDsNwzAe0lUMT9bh5+0/&#xa;9Q/2lp/WmUSETgAAAER6VFh0Q29tbWVudAAAeNpzTcksSU1RSKpUCEgszVEIzkgtyk3MU0jLL1&#xa;IID3DOySxILCrRUQgoTcrJTFZwyc9NzMwDANHGEgv4LaoLAAAAAElFTkSuQmCC" ID="ID_1928895995" CREATED="1354896635815" MODIFIED="1354896689036"/>
</node>
<node TEXT="Pangloss-icon.png" ID="ID_1267645465" CREATED="1354896767108" MODIFIED="1354896776836">
<node TEXT="iVBORw0KGgoAAAANSUhEUgAAACoAAAArBAMAAAD8sQfNAAAAMFBMVEX2hhr3jSn4lDf1nkr2pl&#xa;X6q2f7tnf7w5D5ypr90az82Lj74cT+6Nf78OP/9+/+//za3yxcAAAACXBIWXMAAArwAAAK8AFC&#xa;rDSYAAACJklEQVQoz43TT2jTUBgA8K9J1rhZtuBZR0567UmGB1tkJyfq0Z0MIjsoLGUeggq2F/&#xa;VSdbLjYO7mobIMRBFnTaFTBGF48zLthqyFiWSzXdI/WT6/l9csQXfwQZKXH1++F773PcDDBrCb&#xa;jV4ZP/vfa95KOdT94rFnroxquW61RAs/5nmsXhhtym2wN580z+IWiDbPsDE4K2STvqbPrfhpgA&#xa;xpC/QdYOMcmO8utmlyhMUW5EKg9PbDdOgpkL53oT8G3qhWk01YrA8HI2Fr5+1PKulMKcLp9iQt&#xa;3iHNxUP962YjzTLUI4XT1zKY5XnTMQarx1f7pkQm3sQq11heOIqrMP6PilYvjw2m7XhakG4jVt&#xa;lqMUuAjt68T/o0psPriBqIf+VNYWmO/8NbLZbBWg2r04xQhbGfWqD72QMdakz+qnkK099RgqFg&#xa;gytMd850+ihI+Nh/sOAwrdqhPlImFtMgLCmkL9aWxRlerw/jd1WAZIF0S8/pDkzBApiIa2w3WW&#xa;zOXrZ35W4CL1+aprXUfiXxlbc70pWwfvJWqYyzfbWLN9yMnd5G5yVAfqOvVmfEH77q3LdwSQW5&#xa;FcbupfBKBqeOI/v8NVcDKxJ+TeE6jOEXgFNca10FLDdh0V7nqQ0FKhJpj6qTd6i0WRjlzZkkZc&#xa;1nbtJUgcFKoCdYfTX6JAsStcfAYqAmU1eRqaukPUoY7NYFfi627z03jDsNwzAe0lUMT9bh5+0/&#xa;9Q/2lp/WmUSETgAAAER6VFh0Q29tbWVudAAAeNpzTcksSU1RSKpUCEgszVEIzkgtyk3MU0jLL1&#xa;IID3DOySxILCrRUQgoTcrJTFZwyc9NzMwDANHGEgv4LaoLAAAAAElFTkSuQmCC" ID="ID_41763323" CREATED="1354896635815" MODIFIED="1354896689036"/>
</node>
</node>
</node>
</map>
