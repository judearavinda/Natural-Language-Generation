package classes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Graph {

	public class Edge {
		private Node startNode;
		private Node endNode;
		private double probability;

		public Edge(Node startNode, Node endNode, double probability) {
			this.startNode = startNode;
			this.endNode = endNode;
			this.probability = probability;
		}

		public Node getStartNode() {
			return startNode;
		}

		public void setStartNode(Node startNode) {
			this.startNode = startNode;
		}

		public Node getEndNode() {
			return endNode;
		}

		public void setEndNode(Node endNode) {
			this.endNode = endNode;
		}

		public double getProbability() {
			return probability;
		}

		public void setProbability(double probability) {
			this.probability = probability;
		}
	}

	public class Node {
		private String word;
		private String partofSpeech;

		public String getPartofSpeech() {
			return partofSpeech;
		}

		public void setPartofSpeech(String partofSpeech) {
			this.partofSpeech = partofSpeech;
		}

		public Node(String word, String partofSpeech) {
			this.word = word;
			this.partofSpeech = partofSpeech;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}
	}

	private Map<Node, ArrayList<Edge>> edges;

	public Graph() {
		this.edges = new HashMap<Node, ArrayList<Edge>>();
	}

	public void addVertex(Node newNode) {
		// check if already exists
		if (edges.containsKey(newNode))
			return;
		this.edges.put(newNode, new ArrayList<Edge>());
	}

	public void addEdge(Node newNode, Node finalNode, double probability) {
		// check if already exists
		if (!edges.containsKey(newNode))
			addVertex(newNode);
		if (!edges.containsKey(finalNode))
			addVertex(finalNode);
		this.edges.get(newNode).add(new Edge(newNode, finalNode, probability));
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
			neighbours = new ArrayList<Node>();
			// get neighbours from currentNode
			for (int i = 0; i < this.edges.get(currentNode).size(); i++) {
				neighbours.add(this.edges.get(currentNode).get(i).getEndNode());
			}
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
		ArrayList<Node> neighbours;
		s.push(root);
		visitedNodes.add(root);
		Node currentNode;
		Node currentNeighbour;
		while (!s.isEmpty()) {
			currentNode = s.pop();
			// process the node
			processNode(currentNode);
			neighbours = new ArrayList<Node>();
			// get neighbours from currentNode
			for (int i = 0; i < this.edges.get(currentNode).size(); i++) {
				neighbours.add(this.edges.get(currentNode).get(i).getEndNode());
			}
			for (int i = 0; i < neighbours.size(); i++) {
				currentNeighbour = neighbours.get(i);
				if (!visitedNodes.contains(currentNeighbour)) {
					s.push(currentNeighbour);
					visitedNodes.add(currentNeighbour);
				}
			}
		}
	}

	public int size() {
		return edges.size();
	}
}
