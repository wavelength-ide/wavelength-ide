package edu.kit.wavelength.client.view.update;

import java.util.Set;

import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;

import edu.kit.wavelength.client.model.term.*;

public class PlugDiagramRenderer {
	
	/**
	 * 
	 * A layout tree for rendering plug diagrams as SVGs. The reason for this data structure is
	 * because once created, SVG nodes aren't always simple to move around.
	 * Paths, for instance, always have location (0,0), and move the pen as their first command.
	 * Another reason is because we want to do the layout hierarchically, but render one flat SVG
	 * so as not to bump into any depth limits. Rendering a single, flat SVG without simple hierarchical
	 * relative positioning is a pain.
	 * 
	 * So generally, we create a bunch of layout elements and manually do the layout, spacing, etc.
	 * and "rendering" it means creating the corresponding SVG nodes with the newly computed absolute positioning.
	 * 
	 * The coordinate system is as follows:
	 * +--> x
	 * |
	 * V y
	 * 
	 * All layout functions have a local coordinate system centered at the top-left corner of the AST node.
	 */
	
	static final float fontSize = 17f;
	private static final float chevronSharpness = 0.6f;
	static final float strokeWidth = 2f;
	private static final float spacing = 7f;
	static final float pacmanRadius = 14.4844f;
	static final float arrowWidth = 5f;
	static final float arrowOverlap = 10f;
	static final float boundVarRectWidth = 30f;
	static final float boundVarRectHeight = 30f;
	static OMSVGDocument doc;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {

		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		svg.setAttribute("width", "100%");
		
		
		SVGElement root = PlugDiagramRenderer.layoutLambdaTerm(t);
		root.translate(spacing, spacing);
		root.clearAbsoluteLayout();
		root.calculateAbsoluteLayout();
		
		svg.setAttribute("height", Float.toString(root.height + 2*spacing) + "px");
		for (OMSVGElement el : root.render()) {
			svg.appendChild(el);
		}
		
		target.getElement().appendChild(svg.getElement());
	}
	
	private static SVGElement layoutLambdaTerm(LambdaTerm term) {
		if (term instanceof Application) return layoutApplication((Application) term);
		if (term instanceof Abstraction) return layoutAbstraction((Abstraction) term);
		if (term instanceof BoundVariable) return layoutBoundVariable((BoundVariable) term);
		if (term instanceof FreeVariable) return layoutFreeVariable((FreeVariable) term);
		if (term instanceof NamedTerm) return layoutNamedTerm((NamedTerm) term);
		GWT.log(term.getClass().toString() + "has been forgotten!");
		return null;
	}
	
	private static SVGElement layoutBorderedText(String text) {
		SVGElement border = new SVGRoundedRectElement();
		SVGElement textElem = new SVGTextElement(text);
		border.width = textElem.width + 2*spacing;
		border.height = textElem.height + 2*spacing;
		textElem.translate(spacing, spacing);
		border.addChild(textElem);
		return border;
	}
	
	private static SVGElement layoutNamedTerm(NamedTerm term) {
		// print like a free variable
		return layoutBorderedText(term.getName());
	}


	private static SVGElement layoutFreeVariable(FreeVariable term) {
		return new SVGTextElement(term.getName());
	}


	private static SVGElement layoutBoundVariable(BoundVariable term) {
		SVGElement var = new SVGVariableElement(term);
		return var;
	}


	private static SVGElement layoutAbstraction(Abstraction term) {
		// abs is the container element, it should encompass all others
		SVGElement abs = new SVGAbstractionElement(term);
		SVGElement lam = new SVGTextElement("λ" + term.getPreferredName() + ".");
		SVGElement body = layoutLambdaTerm(term.getInner());
		SVGElement pacman = new SVGPacmanElement();
		pacman.width = pacmanRadius * 2;
		pacman.height = pacmanRadius * 2;

		lam.translate(0, /* centering */ Math.max(0, (body.height - lam.height)/2f ));
		body.translate(lam.width, 0);
		
		pacman.translate(body.x + body.width + spacing, 0);

		abs.addChild(lam);
		abs.addChild(body);
		abs.addChild(pacman);
		abs.height = Math.max(lam.height, body.height);

		Set<SVGElement> substitution_targets = abs.boundVariableLayoutElements();
		if (!substitution_targets.isEmpty()) {
			// we need space for arrows, but maybe centering has already provided that for us
			abs.height = Math.max(body.y + body.height + spacing + arrowWidth, abs.height);
			float leftmost = pacman.x;
			
			// we will need the positions of BoundVariable layout elements relative to this element
			abs.clearAbsoluteLayout();
			abs.calculateAbsoluteLayout();
			for (SVGElement var : abs.boundVariableLayoutElements(0)) {
				leftmost = Math.min(leftmost, var.abs_x);
				// draw arrow head for this child
				SVGElement arrowhead = new SVGArrowheadElement();
				arrowhead.width = var.width;
				arrowhead.height = 3*spacing;
				
				arrowhead.translate(var.abs_x, var.abs_y + var.height - arrowOverlap);
				
				abs.addChild(arrowhead);
			}
			SVGElement bottomBar = new SVGLineElement(arrowWidth);
			bottomBar.translate(leftmost, body.y + body.height + spacing);
			bottomBar.width += pacman.x - leftmost;
			
			// vertically center pacman
			pacman.translate(0, abs.height/2f );

			abs.width += lam.width + body.width + spacing + pacman.width;
			
			
			
			abs.addChild(bottomBar);
		}
		
		return abs;
	}


	public static SVGElement layoutApplication(Application app) {
		SVGElement roundedRect = new SVGRoundedRectElement();
		SVGElement appElem = new SVGApplicationElement(app);
		roundedRect.addChild(appElem);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGElement lres = layoutLambdaTerm(left);
		
		LambdaTerm right = app.getRightHandSide();
		SVGElement rres = layoutLambdaTerm(right);
	
		float maxheight = Math.max(lres.height, rres.height);
		lres.translate(spacing, Math.max(0, (maxheight - lres.height) / 2 + spacing));
		rres.translate(spacing, Math.max(0, (maxheight - rres.height) / 2 + spacing));
		
		SVGElement chevron = new SVGChevronElement();
		chevron.height = maxheight + 2*spacing; // spacing above and below
		chevron.width = chevron.height * chevronSharpness / 2;
		
		if (left instanceof Abstraction) {
			// make the left part of the Application a bit smaller, so the abstraction's
			// pacman plug slots *just right* into the chevron
			lres.width -= pacmanRadius;
		} else {
			chevron.translate(spacing, 0);
		}

		chevron.translate(lres.x + lres.width, 0);
		rres.translate(chevron.x + chevron.width, 0);
		
		roundedRect.width = rres.x + rres.width + spacing;
		roundedRect.height = chevron.height;
		
		appElem.addChild(lres);
		appElem.addChild(chevron);
		appElem.addChild(rres);
		
		return roundedRect;
	}
}
