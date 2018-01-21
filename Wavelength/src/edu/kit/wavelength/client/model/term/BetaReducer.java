package edu.kit.wavelength.client.model.term;

import java.util.Objects;

/**
 * A {@link Visitor} that transforms a {@link LambdaTerm} by beta reducing a given
 * redex.
 *
 */
public final class BetaReducer extends TermTransformer {
	
	private final Application toReduce;
	
	/**
	 * Creates a new beta reducer that reduces the given redex.
	 * @param toReduce The application that should be reduced.
	 * This must be a redex, otherwise an exception is thrown.
	 */
	public BetaReducer(Application toReduce) {
		Objects.requireNonNull(toReduce);
		
		if (toReduce.acceptVisitor(new IsRedexVisitor()) == false)
			throw new IllegalArgumentException("toReduce must be a redex");

		this.toReduce = toReduce;
	}
	
	@Override
	public LambdaTerm visitPartialApplication(PartialApplication app) {
		return null;
	}

	@Override
	public LambdaTerm visitAbstraction(Abstraction abs) {
		return new Abstraction(abs.getPreferredName(), abs.getInner().acceptVisitor(this));
	}

	@Override
	public LambdaTerm visitApplication(Application app) {
		if (app != toReduce)
			return new Application(app.getLeftHandSide().acceptVisitor(this),
				app.getRightHandSide().acceptVisitor(this));
			
		return app.getLeftHandSide().acceptVisitor(new SubstitutionVisitor(-1, app.getRightHandSide()));
	}

	@Override
	public LambdaTerm visitBoundVariable(BoundVariable var) {
		return var;
	}

	@Override
	public LambdaTerm visitFreeVariable(FreeVariable var) {
		return var;
	}
 
}
