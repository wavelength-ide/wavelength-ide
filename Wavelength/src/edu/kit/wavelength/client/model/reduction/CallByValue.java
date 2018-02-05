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
		return term.acceptVisitor(new CallByValueVisitor());
	}

	@Override
	public String getName() {
		return "Call by Value";
	}

	@Override
	public StringBuilder serialize() {
		return new StringBuilder("V");
	}

	/**
	 * A visitor for finding the next redex by the call by value reduction
	 * order.
	 */
	private class CallByValueVisitor extends NameAgnosticVisitor<Application> {

		@Override
		public Application visitPartialApplication(PartialApplication app) {
			return app.getRepresented().acceptVisitor(this);
		}

		@Override
		public Application visitAbstraction(Abstraction abs) {
			return null;
		}

		@Override
		public Application visitApplication(Application app) {
			if (app.acceptVisitor(new IsRedexVisitor()) && app.getRightHandSide().acceptVisitor(new IsValueVisitor())) {
				// && new NormalOrder().next(app) == null
				return app;
			} else {
				Application possibleRedex = app.getLeftHandSide().acceptVisitor(this);
				if (possibleRedex != null) {
					return possibleRedex;
				} else {
					return app.getRightHandSide().acceptVisitor(this);
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

		/**
		 * A visitor that determines if a given lambda term is a value or not.
		 * 
		 * According to our current definition every abstraction is a value.
		 * (https://www.itu.dk/~sestoft/papers/sestoft-lamreduce.pdf)
		 */
		private class IsValueVisitor extends NameAgnosticVisitor<Boolean> {

			@Override
			public Boolean visitPartialApplication(PartialApplication app) {
				return app.getRepresented().acceptVisitor(this);
			}

			@Override
			public Boolean visitAbstraction(Abstraction abs) {
				return true;
			}

			@Override
			public Boolean visitApplication(Application app) {
				return false;
			}

			@Override
			public Boolean visitBoundVariable(BoundVariable var) {
				return false;
			}

			@Override
			public Boolean visitFreeVariable(FreeVariable var) {
				return false;
			}
		}
	}
}
