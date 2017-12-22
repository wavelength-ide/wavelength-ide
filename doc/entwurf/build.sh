mkdir -p build/doclet

javadoc -docletpath doclet/Wavelet.jar -doclet edu.kit.wavelet.Wavelet -sourcepath ../../Wavelength/src/ -subpackages edu.kit.wavelength

latexmk -pdf -auxdir=build/latex -outdir=build/latex entwurf.tex

cp build/latex/entwurf.pdf ./entwurf.pdf
