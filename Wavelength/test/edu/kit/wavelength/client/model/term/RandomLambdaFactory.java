package edu.kit.wavelength.client.model.term;

import java.util.Random;

public class RandomLambdaFactory {

	private final int maxDepth;
	private final double appProbability;
	private final double appScaling;
	private final double absProbability;
	private int globalFreeVariables = 0;
	Random rand;

	/**
	 * Constructs a new term factory for creating random lambda terms.
	 * 
	 * @param maxDepth
	 *            the maximal depth that the tree representing a generated
	 *            lambda term will have
	 */
	public RandomLambdaFactory(int maxDepth) {
		this.maxDepth = maxDepth;
		appProbability = 0.25;
		appScaling = 1;
		absProbability = 0.25;

		rand = new Random();
	}

	/**
	 * Constructs a new term factory for creating random lambda terms.
	 * 
	 * @param maxDepth
	 *            the maximal depth that the tree representing a generated
	 *            lambda term will have
	 * @param appProbability
	 *            the probability of generating an application
	 * @param appScaling
	 *            a scaling factor that will make nested application less or
	 *            more common. It should be kept below of equals to 1. 1 means
	 *            no modification <1 will make nested applications less and >1
	 *            more common
	 * @param absProbability
	 *            the probability of generating an abstraction
	 */
	public RandomLambdaFactory(int maxDepth, double appProbability, double appScaling, double absProbability) {
		if (appProbability > 1) {
			throw new IllegalArgumentException("Probability for applications must be less or equals to 1.");
		}
		if (absProbability > 1) {
			throw new IllegalArgumentException("Probability for abstractions must be less or equals to 1.");
		}
		if (appProbability + absProbability > 1) {
			throw new IllegalArgumentException("Sum of both probabilities must be less or equals to 1.");
		}

		this.maxDepth = maxDepth;
		this.appProbability = appProbability;
		this.appScaling = appScaling;
		this.absProbability = absProbability;

		rand = new Random();
	}

	/**
	 * Generates a new random lambda term after the parameters given in the
	 * constructor.
	 * 
	 * @return a random lambda term
	 */
	public LambdaTerm generateLambdaTerm() {
		globalFreeVariables = 0;

		return generateLambdaTerm(maxDepth, 0, appProbability);
	}

	/**
	 * Used to recursively generate lambda terms. The generation of the tree is
	 * similar to DFS
	 * 
	 * @param remainingDepth
	 *            the number of remaining Terms that this tree is allowed to
	 *            grow from this term
	 * @param numAbstractions
	 *            the number of abstractions above the current generated term
	 * @param currentAppProb
	 *            the probability for generation an abstraction
	 * @return the generated lambda term
	 */
	private LambdaTerm generateLambdaTerm(int remainingDepth, int numAbstractions, double currentAppProb) {

		// Reached end of Tree. Have to generate Free or BoundVariable
		if (remainingDepth <= 1) {
			return generateLeaf(numAbstractions);
		}

		double prob = rand.nextDouble();
		if (prob < currentAppProb) {
			// First check if we want to generate an Application
			return new Application(
					generateLambdaTerm(remainingDepth - 1, numAbstractions, currentAppProb *= appScaling),
					generateLambdaTerm(remainingDepth - 1, numAbstractions, currentAppProb *= appScaling));
		} else if (prob < currentAppProb + absProbability) {
			// Now check if we want to generate an Abstraction
			return new Abstraction("b" + (numAbstractions + 1),
					generateLambdaTerm(remainingDepth - 1, numAbstractions + 1, currentAppProb));
		} else
			return generateLeaf(numAbstractions);
	}

	/**
	 * Generates a leaf for the lambda tree. This is either a free or a bound
	 * variable.
	 * 
	 * @param numAbstractions
	 *            the number of abstractions that are above this leaf
	 * @return the leaf for the lambda tree
	 */
	private LambdaTerm generateLeaf(int numAbstractions) {
		if (numAbstractions > 0 && rand.nextDouble() < 0.5) {
			return new BoundVariable(rand.nextInt(numAbstractions) + 1);
		} else {
			return new FreeVariable("f" + (++globalFreeVariables));
		}
	}
}
