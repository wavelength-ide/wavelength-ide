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

	private UpdateTreeOutput output;
	private Application nextRedex;

	/**
	 * Creates a new ResolvedNamesVisitor for tree pretty printing.
	 * 
	 * @param libraries
	 *            The libraries to take into account.
	 * @param output
	 *            The output that keeps track of the number of nodes
	 * @param nextRedex
	 *            The redex that is reduced next with the current
	 *            {@link ReductionOrder}
	 */
	public TreeTermVisitor(List<Library> libraries, UpdateTreeOutput output, Application nextRedex) {
		super(libraries);
		this.output = output;
		this.nextRedex = nextRedex;
	}

	@Override
	public TreeTriple visitApplication(Application app) {
		Objects.requireNonNull(app);

		int id = output.nodeId;
		output.nodeId += 1;
		TreeTriple left = app.getLeftHandSide().acceptVisitor(this);
		TreeTriple right = app.getRightHandSide().acceptVisitor(this);
		String node = "";

		// color the tree node if the given application is a redex, the color is
		// depending on whether the redex is the nextRedex
		if (app.acceptVisitor(new IsRedexVisitor())) {
			if (app == nextRedex) {
				node = "{id: " + id + ", label: 'App', color:{background:'#2b91af',border:'#2b91af'},},";
			} else {
				node = "{id: " + id + ", label: 'App', color:{background:'#E6F2FD',border:'#2b91af'},},";
			}
		} else {
			node = "{id: " + id + ", label: 'App'},";
		}

		// add edges from the "App" node to the left and right side of the application
		String edge1 = "{from: " + id + ", to: " + left.idFirst + ", color:{color: '#33333'}},";
		String edge2 = "{from: " + id + ", to: " + right.idFirst + ", color:{color: '#33333'}},";

		return new TreeTriple(left.nodes + right.nodes + node, left.edges + right.edges + edge1 + edge2, id);
	}

	@Override
	public TreeTriple visitNamedTerm(NamedTerm term) {
		Objects.requireNonNull(term);

		int id = output.nodeId;
		output.nodeId += 1;

		String node = "";

		// color the tree node if the given named term is a redex, the color is
		// depending on whether the redex is the nextRedex
		if (term.getInner().acceptVisitor(new IsRedexVisitor())) {
			if (term.getInner() == nextRedex) {
				node = "{id: " + id + ", label: '" + term.getName()
						+ "', color:{background:'#2b91af',border:'#2b91af'},},";
			} else {
				node = "{id: " + id + ", label: '" + term.getName()
						+ "', color:{background:'#E6F2FD',border:'#2b91af'},},";
			}
		} else {
			node = "{id: " + id + ", label: '" + term.getName() + "'},";
		}

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

		int id = output.nodeId;
		output.nodeId += 1;
		// create a node with the name of the free variable
		String node = "{id: " + id + ", label: '" + var.getName() + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitBoundVariable(BoundVariable var, String resolvedName) {
		Objects.requireNonNull(var);

		int id = output.nodeId;
		output.nodeId += 1;
		// create a node with the name of the bound variable
		String node = "{id: " + id + ", label: '" + resolvedName + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitAbstraction(Abstraction abs, String resolvedName) {
		Objects.requireNonNull(abs);

		int idAbs = output.nodeId;
		output.nodeId += 1;
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

}
