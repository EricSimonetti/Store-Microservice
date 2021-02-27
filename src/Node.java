import java.util.ArrayList;

public class Node {
    private int id;
    private String locationFlag;
    private String[] rightItems;
    private String[] leftItems;
    private ArrayList<Node> neighbors;
    private ArrayList<Double> weights;

    public Node(int id, String locationFlag, String[] rightItems, String[] leftItems){
        this.id = id;
        this.locationFlag = locationFlag;
        this.rightItems = rightItems;
        this.leftItems = leftItems;
        neighbors = new ArrayList<>();
        weights = new ArrayList<>();
    }

    public void addNeighbor(Node neighbor, double weight){
        neighbors.add(neighbor);
        weights.add(weight);
    }
}
