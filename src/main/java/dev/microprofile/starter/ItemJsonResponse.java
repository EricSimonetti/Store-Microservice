package dev.microprofile.starter;

//Class created to be made directly into jsons for responses to the GUI
public class ItemJsonResponse {
    private String name;
    private boolean inStock;

    ItemJsonResponse(String name, boolean inStock){
        this.name = name;
        this.inStock = inStock;
    }

    String getName() { return name; }
    boolean isInStock() { return inStock; }
}
