// import all the java file read and functions to create a stack and read input files.
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Maze {
	// Private variables for maze dimensions, coin count, and maze structure.
	private int coin; // Number of coins available for opening doors.
	private int width; // Number of columns in the maze.
	private int length; // Number of rows in the maze.
	private Graph maze; // Graph representing the maze.
	private GraphNode start; // Starting node of the maze.
	private GraphNode exit; // Exit node of the maze.
	private Stack<GraphNode> path; // Stack to track the path from start to exit.
	private Stack<GraphEdge> execution; // Stack to track visited edges for backtracking.

	// Constructor to initialize the maze from a file.
	Maze (String inputFile) throws GraphException,MazeException{
		String construct; // Temporarily holds line data for processing.
		path = new Stack<GraphNode>();  // Initializes the path stack.
		execution = new Stack<GraphEdge>();  // Initializes the edge tracking stack.

		try {
			FileReader file = new FileReader(inputFile);  // Opens the input file.
			Scanner line = new Scanner(file); // Scanner to read line by line from the file.
			construct = line.nextLine();  // Skips the first line if it's not needed.

			// Parsing the width, length, and coin count from the file.
			construct = line.nextLine();
			width = Integer.parseInt(construct); // Reads the width from the file.
			construct = line.nextLine();
			length = Integer.parseInt(construct); // Reads the length from the file.
			//finally last line before moving to the maze is the coin line to show how many coin is available.
			construct = line.nextLine();
			coin = Integer.parseInt(construct); // Reads the initial number of coins.

			// Total number of nodes in the maze.
			int size = width * length;
			//create a string instruct to instruct what to do to create the maze
			String instruct;
			//set up 2 counter for width and depth
			int counter_w = 0;
			int counter_d = 0;
			//set up a boolean horizontal to true to go between horizontal and vertical
			boolean horizontal = true;
			// Constructs the graph with the specified size.
			maze = new Graph(size);

			// Process lines from the file to construct the maze.
			while (line.hasNextLine() && counter_w < size) {
				instruct = line.nextLine(); // Reads the next line of instructions for the maze.

				if(horizontal) {
					// Processing horizontal edges and nodes.
					for (int i = 0; i < instruct.length(); i = i + 2) {
						char val = instruct.charAt(i); // Node type or corridor identifier.

						// Assigning start and exit nodes based on characters.
						if(val == 's') {
							start = maze.getNode(counter_w);
						}
						else if (val == 'x') {
							exit = maze.getNode(counter_w);
						}

						// Processing corridors and doors between nodes.
						if ((val == 'o' || val =='s' || val == 'x') && i+1 < instruct.length()) {
							char sign = instruct.charAt(i+1);// Corridor or door identifier.
							// Adds a corridor edge with a weight of 0.
							if(sign == 'c') {
								maze.insertEdge(maze.getNode(counter_w),maze.getNode(counter_w+1), 0, "corridor");
							}

							//Otherwise digit meaning door --> get the integer from the character
							else if(Character.isDigit(sign)) {
								// Adds a door with a specific weight if the sign is a digit.
								int it2 = Character.getNumericValue(sign);
								maze.insertEdge(maze.getNode(counter_w), maze.getNode(counter_w+1), it2, "door");
							}
							//Move the pointer to the next node
							counter_w++;
						}
					}
					// Move to the next line after completing horizontal processing.
					if(counter_w < size-1) {
						counter_w++;
					}
					// Switch to vertical processing.
					horizontal = false;
				}
				//now when horizontal is false
				else {
					//create a for loop again with the same instruct that is also i + 2
					for (int i = 0; i < instruct.length(); i = i + 2) {
						// Check the instructions for vertical edges
						char input = instruct.charAt(i);
						// Insert the node at counter_d + width since we're inserting vertical edges.
						if(input == 'c') {
							maze.insertEdge(maze.getNode(counter_d), maze.getNode(counter_d+width), 0, "corridor");
						}

						//If it's a digit, add the width with depth node.
						else if(Character.isDigit(input)) {
							int it = Character.getNumericValue(input);
							maze.insertEdge(maze.getNode(counter_d), maze.getNode(counter_d+width), it, "door");
						}
						//then increase the point for depth as well
						counter_d++;
					}
					//horizontal back to true;
					horizontal = true;
				}
			}
		} catch (FileNotFoundException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Function to solve the maze and return an iterator with the path.
	public Iterator solve() throws GraphException {
		// Invoke the private helper function to determine the path.
		boolean check = findpath(start,exit);
		// If no solution is found, throw an exception.
		if (!check) {
			throw new GraphException("Maze no solution");
		}
		// Return the iterator containing the path.
		return path.iterator();
	}
	// Helper function to recursively check for a path from start to exit.
	private boolean findpath(GraphNode start, GraphNode exit) throws GraphException {
		// Push the start node into the path stack.
		path.push(start);
		// Check if the start node is the exit node; if so, return true.
		if (start.equals(exit)){
			return true;
		}
		// Mark the current node as visited to avoid revisiting it.
		start.mark(true);
		// Create a list to store all incident edges of the start node.
		LinkedList<GraphEdge> edge_list =  new LinkedList<GraphEdge>();
		// Obtain an iterator with all the incident edges of the start node.
		Iterator list = maze.incidentEdges(start);
		// Add all incident edges from the iterator to the edge list.
		while(list.hasNext()) {
			GraphEdge info = (GraphEdge) list.next();
			edge_list.add((GraphEdge) info);
		}
		// Iterate through each incident edge of the start node.
		for (GraphEdge edge : edge_list) {
			// Obtain the second node connected by this edge.
			GraphNode node = edge.secondEndpoint();
			// Check if the second node is not marked, indicating it hasn't been visited yet.
			if (!node.isMarked()){
				//if its a corridor then we push the edge into our execution stack and then we recursively
				// call the find path again with the node from the second end point
				if(edge.getLabel() == "corridor") {
					execution.push(edge);
					if(findpath(node, exit)) {
						return true;
					}
				}
				// If it's a door, check if there are enough coins to pass through.
				else if(edge.getLabel() == "door") {
					// If there are enough coins, deduct them and recursively find a path.
					if (coin  - edge.getType()   >= 0) {
						execution.push(edge);
						// Deduct the required coins.
						coin = coin - edge.getType();
						// Recursively find a pathway to exit.
						if (findpath(node, exit)) {
							return true;
						}
					}
				}
			}
			// Check the first endpoint of the edge.
			GraphNode node2 = edge.firstEndpoint();
			// If the endpoint node is not marked, proceed.
			if (!node2.isMarked()){
				// If the edge label indicates a corridor, explore it.
				if(edge.getLabel() == "corridor") {
					execution.push(edge);
					// Recursively find a path starting from the next node.
					if(findpath(node2, exit)) {
						return true;
					}
				}
				// If the edge label indicates a door, consider the cost.
				else if(edge.getLabel() == "door") {
					// Check if the user has enough coins to pass through the door.
					if (coin - edge.getType()  >= 0) {
						execution.push(edge);
						// Deduct the cost of passing through the door from the user's coins.
						coin = coin - edge.getType();
						// Recursively find a path starting from the next node.
						if (findpath(node2, exit)) {
							return true;
						}
					}
				}
			}
		}
		// If none of the recursive calls return true, it means there's no valid path, so the coin must be added back.
		if(!execution.isEmpty()) {
			// Retrieve the last edge from the execution stack to add the coin back.
			// temporary edge to add the coin back
			GraphEdge temp = execution.pop();
			coin = coin + temp.getType();  // Add the coin back.
		}
		//Unmark the current node and remove it from the path since it's not part of the correct route.
		start.mark(false);
		path.pop();
		return false;
	}
    // This function retrieves the maze graph if it's not null; otherwise, it throws a GraphException.
	Graph getGraph() throws GraphException {
		if (maze == null) {
			throw new GraphException("Maze is null");
		}
		return maze;
	}

}