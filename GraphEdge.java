public class GraphEdge {

	// Private instance variables
	private GraphNode u; // The first endpoint of the edge
	private GraphNode v; // The second endpoint of the edge
	private int type; // The type of the edge
	private String label; // The label of the edge

	/**
	 * Constructor for the GraphEdge class.
	 * @param u The first endpoint of the edge.
	 * @param v The second endpoint of the edge.
	 * @param type The type of the edge.
	 * @param label The label of the edge.
	 */
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}

	/**
	 * Returns the first endpoint of the edge.
	 * @return The first endpoint of the edge.
	 */
	public GraphNode firstEndpoint() {
		return this.u;
	}

	/**
	 * Returns the second endpoint of the edge.
	 * @return The second endpoint of the edge.
	 */
	public GraphNode secondEndpoint() {
		return this.v;
	}

	/**
	 * Returns the type of the edge.
	 * @return The type of the edge.
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type of the edge to the specified value.
	 * @param type The new type of the edge.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the label of the edge.
	 * @return The label of the edge.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Sets the label of the edge to the specified value.
	 * @param label The new label of the edge.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
}
