package edu.kit.wavelength.client.view.update;

import java.util.Set;

import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

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
	
	static final float fontSize = 20f;
	static final float chevronSharpness = 0.65f;
	static final float strokeWidth = 2f;
	private static final float spacing = 7f;
	
	static final float pacmanRadius = 14.5f;
	static final float pacmanOverlap = pacmanRadius;
	
	static final float arrowheadWidth = 30f;
	static final float arrowheadHeight = 12f;
	static final float arrowStrokeWidth = 8f;
	static final float arrowOverlap = 10f;
	
	static final float boundVarRectWidth = 30f;
	static final float boundVarRectHeight = 30f;
	static final String black = "#000000";  // readability
	static OMSVGDocument doc;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {
		target.addStyleName("autoHighlight");

		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		
		SVGElement root = PlugDiagramRenderer.layoutLambdaTerm(t, nextRedex, null, target);
		root.translate(spacing, spacing);
		root.clearAbsoluteLayout();
		root.calculateAbsoluteLayout();
		
		svg.setAttribute("width", Float.toString(root.width + root.x + spacing) + "px");
		svg.setAttribute("height", Float.toString(root.height + root.y + spacing) + "px");
		
		for (OMSVGElement el : root.render()) {
			svg.appendChild(el);
		}
		
		target.getElement().appendChild(svg.getElement());
	}
	
	/**
	 * parentRedex only really applies to Application and Abstraction
	 */
	private static SVGElement layoutLambdaTerm(LambdaTerm term, LambdaTerm nextRedex, Application parentRedex, Panel wrapper) {
		if (term instanceof Application) return layoutApplication((Application) term, nextRedex, wrapper);
		if (term instanceof Abstraction) return layoutAbstraction((Abstraction) term, nextRedex, parentRedex, wrapper);
		if (term instanceof BoundVariable) return layoutBoundVariable((BoundVariable) term, nextRedex);
		if (term instanceof FreeVariable) return layoutFreeVariable((FreeVariable) term, nextRedex);
		if (term instanceof NamedTerm) return layoutNamedTerm((NamedTerm) term, nextRedex);
		if (term instanceof PartialApplication) return layoutPartialApplication((PartialApplication) term, nextRedex);
		// Should be dead code, unless someone adds new types of LambdaTerms
		throw new UnsupportedOperationException("Don't know how to layout terms of type " + term.getClass().getCanonicalName() + "as plug diagram");
	}
	
	private static SVGElement layoutPartialApplication(PartialApplication term, LambdaTerm nextRedex) {
		// "'".repeat(term.getNumReceived()) for java <11, see https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
		String primes = new String(new char[term.getNumReceived()]).replace("\0", "'");
		return layoutBorderedText(term.getName() + primes);
	}

	private static SVGElement layoutBorderedText(String text) {
		SVGElement border = new SVGRoundedRectElement(false);
		SVGElement textElem = new SVGTextElement(text);
		border.width = textElem.width + 2*spacing;
		border.height = textElem.height + 2*spacing;
		textElem.translate(spacing, spacing);
		border.addChild(textElem);
		return border;
	}
	
	
	private static SVGElement layoutNamedTerm(NamedTerm term, LambdaTerm parentRedex) {
		if (parentRedex == null) {
			// print like a free variable
			return layoutBorderedText(term.getName());
		}
		// we're a stand-in for something reducible, let's add a click handler TODO
		return layoutBorderedText(term.getName());
	}


	private static SVGElement layoutFreeVariable(FreeVariable term, LambdaTerm nextRedex) {
		return new SVGTextElement(term.getName());
	}


	private static SVGElement layoutBoundVariable(BoundVariable term, LambdaTerm nextRedex) {
		SVGElement var = new SVGVariableElement(term);
		return var;
	}


	private static SVGElement layoutAbstraction(Abstraction term, LambdaTerm nextRedex, Application thisRedex, Panel wrapper) {
		boolean isNextRedex = nextRedex != null && nextRedex == thisRedex;
		// abs is the container element, it should encompass all others
		SVGElement abs = new SVGAbstractionElement(term);
		SVGElement body = layoutLambdaTerm(term.getInner(), nextRedex, null, wrapper);
		SVGElement pacman = new SVGPacmanElement(isNextRedex, thisRedex, wrapper);
		pacman.width = pacmanRadius * 2;
		pacman.height = pacmanRadius * 2;

		pacman.translate(body.x + body.width + spacing, spacing);

		abs.addChild(body);
		// We don't want pacman to bump against the frame, no matter how small the body may be
		abs.height = Math.max(body.height, pacman.y + pacmanRadius + spacing);

		Set<SVGElement> substitution_targets = abs.boundVariableLayoutElements();
		SVGElement bottomBar = null;
		if (!substitution_targets.isEmpty()) {
			// we need space for arrows, but maybe centering has already provided that for us
			abs.height = Math.max(body.y + body.height + spacing + arrowStrokeWidth, abs.height);
			float leftmost = pacman.x;
			
			// we will need the positions of BoundVariable layout elements relative to this element
			abs.clearAbsoluteLayout();
			abs.calculateAbsoluteLayout();
			
			bottomBar = new SVGLineElement(arrowStrokeWidth, isNextRedex, "round");
			// we need bottomBar to be at its final y pos before creating vertical arrow segments
			bottomBar.translate(0, body.y + body.height + 2*spacing);
			
			for (SVGElement var : abs.boundVariableLayoutElements(0)) {
				leftmost = Math.min(leftmost, var.abs_x);
				// draw arrow head for this child
				SVGElement arrowhead = new SVGArrowheadElement(isNextRedex);
				
				float arrowhead_x = var.abs_x + /* centering */ (var.width - arrowhead.width)/2;
				float arrowhead_y = var.abs_y + var.height - arrowOverlap;
				arrowhead.translate(arrowhead_x, arrowhead_y);
				abs.addChild(arrowhead);
				
				SVGLineElement lastSegmentBackground = new SVGLineElement(arrowStrokeWidth+6, false, "#FFFFFF", "butt");
				lastSegmentBackground.translate(arrowhead_x + arrowhead.width/2, arrowhead.y + arrowhead.height);
				lastSegmentBackground.height = bottomBar.y - lastSegmentBackground.y;
				abs.addChild(lastSegmentBackground);
				SVGLineElement lastSegment = new SVGLineElement(arrowStrokeWidth, isNextRedex, "butt");
				lastSegment.translate(lastSegmentBackground.x, lastSegmentBackground.y - 1);
				lastSegment.height = lastSegmentBackground.height + 1;
				abs.addChild(lastSegment);
				
				
			}
			bottomBar.translate(leftmost + arrowheadWidth/2, 0);
			bottomBar.width += pacman.x - leftmost - arrowheadWidth/2 + pacmanOverlap;
		} else {
			// no substitution targets, no arrows... center body
			body.translate(0, (abs.height - body.height)/2f );
		}
		// vertically center pacman
		pacman.translate(0, (abs.height - 2*spacing)/2f );
		
		
		if (!substitution_targets.isEmpty()) {
			SVGLineElement firstSegment = new SVGLineElement(arrowStrokeWidth, isNextRedex, "butt");
			// the pixel offset is to ensure the line doesn't just touch the pacman in a single point
			firstSegment.translate(pacman.x + pacmanOverlap, pacman.y + pacmanOverlap - 1);
			firstSegment.height = bottomBar.y - pacman.y - pacmanOverlap + 1;
			abs.addChild(firstSegment);
			abs.addChild(bottomBar);
		}
		// finally, draw pacman over arrow
		abs.addChild(pacman);
		abs.width += body.width + spacing + pacman.width;

		
		return abs;
	}


	public static SVGElement layoutApplication(Application app, LambdaTerm nextRedex, Panel wrapper) {
		boolean isNextRedex = nextRedex == app;
		boolean isRedex = isNextRedex || app.acceptVisitor(new IsRedexVisitor());
		
		SVGRoundedRectElement roundedRect = new SVGRoundedRectElement(isNextRedex);
		SVGElement appElem = new SVGElement();
		roundedRect.addChild(appElem);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGElement lres;
		if (isRedex) {
			lres = layoutLambdaTerm(left, nextRedex, app, wrapper);
		} else {
			lres = layoutLambdaTerm(left, nextRedex, null, wrapper);
		}
		
		LambdaTerm right = app.getRightHandSide();
		SVGElement rres = layoutLambdaTerm(right, nextRedex, null, wrapper);
	
		float maxheight = Math.max(lres.height, rres.height);
		lres.translate(
				/* (\x.\x.\x....) tends to make lres overlap the border*/
				2 * spacing,
				/* vertical centering */
				Math.max(0, (maxheight - lres.height) / 2 + spacing));
		rres.translate(spacing, Math.max(0, (maxheight - rres.height) / 2 + spacing));
		
		SVGElement chevron = new SVGChevronElement(isNextRedex);
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
		// if rres is less wide than the roundedRect's radius, the chevron get all ugly
		if (rres.width < roundedRect.getRadius()) {
			roundedRect.width += roundedRect.getRadius() - rres.width;
		}

		
		appElem.addChild(lres);
		appElem.addChild(chevron);
		appElem.addChild(rres);
		
		return roundedRect;
	}
}
