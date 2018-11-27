package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGPathSegList;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGTextElement;

import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.update.SVGLayoutElement.LayoutItemType;

class SVGLayoutElement {
	// TODO turn this into class hierarchy once we know which types we need
	enum LayoutItemType {
		RECT,
		ABS,
		TEXT,
		DEBUG,
		LINE,
		ARROWHEAD,
		CHEVRON,
		PACMAN,
		// ...
	}

	public float x;
	public float y;
	public float width;
	public float height;
	
	// absolute positioning may be recalculated multiple times 
	// from different roots (we need it while drawing arrows
	// for each abstraction), so we need these separately
	public float abs_x;
	public float abs_y;
	public SVGLayoutElement.LayoutItemType type;
	private List<SVGLayoutElement> children;
	LambdaTerm term;
	private String text;

	public SVGLayoutElement(String text) {
		this(SVGLayoutElement.LayoutItemType.TEXT);
		this.text = text;
		this.width = PlugDiagramRenderer.fontSize * text.length() * 0.6f;
		this.height = PlugDiagramRenderer.fontSize;
	}

	public SVGLayoutElement(SVGLayoutElement.LayoutItemType type) {
		this(type, (LambdaTerm) null);
	}
	
	public SVGLayoutElement(SVGLayoutElement.LayoutItemType type, LambdaTerm term) {
		this.children = new ArrayList<>();
		this.type = type;
		this.term = term;
	}
	public void addChild(SVGLayoutElement elem) {
		this.children.add(elem);
	}
	public void moveTo(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void clearAbsoluteLayout() {
		this.abs_x = x;
		this.abs_y = y;
		for (SVGLayoutElement child : children) {
			child.clearAbsoluteLayout();
		}
	}
	/**
	 * propagates relative positions through the tree, turning relative x-y-coordinates to absolute ones
	 */
	public void calculateAbsoluteLayout() {
		for (SVGLayoutElement child : children) {
			child.abs_x += abs_x;
			child.abs_y += abs_y;
			child.calculateAbsoluteLayout();
		}
		
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	/*
	 * Since we want to render arrows from an Abstraction to its bound variables, we need
	 * to find all layout elements corresponding to a certain abstraction's binding
	 */
	public Set<SVGLayoutElement> boundVariableLayoutElements() {
		return boundVariableLayoutElements(0);
	}
	
	public Set<SVGLayoutElement> boundVariableLayoutElements(int deBruijnIndex) {
		Set<SVGLayoutElement> res = new HashSet<>();
		if (type == LayoutItemType.ABS) {
			deBruijnIndex += 1;
		} else if (term != null && term instanceof BoundVariable && ((BoundVariable) term).getDeBruijnIndex() == deBruijnIndex) {
			res.add(this);
			return res;
		}
		for (SVGLayoutElement child : children) {
			res.addAll(child.boundVariableLayoutElements(deBruijnIndex));
		}
		return res;
	}
	

	
	public Set<OMSVGElement> renderPacman() {
		// m 978.895 193.92
		// a rx      ry      angle 0 0 dx       dy
		// a 14.4852 14.4852 0     0 0 -14.4843 14.4844
		// a 14.4852 14.4852 0     0 0 14.4843 14.4863
		// a 14.4852 14.4852 0     0 0 7.4629 -2.08789
		// l -6.9082 -13.6738
		// l 5.9688 -11.6406
		// a 14.4852 14.4852 0     0 0 -6.5235 -1.56836
		// Z

		// TODO maybe use pacmanRadius?
		OMSVGPathElement pacman = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = pacman.getPathSegList();
		segs.appendItem(pacman.createSVGPathSegMovetoAbs(this.abs_x + 14.4844f, this.abs_y - 14.4844f));
		segs.appendItem(pacman.createSVGPathSegArcRel(-14.4843f, 14.4844f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegArcRel(14.4843f, 14.4863f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegArcRel(7.4629f, -2.08789f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegLinetoRel(-6.9082f, -13.6738f));
		segs.appendItem(pacman.createSVGPathSegLinetoRel(5.9688f, -11.6406f));
		segs.appendItem(pacman.createSVGPathSegArcRel(-6.5235f, -1.56836f, 14.4852f, 14.4852f, 0f, false, false));
		segs.appendItem(pacman.createSVGPathSegClosePath());

		Set<OMSVGElement> res = new HashSet<>();
		res.add(pacman);
		return res;
	}
	
	public OMSVGElement renderVar() {
		// somewhat hacky, but we want bound variables to be basically invisible, but take up a certain amount of space
		// TODO maybe make a rect instead?
		return renderText("  ");
	}
	
	public OMSVGElement renderChevron() {
		OMSVGPathElement chevron = PlugDiagramRenderer.doc.createSVGPathElement();
		OMSVGPathSegList segs = chevron.getPathSegList();
        segs.appendItem(chevron.createSVGPathSegMovetoAbs(this.abs_x + this.width, this.abs_y));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(-this.width, this.height / 2));
        segs.appendItem(chevron.createSVGPathSegLinetoRel(this.width, this.height / 2));
        chevron.setAttribute("stroke-width", PlugDiagramRenderer.strokeWidth);
        chevron.setAttribute("stroke", "#000000");
        chevron.setAttribute("fill", "none");
        
		return chevron;
	}
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = new HashSet<>();
		switch (this.type) {
		case PACMAN:
			res = renderPacman();
			break;
		case ABS:
			break;
		case TEXT:
			res.add(renderText(this.text));
			break;
		case RECT:
			res.add(renderRoundedRect());
			break;
		case CHEVRON:
			res.add(renderChevron());
			break;
		default:
			res.add(renderDebugRect(null));
			break;
		}
		for (SVGLayoutElement child : children) {
			res.addAll(child.render());
		}
		return res;
		
	}
	
	private OMSVGElement renderText(String text) {
		OMSVGTextElement elem = PlugDiagramRenderer.doc.createSVGTextElement(abs_x, abs_y+PlugDiagramRenderer.fontSize, OMSVGLength.SVG_LENGTHTYPE_PX, text);
		elem.setAttribute("font-size", Float.toString(PlugDiagramRenderer.fontSize) + "px");
		elem.setAttribute("font-family", "monospace");
		return elem;
	}
	
	private OMSVGElement renderDebugRect(String stroke) {
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, 0, 0);
		rect.setAttribute("stroke", stroke == null ? "#DD0000" : stroke);
		rect.setAttribute("stroke-width", PlugDiagramRenderer.strokeWidth);
		rect.setAttribute("fill", "none");
		return rect;
	}
	
	private OMSVGElement renderRoundedRect() {
		
		OMSVGRectElement rect = PlugDiagramRenderer.doc.createSVGRectElement(abs_x, abs_y, width, height, 2* height / 5, 2 * height / 5);
		rect.setAttribute("stroke", "#000000");
		rect.setAttribute("stroke-width", PlugDiagramRenderer.strokeWidth);
		rect.setAttribute("fill", "none");
		return rect;
	}
	
}
