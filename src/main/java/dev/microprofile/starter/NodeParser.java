package dev.microprofile.starter;

import java.sql.SQLException;
import java.util.*;
import java.sql.ResultSet;

//NodeParser class takes result sets from sql queries and builds the node graph needed for traversal,
//while keeping track of the root node for later use
public class NodeParser {
    private Node root;
    private HashMap<Integer, Node> nodeMap;
    private List<Node> nodeList;

    private List<Map<String,Object>> testNodes;
    private List<Map<String,Object>> testItems;

    NodeParser(){
        Node root = null;
        nodeMap = new LinkedHashMap<>();
        nodeList = new ArrayList<>();
        testNodes = new ArrayList<>();
        testItems = new ArrayList<>();

    }

    /**
     * Returns root node (this is the entrance node for the store)
     *
     * @return The root node
     */
    Node getRoot(){
        return root;
    }

    /**
     * Builds node objects and edge objects connecting them
     *
     *
     * @param queryData A ResultSet obeject from a mysql query
     */
    void loadNodes(ResultSet queryData){
        Node node;

        int id;

        int adjId;
        int adjWeight;

        Edge edge;

        try {
            while(queryData.next()) {
                id = queryData.getInt("pathNodeID");
                if (id > 0) {
                    node = new Node(id, null);
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

        setEdges();
    }

    /**
     * Sets the edges of neighboring nodes so the graph is bi-directional
     */
    private void setEdges(){
        Node node;
        List<Edge> adjNodes;
        Edge edge;

        for(Node n : nodeList){
            node = n;
            adjNodes = node.getNeighbors();
            for(Edge e : adjNodes){
                edge = e;
                edge.setNode(nodeMap.get(edge.getNodeId()));
            }
        }
    }

    /**
     * Creates and inserts items into the nodes of the graph
     *
     *
     * @param queryData A ResultSet obeject from a mysql query
     */
    void loadItems(ResultSet queryData){
        int id;
        Item item;

        try {
            while (queryData.next()) {
                item = nextItemFromDBQuery(queryData);

                id = queryData.getInt("pathNodeID");
                for(Node n : nodeList){
                    if(n.getId()==id){
                        n.addItem(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Item nextItemFromDBQuery(ResultSet queryData) throws SQLException{
        String name = queryData.getString("itemName");
        boolean stock = queryData.getBoolean("itemStockNum");
        String department = queryData.getString("departmentName");
        boolean sale = queryData.getBoolean("saleBool");
        int aisle = queryData.getInt("aisle");
        int rack = queryData.getInt("rack");
        String shelf = queryData.getString("shelf");
        String side = queryData.getString("side");

        return new Item(name, stock, department, sale, aisle, rack, shelf, side);
    }

    void reset(){
        for(Node n : nodeList){
            n.setParent(null);
            n.setPathLength(Double.MAX_VALUE);
        }
    }









    /*
    non-database testing functions
     */
    public void loadNodes(List<Map<String,Object>> dataList){
        System.out.println("building nodes");
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
                node = new Node(id, new ArrayList<>());
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
        setEdges();
    }

    public void loadItems(List<Map<String,Object>> dataList){
        Node node;
        int id;
        int i = 0;
        String name;
        boolean stock;
        String department;
        boolean sale;
        int aisle;
        int rack;
        String shelf;
        String side;

        Item item;

        Map<String,Object> queryData;
        while(i < dataList.size()) {
            queryData = dataList.get(i);
            id = (Integer)queryData.get("nodeID");
            if (id > 0) {
                node = nodeMap.get(id);
                name = (String)queryData.get("catName");
                stock = false;
                department = "dummy department";
                sale = false;
                aisle = 0;
                rack = 0;
                shelf = "dummy shelf";
                side = "dummy side";
                item = new Item(name, stock, department, sale, aisle, rack, shelf, side);

                node.addItem(item);
            }
            i++;
        }
    }

    List<Map<String,Object>> getTestNodes(){ return testNodes; }
    List<Map<String,Object>> getTestItems(){ return testItems; }

    void initTestItems() {
        Map<String,Object>tmp;

        tmp = new HashMap<>();
        tmp.put("nodeID",1);
        tmp.put("catName","air");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",2);
        tmp.put("catName","carrots");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",3);
        tmp.put("catName","apples");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",4);
        tmp.put("catName","lettuce");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",5);
        tmp.put("catName","eggs");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",6);
        tmp.put("catName","bananas");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",7);
        tmp.put("catName","tomatoes");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",8);
        tmp.put("catName","flour");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",9);
        tmp.put("catName","chips");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",10);
        tmp.put("catName","bread");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",11);
        tmp.put("catName","discount dvds");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",12);
        tmp.put("catName","peanut butter");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",13);
        tmp.put("catName","pizza");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",14);
        tmp.put("catName","cookies");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",15);
        tmp.put("catName","tupleware");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",16);
        tmp.put("catName","aprons");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",17);
        tmp.put("catName","shoes");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",18);
        tmp.put("catName","ham");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",19);
        tmp.put("catName","turkey");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",20);
        tmp.put("catName","fish");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",21);
        tmp.put("catName","lobster");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",22);
        tmp.put("catName","cheese");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",23);
        tmp.put("catName","socks");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",24);
        tmp.put("catName","ice cream");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",25);
        tmp.put("catName","beans");
        tmp.put("side","right");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",26);
        tmp.put("catName","milk");
        tmp.put("side","left");
        testItems.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",27);
        tmp.put("catName","gum");
        tmp.put("side","left");
        testItems.add(tmp);

        /*
        tmp = new HashMap<>();
        tmp.put("nodeID",);
        tmp.put("catName","");
        tmp.put("side","");
        testItems.add(tmp);
        */
    }

    void initTestNodes(){
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
        tmp.put("eastNodeID",2);
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
        tmp.put("westNodeID",9);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",9);
        tmp.put("southNodeID",7);
        tmp.put("southNodeDistance",4);
        tmp.put("northNodeID",10);
        tmp.put("northNodeDistance",1);
        tmp.put("eastNodeID",8);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",10);
        tmp.put("southNodeID",9);
        tmp.put("southNodeDistance",1);
        tmp.put("westNodeID",11);
        tmp.put("westNodeDistance",7);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",11);
        tmp.put("southNodeID",12);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNodeID",10);
        tmp.put("eastNodeDistance",7);
        tmp.put("westNodeID",14);
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
        tmp.put("eastNodeID",3);
        tmp.put("eastNodeDistance",7);
        tmp.put("westNodeID",16);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",14);
        tmp.put("southNodeID",15);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNodeID",11);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNodeID",20);
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
        tmp.put("eastNodeID",13);
        tmp.put("eastNodeDistance",3);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",17);
        tmp.put("northNodeID",16);
        tmp.put("northNodeDistance",3);
        tmp.put("westNodeID",18);
        tmp.put("westNodeDistance",4);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",18);
        tmp.put("northNodeID",19);
        tmp.put("northNodeDistance",8);
        tmp.put("eastNodeID",17);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNodeID",23);
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
        tmp.put("eastNodeID",14);
        tmp.put("eastNodeDistance",4);
        tmp.put("westNodeID",21);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",21);
        tmp.put("southNodeID",22);
        tmp.put("southNodeDistance",7);
        tmp.put("eastNodeID",20);
        tmp.put("eastNodeDistance",5);
        tmp.put("westNodeID",26);
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
        tmp.put("eastNodeID",18);
        tmp.put("eastNodeDistance",5);
        tmp.put("westNodeID",24);
        tmp.put("westNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",24);
        tmp.put("northNodeID",25);
        tmp.put("northNodeDistance",8);
        tmp.put("eastNodeID",23);
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
        tmp.put("eastNodeID",21);
        tmp.put("eastNodeDistance",5);
        testNodes.add(tmp);

        tmp = new HashMap<>();
        tmp.put("nodeID",27);
        tmp.put("northNodeID",3);
        tmp.put("northNodeDistance",4);
        tmp.put("eastNodeID",1);
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
}
