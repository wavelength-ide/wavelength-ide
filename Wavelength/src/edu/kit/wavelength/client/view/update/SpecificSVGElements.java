package edu.kit.wavelength.client.view.update;

import java.util.HashSet;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGPathSegList;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGTextElement;

import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;

/**
 * Collection of different layouting nodes needed for SVG diagrams. Since many of these are very small,
 * it made no sense to split them into different files. 
 */

class SVGDebugElement extends SVGElement {

	String stroke;
	public SVGDebugElement(String stroke) {
		this.stroke = stroke;
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, 0, 0);
		rect.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		rect.setAttribute("stroke", stroke == null ? "#DD0000" : stroke);
		rect.setAttribute("fill", "none");
		res.add(rect);
		return res;
	}
}

class SVGTextElement extends SVGElement {
	private String text;

	public SVGTextElement(String text) {
		this.text = text;
		this.width = PlugDiagramRenderer.fontSize * text.length() * 0.6f;
		this.height = PlugDiagramRenderer.fontSize;
		
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGTextElement elem = PlugDiagramRenderer.doc.createSVGTextElement(abs_x, abs_y + PlugDiagramRenderer.fontSize, OMSVGLength.SVG_LENGTHTYPE_PX, text);
		elem.setAttribute("font-size", Float.toString(PlugDiagramRenderer.fontSize) + "px");
		elem.setAttribute("font-family", "monospace");
		elem.setAttribute("dominant-baseline", "ideographic");
		res.add(elem);
		return res;
		
	}
}

class SVGRoundedRectElement extends SVGElement {

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, 2* height / 5, 2 * height / 5);
		rect.setAttribute("stroke", "#000000");
		rect.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		rect.setAttribute("fill", "none");
		res.add(rect);
		return res;
	}
}

class SVGChevronElement extends SVGElement {

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement chevron = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = chevron.getPathSegList();
        segs.appendItem(chevron.createSVGPathSegMovetoAbs(this.abs_x + this.width, this.abs_y));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(-this.width, this.height / 2));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(this.width, this.height / 2));
        chevron.setAttribute("stroke", "#000000");
        chevron.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
        chevron.setAttribute("fill", "none");
        res.add(chevron);
		return res;
	}
}

class SVGPacmanElement extends SVGElement {
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		// TODO maybe use pacmanRadius?
		OMSVGPathElement pacman = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = pacman.getPathSegList();
		segs.appendItem(pacman.createSVGPathSegMovetoAbs(this.abs_x + 14.4844f, this.abs_y - 14.4844f));
		// endx, endy, rx, ry, ...
		segs.appendItem(pacman.createSVGPathSegArcRel(-14.4843f, 14.4844f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegArcRel(14.4843f, 14.4863f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegArcRel(7.4629f, -2.08789f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegLinetoRel(-6.9082f, -13.6738f));
		segs.appendItem(pacman.createSVGPathSegLinetoRel(5.9688f, -11.6406f));
		segs.appendItem(pacman.createSVGPathSegArcRel(-6.5235f, -1.56836f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegClosePath());

		res.add(pacman);
		return res;
		
	}
}


class SVGLineElement extends SVGElement {
	
	String strokeWidth;
	
	public SVGLineElement(float strokeWidth) {
		this.strokeWidth = Float.toString(strokeWidth) + "px";
	}
	
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement line = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = line.getPathSegList();
		segs.appendItem(line.createSVGPathSegMovetoAbs(this.abs_x, this.abs_y));
		segs.appendItem(line.createSVGPathSegLinetoRel(this.width, this.height));
		line.setAttribute("stroke-width", strokeWidth);
		line.setAttribute("stroke-linecap", "round");
		line.setAttribute("stroke", "#000000");
		res.add(line);
		return res;
	}
}

class SVGVariableElement extends SVGElement {
	
	public SVGVariableElement(LambdaTerm term) {
		this.term = term;
		this.width = PlugDiagramRenderer.boundVarRectWidth;
		this.height = PlugDiagramRenderer.boundVarRectHeight;
	}
	
	public Set<SVGElement> boundVariableLayoutElements(int deBruijnIndex) {
		Set<SVGElement> res = new HashSet<>();
		if (((BoundVariable) term).getDeBruijnIndex() == deBruijnIndex) {
			res.add(this);
			return res;
		}
		return res;
	}
}

class SVGAbstractionElement extends SVGElement {

	public SVGAbstractionElement(LambdaTerm term) {
		this.term = term;
	}
	
	public Set<SVGElement> boundVariableLayoutElements(int deBruijnIndex) {
		Set<SVGElement> res = new HashSet<>();
		for (SVGElement child : children) {
			res.addAll(child.boundVariableLayoutElements(deBruijnIndex + 1));
		}
		return res;
	}
}

class SVGArrowheadElement extends SVGElement {
	public SVGArrowheadElement() {
		width = PlugDiagramRenderer.arrowheadWidth;
		height = PlugDiagramRenderer.arrowheadHeight;
	}
	
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement line = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = line.getPathSegList();
		segs.appendItem(line.createSVGPathSegMovetoAbs(this.abs_x + width/2, this.abs_y));
		segs.appendItem(line.createSVGPathSegLinetoRel(this.width/2, this.height));
		segs.appendItem(line.createSVGPathSegLinetoRel(-this.width, 0));
		segs.appendItem(line.createSVGPathSegClosePath());
		line.setAttribute("stroke-linecap", "butt");
		line.setAttribute("fill", "#000000");
		res.add(line);
		return res;
	}
}