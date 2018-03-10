package edu.kit.wavelength.client.model.reduction;

import java.util.Random;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.view.exercise.TermGenerator;

public class TestTermGenerator extends TermGenerator {
	
	@Override
	protected int nextInt(int bound) {
		return new Random().nextInt(bound);
	}
	
	public Abstraction getRandomAbstraction() {
		return new Abstraction(getRandomVarName(), getNewTerm(0, 100));
	}
	
	public Application getRandomApplication() {
		return new Application(getNewTerm(0, 100), getNewTerm(0, 100));
	}
	
	public FreeVariable getRandomFreeVariable() {
		return new FreeVariable(getRandomVarName());
	}
	
	public Application getRandomRedex() {
		return new Application(getRandomApplication(), getNewTerm(0, 100, 1, 1));
	}
}
