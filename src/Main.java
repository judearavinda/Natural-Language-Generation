
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import classes.Graph;
import classes.Graph.Edge;
import classes.Graph.Node;
import classes.TraverseNode;

public class Main {

	enum SearchType {
		bfs, dfs, heurisitic
	};

	static final String fileName = "input.txt";

	public static void main(String[] args) {
		print(sentenceGenerator("the", new String[] { "DT", "NN", "VBD", "NNP", "IN", "DT", "NN" },
				stringfiedFile(fileName), SearchType.bfs));
		print(sentenceGenerator("the", new String[] { "DT", "NN", "VBD", "NNP", "IN", "DT", "NN" },
				stringfiedFile(fileName), SearchType.dfs));
		print(sentenceGenerator("the", new String[] { "DT", "NN", "VBD", "NNP", "IN", "DT", "NN" },
				stringfiedFile(fileName), SearchType.heurisitic));
		SearchType[] searchtypes = { SearchType.bfs, SearchType.dfs, SearchType.heurisitic };
		for (int i = 0; i < searchtypes.length; i++) {
			System.out.println("");
			System.out.println(searchtypes[i]);
			print(sentenceGenerator("benjamin", new String[] { "NNP", "VBD", "DT", "NN" }, stringfiedFile(fileName),
					searchtypes[i]));
			print(sentenceGenerator("a", new String[] { "DT", "NN", "VBD", "NNP" }, stringfiedFile(fileName),
					searchtypes[i]));
			print(sentenceGenerator("benjamin", new String[] { "NNP", "VBD", "DT", "JJS", "NN" },
					stringfiedFile(fileName), searchtypes[i]));
			print(sentenceGenerator("a", new String[] { "DT", "NN", "VBD", "NNP", "IN", "DT", "NN" },
					stringfiedFile(fileName), searchtypes[i]));
		}

	}

	static String sentenceGenerator(String startingWord, String[] sentenceSpec, String graph, SearchType searchType) {
		Graph g = textProcesser(graph);
		if (searchType == SearchType.heurisitic) { // search for the highest
													// probability first
			for (ArrayList<Edge> value : g.nodes.values()) {
				Collections.sort(value, (o1, o2) -> Double.compare(o2.probability, o1.probability));
			}
		}
		Node root = g.new Node(startingWord, sentenceSpec[0]);

		LinkedList<TraverseNode> currentNodes = new LinkedList<>();
		TraverseNode currentTNode = new TraverseNode(root, root.word, 1.0, 1);
		currentNodes.add(currentTNode);

		double maxProbability = 0;
		String maxProbabilitySentence = "something went wrong";
		int nodesConsidered = 0;

		while (currentNodes.size() > 0) {
			switch (searchType) {
			case bfs:
				currentTNode = currentNodes.pollFirst(); // bfs
				break;
			case dfs:
				currentTNode = currentNodes.pollLast(); // dfs
				break;
			case heurisitic:
				currentTNode = currentNodes.pollLast(); // dfs - heuristic uses
														// dfs
				break;
			}
			nodesConsidered++;
			if (currentTNode.numWords == sentenceSpec.length) { // Found a final
																// node
				if (maxProbability < currentTNode.probability) {
					maxProbability = currentTNode.probability;
					maxProbabilitySentence = currentTNode.fullString;
				}
				continue;
			}
			ArrayList<Edge> childrenList = g.nodes.get(currentTNode.node);
			for (Graph.Edge edge : childrenList) {
				// currentTNode.numWords is actually looks at the next index
				// because starts its count at 1, not 0
				if (!edge.endNode.partofSpeech.equals(sentenceSpec[currentTNode.numWords])) {
					// print(currentTNode.fullString + ' ' + edge.endNode.word);
				} else {
					double newNodeProbability = currentTNode.probability * edge.probability;
					if (newNodeProbability > maxProbability) {
						TraverseNode node = new TraverseNode(edge.endNode,
								currentTNode.fullString + ' ' + edge.endNode.word, newNodeProbability,
								currentTNode.numWords + 1);
						currentNodes.add(node);
					}
					// print(node);
				}
			}
		}

		return "â€œ" + maxProbabilitySentence + "â€� with probability " + maxProbability + "\nTotal nodes considered: "
				+ nodesConsidered;
	}

	static Graph textProcesser(String graphText) {
		Graph graph = new Graph();
		String[] inputLines = graphText.split("\\r\\n|\\n|\\r");
		// Collections.shuffle(Arrays.asList(inputLines));
		for (String line : inputLines) {
			// go through each line
			line = line.replaceAll("//", "/");
			String[] tokens = line.split("/");
			if (tokens.length > 4) {
				String word1 = tokens[0];
				String partofSpeech = tokens[1];
				String word2 = tokens[2];
				String partofSpeech2 = tokens[3];
				double probability = Double.parseDouble(tokens[4]);
				// create new nodes
				Node node1 = graph.new Node(word1, partofSpeech);
				Node node2 = graph.new Node(word2, partofSpeech2);
				graph.addEdge(node1, node2, probability);
			}
		}
		return graph;
	}

	static String stringfiedFile(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static void print(Object obj) {
		System.out.println(obj);
	}
}
