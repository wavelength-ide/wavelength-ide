package edu.kit.wavelength.client.view.update;

import java.util.List;
import java.util.Objects;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.IsRedexVisitor;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the
 * {@link TreeOutput} view.
 */
public class TreeTermVisitor extends ResolvedNamesVisitor<TreeTriple> {

	private Application nextRedex;
	private int nodeId;

	/**
	 * Creates a new ResolvedNamesVisitor for tree pretty printing.
	 * 
	 * @param libraries
	 *            The libraries to take into account.
	 * @param updateOutput
	 *            The output that keeps track of the number of nodes
	 * @param nextRedex
	 *            The redex that is reduced next with the current
	 *            {@link ReductionOrder}
	 */
	public TreeTermVisitor(List<Library> libraries, Application nextRedex) {
		super(libraries);
		this.nextRedex = nextRedex;
		nodeId = 0;
	}

	@Override
	public TreeTriple visitApplication(Application app) {
		Objects.requireNonNull(app);

		int id = nodeId;
		nodeId += 1;
		TreeTriple left = app.getLeftHandSide().acceptVisitor(this);
		TreeTriple right = app.getRightHandSide().acceptVisitor(this);

		String node = colorNode(app, id, "App");

		// add edges from the "App" node to the left and right side of the application
		String edge1 = "{from: " + id + ", to: " + left.idFirst + ", color:{color: '#33333'}},";
		String edge2 = "{from: " + id + ", to: " + right.idFirst + ", color:{color: '#33333'}},";

		String nodes = left.nodes + right.nodes + node;
		String edges = left.edges + right.edges + edge1 + edge2;
		return new TreeTriple(nodes, edges, id);
	}

	@Override
	public TreeTriple visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		int id = nodeId;
		nodeId += 1;

		String node = colorNode(term.getInner(), id, term.getName());
		return new TreeTriple(node, "", id);
	}

	@Override
	public TreeTriple visitPartialApplication(PartialApplication app) {
		Objects.requireNonNull(app);

		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public TreeTriple visitFreeVariable(FreeVariable var) {
		Objects.requireNonNull(var);

		int id = nodeId;
		nodeId += 1;
		// create a node with the name of the free variable
		String node = "{id: " + id + ", label: '" + var.getName() + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);

		int id = nodeId;
		nodeId += 1;
		// create a node with the name of the bound variable
		String node = "{id: " + id + ", label: '" + resolvedName + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);

		int idAbs = nodeId;
		nodeId += 1;
		TreeTriple inner = abs.getInner().acceptVisitor(this);
		String label = "'λ" + resolvedName + "'";
		// add a node for the name of the abstraction and add all nodes from the inner
		// term
		String nodes = "{id: " + idAbs + ", label: " + label + "}," + inner.nodes;
		// add an edge from the node to the first node of the inner term and add all
		// edges from the inner term
		String edges = "{from: " + idAbs + ", to: " + inner.idFirst + "}," + inner.edges;
		return new TreeTriple(nodes, edges, idAbs);
	}

	/**
	 * color the tree node if the given application is a redex, the color is
	 * depending on whether the redex is the nextRedex.
	 */
	private String colorNode(LambdaTerm app, int id, String label) {
		Objects.requireNonNull(app);
		assert id >= 0;

		String node;
		if (app.acceptVisitor(new IsRedexVisitor())) {
			if (app == nextRedex) {
				node = "{id: " + id + ", label: '" + label + "', color:{background:'#2B91AF',border:'#2B91AF'},},";
			} else {
				node = "{id: " + id + ", label: '" + label + "', color:{background:'#E6F2FD',border:'#2B91AF'},},";
			}
		} else {
			node = "{id: " + id + ", label: '" + label + "'},";
		}
		return node;
	}

}
