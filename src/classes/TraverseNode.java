package classes;

public class TraverseNode {
    public Graph.Node node;
    public String fullString;
    public double probability;
    public TraverseNode(Graph.Node node, String  fullString, double probability){
        this.node = node;
        this.fullString = fullString;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return fullString;
    }
}
