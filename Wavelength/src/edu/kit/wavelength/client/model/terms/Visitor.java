package edu.kit.wavelength.client.model.terms;

/**
 * Represents a visitor that visits lambda terms and returns
 * objects of a given type upon visiting a lambda term.
 *
 * @param <T> The type of object that is returned when visiting
 * a term.
 */
public interface Visitor<T> {
	
	/**
	 * Visit an abstraction.
	 * @param abs The abstraction to be visited
	 * @return The return value of the visitor when visiting the given abstraction
	 */
	T visitAbstraction(Abstraction abs);
	
	/**
	 * Visit an application.
	 * @param app The application to be visited
	 * @return The return value of the visitor when visiting the given application
	 */
	T visitApplication(Application app);
	
	/**
	 * Visit a bound variable.
	 * @param var The bound variable to be visited
	 * @return The return value of the visitor when visiting the given bound variable
	 */
	T visitBoundVariable(BoundVariable var);
	
	/**
	 * Visit a free variable.
	 * @param var The free variable to be visited
	 * @return The return value of the visitor when visiting the given free variable
	 */
	T visitFreeVariable(FreeVariable var);
	
	/**
	 * Visit a named term.
	 * @param term The named term to be visited
	 * @return The return value of the visitor when visiting the given named term
	 */
	T visitNamedTerm(NamedTerm term);
	
	/**
	 * Visit a partial application.
	 * @param app The partial application to be visited
	 * @return The return value of the visitor when visiting the given partial application
	 */
	T visitPartialApplication(PartialApplication app);
}
