package edu.kit.wavelength.client.view.exercise;

import java.util.ArrayList;
import java.util.List;

import edu.kit.wavelength.client.model.reduction.NormalOrder;

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
		String exerciseTask = "The active exercise's task will be displayed here. \n"
				+ " To show a solution to the active exercise press the lightbulb button to the right.";
		String exerciseSolution = "To leave the exercise mode press the X button in the upper right corner.";
		String exercisePredef = "Some exercises will display prewritten terms for you to work with here.";
		Exercise exerciseMode = new ConcreteExercise(exerciseName, exerciseTask, exerciseSolution, exercisePredef);

		String varName = "Variables";
		String varTask = "In lambda calculus, bound variables are variables enclosed in the body of an abstration,"
				+ " while free variable are those variables not enclosed by an abstration.\n Note that one occurence of a variable in a given term"
				+ " may be a bound variable while another one might be a free variable. Which variables in the displayed term are bound? Which are free?";
		String varSolution = "";
		String varPredef = "v ((λx.λv.(v x)) x)";
		Exercise var = new ConcreteExercise(varName, varTask, varSolution, varPredef);

		String freeVarName = "Variable II";
		String freeVarTask = "Which variables in the displayed term are bound variables?";
		String freeVarSolution = "The term's first x, second y, first v and fourth occurences are bound variables.";
		String freeVarPredef = "(λx.x y)(λy. y x)((λv. x v) λx.x)";
		Exercise freeVar = new ConcreteExercise(freeVarName, freeVarTask, freeVarSolution, freeVarPredef);

		String redexName = "β-Redex";
		String redexTask = "A reducable expression or redex is a λ-term which may be reduced by one of the lambda calculus's "
				+ "reduction rules. In the following exercises redex will always refer to a β-redex,"
				+ " a redex which may be reduced using β-reduction. A β-redex always consists of an application whoose"
				+ " left argument is a abstraction. Does the displayed λ-term contain a redex and if so which part of the term"
				+ " is a redex?";
		String redexSolution = "(λx. x x)(k)  is the term's only redex";
		String redexPredef = "λv.(((λx.x x)(k))v)";
		Exercise redex = new ConcreteExercise(redexName, redexTask, redexSolution, redexPredef);

		String redex2Name = "β-Redex II";
		String redex2Task = "Does the displayed λ-term contain a redex and if so which part of the term"
				+ " is a redex?";
		String redex2Solution = "The λ-term does not contain a redex";
		String redex2Predef = "λy.y x (λv.u v) y";
		Exercise redex2 = new ConcreteExercise(redex2Name, redex2Task, redex2Solution, redex2Predef);

		String redex3Name = "β-Redex III";
		String redex3Task = "Does the displayed λ-term contain a redex and if so which part of the term is a redex?";
		String redex3Solution = "(λx.z x) (x y) is the term's only redex";
		String redex3Predef = "λx.λy.(λz.(λx.z x) (x y))";
		Exercise redex3 = new ConcreteExercise(redex3Name, redex3Task, redex3Solution, redex3Predef);

		String alphaReduName = "α-conversion";
		String alphaReduTask = "α-conversion makes it possible to rename variables bound by an abstraction with some restrictions to prevent the term"
				+ " changing it structure: The renaming must not result in free variables becoming bound ones and only variables bound to the"
				+ " selected abstration may be renamed. If a term can be transformed into another term only by α-renaming variables, the two terms are α-equivalent. \n"
				+ " Observing these restriction which of these terms are α-equivalent to the first one?";
		String alphaReduSolution = "Only the first and fifth terms are α-equivalent.";
		String alphaReduPredef = "λx.(λx.y x) z \n" + "λz.(λx.y x) z \n" + "λx.(λy.y y) z \n" + "λx.(λv.y v) z \n"
				+ "λy.(λx.y x) y \n" + "λx.(λz.y z) o ";
		Exercise alphaRedu = new ConcreteExercise(alphaReduName, alphaReduTask, alphaReduSolution, alphaReduPredef);

		String order1Name = "Normal Order";
		String order1Task = "If a λ-term contains multiple reducable expression, the question which to reduce first is answered by the applied reduction order "
				+ " one of which is the normal reduction order. Using this reduction order the outermost leftmost redex is reduced first. In respect to the tree output format"
				+ " this is equivalent to traversing the term-tree in accordance with depth-first-search until one reaches a redex.\n"
				+ " Which abstraction in the displayed term makes up the left part of the redex to reduce first? \n"
				+ " You can check your answer by selecting the normal order reduction strategy and reducing the term using step-by-step reduction.";
		String order1Solution = "(λz.v z) x";
		String order1Predef = "λx. x((λz.z) x)((λv.v ((λz.x v) z)) j)";
		Exercise order1 = new ConcreteExercise(order1Name, order1Task, order1Solution, order1Predef);

		String order2Name = "Applicative Order";
		String order2Task = "Applicative order is the exact opposite of the normal reduction order, using it the innermost rightmost redex is reduced first. "
				+ "This means a redex is only reduced once it does not contain any redex itself, in respect to tree output no redex is reduced until"
				+ " all redexes in its subtree have been reduced. \n"
				+ " Using applicative order which redex in the displayed term will be reduced first?";
		String order2Solution = "(λv.x v) z  will be reduced first";
		String order2Predef = "λx.x((λz.v z) x)((λv.v ((λv.x v) z))j)";
		Exercise order2 = new ConcreteExercise(order2Name, order2Task, order2Solution, order2Predef);

		String order3Name = "Call-by-Name";
		String order3Task = "Call-by-value reduction is similiar to the normal reduction order, the outermost leftmost redex is reduced first."
				+ "call-by-value's major difference is the restriction that only redexes not enclosed in the body of an abstraction may be reduced."
				+ " This means, using call-by-name a term might still contain redexes but will not be reduced further. \n"
				+ " Using call-by-value which redex in the displayed term will be reduced first?";
		String order3Solution = "(λz.((λv.v x) x) z z)(u v) will be reduced first.";
		String order3Predef = "(λz.((λv.v x) x) z z)(u v)(λx. x)u";
		Exercise order3 = new ConcreteExercise(order3Name, order3Task, order3Solution, order3Predef);

		String order4Name = "Call-by-Value";
		String order4Task = "Just like call-by-value, using the call-by-value order the outermost leftmost redex is reduced first and redexes enclosed by an abstraction"
				+ " will never be reduced but in addition this reduction order demands that the right side of a redex has to be a value if it is to be reduced."
				+ " \"Value\" refers to a lambda term that can not be reduced any further, meaning either a variable or an abstraction. \n "
				+ " Using call-by-value which redex in the displayed term will be reduced first?";
		String order4Solution = "(λx.x)(λz. z x)";
		String order4Predef = "(λv. v v)(a b)((λx.x)(λz. z x))";
		Exercise order4 = new ConcreteExercise(order4Name, order4Task, order4Solution, order4Predef);

		Exercise normalRedex = new RedexExercise(new NormalOrder());
		
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		exerciseList.add(exerciseMode);
		exerciseList.add(var);
		exerciseList.add(freeVar);
		exerciseList.add(redex);
		exerciseList.add(redex2);
		exerciseList.add(redex3);
		exerciseList.add(order1);
		exerciseList.add(order2);
		exerciseList.add(order3);
		exerciseList.add(order4);
		exerciseList.add(alphaRedu);
		exerciseList.add(normalRedex);
		
		return exerciseList;
	}

	/**
	 * Deserializes an {@link Exercise}.
	 * @param serialized supposedly a serialization of an Exercise
	 * @return Exercise belonging to serialization
	 * @throws IllegalArgumentException if serialized is {@code null} or empty or exercise could not be found
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
