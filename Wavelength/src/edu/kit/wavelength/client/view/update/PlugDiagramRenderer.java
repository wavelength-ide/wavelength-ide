package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGAnimateElement;
import org.vectomatic.dom.svg.OMSVGDefsElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGFECompositeElement;
import org.vectomatic.dom.svg.OMSVGFEFloodElement;
import org.vectomatic.dom.svg.OMSVGFEGaussianBlurElement;
import org.vectomatic.dom.svg.OMSVGFEMergeElement;
import org.vectomatic.dom.svg.OMSVGFEMergeNodeElement;
import org.vectomatic.dom.svg.OMSVGFilterElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;

import edu.kit.wavelength.client.model.term.*;

public class PlugDiagramRenderer {
	
	/**
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
	 * Each node in the layout tree may optionally be part of an SVGElementGroup. This doesn't affect the hierarchical
	 * positioning of a node, but its location in the resulting SVG. The idea is to render different nodes into different
	 * <g> tags, to allow styling of groups of layout elements via CSS. (i.e. hovering over a redex should highlight all
	 * layout elements specific to this redex)
	 * 
	 * As for the principles of how the diagrams should *look*, the general rule is that the algorithmically
	 * rendered diagrams should look like what a human would produce when instructed to make *good* diagrams.
	 * As a consequence, there's a few special cases we handle to make the diagrams prettier, less confusing,
	 * or more visually instructive, even if it complicates rendering or the transformation from textual representation.
	 * The big two being:
	 * * A top-level abstraction is rendered with a border, because it looks better and keeps diagrams visually consistent
	 * * Bound variables directly within abstractions are also bordered, since this is the only case in which 
	 *   an arrow points at a location that doesn't necessarily look like a "hole". The border fixes that.   
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
	static final float spacing = 7f;
	
	static final float pacmanRadius = 14.5f;
	static final float pacmanOverlap = pacmanRadius - 5;
	
	static final float arrowheadWidth = 30f;
	static final float arrowheadHeight = 12f;
	static final float arrowStrokeWidth = 8f;
	static final float arrowOverlap = 10f;
	
	static final float boundVarRectWidth = 30f;
	static final float boundVarRectHeight = 30f;
	static final String black = "#000000";  // readability
	static final String glowColor = "#009782";
	static final String glowFilterName = "glow";
	static OMSVGDocument doc;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {
		target.addStyleName("autoHighlight");

		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();

		/*
		 * Due to browsers not wiring up SVG animations correctly if you add them via the
		 * DOM API, we have to jump through some hoops to get both animations and click handlers
		 * to work:
		 * First we create an SVG with the animation via the DOM API, and force the browser to
		 * properly register the animation by forcing it to *parse* the HTML definition as text
		 * (Sadly, I found no other way of doing that, no amount of forced redraws or pausing/
		 * unpausing animations did the trick)
		 * With this in place, we fetch the SVG Element, and render into it, registering click
		 * handlers along the way.
		 */
		OMSVGSVGElement draftSvg = doc.createSVGSVGElement();
		// create glowing animation for mouseover elements
		draftSvg.appendChild(getSVGGlowAnimation());
		
		String htmlString = draftSvg.getElement().getString();
		HTML parsedSvgWrapper = new HTML(htmlString);
		Element svgElement = parsedSvgWrapper.getElement().getFirstChildElement();
		target.getElement().appendChild(svgElement);

		
		List<SVGElement> groupRoots = new ArrayList<>();
		SVGElement root = PlugDiagramRenderer.layoutRootLambdaTerm(t, nextRedex, target, groupRoots);
		root.clearAbsoluteLayout();
		root.calculateAbsoluteLayout();

		float maxWidth = root.x + root.width;
		float maxHeight = root.y + root.height;
		for (SVGElement groupRoot : groupRoots) {
			maxWidth = Math.max(maxWidth, groupRoot.x + groupRoot.width);
			maxHeight = Math.max(maxHeight, groupRoot.y + groupRoot.height);
		}
		
		svgElement.setAttribute("width", Float.toString(maxWidth + spacing) + "px");
		svgElement.setAttribute("height", Float.toString(maxHeight + spacing) + "px");
		ListIterator<SVGElement> li = groupRoots.listIterator(groupRoots.size());
		while(li.hasPrevious()) {
			SVGElement groupRoot = li.previous();
			for (OMSVGElement elem : groupRoot.renderHierarchy(groupRoot)) {
				GWT.log("PING");
				svgElement.appendChild(elem.getElement());
			}
		}

		for (OMSVGElement elem : root.renderHierarchy(null)) {
			svgElement.appendChild(elem.getElement());
		}
		


	}
	
	// There's no reason *not* to memoize this.
	private static OMSVGDefsElement memoizedGlowAnimation;
	
	private static OMSVGDefsElement getSVGGlowAnimation() {
		if (memoizedGlowAnimation != null)
			return memoizedGlowAnimation;
		
		OMSVGDefsElement defs = new OMSVGDefsElement();
		OMSVGFilterElement filter = new OMSVGFilterElement();
		// without this, horizontal and vertical lines disappear, because their bbox size is 0x0
		filter.setAttribute("filterUnits", "userSpaceOnUse");
		defs.appendChild(filter);
		filter.setId(glowFilterName);
		filter.setAttribute("x", "-1");
		filter.setAttribute("y", "-1");
		filter.setAttribute("width", "300%");
		filter.setAttribute("height", "300%");
		OMSVGFEGaussianBlurElement blur = new OMSVGFEGaussianBlurElement();
		blur.setAttribute("result", "blurOut");
		blur.setAttribute("in", "");
		blur.setAttribute("stdDeviation", "7");
		filter.appendChild(blur);
		OMSVGFEFloodElement flood = new OMSVGFEFloodElement();
		flood.setId("glowFlood");
		flood.setAttribute("flood-color", glowColor);
		flood.setAttribute("flood-opacity", "1");
		filter.appendChild(flood);
		OMSVGFECompositeElement composite = new OMSVGFECompositeElement();
		composite.setAttribute("in2", "blurOut");
		composite.setAttribute("operator", "in");
		filter.appendChild(composite);
		OMSVGFEMergeElement merge = new OMSVGFEMergeElement();
		merge.appendChild(new OMSVGFEMergeNodeElement());
		OMSVGFEMergeNodeElement mergenode = new OMSVGFEMergeNodeElement();
		mergenode.setAttribute("in", "SourceGraphic");
		merge.appendChild(mergenode);
		filter.appendChild(merge);
		
		OMSVGAnimateElement animation = new OMSVGAnimateElement();
		animation.setAttribute("xlink:href", "#glowFlood");
		animation.setAttribute("attributeName", "flood-opacity");
		animation.setAttribute("repeatCount", "indefinite");
		animation.setAttribute("values", "1;0;1");
		animation.setAttribute("dur", "1s");
		defs.appendChild(animation);
		memoizedGlowAnimation = defs;
		return defs;
	}
	
	/**
	 * parentRedex only really applies to Application and Abstraction
	 */
	private static SVGElement layoutLambdaTerm(LambdaTerm term, LambdaTerm nextRedex, SVGElementGroup group, Panel wrapper, List<SVGElement> groups) {
		if (term instanceof Application) return layoutApplication((Application) term, nextRedex, wrapper, groups);
		if (term instanceof Abstraction) return layoutAbstraction((Abstraction) term, nextRedex, group, wrapper, groups);
		if (term instanceof BoundVariable) return layoutBoundVariable((BoundVariable) term, nextRedex);
		if (term instanceof FreeVariable) return layoutFreeVariable((FreeVariable) term, nextRedex);
		if (term instanceof NamedTerm) return layoutNamedTerm((NamedTerm) term, nextRedex);
		if (term instanceof PartialApplication) return layoutPartialApplication((PartialApplication) term, nextRedex);
		// Should be dead code, unless someone adds new types of LambdaTerms
		throw new UnsupportedOperationException("Don't know how to layout terms of type " + term.getClass().getCanonicalName() + "as plug diagram");
	}
	
	/**
	 * Perform layout of a "whole" lambda term. There's only one special case, compared to layoutLambdaTerm:
	 * Top-level absractions should be wrapped in their own box. I dislike special casing them, but 
	 * "raw" Abstractions on the top level look wrong. 
	 */
	private static SVGElement layoutRootLambdaTerm(LambdaTerm term, LambdaTerm nextRedex, Panel wrapper, List<SVGElement> groups) {
		SVGElement root;
		if (term instanceof Abstraction) {
			SVGElement elem = layoutLambdaTerm(term, nextRedex, null, wrapper, groups);
			root = RoundedRectAround(elem);
		} else {
			root = layoutLambdaTerm(term, nextRedex, null, wrapper, groups);
		}
		root.translate(spacing, spacing);
		return root;
	}
	
	private static SVGElement RoundedRectAround(SVGElement other) {
		SVGElement border = new SVGRoundedRectElement();
		border.addChild(other);
		other.translate(spacing, spacing);
		border.width = other.width + 2*spacing;
		border.height = other.height + 2*spacing;
		// this is our ideal frame for the other element, however,
		// it might intersect with the content due to the rounded
		// corners
		float offset = other.height / 12;
		border.width += offset;
		border.height += 2*offset;
		other.translate(0, offset);
		return border;
	}
	
	private static SVGElement layoutPartialApplication(PartialApplication term, LambdaTerm nextRedex) {
		// "'".repeat(term.getNumReceived()) for java <11
		// see https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
		String primes = new String(new char[term.getNumReceived()]).replace("\0", "'");
		return layoutBorderedText(term.getName() + primes);
	}

	private static SVGElement layoutBorderedText(String text) {
		SVGElement textElem = new SVGTextElement(text);
		return RoundedRectAround(textElem);
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


	private static SVGElement layoutAbstraction(Abstraction term, LambdaTerm nextRedex, SVGElementGroup thisRedexClickables, Panel wrapper, List<SVGElement> groups) {		
		// abs is the container element, it should encompass all others
		SVGElement abs = new SVGAbstractionElement(term);

		SVGElement body;
		if (term.getInner() instanceof BoundVariable) {
			body = RoundedRectAround(layoutBoundVariable((BoundVariable) term.getInner(), nextRedex));
		} else {
			body = layoutLambdaTerm(term.getInner(), nextRedex, null, wrapper, groups);
		}
		SVGElement pacman = new SVGPacmanElement();
		pacman.width = pacmanRadius * 2;
		pacman.height = pacmanRadius * 2;

		pacman.translate(0, spacing);
		body.translate(pacman.x + pacman.width + spacing, 0);

		abs.addChild(body);
		// We don't want pacman to bump against the frame, no matter how small the body may be
		abs.height = Math.max(body.height, pacman.y + pacmanRadius + spacing);

		Set<SVGElement> substitution_targets = abs.boundVariableLayoutElements();
		SVGElement bottomBar = null;
		if (!substitution_targets.isEmpty()) {
			// we need space for arrows, but maybe centering has already provided that for us
			abs.height = Math.max(body.y + body.height + spacing + arrowStrokeWidth, abs.height);
			float rightmost = pacman.x;
			
			// we will need the positions of BoundVariable layout elements relative to this element
			abs.clearAbsoluteLayout();
			abs.calculateAbsoluteLayout();
			
			bottomBar = new SVGLineElement(arrowStrokeWidth, "round");
			// we need bottomBar to be at its final y pos before creating vertical arrow segments
			bottomBar.translate(pacman.x + pacman.width - pacmanOverlap, body.y + body.height + 2*spacing);
			
			for (SVGElement var : abs.boundVariableLayoutElements(0)) {
				rightmost = Math.max(rightmost, var.abs_x);
				// draw arrow head for this child
				SVGElement arrowhead = new SVGArrowheadElement();
				
				
				float arrowhead_x = var.abs_x + /* centering */ (var.width - arrowhead.width)/2;
				float arrowhead_y = var.abs_y + var.height - arrowOverlap;
				arrowhead.translate(arrowhead_x, arrowhead_y);
				arrowhead.addToGroup(thisRedexClickables);
				abs.addChild(arrowhead);
				
				SVGLineElement lastSegmentBackground = new SVGLineElement(arrowStrokeWidth+6, "#FFFFFF", "butt", false);
				lastSegmentBackground.translate(arrowhead_x + arrowhead.width/2, arrowhead.y + arrowhead.height);
				lastSegmentBackground.height = bottomBar.y - lastSegmentBackground.y;
				abs.addChild(lastSegmentBackground);
				lastSegmentBackground.addToGroup(thisRedexClickables);
				SVGLineElement lastSegment = new SVGLineElement(arrowStrokeWidth, "butt");
				lastSegment.translate(lastSegmentBackground.x, lastSegmentBackground.y - 1);
				lastSegment.height = lastSegmentBackground.height + 1;
				lastSegment.addToGroup(thisRedexClickables);
				abs.addChild(lastSegment);
			}
			bottomBar.width += rightmost - pacman.x - pacman.width + pacmanOverlap + arrowheadWidth/2;
		} else {
			// no substitution targets, no arrows... center body
			body.translate(0, (abs.height - body.height)/2f );
		}
		// vertically center pacman
		pacman.translate(0, (abs.height - 2*spacing)/2f );
		
		
		if (!substitution_targets.isEmpty()) {
			SVGLineElement firstSegment = new SVGLineElement(arrowStrokeWidth, "butt");
			// the pixel offset is to ensure the line doesn't just touch the pacman in a single point
			firstSegment.translate(pacman.x + pacman.width - pacmanOverlap, pacman.y + pacmanOverlap - 1);
			firstSegment.height = bottomBar.y - pacman.y - pacmanOverlap + 1;
			firstSegment.addToGroup(thisRedexClickables);
			abs.addChild(firstSegment);
			bottomBar.addToGroup(thisRedexClickables);
			abs.addChild(bottomBar);
		}
		// finally, draw pacman over arrow
		abs.addChild(pacman);
		pacman.addToGroup(thisRedexClickables);
		abs.width += body.width + spacing + pacman.width;
		
		return abs;
	}


	public static SVGElement layoutApplication(Application app, LambdaTerm nextRedex, Panel wrapper, List<SVGElement> groups) {
		boolean isNextRedex = nextRedex == app;
		boolean isRedex = isNextRedex || app.acceptVisitor(new IsRedexVisitor());
		
		SVGRoundedRectElement roundedRect = new SVGRoundedRectElement();
		SVGElement appElem = new SVGElement();
		roundedRect.addChild(appElem);
		SVGElementGroup clickables = new SVGElementGroup(isNextRedex, wrapper, isRedex ? app : null);
		groups.add(clickables);
		roundedRect.addToGroup(clickables);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGElement lres;
		if (isRedex) {
			lres = layoutLambdaTerm(left, nextRedex, clickables, wrapper, groups);
		} else {
			lres = layoutLambdaTerm(left, nextRedex, null, wrapper, groups);
		}
		
		LambdaTerm right = app.getRightHandSide();
		SVGElement rres = layoutLambdaTerm(right, nextRedex, null, wrapper, groups);
	
		float maxheight = Math.max(lres.height, rres.height);
		rres.translate(spacing, /* vertical centering */
				Math.max(0, (maxheight - rres.height) / 2 + spacing));
		lres.translate(spacing, Math.max(0, (maxheight - lres.height) / 2 + spacing));
		
		SVGElement chevron = new SVGChevronElement();
		chevron.height = maxheight + 2*spacing; // spacing above and below
		chevron.width = chevron.height * chevronSharpness / 2;
		
		if (left instanceof Abstraction) {
			// move the right hand side of the diagram a bit to the left, so the abstraction's
			// pacman plug slots *just right* into the chevron
			lres.translate(-pacmanRadius - spacing, 0);
			// additionally, make it a bit wider, since \x.\x.... messes with the right border
			// as a general rule of thumb, the taller the inner element, the higher the chance
			// of it intersecting, and the more we need to add to the width. the divisor is a
			// magic number adjusted to look good.
			lres.width += lres.height / 6;
		} else {
			chevron.translate(spacing, 0);
		}

		chevron.translate(rres.x + rres.width, 0);
		lres.translate(chevron.x + chevron.width, 0);

		roundedRect.width = lres.x + lres.width + spacing;
		roundedRect.height = chevron.height;
		// if rres is less wide than the roundedRect's radius, the chevron gets all ugly
		if (chevron.x < roundedRect.getRadius()) {
			float dx = roundedRect.getRadius() - chevron.x;
			roundedRect.width += dx;
			chevron.translate(dx, 0);
			lres.translate(dx, 0);
			
		}

		appElem.addChild(rres);
		appElem.addChild(lres);
		appElem.addChild(chevron);
		chevron.addToGroup(clickables);

		return roundedRect;
	}
}
