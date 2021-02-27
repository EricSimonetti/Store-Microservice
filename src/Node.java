import java.util.ArrayList;

public class Node {
    private int id;
    private String locationFlag;
    private String[] rightItems;
    private String[] leftItems;
    private ArrayList<Edge> neighbors;

    public Node(int id, String locationFlag, String[] rightItems, String[] leftItems){
        this.id = id;
        this.locationFlag = locationFlag;
        this.rightItems = rightItems;
        this.leftItems = leftItems;
        neighbors = new ArrayList<>();
    }

    public void addNeighbor(Node neighbor, double weight){
        neighbors.add(new Edge(neighbor, weight));
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

    public String[] getLeftItems() {
        return leftItems;
    }

    public String[] getRightItems() {
        return rightItems;
    }
}
