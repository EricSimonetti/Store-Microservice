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
        tmp.put("nodeID",7);
        tmp.put("catName","tomatoes");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",4);
        tmp.put("catName","lettuce");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",8);
        tmp.put("catName","flour");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",5);
        tmp.put("catName","eggs");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",20);
        tmp.put("catName","fish");
        tmp.put("side","right");
        testItems.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",15);
        tmp.put("catName","tupleware");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",24);
        tmp.put("catName","ice cream");
        tmp.put("side","left");
        testItems.add(tmp);
    }

    public void initTestNodes(){
        Map<String,Object>tmp;

        tmp = new HashMap<>();
        tmp.put("nodeID",1);
        tmp.put("northNodeID",2);
        tmp.put("northNodeDistance",4);
        tmp.put("westNodeID",27);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",2);
        tmp.put("northNodeID",4);
        tmp.put("northNodeDistance",2);
        tmp.put("southNodeID",1);
        tmp.put("southNodeDistance",4);
        tmp.put("westNodeID",3);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",3);
        tmp.put("northNodeID",5);
        tmp.put("northNodeDistance",2);
        tmp.put("southNodeID",27);
        tmp.put("southNodeDistance",4);
        tmp.put("westNodeID",13);
        tmp.put("westNodeDistance",7);
        tmp.put("eastNode",2);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",4);
        tmp.put("southNodeID",2);
        tmp.put("southNodeDistance",2);
        tmp.put("northNodeID",6);
        tmp.put("northNodeDistance",5);
        testNodes.add(tmp);


        tmp = new HashMap<>();
        tmp.put("nodeID",5);
        tmp.put("southNodeID",3);
        tmp.put("southNodeDistance",2);
        tmp.put("northNodeID",7);
        tmp.put("northNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",6);
        tmp.put("southNodeID",4);
        tmp.put("southNodeDistance",5);
        tmp.put("northNodeID",8);
        tmp.put("northNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",7);
        tmp.put("southNodeID",5);
        tmp.put("southNodeDistance",5);
        tmp.put("northNodeID",9);
        tmp.put("northNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",8);
        tmp.put("southNodeID",6);
        tmp.put("southNodeDistance",4);
        tmp.put("northNodeID",9);
        tmp.put("northNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",9);
        tmp.put("southNodeID",7);
        tmp.put("southNodeDistance",4);
        tmp.put("northNodeID",10);
        tmp.put("northNodeDistance",1);
        tmp.put("eastNode",8);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",10);
        tmp.put("southNodeID",9);
        tmp.put("southNodeDistance",1);
        tmp.put("westNode",11);
        tmp.put("westNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",11);
        tmp.put("southNodeID",12);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNode",10);
        tmp.put("eastNodeDistance",7);
        tmp.put("westNode",14);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",12);
        tmp.put("southNodeID",13);
        tmp.put("southNodeDistance",5);
        tmp.put("northNodeID",11);
        tmp.put("northNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",13);
        tmp.put("northNodeID",12);
        tmp.put("northNodeDistance",5);
        tmp.put("eastNode",3);
        tmp.put("eastNodeDistance",7);
        tmp.put("westNode",16);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",14);
        tmp.put("southNodeID",15);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNode",11);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNode",20);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",15);
        tmp.put("southNodeID",16);
        tmp.put("southNodeDistance",5);
        tmp.put("northNodeID",14);
        tmp.put("northNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",16);
        tmp.put("southNodeID",17);
        tmp.put("southNodeDistance",3);
        tmp.put("northNodeID",15);
        tmp.put("northNodeDistance",5);
        tmp.put("eastNode",13);
        tmp.put("eastNodeDistance",3);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",17);
        tmp.put("northNodeID",16);
        tmp.put("northNodeDistance",3);
        tmp.put("westNode",18);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",18);
        tmp.put("northNodeID",19);
        tmp.put("northNodeDistance",8);
        tmp.put("eastNode",17);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNode",23);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",19);
        tmp.put("southNodeID",18);
        tmp.put("southNodeDistance",8);
        tmp.put("northNodeID",20);
        tmp.put("northNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",20);
        tmp.put("southNodeID",19);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNode",14);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNode",21);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",21);
        tmp.put("southNodeID",22);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNode",20);
        tmp.put("eastNodeDistance",5);
        tmp.put("westNode",26);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",22);
        tmp.put("southNodeID",23);
        tmp.put("southNodeDistance",8);
        tmp.put("northNodeID",21);
        tmp.put("northNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",23);
        tmp.put("northNodeID",22);
        tmp.put("northNodeDistance",8);
        tmp.put("eastNode",18);
        tmp.put("eastNodeDistance",5);
        tmp.put("westNode",24);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",24);
        tmp.put("northNodeID",25);
        tmp.put("northNodeDistance",8);
        tmp.put("eastNode",23);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",25);
        tmp.put("southNodeID",24);
        tmp.put("southNodeDistance",8);
        tmp.put("northNodeID",26);
        tmp.put("northNodeDistance",7);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",26);
        tmp.put("southNodeID",25);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNode",21);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);
        
        tmp = new HashMap<>();
        tmp.put("nodeID",27);
        tmp.put("northNodeID",3);
        tmp.put("northNodeDistance",4);
        tmp.put("eastNode",1);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);
        
        /*tmp = new HashMap<>();
        tmp.put("nodeID",);
        tmp.put("southNodeID",);
        tmp.put("southNodeDistance",);
        tmp.put("northNodeID",);
        tmp.put("northNodeDistance",);
        tmp.put("eastNode",);
        tmp.put("eastNodeDistance",);
        tmp.put("westNode",);
        tmp.put("westNodeDistance",);
        testNodes.add(tmp);*/
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
