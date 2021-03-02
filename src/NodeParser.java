import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;


public class NodeParser {
    private Node root;
    private HashMap<Integer, Node> nodeMap;
    private List<Node> nodeList;

    public NodeParser(){
        Node root = null;
        nodeMap = new LinkedHashMap<>();
        nodeList = new ArrayList<>();
    }

    public Node getRoot(){
        return root;
    }

    public void process(){
        Node node;
        List<Edge> adjNodes;
        Edge edge;

        for(int i = 0; i < nodeList.size(); i++){
            node = nodeList.get(i);
            adjNodes = node.getNeighbors();
            for(int n = 0; n < adjNodes.size(); n++){
                edge = adjNodes.get(n);
                edge.setNode(nodeMap.get(edge.getNodeId()));
            }
        }
    }

    public void parse(ResultSet queryData){
        Node node;

        int id;

        int adjId;
        int adjWeight;

        Edge edge;

        try {
            while(queryData.next()) {
                id = queryData.getInt("nodeID");
                if (id > 0) {
                    node = new Node(id, null, null, null);
                    if(id == 1){
                        root = node;
                    }
                    adjId = queryData.getInt("northNodeID");
                    if (adjId > 0) {
                        adjWeight = queryData.getInt("northNodeDistance");
                        edge = new Edge(adjId, null, adjWeight);
                        node.addNeighbor(edge);
                    }
                    adjId = queryData.getInt("eastNodeID");
                    if (adjId > 0) {
                        adjWeight = queryData.getInt("eastNodeDistance");
                        edge = new Edge(adjId, null, adjWeight);
                        node.addNeighbor(edge);
                    }
                    adjId = queryData.getInt("wsetNodeID");
                    if (adjId > 0) {
                        adjWeight = queryData.getInt("westNodeDistance");
                        edge = new Edge(adjId, null, adjWeight);
                        node.addNeighbor(edge);
                    }
                    adjId = queryData.getInt("southNodeID");
                    if (adjId > 0) {
                        adjWeight = queryData.getInt("southNodeDistance");
                        edge = new Edge(adjId, null, adjWeight);
                        node.addNeighbor(edge);
                    }

                    nodeList.add(node);
                    nodeMap.put(id, node);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        process();
    }

    public void parse(List<Map<String,Object>> dataList){
        Node node;

        int id;

        int adjId;
        int adjWeight;

        Edge edge;
        int i = 0;

        while(dataList.get(i) != null) {
            Map<String,Object> queryData = dataList.get(i);
            id = (Integer)queryData.get("nodeID");
            if (id > 0) {
                node = new Node(id, null, null, null);
                if(id == 1){
                    root = node;
                }
                adjId = (Integer)queryData.get("northNodeID");
                if (adjId > 0) {
                    adjWeight = (Integer)queryData.get("northNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                adjId = (Integer)queryData.get("eastNodeID");
                if (adjId > 0) {
                    adjWeight = (Integer)queryData.get("eastNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                adjId = (Integer)queryData.get("wsetNodeID");
                if (adjId > 0) {
                    adjWeight = (Integer)queryData.get("westNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                adjId = (Integer)queryData.get("southNodeID");
                if (adjId > 0) {
                    adjWeight = (Integer)queryData.get("southNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }

                nodeList.add(node);
                nodeMap.put(id, node);
            }
            i++;
        }
        process();
    }

}
