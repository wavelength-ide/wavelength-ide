package edu.kit.wavelength.client.model.term;

/**
 * A {@link Visitor} that substitutes {@link BoundVariable}s with a given De Bruijn
 * index with a given substituent.
 *
 */
public final class SubstitutionVisitor extends TermTransformer {
	
	private final int depth;
	private final LambdaTerm substituent;
	
	/**
	 * Creates a new substitution visitor.
	 * @param depth The De Bruijn index that should be substituted
	 * @param substituent The term that should be substituted with
	 */
	public SubstitutionVisitor(int depth, LambdaTerm substituent) {
		this.depth = depth;
		this.substituent = substituent;
	}
	
	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		return null;
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		return null;
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		return null;
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return null;
	}

}
