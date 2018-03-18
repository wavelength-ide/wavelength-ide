package edu.kit.wavelength.client.view.update;

import com.google.gwt.user.client.ui.Label;

import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ExecutionObserver;

public class UpdateReductionStepCounter implements ExecutionObserver {

	private static Label counter = App.get().reductionStepCounterLabel();
	
	@Override
	public void pushTerm(LambdaTerm t) {
		counter.setText(String.valueOf(Integer.valueOf(counter.getText()) + 1));
	}

	@Override
	public void removeLastTerm() {
		counter.setText(String.valueOf(Integer.valueOf(counter.getText()) - 1));
	}

	@Override
	public void clear() {
		counter.setText("0");
	}

	@Override
	public void reloadTerm() {}

	@Override
	public void pushError(String error) {}

}
