import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        NodeParser nodes = new NodeParser();
        nodes.loadNodes(nodes.testNodes);
        nodes.loadItems(nodes.testItems);

        Node entranceNode = nodes.getRoot();
    }

    private void createGraph(Node node){

    }

    private ArrayList<Node> findShortishPath(Node entrance, ArrayList<String> items){
        ArrayList<Node> shortishPath = new ArrayList<>();
        Node source = entrance;

        while(items.size()>0){
            shortishPath.addAll(dijkstra(source, items));      //do dijkstra to find next closest required node + concat path to next node
            source = shortishPath.get(shortishPath.size()-1);  //set source to the next closest required node
        }

        return shortishPath;
    }

    private ArrayList<Node> dijkstra(Node source, ArrayList<String> items){
        Node nextClosest = new Node(0, "", null, null);
        ArrayList<Node> queue = new ArrayList<>();

        queue.add(source);
        source.setPathLength(0);
        Node current;
        while(queue.size()>0){
            current = queue.remove(0);

            for(Edge e : current.getNeighbors()){
                Node edgeNode = e.getNode();
                double newPathLength = e.getWeight() + current.getPathLength();

                if(newPathLength < edgeNode.getPathLength()){
                    edgeNode.setPathLength(newPathLength);
                    edgeNode.setParent(current);
                }
                if(current.getPathLength() < nextClosest.getPathLength()){             //if the node is closer than the closest required node
                    queue.add(current);
                    if(current.getLeftItems().stream().anyMatch(items::contains) ||    //if the node is a required node
                       current.getRightItems().stream().anyMatch(items::contains)){
                        nextClosest = current;                                         //set that node as nextClosest
                    }
                }
            }
        }
        ArrayList<Node> nextClosestPath = new ArrayList<>();
        current = nextClosest;
        while(current.getParent()!=null){
            nextClosestPath.add(0, current);
            current = current.getParent();
        }

        items.removeAll(nextClosest.getLeftItems().stream()   //remove matching items in nextClosest from the
                .filter(items::contains)                      //set we're searching for
                .collect(Collectors.toList()));
        items.removeAll(nextClosest.getRightItems().stream()
                .filter(items::contains)
                .collect(Collectors.toList()));

        resetDijkstra(source);                                //reset values and parents
        return nextClosestPath;
    }

    private void resetDijkstra(Node source){
        ArrayList<Node> queue = new ArrayList<>();
        Node current = source;
        queue.add(current);
        while(queue.size()>0) {
            current = queue.get(0);
            queue.remove(current);

            for (Edge e : current.getNeighbors()) {
                Node edgeNode = e.getNode();

                if (edgeNode.getParent()!=null) {
                    edgeNode.setParent(null);
                    edgeNode.setPathLength(Double.MAX_VALUE);
                    queue.add(edgeNode);
                }
            }
        }
    }
}
