package edu.kit.wavelength.client.view.update;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vectomatic.dom.svg.OMSVGAnimatedTransformList;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGEllipseElement;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGPathSeg;
import org.vectomatic.dom.svg.OMSVGPathSegList;
import org.vectomatic.dom.svg.OMSVGRect;
import org.vectomatic.dom.svg.OMSVGRectElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.OMSVGTextElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.SVGConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.kit.wavelength.client.model.term.*;

public class PlugDiagramRenderer {
	private enum LayoutItemType {
		RECT,
		ABS,
		VAR,
		TEXT,
		
		CHEVRON,
		PACMAN,
		// ...
	}

	private static class SVGLayoutElement {
		public float x;
		public float y;
		public float width;
		public float height;
		public LayoutItemType type;
		private List<SVGLayoutElement> children;
		private LambdaTerm term;
		private String text;

		public SVGLayoutElement(String text) {
			this(LayoutItemType.TEXT);
			this.text = text;
			this.width = PlugDiagramRenderer.fontSize * text.length() * 0.6f;
			this.height = PlugDiagramRenderer.fontSize;
		}

		public SVGLayoutElement(LayoutItemType type) {
			this(type, (LambdaTerm) null);
		}
		
		public SVGLayoutElement(LayoutItemType type, LambdaTerm term) {
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
		
		/**
		 * propagates relative positions through the tree, turning relative x-y-coordinates to absolute ones
		 */
		public void finalizeLayout() {
			for (SVGLayoutElement child : children) {
				child.translate(this.x, this.y);
				child.finalizeLayout();
			}
			
		}
		
		public void translate(float x, float y) {
			this.x += x;
			this.y += y;
		}
		
		public Set<OMSVGElement> renderAbs() {
			// m 978.895 193.92
			// a rx      ry      angle 0 0 dx       dy
			// a 14.4852 14.4852 0     0 0 -14.4843 14.4844
			// a 14.4852 14.4852 0     0 0 14.4843 14.4863
			// a 14.4852 14.4852 0     0 0 7.4629 -2.08789
			// l -6.9082 -13.6738
			// l 5.9688 -11.6406
			// a 14.4852 14.4852 0     0 0 -6.5235 -1.56836
			// Z

			OMSVGPathElement pacman = doc.createSVGPathElement();
			OMSVGPathSegList segs = pacman.getPathSegList();
			segs.appendItem(pacman.createSVGPathSegMovetoAbs(this.x, this.y));
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
		public Set<OMSVGElement> renderRect() {
			// TODO return null;
			return null;
		}
		public OMSVGElement renderVar() {
			// somewhat hacky, but we want bound variables to be basically invisible, but take up a certain amount of space
			// TODO maybe make a rect instead?
			return renderText("  ");
		}
		public OMSVGElement renderChevron() {
			OMSVGPathElement chevron = doc.createSVGPathElement();
			OMSVGPathSegList segs = chevron.getPathSegList();
            segs.appendItem(chevron.createSVGPathSegMovetoAbs(this.x + this.width, this.y));
            segs.appendItem(chevron.createSVGPathSegLinetoRel(-this.width, this.height / 2));
            segs.appendItem(chevron.createSVGPathSegLinetoRel(this.width, this.height / 2));
            chevron.setAttribute("stroke-width", strokeWidth);
            chevron.setAttribute("stroke", "#000000");
            chevron.setAttribute("fill", "none");
            
			return chevron;
		}
		public Set<OMSVGElement> render() {
			Set<OMSVGElement> res = new HashSet<>();
			switch (this.type) {
			case ABS:
				res = renderAbs();
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
			OMSVGTextElement elem = doc.createSVGTextElement(x, y+fontSize, OMSVGLength.SVG_LENGTHTYPE_PX, text);
			elem.setAttribute("font-size", Float.toString(fontSize) + "px");
			elem.setAttribute("font-family", "monospace");
			return elem;
		}
		
		private OMSVGElement renderDebugRect(String stroke) {
			OMSVGRectElement rect = doc.createSVGRectElement(x, y, width, height, 0, 0);
			rect.setAttribute("stroke", stroke == null ? "#DD0000" : stroke);
			rect.setAttribute("stroke-width", strokeWidth);
			rect.setAttribute("fill", "#101010");
			return rect;
		}
		
		private OMSVGElement renderRoundedRect() {
			
			OMSVGRectElement rect = doc.createSVGRectElement(x, y, width, height, 2* height / 5, 2 * height / 5);
			rect.setAttribute("stroke", "#000000");
			rect.setAttribute("stroke-width", strokeWidth);
			rect.setAttribute("fill", "none");
			return rect;
		}
		private float pxToFloat(String attribute) {
			return Float.parseFloat(attribute.substring(0, attribute.length()-2));
		}

	}
	
	private static final float fontSize = 17f;
	private static final float chevronSharpness = 0.8f;
	private static final String strokeWidth = "2px";
	private static final float spacing = 7f;
	private static OMSVGDocument doc;
	
	private static Logger l;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {

		PlugDiagramRenderer.l = java.util.logging.Logger.getLogger("SVGDebug");
		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		svg.setAttribute("width", "100%");
		
		SVGLayoutElement root = PlugDiagramRenderer.layoutLambdaTerm(t);
		root.translate(spacing, spacing);
		root.finalizeLayout();
		
		for (OMSVGElement el : root.render()) {
			svg.appendChild(el);
		}
		
		target.getElement().appendChild(svg.getElement());
	}
	
	
	private static SVGLayoutElement layoutLambdaTerm(LambdaTerm term) {
		if (term instanceof Application) return layoutApplication((Application) term);
		if (term instanceof Abstraction) return layoutAbstraction((Abstraction) term);
		if (term instanceof BoundVariable) return layoutBoundVariable((BoundVariable) term);
		if (term instanceof FreeVariable) return layoutFreeVariable((FreeVariable) term);
		if (term instanceof NamedTerm) return layoutNamedTerm((NamedTerm) term);
		
		return null;
	}
	
	private static SVGLayoutElement layoutBorderedText(String text) {
		SVGLayoutElement border = new SVGLayoutElement(LayoutItemType.RECT);
		SVGLayoutElement textElem = new SVGLayoutElement(text);
		border.width = textElem.width + 2*spacing;
		border.height = textElem.height + 2*spacing;
		textElem.translate(spacing, spacing);
		border.addChild(textElem);
		return border;
	}
	
	private static SVGLayoutElement layoutNamedTerm(NamedTerm term) {
		// print like a free variable
		return layoutBorderedText(term.getName());
	}


	private static SVGLayoutElement layoutFreeVariable(FreeVariable term) {
		// TODO Auto-generated method stub
		return new SVGLayoutElement(term.getName());
	}


	private static SVGLayoutElement layoutBoundVariable(BoundVariable term) {
		// TODO Auto-generated method stub
		return layoutBorderedText(Integer.toString(term.getDeBruijnIndex()));
	}


	private static SVGLayoutElement layoutAbstraction(Abstraction term) {
		SVGLayoutElement lam = new SVGLayoutElement("λ" + term.getPreferredName() + ".");
		SVGLayoutElement body = layoutLambdaTerm(term.getInner());
		SVGLayoutElement pacman = new SVGLayoutElement(LayoutItemType.PACMAN);
		
		lam.translate(0, /* centering */ Math.max(0, (body.height - lam.height)/2f ));
		body.translate(lam.width, 0);
		lam.width += body.width;
		lam.height = Math.max(lam.height, body.height);
		body.translate(-lam.x, -lam.y);
		
		pacman.translate(body.x + body.width, lam.y);
		lam.width += pacman.width;
		lam.addChild(pacman);
		
		// pretend that lam encloses body
		lam.addChild(body);
		return lam;
	}


	public static SVGLayoutElement layoutApplication(Application app) {
		SVGLayoutElement roundedRect = new SVGLayoutElement(LayoutItemType.RECT, app);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGLayoutElement lres = layoutLambdaTerm(left);
		
		LambdaTerm right = app.getRightHandSide();
		SVGLayoutElement rres = layoutLambdaTerm(right);
	
		float maxheight = Math.max(lres.height, rres.height);
		lres.translate(spacing, Math.max(0, (maxheight - lres.height) / 2 + spacing));
		rres.translate(spacing, Math.max(0, (maxheight - rres.height) / 2 + spacing));
		
		SVGLayoutElement chevron = new SVGLayoutElement(LayoutItemType.CHEVRON);
		chevron.height = maxheight + 2*spacing; // spacing above and below
		chevron.width = chevron.height * chevronSharpness / 2;
		chevron.translate(lres.x + lres.width, 0);
		rres.translate(chevron.x + chevron.width, 0);
		
		roundedRect.width = rres.x + rres.width + spacing;
		roundedRect.height = chevron.height;
		
		roundedRect.addChild(lres);
		roundedRect.addChild(chevron);
		roundedRect.addChild(rres);
		
		return roundedRect;
	}
}
