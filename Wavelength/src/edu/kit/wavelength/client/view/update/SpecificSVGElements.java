package edu.kit.wavelength.client.view.update;

import java.util.HashSet;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGPathSegList;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGTextElement;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Panel;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.action.StepManually;

/**
 * Collection of different layouting nodes needed for SVG diagrams. Since many
 * of these are very small, it made no sense to split them into different files.
 */
abstract class SVGColoredElement extends SVGElement {
	protected String color;

	public SVGColoredElement(String color) {
		this.color = color;
	}
}

abstract class SVGRedexElement extends SVGElement {

	protected boolean isNextRedex;

	public SVGRedexElement(boolean isNextRedex) {
		this.isNextRedex = isNextRedex;
	}
}

class SVGDebugElement extends SVGColoredElement {

	public SVGDebugElement(String color) {
		super(color);
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
		OMSVGTextElement elem = PlugDiagramRenderer.doc.createSVGTextElement(abs_x,
				abs_y + PlugDiagramRenderer.fontSize, OMSVGLength.SVG_LENGTHTYPE_PX, text);
		// due to text rendering being inscrutable, a 17px "font-size" actually gets layouted as 20px
		// So to get the font size at which centering isn't off, subtract some (empirically tested) amount
		// from the layouting-size
		elem.setAttribute("font-size", Float.toString(PlugDiagramRenderer.fontSize - 3) + "px");
		elem.setAttribute("font-family", "monospace");
		elem.setAttribute("dominant-baseline", "ideographic");
		res.add(elem);
		return res;

	}
}

class SVGRoundedRectElement extends SVGRedexElement {

	public SVGRoundedRectElement(boolean isNextRedex) {
		super(isNextRedex);
	}

	public float getRadius() {
		return 2 * height / 5;
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		float r = getRadius();
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, r, r);
		rect.setAttribute("stroke", PlugDiagramRenderer.black);
		rect.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		rect.setAttribute("fill", "none");
		if (this.isNextRedex) {
			rect.addClassNameBaseVal("nextRedex");
			rect.addClassNameBaseVal("stroke");
		}
		res.add(rect);
		return res;
	}
}

class SVGChevronElement extends SVGRedexElement {

	public SVGChevronElement(boolean isNextRedex) {
		super(isNextRedex);
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement chevron = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = chevron.getPathSegList();
		segs.appendItem(chevron.createSVGPathSegMovetoAbs(this.abs_x, this.abs_y));
		segs.appendItem(chevron.createSVGPathSegLinetoRel(this.width, this.height / 2));
		segs.appendItem(chevron.createSVGPathSegLinetoRel(-this.width, this.height / 2));
		chevron.setAttribute("stroke", PlugDiagramRenderer.black);
		chevron.setAttribute("stroke-width", Float.toString(PlugDiagramRenderer.strokeWidth));
		chevron.setAttribute("fill", "none");
		if (this.isNextRedex) {
			chevron.addClassNameBaseVal("nextRedex");
			chevron.addClassNameBaseVal("stroke");
		}
		res.add(chevron);
		return res;
	}
}

class SVGPacmanElement extends SVGRedexElement {
	
	/*
	 * The pacman elements are special in their handling of coordinates:
	 * Their point of origin is vertically centered on the left side of
	 * the circle with pacmanRadius.
	 */
	Application clickRedex;
	Panel wrapper;

	public SVGPacmanElement(boolean isNextRedex, Application clickRedex, Panel wrapper) {
		super(isNextRedex);
		this.clickRedex = clickRedex;
		this.wrapper = wrapper;
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();

		float r = PlugDiagramRenderer.pacmanRadius;
		OMSVGPathElement pacman = PlugDiagramRenderer.doc.createSVGPathElement();
		pacman.setAttribute("stroke", "none");
		pacman.setAttribute("fill", PlugDiagramRenderer.black);
		if (this.isNextRedex) {
			pacman.addClassNameBaseVal("nextRedex");
			pacman.addClassNameBaseVal("fill");
		}

		OMSVGPathSegList segs = pacman.getPathSegList();
		// dis  /
		// b   / angle here is atan(1/sharpness)
		// pa./_ _ _ _
		// cm \
		// an  \
		double alpha = Math.atan(1 / PlugDiagramRenderer.chevronSharpness);
		float left_corner_center_dx = (float) Math.cos(alpha) * r;
		float center_top_corner_dy = (float) -Math.sin(alpha) * r;
		
		// start at the bottom left corner
		segs.appendItem(pacman.createSVGPathSegMovetoAbs(this.abs_x + r - left_corner_center_dx,
				this.abs_y - center_top_corner_dy));
		// endx, endy, r, r
		segs.appendItem(pacman.createSVGPathSegArcAbs(this.abs_x + r, this.abs_y + r, r, r, 0f, false, false)); // bottom left flap
		segs.appendItem(pacman.createSVGPathSegArcRel(r, -r, r, r, 0f, false, false)); // bottom right quarter
		segs.appendItem(pacman.createSVGPathSegArcRel(-r,-r, r, r, 0f, false, false)); // top right quarter
		segs.appendItem(pacman.createSVGPathSegArcAbs(this.abs_x + r - left_corner_center_dx,
				this.abs_y + center_top_corner_dy, r, r, 0f, false, false)); // top left flap
		segs.appendItem(pacman.createSVGPathSegLinetoRel(left_corner_center_dx, -center_top_corner_dy));
		segs.appendItem(pacman.createSVGPathSegClosePath());

		if (clickRedex != null) {
			pacman.addClassNameBaseVal("clickable");
			// when clicked reduce the clicked application
			pacman.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					wrapper.addStyleName("notclickable");
					new StepManually(clickRedex).run();
					wrapper.removeStyleName("autoHighlight");
				}
			});
			pacman.addMouseOverHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					wrapper.removeStyleName("autoHighlight");
				}
			});
			pacman.addMouseOutHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					if (wrapper.getStyleName().indexOf("notclickable") == -1) {
						// SVG panel hasn't been disabled yet, we are still the active panel 
						wrapper.addStyleName("autoHighlight");
					}
				}
			});
		}
		res.add(pacman);
		return res;
	}
}

class SVGLineElement extends SVGRedexElement {

	String strokeWidth;
	String linecap;
	String stroke;

	public SVGLineElement(float strokeWidth, boolean isNextRedex, String linecap) {
		this(strokeWidth, isNextRedex, PlugDiagramRenderer.black, linecap);
	}
	
	public SVGLineElement(float strokeWidth, boolean isNextRedex, String color, String linecap) {
		super(isNextRedex);
		this.strokeWidth = Float.toString(strokeWidth) + "px";
		this.linecap = linecap;
		this.stroke = color;
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement line = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = line.getPathSegList();
		segs.appendItem(line.createSVGPathSegMovetoAbs(this.abs_x, this.abs_y));
		segs.appendItem(line.createSVGPathSegLinetoRel(this.width, this.height));
		line.setAttribute("stroke-width", strokeWidth);
		line.setAttribute("stroke-linecap", linecap);
		line.setAttribute("stroke", stroke);
		if (this.isNextRedex) {
			line.addClassNameBaseVal("nextRedex");
			line.addClassNameBaseVal("stroke");
		}
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

class SVGArrowheadElement extends SVGRedexElement {

	public SVGArrowheadElement(boolean isNextRedex) {
		super(isNextRedex);
		width = PlugDiagramRenderer.arrowheadWidth;
		height = PlugDiagramRenderer.arrowheadHeight;
	}

	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = super.render();
		OMSVGPathElement line = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = line.getPathSegList();
		segs.appendItem(line.createSVGPathSegMovetoAbs(this.abs_x + width / 2, this.abs_y));
		segs.appendItem(line.createSVGPathSegLinetoRel(this.width / 2, this.height));
		segs.appendItem(line.createSVGPathSegLinetoRel(-this.width, 0));
		segs.appendItem(line.createSVGPathSegClosePath());
		line.setAttribute("stroke-linecap", "butt");
		line.setAttribute("fill", PlugDiagramRenderer.black);
		if (this.isNextRedex) {
			line.addClassNameBaseVal("nextRedex");
			line.addClassNameBaseVal("fill");
		}
		res.add(line);
		return res;
	}
}