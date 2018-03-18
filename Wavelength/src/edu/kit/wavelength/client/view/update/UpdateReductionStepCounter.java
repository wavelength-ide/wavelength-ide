package edu.kit.wavelength.client.view.update;

import edu.kit.wavelength.client.view.App;
import edu.kit.wavelength.client.view.execution.ReductionStepCountObserver;

public class UpdateReductionStepCounter implements ReductionStepCountObserver {

	@Override
	public void update(int count) {
		App.get().reductionStepCounterLabel().setText(String.valueOf(count));
	}

}
