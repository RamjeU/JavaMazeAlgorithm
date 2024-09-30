import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


// Implements a graph structure using adjacency lists.
public class Graph implements GraphADT {
	// Maps each node to a list of edges (adjacency list) for efficient edge lookup.
	private Map<GraphNode, List<GraphEdge>> adjList;
	// Array to store all nodes in the graph, allowing fast access by node index.
	private GraphNode[] nodes;

	// Constructor to initialize the graph with a specific number of nodes.
	public Graph(int n) {
		this.adjList = new HashMap<>();
		this.nodes = new GraphNode[n];
		// Populate the graph with n nodes and initialize their adjacency lists.
		for (int i = 0; i < n; i++) {
			nodes[i] = new GraphNode(i);
			adjList.put(nodes[i], new ArrayList<>());
		}
	}
	// Inserts an edge between two nodes, updating the adjacency lists of both nodes.
	@Override
	public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException {
		// Ensure both nodes exist in the graph before inserting the edge.
		validateNode(u);
		validateNode(v);
		// Create a new edge and add it to the adjacency lists of both nodes.
		GraphEdge edge = new GraphEdge(u, v, type, label);
		adjList.get(u).add(edge); // Add edge to u's list
		adjList.get(v).add(edge); // Since it's an undirected graph, add edge to v's list as well
	}
	// Retrieves a node by its index, throwing an exception if the node doesn't exist.
	@Override
	public GraphNode getNode(int u) throws GraphException {
		if (u < 0 || u >= nodes.length) {
			throw new GraphException("Node does not exist.");
		}
		return nodes[u];
	}
	// Returns an iterator over all edges incident to a node, or null if the node has no edges.
	@Override
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		validateNode(u); // Ensure the node exists
		List<GraphEdge> edges = adjList.get(u);
		if (edges.isEmpty()) {
			return null;
		}
		return edges.iterator();
	}
	// Gets the edge between two specific nodes, throwing an exception if no such edge exists.
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		validateNode(u);
		validateNode(v);
		// Search for the edge in the adjacency list of the first node.
		for (GraphEdge edge : adjList.get(u)) {
			if (edge.firstEndpoint().equals(v) || edge.secondEndpoint().equals(v)) {
				return edge;
			}
		}
		// If no edge is found, throw an exception.
		throw new GraphException("No edge exists between the given nodes.");
	}

	// Checks if two nodes are adjacent (i.e., there is an edge between them).
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		validateNode(u);
		validateNode(v);
		try {
			// If getEdge does not throw an exception, the nodes are adjacent.
			getEdge(u, v);
			return true;
		} catch (GraphException e) {
			// If an exception is caught, the nodes are not adjacent.
			return false;
		}
	}
	// Helper method to validate if a node exists in the graph, throwing an exception if it doesn't.
	private void validateNode(GraphNode node) throws GraphException {
		if (!adjList.containsKey(node)) {
			throw new GraphException("Node does not exist in the graph.");
		}
	}
}
