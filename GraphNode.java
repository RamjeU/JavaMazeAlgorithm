public class GraphNode {
	// Instance variables
	private int name; // The name of the node
	private boolean mark; // The mark of the node, true or false

	/**
	 * Constructor for the GraphNode class.
	 * @param name The name of the node.
	 */
	public GraphNode(int name) {
		this.name = name;
		this.mark = false; // Nodes start unmarked
	}

	/**
	 * Marks the node with the specified value.
	 * @param mark The new mark value for the node.
	 */
	public void mark(boolean mark) {
		this.mark = mark;
	}

	/**
	 * Checks if the node is marked.
	 * @return The current mark of the node.
	 */
	public boolean isMarked() {
		return this.mark;
	}

	/**
	 * Gets the name of the node.
	 * @return The name of the node.
	 */
	public int getName() {
		return this.name;
	}
}
