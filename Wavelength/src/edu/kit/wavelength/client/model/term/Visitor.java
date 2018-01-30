package edu.kit.wavelength.client.model.term;

/**
 * Represents a visitor that visits {@link lambdaTerm}s and returns objects of a
 * given type upon visiting a {@link lambdaTerm}.
 *
 * @param <T>
 *            The type of object that is returned when visiting a term.
 */
public interface Visitor<T> {

	/**
	 * Visit an {@link Abstraction}.
	 * 
	 * @param abs
	 *            The {@link Abstraction} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link Abstraction}
	 */
	T visitAbstraction(Abstraction abs);

	/**
	 * Visit an {@link Application}.
	 * 
	 * @param app
	 *            The {@link Application} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link Application}
	 */
	T visitApplication(Application app);

	/**
	 * Visit a {@link BoundVariable}.
	 * 
	 * @param var
	 *            The {@link BoundVariable} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link BoundVariable}
	 */
	T visitBoundVariable(BoundVariable var);

	/**
	 * Visit a {@link FreeVariable}.
	 * 
	 * @param var
	 *            The {@link FreeVariable} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link FreeVariable}
	 */
	T visitFreeVariable(FreeVariable var);

	/**
	 * Visit a {@link NamedTerm}.
	 * 
	 * @param term
	 *            The {@link NamedTerm} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link NamedTerm}
	 */
	T visitNamedTerm(NamedTerm term);

	/**
	 * Visit a {@link PartialApplication}.
	 * 
	 * @param app
	 *            The {@link PartialApplication} to be visited
	 * @return The return value of the {@link Visitor} when visiting the given
	 *         {@link PartialApplication}
	 */
	T visitPartialApplication(PartialApplication app);
}
