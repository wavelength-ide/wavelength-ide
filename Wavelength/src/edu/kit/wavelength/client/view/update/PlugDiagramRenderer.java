package edu.kit.wavelength.client.view.update;

import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.utils.OMSVGParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.kit.wavelength.client.model.term.*;
import edu.kit.wavelength.client.view.update.SVGLayoutElement.LayoutItemType;

public class PlugDiagramRenderer {
	static final float fontSize = 17f;
	private static final float chevronSharpness = 0.6f;
	static final String strokeWidth = "2px";
	private static final float spacing = 7f;
	static final float pacmanRadius = 14.4844f;
	static OMSVGDocument doc;
	
	public static void renderDiagram(LambdaTerm t, Application nextRedex, Panel target) {

		PlugDiagramRenderer.doc = OMSVGParser.currentDocument();
		OMSVGSVGElement svg = doc.createSVGSVGElement();
		svg.setAttribute("width", "100%");
		
		
		SVGLayoutElement root = PlugDiagramRenderer.layoutLambdaTerm(t);
		root.translate(spacing, spacing);
		root.finalizeLayout();
		
		svg.setAttribute("height", Float.toString(root.height + 2*spacing) + "px");
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
		GWT.log(term.getClass().toString() + "has been forgotten!");
		return null;
	}
	
	private static SVGLayoutElement layoutBorderedText(String text) {
		SVGLayoutElement border = new SVGLayoutElement(SVGLayoutElement.LayoutItemType.RECT);
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
		SVGLayoutElement abs = new SVGLayoutElement(LayoutItemType.ABS);
		SVGLayoutElement lam = new SVGLayoutElement("λ" + term.getPreferredName() + ".");
		SVGLayoutElement body = layoutLambdaTerm(term.getInner());
		SVGLayoutElement pacman = new SVGLayoutElement(SVGLayoutElement.LayoutItemType.PACMAN);
		pacman.width = 14.4844f * 2;
		pacman.height = 14.4844f * 2;
		
	
		lam.translate(0, /* centering */ Math.max(0, (body.height - lam.height)/2f ));
		pacman.translate(0, /* centering */ body.height/2f );
		body.translate(lam.width, 0);
		
		abs.width += lam.width + body.width + spacing + pacman.width;
		abs.height = Math.max(lam.height, body.height);
		
		pacman.translate(body.x + body.width + spacing, 0);

		abs.addChild(lam);
		abs.addChild(body);
		abs.addChild(pacman);
		return abs;
	}


	public static SVGLayoutElement layoutApplication(Application app) {
		SVGLayoutElement roundedRect = new SVGLayoutElement(SVGLayoutElement.LayoutItemType.RECT, app);
		
		LambdaTerm left = app.getLeftHandSide();
		SVGLayoutElement lres = layoutLambdaTerm(left);
		
		LambdaTerm right = app.getRightHandSide();
		SVGLayoutElement rres = layoutLambdaTerm(right);
		if (left instanceof Abstraction) {
			GWT.log(((Abstraction) left).getPreferredName());
			// make the left part of the Application a bit smaller, so the abstraction's
			// pacman plug slots *just right* into the chevron
			lres.width -= pacmanRadius;
		}
	
		float maxheight = Math.max(lres.height, rres.height);
		lres.translate(spacing, Math.max(0, (maxheight - lres.height) / 2 + spacing));
		rres.translate(spacing, Math.max(0, (maxheight - rres.height) / 2 + spacing));
		
		SVGLayoutElement chevron = new SVGLayoutElement(SVGLayoutElement.LayoutItemType.CHEVRON);
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
