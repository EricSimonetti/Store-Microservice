public class Edge {
    private Node destination;
    private double weight;

    public Edge(Node node, double weight){
        this.destination = node;
        this.weight = weight;
    }

    public Node getNode(){
        return destination;
    }

    public double getWeight(){
        return weight;
    }
}
