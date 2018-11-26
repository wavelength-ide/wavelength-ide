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

	private static class SVGLayoutElement {
		public float x;
		public float y;
		public float width;
		public float height;
		private Class<?> TermType;
//		public Set<OMSVGElement> elems;

		public void moveTo(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public void translate(int x, int y) {
			this.x += x;
			this.y += y;
		}
		
		public Set<OMSVGElement> render() {
			// TODO
			return null;
		}
		
		
		public void recalc() {
			left = Float.MAX_VALUE;
			top = Float.MAX_VALUE;
			right = Float.MIN_VALUE;
			bottom = Float.MIN_VALUE;
			for (OMSVGElement el : elems) {
				float x = pxToFloat(el.getAttribute("x"));
				float y = pxToFloat(el.getAttribute("y"));
				float width = pxToFloat(el.getAttribute("width"));
				float height = pxToFloat(el.getAttribute("height"));
				left = Math.min(x,  left);
				right = Math.max(x+width,  right);
				top = Math.min(y, top);
				bottom = Math.max(y+height, bottom);
			}
		}
		
		public void union(PartialResult other) {
			if (this.elems.addAll(other.elems)) {
				left = Math.min(left, other.left);
				top = Math.min(top,  other.top);
				bottom = Math.max(bottom, other.bottom);
				right = Math.max(right,  other.right);
			}
		}

		private void shift(float x, float y) {
			this.left += x;
			this.right += x;
			this.top += y;
			this.bottom += y;
			for (OMSVGElement e : elems) {
				PlugDiagramRenderer.shift(e, x, y);
			}
		}
	
	}
	
	private static final float fontSize = 25f;
	private static OMSVGDocument doc;
	private static Logger l;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {

		PlugDiagramRenderer.l = java.util.logging.Logger.getLogger("SVGDebug");
		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		OMSVGElement rr = renderRoundedRectAt(0, 0, 120, 50);
		
		for (OMSVGElement el : PlugDiagramRenderer.renderApplication((Application) t).elems) {
			svg.appendChild(el);
		}
		
		target.getElement().appendChild(svg.getElement());
	}
	
	public static PartialResult renderTextAt(String text, float x, float y) {
		OMSVGTextElement elem = doc.createSVGTextElement(x, y+fontSize, OMSVGLength.SVG_LENGTHTYPE_PX, text);
		elem.setAttribute("font-size", Float.toString(fontSize));
		return elem;
		
	}
	
	public static OMSVGElement renderBoundVariableAt(BoundVariable v, float x, float y) {
		// somewhat hacky, but we want bound variables to be basically invisible, but take up a certain amount of space
		// TODO maybe make a rect instead?
		return renderTextAt("  ", x, y);
	}
	
	public static OMSVGElement renderRoundedRectAt(float x, float y, float width, float height) {
		OMSVGRectElement rect = doc.createSVGRectElement(x, y, width, height, 2* height / 5, 2 * height / 5);
		rect.setAttribute("stroke", "#000000");
		rect.setAttribute("stroke-width", "2");
		rect.setAttribute("fill", "none");
		return rect;
	}
	
	private static void shift(OMSVGElement e, float x, float y) {
		if (e instanceof OMSVGPathElement) {
			// path elements don't do x,y attributes. Instead, use transform: translate(x,y)
			// FIXME TODO
			//l.log(Level.SEVERE, e.getAttribute("transform"));
		} else {
			l.log(Level.SEVERE, e.getAttribute("x"));
			float oldx = pxToFloat(e.getAttribute("x"));
			float oldy = pxToFloat(e.getAttribute("y"));
			e.setAttribute("x", Float.toString(oldx + x));
			e.setAttribute("y", Float.toString(oldy + y));
		}
	}
	
	
	private static PartialResult renderLambdaTerm(LambdaTerm t) {
		return null;
	}
	
	private static float pxToFloat(String attribute) {
		return Float.parseFloat(attribute.substring(0, attribute.length()-2));
	}
	
	public static PartialResult renderApplication(Application app) {
		LambdaTerm left = app.getLeftHandSide();
		PartialResult lres = renderLambdaTerm(left);
		LambdaTerm right = app.getRightHandSide();
		PartialResult rres = renderLambdaTerm(right);
		// float maxheight = Math.max(lres.bottom - lres.top, rres.bottom - rres.top);
		
		// that's the height we'll need to scale the chevron to...
		//OMSVGPathElement chevron = doc.createSVGPathElement();
		//OMSVGPathSegList segments = chevron.getPathSegList();
		// TODO
		//segments.appendItem(chevron.createSVGPathSegMovetoAbs(500, 42));
		//shift(chevron, 10, 4422);
		
		Set<OMSVGElement> elems = new HashSet<>(5);
		
		elems.add(renderRoundedRectAt(0, 0, 100, 20));
		OMSVGElement text = renderTextAt("Test", 0, 0);
		
		elems.add(text);
		PartialResult res = new PartialResult(elems);
		
		return res;
	}
}
