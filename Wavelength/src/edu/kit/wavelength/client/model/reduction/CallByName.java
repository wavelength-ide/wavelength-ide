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
 * The call by name reduction order for the untyped lambda calculus.
 * 
 * The leftmost outermost redex that is not enclosed by an abstraction is
 * selected for reduction.
 *
 */
public final class CallByName implements ReductionOrder {
	
	public static final char ID = 'N';

	@Override
	public Application next(LambdaTerm term) {
		return (Application) term.acceptVisitor(new CallByNameVisitor());
	}

	@Override
	public String getName() {
		return "Call by Name";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("" + ID);
	}

	/**
	 * The CallByNameVisitor is used to find the next redex to reduce in a lambda term it visits
	 * using the call-by-value reduction order.
	 * 
	 * This is accomplished in a way similar to normal order reduction with the restriction imposed by the
	 * call-by-name reduction order: The visitor does not attempt to find redexes in term enclosed in an abstraction.
	 *
	 */
	private class CallByNameVisitor extends NameAgnosticVisitor<Application> {

		@Override
		public Application visitPartialApplication(PartialApplication app) {
			return (Application) app.getRepresented().acceptVisitor(this);
		}

		@Override
		public Application visitAbstraction(Abstraction abs) {
			// Since call-by-name does not permitted the reduction of redexes enclosed in an abstraction,
			// no abstractions sub term can contain a redex this visitor may return.
			return null;
		}

		@Override
		public Application visitApplication(Application app) {
			// This method works just like its counterpart in the NormalOrder class:
			// If the visited application is a redex, it is returned.
			// If the visited application is not a redex, its left hand side is searched for a redex,
			// if none is found the right hand side is searched next.
			if (app.acceptVisitor(new IsRedexVisitor())) {
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
