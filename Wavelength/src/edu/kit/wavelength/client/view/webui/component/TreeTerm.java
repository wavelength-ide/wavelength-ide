package edu.kit.wavelength.client.view.webui.component;

import com.google.gwt.user.client.ui.Label;

import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.view.action.StepManually;

/**
 * Represents the visual display of a lambda term as a tree.
 */
public class TreeTerm {
	
	private Label node;
	
	private TreeTerm left, right;
	
	public TreeTerm(String text, TreeTerm left, TreeTerm right) {
		node = new Label();
		node.setText(text);
		this.left = left;
		this.right = right;
	}
	
	public void setAction(Application redex) {
		node.addClickHandler(event -> new StepManually(redex).run());
	}
	
	public TreeTerm getLeft() {
		return left;
	}
	
	public TreeTerm getRight() {
		return right;
	}

}

