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
	static OMSVGDocument doc;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {

		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		
		SVGElement root = PlugDiagramRenderer.layoutLambdaTerm(t);
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
	
	private static SVGElement layoutLambdaTerm(LambdaTerm term) {
		if (term instanceof Application) return layoutApplication((Application) term);
		if (term instanceof Abstraction) return layoutAbstraction((Abstraction) term);
		if (term instanceof BoundVariable) return layoutBoundVariable((BoundVariable) term);
		if (term instanceof FreeVariable) return layoutFreeVariable((FreeVariable) term);
		if (term instanceof NamedTerm) return layoutNamedTerm((NamedTerm) term);
		if (term instanceof PartialApplication) return layoutPartialApplication((PartialApplication) term);
		GWT.log(term.getClass().toString() + "has been forgotten!");
		return null;
	}
	
	private static SVGElement layoutPartialApplication(PartialApplication term) {
		// String.repeat for java <11
		String primes = new String(new char[term.getNumReceived()]).replace("\0", "'");
		return layoutBorderedText(term.getName() + primes);
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
		SVGElement body = layoutLambdaTerm(term.getInner());
		SVGElement pacman = new SVGPacmanElement();
		pacman.width = pacmanRadius * 2;
		pacman.height = pacmanRadius * 2;

		pacman.translate(body.x + body.width + spacing, 0);

		abs.addChild(body);
		abs.addChild(pacman);
		abs.height = body.height;

		Set<SVGElement> substitution_targets = abs.boundVariableLayoutElements();
		SVGElement bottomBar = null;
		if (!substitution_targets.isEmpty()) {
			// we need space for arrows, but maybe centering has already provided that for us
			abs.height = Math.max(body.y + body.height + spacing + arrowStrokeWidth, abs.height);
			float leftmost = pacman.x;
			
			// we will need the positions of BoundVariable layout elements relative to this element
			abs.clearAbsoluteLayout();
			abs.calculateAbsoluteLayout();
			
			bottomBar = new SVGLineElement(arrowStrokeWidth);
			// we need bottomBar to be at its final y pos before creating vertical arrow segments
			bottomBar.translate(0, body.y + body.height + 2*spacing);
			
			for (SVGElement var : abs.boundVariableLayoutElements(0)) {
				leftmost = Math.min(leftmost, var.abs_x);
				// draw arrow head for this child
				SVGElement arrowhead = new SVGArrowheadElement();
				
				float arrowhead_x = var.abs_x + /* centering */ (var.width - arrowhead.width)/2;
				float arrowhead_y = var.abs_y + var.height - arrowOverlap;
				arrowhead.translate(arrowhead_x, arrowhead_y);
				abs.addChild(arrowhead);
				
				SVGLineElement lastSegmentBackground = new SVGLineElement(arrowStrokeWidth+6, "#FFFFFF", "butt");
				lastSegmentBackground.translate(arrowhead_x + arrowhead.width/2, arrowhead.y + arrowhead.height);
				lastSegmentBackground.height = bottomBar.y - lastSegmentBackground.y;
				abs.addChild(lastSegmentBackground);
				SVGLineElement lastSegment = new SVGLineElement(arrowStrokeWidth);
				lastSegment.translate(lastSegmentBackground.x, lastSegmentBackground.y);
				lastSegment.height = lastSegmentBackground.height;
				abs.addChild(lastSegment);
				
				
			}
			bottomBar.translate(leftmost + arrowheadWidth/2, 0);
			bottomBar.width += pacman.x - leftmost - arrowheadWidth/2 + pacmanOverlap;
		}
		// vertically center pacman
		pacman.translate(0, abs.height/2f );
		
		
		if (!substitution_targets.isEmpty()) {
			SVGLineElement firstSegment = new SVGLineElement(arrowStrokeWidth);
			firstSegment.translate(pacman.x + pacmanOverlap, pacman.y + pacmanOverlap);
			firstSegment.height = bottomBar.y - pacman.y - pacmanOverlap;
			abs.addChild(firstSegment);
			abs.addChild(bottomBar);
		}

		abs.width += body.width + spacing + pacman.width;

		
		return abs;
	}


	public static SVGElement layoutApplication(Application app) {
		SVGRoundedRectElement roundedRect = new SVGRoundedRectElement();
		SVGElement appElem = new SVGElement();
		roundedRect.addChild(appElem);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGElement lres = layoutLambdaTerm(left);
		
		LambdaTerm right = app.getRightHandSide();
		SVGElement rres = layoutLambdaTerm(right);
	
		float maxheight = Math.max(lres.height, rres.height);
		lres.translate(
				/* (\x.\x.\x....) tends to make lres overlap the border*/
				2 * spacing,
				/* vertical centering */
				Math.max(0, (maxheight - lres.height) / 2 + spacing));
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
