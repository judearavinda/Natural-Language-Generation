
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import classes.Graph;
import classes.Graph.Node;

public class Main {
	private static Graph graph;

	public static void main(String[] args) {
		// create new graph
		graph = new Graph();
		Preprocess();
	}

	private static void Preprocess() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "input.txt"));
			String line = br.readLine();
			String word1;
			String word2;
			String partofSpeech;
			String partofSpeech2;
			double probability;
			Node node1;
			Node node2;

			while (line != null) {
				// go through each line
				// replace double slashes with single slashes
				line = line.replaceAll("//", "/");
				String[] tokens = line.split("/");
				if (tokens.length > 4) {

					word1 = tokens[0];
					partofSpeech = tokens[1];
					word2 = tokens[2];
					partofSpeech2 = tokens[3];
					probability = Double.parseDouble(tokens[4]);
					// create new edges
					node1 = graph.new Node(word1, partofSpeech);
					node2 = graph.new Node(word2, partofSpeech2);
					graph.addEdge(node1, node2, probability);
				}
			}
			System.out.println(graph.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
