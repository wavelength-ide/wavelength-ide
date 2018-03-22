package edu.kit.wavelength.client.model.reduction;

import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NameAgnosticVisitor;
import edu.kit.wavelength.client.model.term.PartialApplication;

/**
 * The applicative reduction order for the untyped lambda calculus.
 *
 * The rightmost innermost redex is selected for reduction.
 */
public final class ApplicativeOrder implements ReductionOrder {
	
	public static final char ID = 'a';
	
	@Override
	public Application next(LambdaTerm term) {
		if (term == null) {
			throw new IllegalArgumentException("Term may not be null.");
		}
		return (Application) term.acceptVisitor(new ApplicativeVisitor());
	}
	
	@Override
	public String getName() {
		return "Applicative Order";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}
	
	/**
	 * The ApplicativeVisitor is used to find the next redex to reduce in a lambda term it visits.
	 * 
	 * This is accomplished by the visitor traversing the term subtree rooted at its host term using depth-first-search,
	 * starting with the rightmost sub term.
	 *
	 */
	private class ApplicativeVisitor extends NameAgnosticVisitor<Application> {

		@Override
		public Application visitPartialApplication(PartialApplication app) {
			return null;
		}

		@Override
		public Application visitAbstraction(Abstraction abs) {
			return abs.getInner().acceptVisitor(this);
		}

		@Override
		public Application visitApplication(Application app) {
			// This method implements the behavior specified by the ApplicativeOrder reduction policy,
			// it first attempts to find a redex in the application's right hand term by recursively visiting its sub terms,
			// if this is unsuccessful the left hand side is searched.
			// If a redex is found it is returned.
			// Only if both sides do not contain a redex, and it is redex itself, this application is returned.
			Application possibleRedex = app.getRightHandSide().acceptVisitor(this);
			if (possibleRedex != null) {
				return possibleRedex;
			} else {
				possibleRedex = app.getLeftHandSide().acceptVisitor(this);
				if (possibleRedex != null) {
					return possibleRedex;
				} else {
					if (app.acceptVisitor(new IsRedexVisitor())) {
						return app;
					} else {
						return null;
					}
				}
			}
		}

		@Override
		public Application visitBoundVariable(BoundVariable var) {
			return null;
		}

		@Override
		public Application visitFreeVariable(FreeVariable var) {
			return null;
		}
		
	}
}
