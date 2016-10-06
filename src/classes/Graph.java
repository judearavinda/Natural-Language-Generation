package classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

	public class Edge {
		public Node startNode;
		public Node endNode;
		public double probability;
		public Edge(Node startNode, Node endNode, double probability) {
			this.startNode = startNode;
			this.endNode = endNode;
			this.probability = probability;
		}

		@Override
		public String toString() {
			return startNode.word + "|"+ startNode.partofSpeech + "<--->" + endNode.word + "|"+ endNode.partofSpeech;
		}
	}

	public class Node {
		public String word;
		public String partofSpeech;
		public Node(String word, String partofSpeech) {
			this.word = word;
			this.partofSpeech = partofSpeech;
		}

		@Override
		public int hashCode() {
			return (word+partofSpeech).hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj.hashCode() == this.hashCode();
		}

		@Override
		public String toString() {
			return this.word + "|"+ this.partofSpeech;
		}
	}

	public Map<Node, ArrayList<Edge>> nodes;

	public Graph() {
		this.nodes = new HashMap<Node, ArrayList<Edge>>();
	}

	public void addVertex(Node newNode) {
		// check if already exists
		if (nodes.containsKey(newNode))
			return;
		this.nodes.put(newNode, new ArrayList<Edge>());
	}

	public void addEdge(Node newNode, Node finalNode, double probability) {
		// check if already exists
		if (!nodes.containsKey(newNode)){
			addVertex(newNode);
		}
		if (!nodes.containsKey(finalNode)){
			addVertex(finalNode);
		}
		this.nodes.get(newNode).add(new Edge(newNode, finalNode, probability));
	}

}
