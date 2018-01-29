package edu.kit.wavelength.client.view.webui.component;

import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;

import edu.kit.wavelength.client.view.api.Output;

/**
 * Displays lambda terms in tree format.
 */
public class TreeOutput implements Output {

	private ScrollPanel output;
	private Canvas canvas;
	private List<TreeTerm> terms;

	public TreeOutput(ScrollPanel output) {
		this.output = output;
		canvas = Canvas.createIfSupported();
	}

	@Override
	public void lock() {
		output.setEnabled(false);
	}

	@Override
	public void unlock() {
		output.setEnabled(true);
	}

	@Override
	public void hide() {
		output.setVisible(false);
	}

	@Override
	public void show() {
		output.setVisible(true);
	}

	@Override
	public boolean isShown() {
		return output.isVisible();
	}

	@Override
	public boolean isLocked() {
		return output.isEnabled();
	}

	@Override
	public void write(String input) {
		output.setText(input);
	}

	public void clear() {
		write("");
		terms.clear();
	}

	@Override
	public void removeLastTerm() {
		if (!terms.isEmpty()) {
			terms.remove(terms.size() - 1);
		}
		// TODO: remove term from display, how?
	}

	public void setTerm(TreeTerm term) {
		terms.add(term);
		// TODO: term-baum traversieren, layouten
		setTerm(term, Window.getClientWidth(), 0);

	}

	private void setTerm(TreeTerm node, int width, int start) {
		if (node == null) {
			return;
		}

		// TODO: add node at width/2

		TreeTerm left = node.getLeft();
		// TODO: draw line to left and right
		if (left != null) {
			// draw line to node
		}

		setTerm(left, width / 2, 0);
		TreeTerm right = node.getRight();

		if (right != null) {
			// draw line to node
		}

		setTerm(right, width / 2, width / 2);

	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		Context2d context = canvas.getContext2d();
		context.beginPath();
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.closePath();
		context.stroke();
	}

}
