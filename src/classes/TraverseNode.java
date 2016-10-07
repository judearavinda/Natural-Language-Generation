package classes;

public class TraverseNode {
    public Graph.Node node;
    public String fullString;
    public double probability;
    public int numWords;
    public TraverseNode(Graph.Node node, String  fullString, double probability, int numWords){
        this.node = node;
        this.fullString = fullString;
        this.probability = probability;
        this.numWords= numWords;
    }

    @Override
    public String toString() {
        return fullString;
    }
}
