package edu.kit.wavelength.client.view.update;

import java.util.List;

import edu.kit.wavelength.client.model.library.Library;
import edu.kit.wavelength.client.model.term.Abstraction;
import edu.kit.wavelength.client.model.term.Application;
import edu.kit.wavelength.client.model.term.BoundVariable;
import edu.kit.wavelength.client.model.term.FreeVariable;
import edu.kit.wavelength.client.model.term.LambdaTerm;
import edu.kit.wavelength.client.model.term.NamedTerm;
import edu.kit.wavelength.client.model.term.PartialApplication;
import edu.kit.wavelength.client.model.term.ResolvedNamesVisitor;

/*
 * String strNodes = new StringBuilder("[")
				.append("{id: 1, label: 'λx'},")
				.append("{id: 2, label: 'λy'},")
				.append("{id: 3, label: 'x'},")
				.append("{id: 4, label: 'y'},")
				.append("{id: 5, label: 'Node 5'}")
				.append("]").toString();
				
	String strEdges = new StringBuilder("[")
			.append("{from: 1, to: 2},")
			.append("{from: 1, to: 3},")
			.append("{from: 2, to: 4},")
			//.append("{from: 2, to: 5},")
			.append("]").toString();
 */

/**
 * Visitor for generating the output of a {@link LambdaTerm} for the {@link TreeOutput} view.
 */
public class TreeTermVisitor extends ResolvedNamesVisitor<TreeTuple> {
	
	//private int id;
	private UpdateTreeOutput output;

	public TreeTermVisitor(List<Library> libraries, UpdateTreeOutput output) {
		super(libraries);
		// id = output.nodeId;
		this.output = output;
	}

	@Override
	public TreeTuple visitApplication(Application app) {
		int id = output.nodeId;
		output.nodeId += 1;
		TreeTuple left = app.getLeftHandSide().acceptVisitor(this);
		TreeTuple right = app.getRightHandSide().acceptVisitor(this);
		String node = "{id: " + id + ", label: 'App'},";
		String edge1 = "{from: " + id + ", to: " + left.idFirst + "},";
		String edge2 = "{from: " + id + ", to: " + right.idFirst + "},";
		return new TreeTuple(left.nodes + right.nodes + node, left.edges + right.edges + edge1 + edge2, id);
	}

	@Override
	public TreeTuple visitNamedTerm(NamedTerm term) {
		int id = output.nodeId;
		String node = "{id: " + this.output.nodeId + ", label: '" + term.getName() + "'},";
		output.nodeId += 1;
		return new TreeTuple(node, "", id);
	}

	@Override
	public TreeTuple visitPartialApplication(PartialApplication app) {
		return app.getRepresented().acceptVisitor(this);
	}

	@Override
	public TreeTuple visitFreeVariable(FreeVariable var) {
		int id = output.nodeId;
		String node = "{id: " + this.output.nodeId + ", label: '" + var.getName() + "'},";
		output.nodeId += 1;
		return new TreeTuple(node, "", id);
	}

	@Override
	protected TreeTuple visitBoundVariable(BoundVariable var, String resolvedName) {
		int id = output.nodeId;
		String node = "{id: " + output.nodeId + ", label: '" + resolvedName + "'},";
		output.nodeId += 1;
		return new TreeTuple(node, "", id);
	}

	@Override
	protected TreeTuple visitAbstraction(Abstraction abs, String resolvedName) {
		int idAbs = 	output.nodeId;
		int idInner = idAbs + 1;
		output.nodeId += 1;
		TreeTuple inner = abs.getInner().acceptVisitor(this);
		String label = "'λ" + resolvedName + "'";
		String nodes = "{id: " + idAbs + ", label: " + label + "}," + inner.nodes;
		String edges = "{from: " + idAbs + ", to: " + idInner + "},";
		return new TreeTuple(nodes, edges, idAbs);		
	}

}
