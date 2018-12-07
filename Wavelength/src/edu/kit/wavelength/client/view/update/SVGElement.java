package edu.kit.wavelength.client.view.update;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGElement;

import edu.kit.wavelength.client.model.term.LambdaTerm;

class SVGElement {
	
	public float x;
	public float y;
	public float width;
	public float height;
	/**
	 * the object graph ("children" relationship) determines the
	 * layout and which objects are positioned relative to one
	 * another. However, in order to get mouse-over highlighting
	 * to work correctly, the rendered OMSVGElements must be places
	 * in groups that do not correspond to the hierarchy of the
	 * layout. Every SVGElement therefore optionally belongs to an
	 * SVGGElement it will be rendered in.
	 * 
	 * This also means that any element which has non-null clickGroup
	 * should *not* be included in the elements returned by render(),
	 */
	public SVGElementGroup clickGroup;
	
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
	
	public void addToGroup(SVGElementGroup group) {
		this.clickGroup = group;
		if (group != null) {
			group.addChild(this);
		}
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
	
	public Set<OMSVGElement> renderForRoot(SVGElement root) {
		Set<OMSVGElement> res = new HashSet<>();
		for (SVGElement child : children) { 
			res.addAll(child.renderForRoot(root));
		}
		return res;
	}
	
	public void addResultForRoot(Set<OMSVGElement> results, OMSVGElement el, SVGElement root) {
		if (root == this.clickGroup) {
			results.add(el);
		}
	}
		
}

