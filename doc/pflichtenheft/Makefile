NAME=pflichtenheft

default: $(NAME).pdf

all: $(NAME).pdf

build/%.pdf: %.tex
	mkdir -p build
	latexmk -pdf -g -auxdir=build -outdir=build $<
	cd build; makeglossaries $(basename $<)
	latexmk -pdf -g -auxdir=build -outdir=build $<

%.pdf: build/%.pdf
	mv $< $@ # atomic!
	cp $@ $<

.PHONY: show clean distclean

show: $(NAME).pdf
	xdg-open $<

clean:
	rm -f *.tmp *.tui *.log *.tuc *.mp *.bbl *.blg *.fls *.idx *.aux *.out *.fdb_latexmk *.ilg *.ind
	rm -rf build

distclean: clean
	rm -f $(NAME).pdf
