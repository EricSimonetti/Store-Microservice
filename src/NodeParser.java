import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;


public class NodeParser {
    private Node root;
    private HashMap<Integer, Node> nodeMap;
    private List<Node> nodeList;

    public List<Map<String,Object>> testNodes;
    public List<Map<String,Object>> testItems;

    public NodeParser(){
        Node root = null;
        nodeMap = new LinkedHashMap<>();
        nodeList = new ArrayList<>();
        testNodes = new ArrayList<>();
        testItems = new ArrayList<>();
        initTestNodes();
        initTestItems();

    }

    private void initTestItems() {
        Map<String,Object>tmp;

        tmp = new HashMap<>();
        tmp.put("nodeID",3);
        tmp.put("catName","tomatoes");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",3);
        tmp.put("catName","lettuce");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",2);
        tmp.put("catName","flour");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",5);
        tmp.put("catName","eggs");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",4);
        tmp.put("catName","ice cream");
        tmp.put("side","right");
        testItems.add(tmp);
    }

    public void initTestNodes(){
        Map<String,Object>tmp;

        tmp = new HashMap<>();
        tmp.put("nodeID",1);
        tmp.put("westNodeID",2);
        tmp.put("westNodeDistance",2);
        tmp.put("eastNodeID",3);
        tmp.put("eastNodeDistance",2);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",2);
        tmp.put("northNodeID",5);
        tmp.put("northNodeDistance",1);
        tmp.put("southNodeID",1);
        tmp.put("southNodeDistance",2);
        tmp.put("eastNodeID",3);
        tmp.put("eastNodeDistance",2);
        tmp.put("westNodeID",4);
        tmp.put("westNodeDistance",1);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",3);
        tmp.put("northNodeID",5);
        tmp.put("northNodeDistance",3);
        tmp.put("southNodeID",1);
        tmp.put("southNodeDistance",2);
        tmp.put("westNodeID",2);
        tmp.put("westNodeDistance",2);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",4);
        tmp.put("southNodeID",2);
        tmp.put("southNodeDistance",1);
        tmp.put("eastNodeID",3);
        tmp.put("eastNodeDistance",1);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",5);
        tmp.put("southNodeID",2);
        tmp.put("southNodeDistance",3);
        tmp.put("eastNodeID",3);
        tmp.put("eastNodeDistance",3);
        tmp.put("westNodeID",4);
        tmp.put("westNodeDistance",1);
        testNodes.add(tmp);
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

    public void loadNodes(ResultSet queryData){
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
                    adjId = queryData.getInt("westNodeID");
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

    public void loadNodes(List<Map<String,Object>> dataList){
        Node node;

        int id;

        int adjId;
        int adjWeight;

        Edge edge;
        int i = 0;
        Map<String,Object> queryData;

        while(i < dataList.size()) {
            queryData = dataList.get(i);
            id = (Integer)queryData.get("nodeID");
            if (id > 0) {
                node = new Node(id, null, new ArrayList<>(), new ArrayList<>());
                if(id == 1){
                    root = node;
                }
                if (queryData.get("northNodeID") != null) {
                    adjId = (Integer)queryData.get("northNodeID");
                    adjWeight = (Integer)queryData.get("northNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                if (queryData.get("eastNodeID") != null) {
                    adjId = (Integer)queryData.get("eastNodeID");
                    adjWeight = (Integer)queryData.get("eastNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                if (queryData.get("westNodeID") != null) {
                    adjId = (Integer)queryData.get("westNodeID");
                    adjWeight = (Integer)queryData.get("westNodeDistance");
                    edge = new Edge(adjId, null, adjWeight);
                    node.addNeighbor(edge);
                }
                if (queryData.get("southNodeID") != null) {
                    adjId = (Integer)queryData.get("southNodeID");
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

    public void loadItems(List<Map<String,Object>> dataList){
        Node node;
        int id;
        int i = 0;
        Map<String,Object> queryData;
        while(i < dataList.size()) {
            queryData = dataList.get(i);
            id = (Integer)queryData.get("nodeID");
            if (id > 0) {
                node = nodeMap.get(id);
                if(queryData.get("side").equals("left")){
                    node.addLeftItems((String) queryData.get("catName"));
                } else {
                    node.addRightItems((String) queryData.get("catName"));
                }
            }
            i++;
        }
        process();
    }
}
