package classes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Graph {

	public class Node {
		private String word;

		public Node(String word) {
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}
	}

	private Map<Node, ArrayList<Node>> edges;

	public Graph() {
		this.edges = new HashMap<Node, ArrayList<Node>>();
	}

	public void addVertex(Node newNode) {
		// check if already exists
		if (edges.containsKey(newNode))
			return;
		this.edges.put(newNode, new ArrayList<Node>());
	}

	public void addEdge(Node newNode, Node finalNode) {
		// check if already exists
		if (!edges.containsKey(newNode))
			this.edges.put(newNode, new ArrayList<Node>());
		this.edges.get(newNode).add(finalNode);
	}

	public void processNode(Node currentNode) {
		System.out.println(currentNode.getWord());
	}

	public void BreadthFirstSearch(Node root) {
		Queue<Node> queue = new ArrayDeque<Node>();
		Node currentNode;
		Node currentNeighbour;
		ArrayList<Node> neighbours;
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		queue.add(root);
		visitedNodes.add(root);
		while (!queue.isEmpty()) {
			currentNode = queue.poll();
			processNode(currentNode);
			neighbours = this.edges.get(currentNode);
			for (int i = 0; i < neighbours.size(); i++) {
				currentNeighbour = neighbours.get(i);
				if (!visitedNodes.contains(currentNeighbour)) {
					queue.add(currentNeighbour);
					visitedNodes.add(currentNeighbour);
				}
			}
		}
	}

	public void DepthFirstSearch(Node root) {
		Stack<Node> s = new Stack<Node>();
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		s.push(root);
		visitedNodes.add(root);
		Node currentNode;
		Node currentNeighbour;
		while (!s.isEmpty()) {
			currentNode = s.pop();
			// process the node
			processNode(currentNode);
			ArrayList<Node> neighbours = this.edges.get(currentNode);
			for (int i = 0; i < neighbours.size(); i++) {
				currentNeighbour = neighbours.get(i);
				if (!visitedNodes.contains(currentNeighbour)) {
					s.push(currentNeighbour);
					visitedNodes.add(currentNeighbour);
				}
			}
		}
	}
}
