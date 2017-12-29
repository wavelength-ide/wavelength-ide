package edu.kit.wavelength.client.model.term;

/**
 * A visitor that transforms a lambda term by beta reducing a given
 * redex.
 *
 */
public final class BetaReducer extends TermTransformer {
	
	private final Application toReduce;
	
	/**
	 * Creates a new beta reducer that reduces the given redex
	 * @param toReduce The application that should be reduced.
	 * This must be a redex, otherwise an exception is thrown.
	 */
	public BetaReducer(Application toReduce) {
		this.toReduce = toReduce;
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
