package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class MainFormCont implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField prodSearchBoxTxt;

    @FXML
    private TextField partSearchTextBox;

    /** The following are TableView columns, which will populate data from the "Parts" observable list*/

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    /**TableView columns for the products observable list*/
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryCol;


    /**Name Search Feature: Searches the observable Products list using the user supplied Product Name or partial name*/
    private ObservableList<Product>searchByProductName(String partialName){
        ObservableList<Product>results = FXCollections.observableArrayList();

        ObservableList<Product>loadAllProducts = Inventory.getAllProducts();
        for(Product tempProd : loadAllProducts){
            if (tempProd.getName().contains(partialName)){
                results.add(tempProd);
            }
        }
        return results;
    }

    /**ID Search Feature: Searches the observable Products list using the user supplied Product ID*/

    private Product getProductWithId(int userId){
        ObservableList<Product>loadAllProducts = Inventory.getAllProducts();
        for (int i = 0; i < loadAllProducts.size(); i++){
            Product tempProduct = loadAllProducts.get(i);

            if(tempProduct.getId() == userId){
            return tempProduct;
            }
        }
        return null;
    }


    /**Tries the name search method above to populate first, if no PRODUCTS are returned,
     * the method uses the number search to populate instead. */
    @FXML
    void OnActionSearchProducts(ActionEvent event) {
        String prodNameInput = prodSearchBoxTxt.getText();
        ObservableList<Product> products = searchByProductName(prodNameInput);

        if(products.size() == 0) {
            try {
                int userID = Integer.parseInt(prodNameInput);
                Product tempProduct = getProductWithId(userID);
                if (tempProduct != null) {
                    products.add(tempProduct);
                }
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }
        productsTable.setItems(products);
    }



    /**Searches the observable PARTS list using the user supplied Part Name */
    private ObservableList<Part>searchByPartName(String partialName){
        ObservableList<Part>results = FXCollections.observableArrayList();

        ObservableList<Part>loadAllParts = Inventory.getAllParts();
        for(Part tempPart : loadAllParts){
            if (tempPart.getName().contains(partialName)){
                results.add(tempPart);
            }
        }
        return results;
    }

    /**ID Search Feature: Searches the observable PARTS list using the user supplied Product ID*/

    private Part getPartWithId(int userId){
        ObservableList<Part>loadAllProducts = Inventory.getAllParts();
        for (int i = 0; i < loadAllProducts.size(); i++){
            Part tempPart = loadAllProducts.get(i);

            if(tempPart.getId() == userId){
                return tempPart;
            }
        }
        return null;
    }
    /**Uses the search method above to populate the search results*/
    @FXML
    void OnActionSearchParts(ActionEvent event) {
        String partNameInput = partSearchTextBox.getText();
        ObservableList<Part> parts = searchByPartName(partNameInput);

        if(parts.size() == 0) {
            try {
                int userID = Integer.parseInt(partNameInput);
                Part tempPart = getPartWithId(userID);
                if (tempPart != null) {
                    parts.add(tempPart);
                }
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        partTable.setItems(parts);
    }




    /**Event handlers for the buttons*/

    /**Closes the program when the user presses the exit button*/
    @FXML
    void OnActionCloseProgram(ActionEvent event) {
        System.exit(0);
    }

    /** Deletes the part from the observable list*/
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        System.out.println("Delete Part Button Clicked");
    }

    /**Deletes the product from the observable list*/
    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete Product Button Clicked");
    }


    /**Changes the page to the "Add Part" page*/
    @FXML
    void OnActionDisplayAddPartMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
        */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Displays the "Add Product" menu*/
    @FXML
    void OnActionDisplayAddProductMenu(ActionEvent event) throws IOException{
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Displays the "Modify Part" menu*/
    @FXML
    void OnActionDisplayModifyPartMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/ModifyPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Displays the "Modify Product" menu*/
    @FXML
    void OnActionDisplayModifyProductMenu(ActionEvent event) throws IOException{
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/ModifyProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Populates parts table*/
    void populatePartTable() {
        partTable.setItems(Inventory.getAllParts());
        /** Calls getId() and assigns it to the column, which populates the table cells
         */
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        /** Calls getId() and assigns it to the column, which populates the table cells
         */
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }



    /**Initializes the controller*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        /**Populates Parts table*/
        populatePartTable();
    }


    }




