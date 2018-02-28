package edu.kit.wavelength.client.model.reduction;

import java.util.Random;

import edu.kit.wavelength.client.view.exercise.TermGenerator;

public class TestTermGenerator extends TermGenerator {
	
	@Override
	protected int nextInt(int bound) {
		return new Random().nextInt(bound);
	}
}
