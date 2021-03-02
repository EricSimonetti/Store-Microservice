import java.util.ArrayList;

public class Node {
    private int id;
    private String locationFlag;
    private ArrayList<String> rightItems;
    private ArrayList<String> leftItems;
    private ArrayList<Edge> neighbors;
    private double pathLength;
    private Node parent;

    public Node(int id, String locationFlag, ArrayList<String> rightItems, ArrayList<String> leftItems){
        this.id = id;
        this.locationFlag = locationFlag;
        this.rightItems = rightItems;
        this.leftItems = leftItems;
        neighbors = new ArrayList<>();
        pathLength = Double.MAX_VALUE;
    }

    public void addNeighbor(Edge edge){
        neighbors.add(edge);
    }
    public void addNeighbor(Node neighbor, double weight){
        neighbors.add(new Edge(-1, neighbor, weight));
    }

    public int getId() {
        return id;
    }

    public String getLocationFlag() {
        return locationFlag;
    }

    public ArrayList<Edge> getNeighbors() {
        return neighbors;
    }

    public ArrayList<String> getLeftItems() {
        return leftItems;
    }

    public ArrayList<String> getRightItems() {
        return rightItems;
    }

    public void addLeftItems(String item) {
        leftItems.add(item);
    }

    public void addRightItems(String item) {
        rightItems.add(item);
    }

    public double getPathLength() {
        return pathLength;
    }

    public Node getParent() {
        return parent;
    }

    public void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
