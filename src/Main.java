import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Node entranceNode = new Node(0, "", null, null);

    }

    private void createGraph(Node node){

    }

    private ArrayList<Node> findShortishPath(Node entrance, ArrayList<String> items){
        ArrayList<Node> shortishPath = new ArrayList<>();
        Node source = entrance;

        while(items.size()>0){
            shortishPath.addAll(dijkstra(source, items));      //do dijkstra to find next closest required node
            source = shortishPath.get(shortishPath.size()-1);  //concat path to next node
            resetDijkstra(entrance);                           //reset values and parents
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
            current = queue.get(0);
            queue.remove(current);

            for(Edge e : current.getNeighbors()){
                Node edgeNode = e.getNode();
                double newPathLength = e.getWeight() + current.getPathLength();

                if(newPathLength < edgeNode.getPathLength()){
                    edgeNode.setPathLength(newPathLength);
                    edgeNode.setParent(current);
                }
                if(current.getPathLength() < nextClosest.getPathLength()){
                    queue.add(current);
                    if(current.getLeftItems().stream().anyMatch(items::contains) ||  //if the node is a required node
                       current.getRightItems().stream().anyMatch(items::contains)){
                        nextClosest = current;                                       //set that node as nextClosest
                        items.removeAll(current.getLeftItems().stream()              //remove items in that node from the
                                .filter(items::contains)                             //set we're searching for
                                .collect(Collectors.toList()));
                        items.removeAll(current.getRightItems().stream()
                                .filter(items::contains)
                                .collect(Collectors.toList()));
                    }
                }
            }
        }
        ArrayList<Node> nextClosestPath = new ArrayList<>();
        current = nextClosest;
        while(current.getParent()!=null){
            nextClosestPath.add(0, current);
        }
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
