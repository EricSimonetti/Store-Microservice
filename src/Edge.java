public class Edge {
    int nodeId;
    private Node destination;
    private double weight;

    public Edge(int id, Node node, double weight){
        this.nodeId = id;
        this.destination = node;
        this.weight = weight;
    }

    public int getNodeId(){
        return nodeId;
    }

    public Node getNode(){
        return destination;
    }

    public Node setNode(Node node){
        return destination = node;
    }

    public double getWeight(){
        return weight;
    }
}
