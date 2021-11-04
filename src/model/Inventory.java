package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

/**Public class "Inventory" and the private members*/
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Getter:
     *
     * @return allParts, retrieves the observable list "allParts"
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter:
     *
     * @return allProducts, retrieves the observable list "allProducts"
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    //** Other public methods*/

    /**
     * Adds a new part to the "allParts" list
     *
     * @param newPart represents the newly constructed part
     * The method passes a new part object to the end of observable list "allParts"
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * Adds a new product to the "allProducts" observable list
     *
     * @param newProduct is the newly constructed product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /**
     * Retrieves the part by part ID
     * @param partID is the part ID the user is searching, user input
     * @return returns the part if the search matched an ID in allParts / otherwise return nothing
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == (partID)) {
                return part;
            }
        }
        return null;
    }


    /**
     * Search for product ID
     *
     * @param productID for which to search, user input
     * @return retrieves the product / otherwise returns nothing
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == (productID)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Search for part name
     *
     * @param partName is the name input to use to search
     * @return returns the part
     */
    public static ObservableList<Part> lookupPart(String partName) {
        for (Part part : allParts) {
            if (Objects.equals(part.getName(), partName)) {
                return (ObservableList<Part>) part;
            }
        }
        return null;
    }

    /**
     * Search for product
     *
     * @param productName is the name of the product for which to search / Otherwise, return nothing
     * @return returns the product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (Product.class.getName().equals(productName)) {
                return (ObservableList<Product>) product;
            }
        }
        return null;
    }

    /**
     * Modifies the part
     *
     * @param index This is the index of the element to replace
     * @param modifiedPart This is the element to be stored at the specified position
     */
    public static void updatePart(int index, Part modifiedPart) {
        allParts.set(index, modifiedPart);
    }


    /**
     * Modifies the product
     *
     * @param index      updates the product index
     * @param newProduct is the product to update
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }



    /**
     *  * Deletes a selected selectedPart from inventory
     * @param selectedPart the selectedPart selected
     * @return if the selectedPart was removed
     */
    public static boolean deletePart(Part selectedPart) {
        {
            ObservableList<Part> loadAllParts = Inventory.getAllParts();
            for (Part tempPart : loadAllParts) {
                if (selectedPart.getId() == tempPart.getId()) {
                    return Inventory.getAllParts().remove(selectedPart);
                }
            }
            return false;
        }
    }



    /**
     * Deletes a selected selectedProduct from inventory
     * @param selectedProduct the selectedProduct selected
     * @return if the selectedProduct was removed
     */
        public static boolean deleteProduct (Product selectedProduct){
            {
                ObservableList<Product> loadAllProducts = Inventory.getAllProducts();
                for (Product tempProduct : loadAllProducts) {
                    if (selectedProduct.getId() == tempProduct.getId()) {
                        return Inventory.getAllProducts().remove(selectedProduct);
                    }
                }
                return false;
            }
        }


}