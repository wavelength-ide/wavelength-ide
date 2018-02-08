package edu.kit.wavelength.client.view.update;

/**
 * This class represents a triple of a string for the trees nodes, a string for
 * the trees edges and an integer for the node id.
 */
public class TreeTriple {

	public final String nodes;
	public final String edges;
	public final int idFirst;

	/**
	 * Creates a new triple with the given parameters.
	 * 
	 * @param nodes
	 *            The nodes represented as string
	 * @param edges
	 *            The edges represented as string
	 * @param idFirst
	 *            The id of the first node
	 */
	public TreeTriple(String nodes, String edges, int idFirst) {
		this.nodes = nodes;
		this.edges = edges;
		this.idFirst = idFirst;
	}

}
