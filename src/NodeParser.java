import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.sql.ResultSet;


public class NodeParser {
    public static Node parse(ResultSet queryData){
        HashMap<Integer, Node> nodeMap = new LinkedHashMap<>();
        List<Node> nodeList = new ArrayList<>();
        Node node;
        Node root = null;

        int id;
        String locationFlag;
        String[] rightItems;
        String[] leftItems;

        int adjId;
        int adjWeight;

        List<Edge> adjNodes;
        Edge edge;

        try {
            while(queryData.next()) {
                id = queryData.getInt("nodeID");
                if (id > 0) {
                    // load item lists

                    node = new Node(id, null, null, null);

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

        for(int i = 0; i < nodeList.size(); i++){
            node = nodeList.get(i);
            adjNodes = node.getNeighbors();
            for(int n = 0; n < adjNodes.size(); n++){
                edge = adjNodes.get(n);
                edge.setNode(nodeMap.get(edge.getNodeId()));
            }
        }

        return root;
    }
}
