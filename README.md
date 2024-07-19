Pangloss
========
Uncompleted state
------------------
This project has not been completed. No further work on this project is planned.
Lessons Learned:
- As a freelancer translator, return on investment of terminology work is low.
- Even with a short document to translate, the Freeplane map can become pretty big, so linking terms together becomes challenging, and the map becomes messy.
- The term list produced by this plugin needs to be imported in another tool (e.g. RWS MultiTerm) in order to make it available in the translation tool (e.g. Trados Studio). But in practice, an important part of the terminology work is done while the translation is in progress. This plugin does not address the synchronization issue.

Terminology add-on for Freeplane
---------------------------------
[Freeplane](http://freeplane.sourceforge.net) is a great tool to edit terms and concepts.
Pangloss adds to Freeplane some terminology-oriented features. Main target users are translators, hut this add-on should hopefully be helpful to anyone dealing with field-specific terms.

Roadmap:
- Version 1: Exporting a termbase
- Version 2: Searching the translation of a term

The directory structure
-----------------------
The programming language is Groovy (version 1.8.6).
- `Pangloss`: The add-on, which is the main deliverable. The build process (from the folders `scripts` and `scripts_lib`) is manual.
- `lib`: The compiled library(ies), from which you can build `tmf.jar` for the add-on.
- `resources`: Some stylesheets for file transformation.
- `scripts`: The Groovy script(s) contained in the add-on.
- `scripts_lib`: The library(ies) contained in the add-on. Currently, there is only one library, called `tmf`.
	- `doc`: The Groovydoc. Browse `overview-summary.html` to understand the purpose of the library(ies).
	- `junit`: The Groovy source code of the unitary tests (style JUnit 4). Dependencies: xmlunit-1.3, easymock-3.1.
	- `junit_bin`: The compiled unitary tests.
	- `src`: The Groovy source code of the library(ies).

Environment
------------
- Windows 7, 64 bits
- Java 1.7.07
- [Groovy/Grails Tool Suite](http://grails.org/products/ggts) 3.1.0

