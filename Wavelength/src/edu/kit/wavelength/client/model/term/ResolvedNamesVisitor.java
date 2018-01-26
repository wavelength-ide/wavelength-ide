package edu.kit.wavelength.client.model.term;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import edu.kit.wavelength.client.model.library.Library;

/**
 * A {@link Visitor} that gives non-colliding names for bound variables.
 *
 * @param <T> The return value of the visitor
 */
public abstract class ResolvedNamesVisitor<T> implements Visitor<T> {
	private DeBruijnList<String> assignedNames;
	private HashSet<String> allAssigned;
	private List<Library> libraries;
	
	public ResolvedNamesVisitor(List<Library> libraries) {
		Objects.requireNonNull(libraries);
		
		this.assignedNames = new DeBruijnList<>();
		this.allAssigned = new HashSet<>();
		this.libraries = libraries;
	}
	
	private boolean collides(Set<String> frees, String name) {
		return allAssigned.contains(name) || frees.contains(name) || libraries.stream().anyMatch(l -> l.containsName(name));
	}
	
	@Override
	public final T visitAbstraction(Abstraction abs)
	{
		String assignedName = abs.getPreferredName();
		Set<String> frees = abs.acceptVisitor(new BlockedNamesVisitor());
		if (collides(frees, assignedName)) {
			int i = 0;
			do {
				++i;
				assignedName = abs.getPreferredName() + String.valueOf(i);
			} while (collides(frees, assignedName));
		}
		assignedNames.push(assignedName);
		allAssigned.add(assignedName);
		T result = this.visitAbstraction(abs, assignedName);
		assignedNames.pop();
		allAssigned.remove(assignedName);
		return result;
	}
	
	@Override
	public abstract T visitApplication(Application app);
	
	@Override
	public final T visitBoundVariable(BoundVariable var)
	{
		return this.visitBoundVariable(var, assignedNames.get(var.getDeBruijnIndex()));
	}
	
	@Override
	public abstract T visitNamedTerm(NamedTerm term);
	
	@Override
	public abstract T visitPartialApplication(PartialApplication app);
	
	@Override
	public abstract T visitFreeVariable(FreeVariable var);
	
	/**
	 * Visit a {@link BoundVariable} with an additional non-colliding name for
	 * said variable.
	 * 
	 * This method is provided merely for convenience, the given name
	 * will be the same as the one provided for the given abstraction.
	 * 
	 * @param var The {@link BoundVariable} to be visited
	 * @param resolvedName The resolved name for the {@link BoundVariable}
	 * @return The return value of the {@link Visitor} upon visiting the given {@link BoundVariable}
	 */
	protected abstract T visitBoundVariable(BoundVariable var, String resolvedName);
	
	/**
	 * Visit an {@link Abstraction} with an additional non-colliding name for
	 * its variable.
	 * 
	 * @param abs The {@link Abstraction} to be visited
	 * @param resolvedName The resolved name for the variable of this
	 * {@link Abstraction}
	 * @return The return value of the visitor upon visiting the given {@link Abstraction}
	 */
	protected abstract T visitAbstraction(Abstraction abs, String resolvedName);
}
