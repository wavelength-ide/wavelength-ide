mkdir -p build/doclet

javadoc -docletpath doclet/TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-hyperref \
	-output build/doclet/out.tex \
	-sourcepath ../../src/src \
	-subpackages edu \
	-sectionlevel section \
	-docclass article \
	-texinit preamble.tex \
	-noindex

latexmk -pdf -auxdir=build/latex -outdir=build/latex build/doclet/out.tex

cp build/latex/out.pdf .
