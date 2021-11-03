package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Product is dependent on the Observable list created in "Inventory" called "Part"*/
public class Product {
    /**
     * This is the list of associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Product constructor*/
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Setters and getters*/

    /**Gets the Id
     *
     * @return returns the product Id
     */
    public int getId() {
        return id;
    }

    /**Setter:
     *
     * @param id is the Id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**Getter: Gets the product name
     * @return returns the product name
     */
    public String getName() {
        return name;
    }

    /**Setter: Sets the product name
     *
     * @param name is the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Getter: Gets the product price
     *
     * @return returns the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**Setter: Sets the product price
     *
     * @param price Is the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**Getter: Gets the amount of product in the inventory stock
     *
     * @return returns how much of the product is in inventory
     */
    public int getStock() {
        return stock;
    }

    /**Setter: Sets how much of the product is in inventory
     *
     * @param stock the amount of product to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**Getter: Gets the minimum amount needed in inventory
     *
     * @return returns the minimum amount required in inventory
     */
    public int getMin() {
        return min;
    }

    /**Setter: Sets the minimum amount required in inventory
     *
     * @param min the minimum amount to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**Getter: Gets the maximum the business can store in inventory
     *
     * @return returns the maximum amount
     */
    public int getMax() {
        return max;
    }

    /**Setter; Sets the maximum amount the business can store in inventory
     *
     * @param max the maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**Adds the associated part to the Observable list Part
     * The associated part is dependent on the observable list "Parts"
     *
     * @param selectedAssociatedPart the associated part
     */
    public void addAssociatedPart(Part selectedAssociatedPart)
    {associatedParts.add(selectedAssociatedPart);}


    /**
     * Removes an item from the associated parts list
     * @param selectedAssociatedPart is the part to remove
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part>getAllAssociatedParts()
    {return associatedParts;}

}