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
 * The call by value reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex that is not enclosed by an abstraction and whose
 * argument is a value (i.e. an abstraction) is selected for reduction.
 *
 */
public final class CallByValue implements ReductionOrder {

	@Override
	public Application next(LambdaTerm term) {
		return (Application) term.acceptVisitor(new CallByValueVisitor());
	}

	@Override
	public String getName() {
		return "Call by Value";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("V");
	}

	private class CallByValueVisitor extends NameAgnosticVisitor<Application> {

		@Override
		public Application visitPartialApplication(PartialApplication app) {
			return (Application) app.getRepresented().acceptVisitor(this);
		}

		@Override
		public Application visitAbstraction(Abstraction abs) {
			return null;
		}

		@Override
		public Application visitApplication(Application app) {
			if (app.acceptVisitor(new IsRedexVisitor()) && new NormalOrder().next(app) == null) {
				return app;
			} else {
				Application possibleRedex = (Application) app.getLeftHandSide().acceptVisitor(this);
				if (possibleRedex != null) {
					return possibleRedex;
				} else {
					return (Application) app.getRightHandSide().acceptVisitor(this);
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
