package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

import edu.kit.wavelength.client.model.reduction.ReductionOrder;
import edu.kit.wavelength.client.model.reduction.ReductionOrders;

/**
 * Static class giving access to all available exercises.
 *
 */
public final class Exercises {

	/**
	 * Returns an unmodifiable list of all available exercises.
	 * 
	 * @return An unmodifiable list that contains exactly one instance of every
	 *         available exercise
	 */
	public static List<Exercise> all() {

		String exerciseName = "Exercise mode";
		String exerciseTask = "The current exercise's task will be displayed here. "
				+ " <br/> To show a solution for the current exercise press the lightbulb button to the right.";
		String exerciseSolution = "To leave the exercise mode press the X button in the upper right corner.";
		String exercisePredef = "Some exercises will display prewritten terms for you to work with here.";
		Exercise exerciseMode = new ConcreteExercise(exerciseName, exerciseTask, exerciseSolution, exercisePredef);

		String varName = "Variables";
		String varTask = "In lambda calculus, bound variables are variables enclosed in the body of an abstraction,"
				+ " while free variables are those not enclosed by an abstraction."
				+ "<br/> Note that the same variable may appear as a bound and as a free variable in the same term. "
				+ "<br/> Which variables in the displayed term are free? " + "<br/> Which are bound?";
		String varSolution = "The first v, and last x are free variables. " + "<br/> All other variables are bound.";
		String varPredef = "v ((λx. λv. (v x)) x)";
		Exercise var = new ConcreteExercise(varName, varTask, varSolution, varPredef);

		String freeVarName = "Variable II";
		String freeVarTask = "Which variables in the displayed term are bound variables?";
		String freeVarSolution = "The term's first x, second y, first v and fourth x are bound variables. "
				+ "<br/> All other variables are free.";
		String freeVarPredef = "(λx. x y)(λy. y x)((λv. x v) λx. x)";
		Exercise freeVar = new ConcreteExercise(freeVarName, freeVarTask, freeVarSolution, freeVarPredef);

		String alphaReduName = "α-conversion";
		String alphaReduTask = "α-conversion enables renaming variables bound by an abstraction with some restrictions "
				+ "to prevent the term"
				+ " from changing its structure: "
				+ "<br/> The renaming must not result in free variables becoming bound ones and only variables bound "
				+ "to the selected abstraction may be renamed. "
				+ "<br/> If a term can be transformed into another term only by α-renaming variables, the two terms "
				+ "are α-equivalent."
				+ "<br/> Knowing these restrictions which of these terms are α-equivalent to the first one?";
		String alphaReduSolution = "Only the first and fourth terms are α-equivalent.";
		String alphaReduPredef = "λx. (λx. y x) z \n" + "λz. (λx. y x) z \n" + "λx. (λy. y y) z \n"
				+ "λx. (λv. y v) z \n" + "λy.(λx.y x) y \n" + "λx.(λz.y z) o ";
		Exercise alphaRedu = new ConcreteExercise(alphaReduName, alphaReduTask, alphaReduSolution, alphaReduPredef);

		String order1Name = "Normal Order";
		String order1Task = "If a λ-term contains multiple reducable expressions the question which to reduce first "
				+ "is answered by the applied reduction order "
				+ " one of which is the normal reduction order. "
				+ "<br/> Using this reduction order the outermost leftmost redex is reduced first. "
				+ "<br/> In respect to the tree output format this is equivalent to traversing the term-tree according"
				+ " to a depth-first-search until one reaches a redex."
				+ "<br/> Which abstraction in the displayed term makes up the left part of the redex to reduce first?"
				+ "<br/> You can check your answer by selecting normal order and reducing the term using the "
				+ "Step-Forward-Button.";
		String order1Solution = "(λz. z) x";
		String order1Predef = "λx. x((λz. z) x)((λv. v ((λz. x v) z)) j)";
		Exercise order1 = new ConcreteExercise(order1Name, order1Task, order1Solution, order1Predef);

		String order2Name = "Applicative Order";
		String order2Task = "Applicative Order is the exact opposite of the normal reduction order: the innermost "
				+ "rightmost redex is reduced first. "
				+ "<br/> This means a redex is only reduced when it does not contain any redex itself. "
				+ "<br/> In respect to tree output no redex is reduced until all redexes in its subtree have been reduced."
				+ "<br/> Using applicative order which redex in the displayed term will be reduced first?";
		String order2Solution = "(λv. x v) z  will be reduced first";
		String order2Predef = "λx. x((λz. v z) x)((λv. v ((λv. x v) z))j)";
		Exercise order2 = new ConcreteExercise(order2Name, order2Task, order2Solution, order2Predef);

		String order3Name = "Call-by-Name";
		String order3Task = "Call-by-Name reduction is similar to the normal reduction order: the outermost leftmost "
				+ "redex is reduced first."
				+ "<br/> Call-by-Name's major difference is the restriction that only redexes not enclosed in the body"
				+ " of an abstraction may be reduced."
				+ "<br/> This means that when using Call-by-Name a term might still contain redexes but will not be "
				+ "reduced further."
				+ "<br/> Using Call-by-Name which redex in the displayed term will be reduced first?";
		String order3Solution = "(λz. ((λv. v x) x) z z)(u v) will be reduced first.";
		String order3Predef = "(λz. ((λv. v x) x) z z)(u v)(λx. x)u";
		Exercise order3 = new ConcreteExercise(order3Name, order3Task, order3Solution, order3Predef);

		String order4Name = "Call-by-Value";
		String order4Task = "Just like Call-by-Name using the Call-by-Value reduction order the outermost leftmost "
				+ "redex is reduced first and redexes enclosed by an abstraction"
				+ " will never be reduced but in addition this reduction order demands that the right side of a "
				+ "redex has to be a value if it is to be reduced."
				+ "<br/> \"Value\" refers to a lambda term that can not be reduced any further meaning that it is "
				+ "either a variable or an abstraction."
				+ "<br/> Using call-by-value which redex in the displayed term will be reduced first?";
		String order4Solution = "(λx. x)(λz. z x)";
		String order4Predef = "(λv. v v)(a b)((λx. x)(λz. z x))";
		Exercise order4 = new ConcreteExercise(order4Name, order4Task, order4Solution, order4Predef);

		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();

		exerciseList.add(exerciseMode);
		exerciseList.add(var);
		exerciseList.add(freeVar);
		exerciseList.add(order1);
		exerciseList.add(order2);
		exerciseList.add(order3);
		exerciseList.add(order4);
		exerciseList.add(alphaRedu);

		List<ReductionOrder> reductions = ReductionOrders.all();
		for (ReductionOrder cOrder : reductions) {
			exerciseList.add(new RedexExercise(cOrder));
		}

		return exerciseList;
	}

	/**
	 * Deserializes an {@link Exercise}.
	 * 
	 * @param serialized
	 *            supposedly a serialization of an Exercise
	 * @return Exercise belonging to serialization
	 * @throws IllegalArgumentException
	 *             if serialized is {@code null} or empty or exercise could not be
	 *             found
	 */
	public static Exercise deserialize(String serialized) {
		if (serialized == null || serialized.isEmpty()) {
			throw new IllegalArgumentException("This is not a valid serialization of an Exercise!");
		}
		for (Exercise exercise : Exercises.all()) {
			if (exercise.getName().equals(serialized)) {
				return exercise;
			}
		}
		throw new IllegalArgumentException("Exercise not found!");

	}
}
