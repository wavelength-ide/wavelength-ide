mkdir -p build/doclet

javadoc -docletpath doclet/TeXDoclet.jar \
	-doclet org.stfm.texdoclet.TeXDoclet \
	-hyperref \
	-output build/doclet/out.tex \
	-sourcepath ../../Wavelength/src \
	-subpackages edu \
	-sectionlevel subsection \
	-docclass article \
	-texinit preamble.tex \
	-texintro intro.tex \
	-texfinish finish.tex \
	-texsetup setup.tex \
	-noindex

latexmk -pdf -auxdir=build/latex -outdir=build/latex build/doclet/out.tex

cp build/latex/out.pdf ./entwurf.pdf
