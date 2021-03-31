package dev.microprofile.starter;

import java.util.ArrayList;

//Class representing a node used to create a graph data structure.
//Each node has an id, a list of items, and edges that connect the node to neighboring nodes.
public class Node {
    private int id;
    private ArrayList<Item> items;
    private ArrayList<Edge> neighbors;
    private double pathLength;
    private Node parent;

    Node(int id, ArrayList<Item> items){
        this.id = id;
        if(items == null)
          this.items = new ArrayList<>();
        else
          this.items = items;
        neighbors = new ArrayList<>();

        pathLength = Double.MAX_VALUE;
    }

    void addNeighbor(Edge edge){
        neighbors.add(edge);
    }
    public void addNeighbor(Node neighbor, double weight){
        neighbors.add(new Edge(-1, neighbor, weight));
    }

    //              ------getters-------

    int getId() {
        return id;
    }

    ArrayList<Edge> getNeighbors() {
        return neighbors;
    }

    ArrayList<Item> getItems() {
        return items;
    }

    void addItem(Item item) {
        items.add(item);
    }

    double getPathLength() {
        return pathLength;
    }

    Node getParent() {
        return parent;
    }

    //              ------setters-------

    void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }
}
