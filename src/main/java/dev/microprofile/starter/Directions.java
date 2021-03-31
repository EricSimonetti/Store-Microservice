package dev.microprofile.starter;

//direction class contains information the customer needs for the location of each item
class Directions {
    private int shoppingListID;
    private String itemName;
    private int aisle;
    private int rack;
    private String shelf;
    private String side;
    private String department;
    private int itemQuantity;
    private boolean itemStockBool;

    Directions(int shoppingListID, String itemName, int itemQuantity){
        this.shoppingListID = shoppingListID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    //              ------getters-------

    int getShoppingListID() { return shoppingListID; }

    String getItemName() { return itemName; }

    int getAisle() { return aisle; }

    int getRack() { return rack; }

    String getShelf() { return shelf; }

    String getSide() { return side; }

    String getDepartment() { return department; }

    int getItemQuantity() { return itemQuantity; }

    boolean getItemStockBool() { return itemStockBool; }

    //              ------setters-------


    void setAisle(int aisle) { this.aisle = aisle; }

    void setRack(int rack) { this.rack = rack; }

    void setShelf(String shelf) { this.shelf = shelf; }

    void setSide(String side) { this.side = side; }

    void setDepartment(String department) { this.department = department; }

    void setItemStockBool(boolean itemStockBool) { this.itemStockBool = itemStockBool; }
}
