package dev.microprofile.starter;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

//PathFinder class is designed to only be used through its static instance, consuming a list of items and returning an optimized path
class PathFinder {
    private static PathFinder INSTANCE = new PathFinder();
    private static NodeParser nodes = new NodeParser();
    private ArrayList<Directions> path = new ArrayList<>();

    //private constructor (since you shouldn't be instantiate any instance of this class)
    //that connects to the database and builds the graph of the store for traversal
    private PathFinder(){}
    /**
     * Sets up a node parser and loads it with the test nodes/items.
     * Finds the root node of the graph and adds items to the list.
     * Runs the algorithm to find the shortest path and prints it.
     *
     * @param items An arraylist of item objects that need to be on the path
     * @see NodeParser
     * @see Item
     */
    static void findPath(ArrayList<Item> items) {

        if(nodes.getRoot() == null) {
            getNodes();
            getItems();

            //non-database testing code
            /*
            System.out.println(nodes);
            nodes.initTestNodes();
            nodes.initTestItems();
            nodes.loadNodes(nodes.getTestNodes());
            nodes.loadItems(nodes.getTestItems());

            // */
        }
        else{
            nodes.reset();
        }

        Node entranceNode = nodes.getRoot();
        if(entranceNode == null){
            System.out.println("Entrance node is null... that's not good");
        }

        ArrayList<Directions> shortishPath = findShortishPath(entranceNode, items);
        printPath(shortishPath);

        INSTANCE.path = shortishPath;
    }

    /**
     * Gets the optimized path including all required items
     *
     * @return The list of direction objects that form the path to all required items.
     * @see Directions
     */
    ArrayList<Directions> getPath(){
        return path;
    }

    static PathFinder getInstance(){
        return INSTANCE;
    }

    /**
     * Uses the entrance node of the graph as a starting point to traverse through the graph to
     * look for the items in the provided list.
     *
     * @param entrance The root node of the graph to traverse.
     * @param items The list of items to search for in the algorithm. Items provided not present in any node will result in
     * a null pointer exception.
     * @return The list of nodes that form the path to all the items.
     */
    private static ArrayList<Directions> findShortishPath(Node entrance, ArrayList<Item> items){
        ArrayList<Directions> shortishPath = new ArrayList<>();
        ArrayList<Node> pathToNextClosest;
        Node source = entrance;

        while(items.size()>0){
	    System.out.println("Items left: " );
            for(Item itemTest : items){
                System.out.println(itemTest.getName());
            }
            System.out.println();
            pathToNextClosest = dijkstra(source, items);                        //do dijkstra to find next closest required node
            source = pathToNextClosest.get(pathToNextClosest.size()-1);         //set source to the next closest required node
            shortishPath.addAll(directionsFromNodes(items, pathToNextClosest)); //concats path to next closest + removes found items
        }

        return shortishPath;
    }


    /**
     * Traverses from the most recently found required node using Dijkstra's algorithm to find the next closest required node
     *
     *
     * @param source The most recently found required node to traverse from.
     * @param items The list of items still left to search for in the algorithm. Items provided not present in any node will result in
     * a null pointer exception.
     * @return The list of nodes that form the path to all the items.
     */
    private static ArrayList<Node> dijkstra(Node source, ArrayList<Item> items){
        Node nextClosest = new Node(0, null);
        ArrayList<Node> queue = new ArrayList<>();

        queue.add(source);
        source.setPathLength(0);
        Node current;
        while(queue.size()>0){
            current = queue.remove(0);
	    System.out.println("Currently at: " + current.getId());
            for(Edge e : current.getNeighbors()){
                Node edgeNode = e.getNode();
		System.out.println("Searching Node " + edgeNode.getId() + " path length: " + edgeNode.getPathLength());
                double newPathLength = e.getWeight() + current.getPathLength();
                if(newPathLength < edgeNode.getPathLength()) {
                    edgeNode.setPathLength(newPathLength);
                    edgeNode.setParent(current);
                    if (edgeNode.getPathLength() < nextClosest.getPathLength()) {           //if the node is closer than the closest required node
                        queue.add(edgeNode);
                        if (edgeNode.getItems().stream().anyMatch(items::contains)) {   //if the node is a required node
                            nextClosest = edgeNode;                                     //set that node as nextClosest
                        }
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
	nodes.reset();//resetDijkstra(source);                                //reset path lengths and parents
        return nextClosestPath;
    }

    /**
     * Uses the path to the next closes node to create direction objects for items found
     * then removes those items from the list we are searching and resets the graph of nodes
     * so it's ready for another run of dijkstra
     *
     * @param nodePath The path from the previous required node to the nextClosest required node
     * @param items The list of items to search for in the algorithm. Items provided not present in any node will result in
     * a null pointer exception.
     * @return The list of directions for all items in the nextClosest node that we are looking for
     */
    private static ArrayList<Directions> directionsFromNodes(ArrayList<Item> items, ArrayList<Node> nodePath){
        ArrayList<Directions> directionsPathToNextClosest = new ArrayList<>();
        Node source = nodePath.get(0);
        Node nextClosest = nodePath.get(nodePath.size()-1);

        ArrayList<Item> matchingNodeItems = new ArrayList(nextClosest.getItems().stream() //all items inside the nextClosest node
                .filter(items::contains)                                                  //that match the items we're looking for
                .collect(Collectors.toList()));
        ArrayList<Item> matchingShoppingListItems = new ArrayList(items.stream()          //all shopping list items that are in
                .filter(nextClosest.getItems()::contains)                                 //the next closest node
                .collect(Collectors.toList()));
	
	for(int i = 0; i<matchingNodeItems.size(); i++){                 //removes duplicate items
            for(int j = i+1; j<matchingNodeItems.size(); j++){
                if(matchingNodeItems.get(i).getName().equals(matchingNodeItems.get(j).getName())){
                    matchingNodeItems.remove(matchingNodeItems.get(j));
                }
            }
        }

	for(Item nodeItem : matchingNodeItems){
            System.out.println("Node item: " + nodeItem.getName());
        }
	for(Item shoppingListItem : matchingShoppingListItems){
            System.out.println("ShoppingList item: " + shoppingListItem.getName() + " " + shoppingListItem.getShoppingListID());
        }	

        for(Item shoppingListItem : matchingShoppingListItems){ //for all shopping list items in the next closest node
            for(Item nodeItem : matchingNodeItems){
                if(shoppingListItem.equals(nodeItem)){          //look at every node item that matches
                    Directions direction = new Directions(shoppingListItem.getShoppingListID(),  //create direction using info from
                            shoppingListItem.getName(),                                          //the shopping list item
                            shoppingListItem.getQuantity());
                    direction.setAisle(nodeItem.getAisle());                                     //add info needed from node item
                    direction.setRack(nodeItem.getRack());
                    direction.setShelf(nodeItem.getShelf());
                    direction.setSide(nodeItem.getSide());
                    direction.setDepartment(nodeItem.getDepartment());
                    direction.setItemStockBool(nodeItem.getStock());

                    directionsPathToNextClosest.add(direction);                                  //concat direction to path

                }
            }
        }

        items.removeAll(matchingNodeItems);                   //remove found items from list we're searching
        return directionsPathToNextClosest;
    }

    /**
     * Traverses from the source node of the most recently preformed dijkstra, and resets all of the distance
     * values, along with any parent pointers
     *
     * @param source The source node from the most recently preformed dijkstra traversal.
     */
    private static void resetDijkstra(Node source){
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

    /**
     * Makes an SQL query to the database to get information about nodes, and uses the NodeParser class to build the
     * received result set into node objects
     *
     * @see NodeParser
     */
    private static void getNodes(){
        Connection con = null;
        PreparedStatement query = null;
        ResultSet res = null;
        String connectionUrl = "jdbc:mysql://localhost:3306/Traveling_Groceries_Nodes_Store_Info_And_Categories_DB";

        try {
            /*
             * build and execute mysql query
             */
            con = MySQLUtil.getConnection();//DriverManager.getConnection(connectionUrl, "vpreikst", "csc480");

            String sqlQuery = "SELECT * FROM Traveling_Groceries_Nodes_Store_Info_And_Categories_DB.PathFindingNodes";
            query = con.prepareStatement(sqlQuery);
            res = query.executeQuery();
            System.out.println("[node result set]" + res.toString());
            nodes.loadNodes(res);

        }
        catch (Exception ex){
            ex.printStackTrace();
            if(ex instanceof SQLException) {
                HandleSQLException((SQLException)ex);
            }
        }

        MySQLUtil.cleanup(con, query, res);
    }

    /**
     * Makes an SQL query to the database to get information about items, and uses the NodeParser class to build the
     * received result set into item objects and add them to their respective nodes
     *
     * @see NodeParser
     */
    private static void getItems(){
        Connection con = null;
        PreparedStatement query = null;
        ResultSet res = null;
        //String connectionUrl = "jdbc:mysql://localhost:3306/Traveling_Groceries_Nodes_Store_Info_And_Categories_DB";

        try{
            /*
             * build and execute mysql query
             */
            con = MySQLUtil.getConnection();
            String sqlQuery = "SELECT IT.itemName, IT.itemStockNum, DE.departmentName, saleBool, aisle, rack, shelf,LO.side, LPNA.pathNodeID" +
                    " FROM Departments AS DE" +
                    " INNER JOIN" +
                    " Locations AS LO" +
                    " ON LO.departmentID = DE.departmentID" +
                    " INNER JOIN" +
                    " ItemLocationAssociations AS ILA" +
                    " ON LO.locationID = ILA.locationID" +
                    " INNER JOIN" +
                    " LocationPathNodeAssociation as LPNA" +
                    " ON LPNA.locationID = LO.locationID" +
                    " INNER JOIN" +
                    " Items as IT" +
                    " ON IT.itemName = ILA.itemName" +
                    " ORDER BY LPNA.pathNodeID";
            query = con.prepareStatement(sqlQuery);
            res = query.executeQuery();
            nodes.loadItems(res);

        }
        catch (Exception ex){
            ex.printStackTrace();
            if(ex instanceof SQLException) {
                HandleSQLException((SQLException)ex);
            }
        }

        MySQLUtil.cleanup(con, query, res);
    }

    static void HandleSQLException(SQLException ex){
        // handle any errors
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    /**
     * Basic print function that prints the ids of all nodes in the path sequentially
     *
     * @param path An arrylist of nodes representing the path
     */
    private static void printPath(ArrayList<Directions> path){
        for(Directions d : path){
            System.out.println("[Direction] Department: " + d.getDepartment()
                    + "\nName: " + d.getItemName()
                    + "\nShoppingListID: " + d.getShoppingListID()
                    + "\nRack: " + d.getRack()
                    + "\nShelf: " + d.getShelf()
                    + "\nSide: " + d.getSide()
                    + "\nQuantity: " + d.getItemQuantity()
                    + "\nStock: " + d.getItemStockBool()
                    + "\n");
        }
    }
}
