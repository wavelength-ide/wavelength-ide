package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGElement;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.action.StepManually;

class SVGElement {
	
	public float x;
	public float y;
	public float width;
	public float height;
	
	// absolute positioning may be recalculated multiple times 
	// from different roots (we need it while drawing arrows
	// for each abstraction), so we need these separately
	public float abs_x;
	public float abs_y;
	protected List<SVGElement> children;
	LambdaTerm term;

	public SVGElement() {
		this.children = new ArrayList<>();
	}
	public void addChild(SVGElement elem) {
		this.children.add(elem);
	}
	
	public void clearAbsoluteLayout() {
		this.abs_x = x;
		this.abs_y = y;
		for (SVGElement child : children) {
			child.clearAbsoluteLayout();
		}
	}
	/**
	 * propagates relative positions through the tree, turning relative x-y-coordinates to absolute ones
	 */
	public void calculateAbsoluteLayout() {
		for (SVGElement child : children) {
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
	public Set<SVGElement> boundVariableLayoutElements() {
		return boundVariableLayoutElements(0);
	}
	
	public Set<SVGElement> boundVariableLayoutElements(int deBruijnIndex) {
		Set<SVGElement> res = new HashSet<>();
		for (SVGElement child : children) {
			res.addAll(child.boundVariableLayoutElements(deBruijnIndex));
		}
		return res;
	}
	
	public Set<OMSVGElement> render() {
		Set<OMSVGElement> res = new HashSet<>();
		for (SVGElement child : children) {
			res.addAll(child.render());
		}
		return res;
		
	}
		
}

