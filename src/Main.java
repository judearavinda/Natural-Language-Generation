
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

import classes.Graph;
import classes.Graph.Node;
import classes.Graph.Edge;
import classes.TraverseNode;

public class Main {
	static final String fileName = "input.txt";
	public static void main(String[] args) {
//		print(question1("hans", new String[]{"NNP","VBD","DT","NN"}, stringfiedFile(fileName)));
		print(question1("benjamin" , new String[]{"NNP","VBD","DT","NN"}, stringfiedFile(fileName)));
		print(question1("a" , new String[]{"DT","NN","VBD","NNP"}, stringfiedFile(fileName)));
		print(question1("benjamin" , new String[]{"NNP","VBD","DT","JJS","NN"}, stringfiedFile(fileName)));
		print(question1("a" , new String[]{"DT","NN","VBD","NNP","IN","DT","NN"}, stringfiedFile(fileName)));
	}




	static String question1(String startingWord, String[] sentenceSpec, String graph){
		Graph g = textProcesser(graph);
		Node root = g.new Node(startingWord, sentenceSpec[0]);


		Queue<TraverseNode> currentNodes = new ArrayDeque<>();
		Queue<TraverseNode> childrenNodes = new ArrayDeque<>();
		TraverseNode currentTNode = new TraverseNode(root, root.word, 1.0);
		currentNodes.add(currentTNode);
//		print(currentTNode);
		for (int i = 1; i < sentenceSpec.length; i++) { //Starts at 1 because we already put in the first word that matches the spec
			while(currentNodes.size() > 0){
				currentTNode = currentNodes.poll();

				ArrayList<Edge> childrenList = g.nodes.get(currentTNode.node);
				if (childrenList == null){ // the node may
					print(currentTNode.node);
				}
				for (Graph.Edge edge : childrenList) {
					if (!edge.endNode.partofSpeech.equals(sentenceSpec[i])) {
//						print(currentTNode.fullString + ' '  + edge.endNode.word);
					}
					else {
						TraverseNode node = new TraverseNode(
								edge.endNode,
								currentTNode.fullString + ' '  + edge.endNode.word,
								currentTNode.probability * edge.probability);
						childrenNodes.add(node);
//						print(node);
					}
				}
			}
			currentNodes = childrenNodes;
			childrenNodes = new ArrayDeque<>();
		}
		double maxProbability = 0;
		String maxProbabilitySentence = "something went wrong";
		for (TraverseNode tn: currentNodes) {
			if (maxProbability < tn.probability ){
				maxProbability = tn.probability;
				maxProbabilitySentence = tn.fullString;
			}
		}
		return "“" + maxProbabilitySentence + "” with probability " + maxProbability + "\nTotal nodes considered: " + currentNodes.size();
	}

	static Graph textProcesser(String graphText) {
		Graph graph = new Graph();
		String[] inputLines = graphText.split("\\r\\n|\\n|\\r");
		for (String line : inputLines ) {
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
