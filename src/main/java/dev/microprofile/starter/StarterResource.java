package dev.microprofile.starter;

import com.google.gson.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/store")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StarterResource {

    /**
     *  Method for handling GET requests to <server>/store.
     *
     *
     * 	@return json string containg the list of all store items, along with their availability meant for use by admins
     */
    @Path("/items")
    @GET
    public String getRequest() {
        String sqlQuery = "SELECT itemName, itemStockNum FROM Traveling_Groceries_Nodes_Store_Info_And_Categories_DB.Items";
        ArrayList<ItemJsonResponse> items = itemsFromDB(sqlQuery, false);
        return new Gson().toJson(items);
    }

    /**
     *  Method for handling POST requests to <server>/search. Returns Items that are in stock and whose name matches the given string
     *
     * 	@param searchTerm String to match item names against
     * 	@return json string containing the names of matching items, along with their availability from the database (which should always be true)
     *
     */
    @Path("/search")
    @POST
    public String getAvailabilityPostRequest(String searchTerm) {
        String pattern = "%"+searchTerm+"%";
        String sqlQuery = "SELECT itemName, itemStockNum " +
                "FROM Traveling_Groceries_Nodes_Store_Info_And_Categories_DB.Items " +
                "WHERE itemName LIKE " + pattern;
        ArrayList<ItemJsonResponse> items = itemsFromDB(sqlQuery, true);
        return new Gson().toJson(items);
    }

    /**
     *  Method for handling POST requests to <server>/list. Adds a new grocery list to the database
     *
     * 	@param items list json with item and shopping list info
     * 	@return json string containg the optimized path including those items
     */
    @Path("/nav")
    @POST
    public String findPathPostRequest(String items) {
        ArrayList<Item> itemList = new ArrayList<>();

        JsonElement jelement = new JsonParser().parse(items);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("lists");
        for(JsonElement je : jarray){
            jobject = je.getAsJsonObject();
            int shoppingListID = jobject.get("shoppingListID").getAsInt();
            JsonArray jItemNameArray = jobject.getAsJsonArray("itemNameArray");
            JsonArray jItemQuantityArray = jobject.getAsJsonArray("itemQuantityArray");
            for(int i = 0; i<jItemNameArray.size(); i++){
                Item item = new Item(jItemNameArray.get(i).getAsString(), jItemQuantityArray.get(i).getAsInt(), shoppingListID);
                itemList.add(item);
            }
        }
        PathFinder.getInstance().findPath(itemList);
        ArrayList<Directions> path = PathFinder.getInstance().getPath();
        return new Gson().toJson(path);
    }

    private ArrayList<ItemJsonResponse> itemsFromDB(String sqlQuery, boolean checkInStock){
        Connection con = null;
        PreparedStatement query = null;
        ResultSet queryData = null;
        Item item = null;
        ArrayList<ItemJsonResponse> items = new ArrayList<>();

        try{
            /*
             * build and execute mysql query
             */
            con = MySQLUtil.getConnection();
            query = con.prepareStatement(sqlQuery);
            queryData = query.executeQuery();


            while (queryData.next()) {
                item = NodeParser.nextItemFromDBQuery(queryData);
                if(item.getStock() || !checkInStock){ //if the item is in stock, or if we don't care whether it is
                    items.add(new ItemJsonResponse(item.getName(), item.getStock())); //create item response
                }
            }


        }
        catch (Exception ex){
            ex.printStackTrace();
            if(ex instanceof SQLException) {
                PathFinder.HandleSQLException((SQLException)ex);
            }
        }
        MySQLUtil.cleanup(con, query, queryData);

        return items;
    }
}
