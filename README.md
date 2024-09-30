# JavaMazeAlgorithm
A Java-based program that solves mazes using graph theory and a modified depth-first search (DFS) traversal. The maze is represented as an undirected graph, with nodes corresponding to rooms and edges representing corridors or doors. Some doors require coins to open, and the program efficiently manages resources to find a path from the maze entrance to the exit.

## Features

- **Graph-based Representation**: Uses adjacency lists to model rooms, corridors, and doors.
- **DFS-based Traversal**: Implements a modified DFS to find a path through the maze while managing coin usage.
- **Coin-based Constraints**: Limits the number of doors that can be opened based on available coins, adding a layer of complexity.
- **Dynamic Input Parsing**: Reads the maze structure from an input file and builds the graph dynamically.
- **Edge Types**: Handles different types of edges, such as corridors (free paths) and doors (coin-based paths).

## Technologies Used

- **Java**: Core programming language.
- **Graph Theory**: Used to model the maze structure.
- **Depth-First Search (DFS)**: Algorithm for maze traversal.
- **File Parsing**: Reads maze layout from an input file for dynamic graph construction.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or later.

### Running the Program

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/maze-solving-algorithm.git
   cd maze-solving-algorithm
