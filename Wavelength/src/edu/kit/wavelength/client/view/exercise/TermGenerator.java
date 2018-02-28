package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;

import com.google.gwt.user.client.Random;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.view.export.BasicExportVisitor;

public class TermGenerator {

	private int minDepth;
	private int maxDepth;
	private int openNodes;

	/**
	 * Creates a new random LambdaTerm.
	 * The input minimal and maximal depth are used to limit the terms size.
	 * @param minDepth The minimal term depth
	 * @param maxDepth The maximal term depth
	 * @return The newly created term
	 * 
	 */
	public LambdaTerm getNewTerm(int minDepth, int maxDepth) {
		return getNewTerm(minDepth, maxDepth, 0, 0);
	}
	
	/**
	 * Create a new random subterm.
	 * @param minDepth The minimal term depth
	 * @param maxDepth The maximal term depth
	 * @param termDepth The current depth
	 * @param abstractionDepth The number of abstractions enclosing this term.
	 * @return The newly created subterm.
	 */
	public LambdaTerm getNewTerm (int minDepth, int maxDepth, int termDepth, int abstractionDepth) {
		if (minDepth > maxDepth) {
			throw new IllegalArgumentException("MinDepth has to be lower than maxDepth.");
		}
		if (termDepth < 0 || abstractionDepth < 0) {
			throw new IllegalArgumentException("TermDepth and abstractionDepth may not be negative.");
		}
		this.maxDepth = maxDepth;
		this.minDepth = minDepth;
		openNodes = 1;
		return makeTerm(termDepth, abstractionDepth);
	}

	/**
	 * Creates a new random LambdaTerm by recursively invoking this method to construct sub terms.
	 * @param termDepth The number of abstractions and applications above the term
	 * @param abstractionDepth The number of abstractions enclosing the term
	 * @return A a new random LambdaTerm
	 */
	private LambdaTerm makeTerm(int termDepth, int abstractionDepth) {
		int random = nextInt(4);
		if (termDepth == maxDepth) {
			// force variable
			random = (random % 2) + 2;
		} else if (termDepth < minDepth) {
			if (openNodes == 1) {
				// force further abstraction or application
				random = random % 2;
			}
		}	
		switch (random) {
		case 0:
			String absVariable = getRandomVarName();
			return new Abstraction(absVariable, makeTerm(termDepth + 1, abstractionDepth + 1));
		case 1:
			openNodes++;
			LambdaTerm left = makeTerm(termDepth + 1, abstractionDepth);
			LambdaTerm right = makeTerm(termDepth + 1, abstractionDepth);
			return new Application(left, right);
		case 2:
			if (abstractionDepth > 0) {
				openNodes--;
				int randomIndex = nextInt(abstractionDepth) + 1;
				return new BoundVariable(randomIndex);
			}
		default:
			openNodes--;
			return new FreeVariable(getRandomVarName());
		}
	}
	
	/**
	 * Return a random integer in [0,bound)
	 * @param bound The upper bound to use
	 * @return a random integer
	 */
	protected int nextInt(int bound) {
		return Random.nextInt(bound);
	}

	private String getRandomVarName() {
		char result = (char) (nextInt('z' - 'a' + 1) + 'a');
		return "" + result;
	}
}