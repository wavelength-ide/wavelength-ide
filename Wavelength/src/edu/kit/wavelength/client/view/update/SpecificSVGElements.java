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

abstract class SVGColoredElement extends SVGElement {
	protected String color;
	
	public SVGColoredElement(String stroke) {
		this.color = stroke;
	}
}

class SVGDebugElement extends SVGColoredElement {
	
	public SVGDebugElement(String stroke) {
		super(stroke);
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, 0, 0);
		rect.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		rect.setAttribute("stroke", this.color == null ? "#DD0000" : this.color);
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

class SVGRoundedRectElement extends SVGColoredElement {

	public SVGRoundedRectElement(String stroke) {
		super(stroke);
	}

	public float getRadius() {
		return 2 * height / 5;
	}
	
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		float r = getRadius();
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, r, r);
		rect.setAttribute("stroke", this.color);
		rect.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		rect.setAttribute("fill", "none");
		res.add(rect);
		return res;
	}
}

class SVGChevronElement extends SVGColoredElement {

	public SVGChevronElement(String stroke) {
		super(stroke);
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement chevron = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = chevron.getPathSegList();
        segs.appendItem(chevron.createSVGPathSegMovetoAbs(this.abs_x + this.width, this.abs_y));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(-this.width, this.height / 2));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(this.width, this.height / 2));
        chevron.setAttribute("stroke", this.color);
        chevron.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
        chevron.setAttribute("fill", "none");
        res.add(chevron);
		return res;
	}
}

class SVGPacmanElement extends SVGColoredElement {

	public SVGPacmanElement(String stroke) {
		super(stroke);
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		float r = PlugDiagramRenderer.pacmanRadius;
		OMSVGPathElement pacman = PlugDiagramRenderer.doc.createSVGPathElement();
		// 
		pacman.setAttribute("stroke", "#FFFFFF");
		pacman.setAttribute("fill", this.color);
		
		OMSVGPathSegList segs = pacman.getPathSegList();
		// dis  /
		// b   /  angle here is atan(1/sharpness) 
		// pa./_ _ _ _
		// cm \
		// an  \
		double alpha = Math.atan(1/PlugDiagramRenderer.chevronSharpness);
		float center_right_corner_dx = (float) Math.cos(alpha) * r;
		float center_top_corner_dy = (float) -Math.sin(alpha) * r;
		// start at the top right corner
		segs.appendItem(pacman.createSVGPathSegMovetoAbs(this.abs_x + r + center_right_corner_dx, this.abs_y + center_top_corner_dy));
		segs.appendItem(pacman.createSVGPathSegArcAbs(this.abs_x + r, this.abs_y - r, r, r, 0f, false, false));  // top right flap 
		// endx, endy, r, r
		segs.appendItem(pacman.createSVGPathSegArcRel(-r, r, r, r, 0f, false, false)); // top left quarter
		segs.appendItem(pacman.createSVGPathSegArcRel(r, r, r, r, 0f, false, false)); // bottom left quarter

		segs.appendItem(pacman.createSVGPathSegArcAbs(this.abs_x + r + center_right_corner_dx, this.abs_y - center_top_corner_dy, r, r, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegLinetoRel(-center_right_corner_dx, center_top_corner_dy));
		segs.appendItem(pacman.createSVGPathSegClosePath());

		res.add(pacman);
		return res;
		
	}
}


class SVGLineElement extends SVGColoredElement {
	
	String strokeWidth;
	String linecap;
	
	public SVGLineElement(float strokeWidth, String stroke, String linecap) {
		super(stroke);
		this.strokeWidth = Float.toString(strokeWidth) + "px";
		this.linecap = linecap;
	}
	
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement line = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = line.getPathSegList();
		segs.appendItem(line.createSVGPathSegMovetoAbs(this.abs_x, this.abs_y));
		segs.appendItem(line.createSVGPathSegLinetoRel(this.width, this.height));
		line.setAttribute("stroke-width", strokeWidth);
		line.setAttribute("stroke-linecap", linecap);
		line.setAttribute("stroke", this.color);
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

class SVGArrowheadElement extends SVGColoredElement {

	public SVGArrowheadElement(String stroke) {
		super(stroke);
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
		line.setAttribute("fill", this.color);
		res.add(line);
		return res;
	}
}