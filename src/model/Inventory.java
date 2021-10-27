package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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


    /** Other public methods*/

    /**
     * Adds a new part to the "allParts" list
     *
     * @param newPart represents the newly constructed part
     *                The method passes a new part object to the end of observable list "allParts"
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
     *
     * @param partID is the part ID the user is searching, user input
     * @return returns the part if the search matched an ID in allParts
     * @return otherwise return nothing
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == (partID)) {
                return (Part) part;
            }
        }
        return null;
    }


    /**
     * Search for product ID
     *
     * @param productID for which to search, user input
     * @return retrieves the product
     * @return otherwise returns nothing
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == (productID)) {
                return (Product) product;
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
            if (part.getName() == (partName)) {
                return (ObservableList<Part>) part;
            }
        }
        return null;
    }

    /**
     * Search for product
     *
     * @param productName is the name of the product for which to search
     * @return returns the product
     * @return null: Otherwise, return nothing
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        for (Product product : allProducts) {
            if (Product.class.getName() == (productName)) {
                return (ObservableList<Product>) product;
            }
        }
        return null;
    }

    /**
     * Modifies the part
     *
     * @param index        This is the index of the element to replace
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
     * Deletes a selected part from inventory
     */
    public static boolean deletePart(Part selectedPart) {
        {
            ObservableList<Part> loadAllParts = Inventory.getAllParts();
            for (int i = 0; i < loadAllParts.size(); i++) {
                Part tempPart = loadAllParts.get(i);

                if (selectedPart.getId() == tempPart.getId()) {
                    return Inventory.getAllProducts().remove(selectedPart);
                }
            }
            return false;
        }
    }

    /**Deletes a selected product from inventory*/
        public static boolean deleteProduct (Product selectedProduct){
            {
                ObservableList<Product> loadAllProducts = Inventory.getAllProducts();
                for (int i = 0; i < loadAllProducts.size(); i++) {
                    Product tempProduct = loadAllProducts.get(i);

                    if (selectedProduct.getId() == tempProduct.getId()) {
                        return Inventory.getAllProducts().remove(selectedProduct);
                    }
                }
                return false;
            }
        }


}