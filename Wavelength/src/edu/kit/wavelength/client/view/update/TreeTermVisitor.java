package edu.kit.wavelength.client.view.update;

import java.util.List;

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

	public TreeTermVisitor(List<Library> libraries, UpdateTreeOutput output, Application nextRedex) {
		super(libraries);
		this.output = output;
		this.nextRedex = nextRedex;
	}

	@Override
	public TreeTriple visitApplication(Application app) {
		int id = output.nodeId;
		output.nodeId += 1;
		TreeTriple left = app.getLeftHandSide().acceptVisitor(this);
		TreeTriple right = app.getRightHandSide().acceptVisitor(this);
		String node = "";
		if (app.acceptVisitor(new IsRedexVisitor())) {
			if (app == nextRedex) {
				node = "{id: " + id + ", label: 'App', color:{background:'#2b91af',border:'#2b91af'},},";
			} else {
				node = "{id: " + id + ", label: 'App', color:{background:'#E6F2FD',border:'#2b91af'},},";
			}
		} else {
			node = "{id: " + id + ", label: 'App'},";
		}

		String edge1 = "{from: " + id + ", to: " + left.idFirst + ", color:{color: '#33333'}},";
		String edge2 = "{from: " + id + ", to: " + right.idFirst + ", color:{color: '#33333'}},";
		return new TreeTriple(left.nodes + right.nodes + node, left.edges + right.edges + edge1 + edge2, id);
	}

	@Override
	public TreeTriple visitNamedTerm(NamedTerm term) {
		int id = output.nodeId;
		output.nodeId += 1;

		String node = "";
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
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public TreeTriple visitFreeVariable(FreeVariable var) {
		int id = output.nodeId;
		output.nodeId += 1;
		String node = "{id: " + id + ", label: '" + var.getName() + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitBoundVariable(BoundVariable var, String resolvedName) {
		int id = output.nodeId;
		output.nodeId += 1;
		String node = "{id: " + id + ", label: '" + resolvedName + "'},";
		return new TreeTriple(node, "", id);
	}

	@Override
	protected TreeTriple visitAbstraction(Abstraction abs, String resolvedName) {
		int idAbs = output.nodeId;
		output.nodeId += 1;
		TreeTriple inner = abs.getInner().acceptVisitor(this);
		String label = "'λ" + resolvedName + "'";
		String nodes = "{id: " + idAbs + ", label: " + label + "}," + inner.nodes;
		String edges = "{from: " + idAbs + ", to: " + inner.idFirst + "}," + inner.edges;
		return new TreeTriple(nodes, edges, idAbs);
	}

}
