package dev.microprofile.starter;

//item class representing items in nodes, or shopping list items
public class Item {
    private String name;
    private boolean stock;
    private String department;
    private boolean sale;
    private int aisle;
    private int rack;
    private String shelf;
    private String side;
    private int quantity;
    private int shoppingListID;

    //constructor for items inside nodes
    Item(String name, boolean stock, String department, boolean sale, int aisle, int rack, String shelf, String side){
        this.name = name;
        this.stock = stock;
        this.department = department;
        this.sale = sale;
        this.aisle = aisle;
        this.rack = rack;
        this.shelf = shelf;
        this.side = side;
    }

    //constructor for items inside shoppinglists
    Item(String name, int quantity, int shoppingListID){
        this.name = name;
        this.quantity = quantity;
        this.shoppingListID = shoppingListID;
    }

    //              ------getters-------

    String getName() {
        return name;
    }

    int getAisle() {
        return aisle;
    }

    String getDepartment() {
        return department;
    }

    boolean getSale(){ return sale; }

    int getRack() {
        return rack;
    }

    String getShelf() {
        return shelf;
    }

    boolean getStock() {
        return stock;
    }

    String getSide(){
        return side;
    }

    int getQuantity() { return quantity; }

    int getShoppingListID() { return shoppingListID; }

    //override the equals method so item lists can be searched and compared by streams
    @Override
    public boolean equals(Object o) {
        if(o.getClass()!=this.getClass()){
            return false;
        }
        return ((Item)o).getName().equals(name);
    }
}
