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
	
	@Override
	public Application next(LambdaTerm term)
	{
		return term.acceptVisitor(new ApplicativeVisitor());
	}
	
	@Override
	public String getName() {
		return "Applicative Order";
	}

	@Override
	public StringBuilder serialize() {
		return null;
	}
	
	private class ApplicativeVisitor extends NameAgnosticVisitor<Application> {

		@Override
		public Application visitPartialApplication(PartialApplication app) {
			return app.getRepresented().acceptVisitor(this);
		}

		@Override
		public Application visitAbstraction(Abstraction abs) {
			return abs.getInner().acceptVisitor(this);
		}

		@Override
		public Application visitApplication(Application app) {
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
