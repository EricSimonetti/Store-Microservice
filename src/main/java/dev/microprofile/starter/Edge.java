package dev.microprofile.starter;

//class representing an edge between nodes. Stores the node id, a pointer to the node, and the weight associated with the edge
class Edge {
    private int nodeId;
    private Node destination;
    private double weight;

    Edge(int id, Node node, double weight){
        this.nodeId = id;
        this.destination = node;
        this.weight = weight;
    }

    //              ------getters-------

    int getNodeId(){
        return nodeId;
    }

    Node getNode(){
        return destination;
    }

    //              ------setters-------

    Node setNode(Node node){
        return destination = node;
    }

    double getWeight(){
        return weight;
    }
}
