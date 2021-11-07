package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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


    /**
     * Name Search Feature: Searches the observable Products list using the user supplied Product Name or partial name
     * @param partialName user text input in search field
     * @return returns any matches
     */
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

    /**
     * ID Search Feature: Searches the observable Products list using the user supplied Product ID
     * @param userId is user input in search field
     * @return returns any matches
     */

    private Product getProductWithId(int userId){
        ObservableList<Product>loadAllProducts = Inventory.getAllProducts();
        for (Product tempProduct : loadAllProducts) {
            if (tempProduct.getId() == userId) {
                return tempProduct;
            }
        }
        return null;
    }


    /**
     * Tries the name search method above to populate first, if no PRODUCTS are returned,
     * the method uses the number search to populate instead.
     * @param event Trigger event: user inputs text in the search field and presses enter
     */
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
        if(products.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No products were found");
            alert.setContentText(null);
            alert.showAndWait();
        }
        productsTable.setItems(products);
    }


    /**
     * Searches the observable PARTS list using the user supplied Part Name
     * @param partialName user input in search field
     * @return returns any matches
     */
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

    /**
     * ID Search Feature: Searches the observable PARTS list using the user supplied Product ID
     * @param userId user input into the search field
     * @return returns any matches
     */

    private Part getPartWithId(int userId){
        ObservableList<Part>loadAllParts = Inventory.getAllParts();
        for (Part tempPart : loadAllParts) {
            if (tempPart.getId() == userId) {
                return tempPart;
            }
        }
        return null;
    }

    /**
     * Uses the search method above to populate the search results
     * @param event Trigger event: user inputs text into the search field and presses enter
     */
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
        if(parts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No Parts were found");
            alert.setContentText(null);
            alert.showAndWait();
        }
        partTable.setItems(parts);
    }




    //Event handlers for buttons

    /**
     * Closes the program when the user presses the exit button
     * @param event the triggering event
     */
    @FXML
    void OnActionCloseProgram(ActionEvent event) {
        System.exit(0);
    }


    /**
     * Deletes the part from the observable list
     * @param event triggering event
     */
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        if(!partTable.getSelectionModel().isEmpty()){ //if empty is not true
            Inventory.deletePart(selectedPart);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("The selected part was deleted");
            alert.setContentText(null);
            alert.showAndWait();
        }
        else if(partTable.getSelectionModel().isEmpty()) { //if empty is true
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please select a part to delete from inventory.");
            alert.setContentText("Thank you!");
            alert.showAndWait();
        }
    }

    /**
     * Deletes the product from the observable list
     * @param event Trigger event: User presses the "delete" button
     */
    @FXML
    void OnActionDeleteProduct(ActionEvent event) throws IOException{
        if(productsTable.getSelectionModel().isEmpty()){ //If empty throw alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please select a product to delete.");
            alert.setContentText("Thank you!");

            alert.showAndWait();

        }

        if (!productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){ //Check if the product has associated parts, throw dialog if true
                Alert associatedPartsAlert = new Alert(Alert.AlertType.WARNING);
                associatedPartsAlert.setTitle("Product Has Parts Associated");
                associatedPartsAlert.setHeaderText("This product has associated parts. " +
                    "Please modify the product to remove associated parts before deleting the product.");
                associatedPartsAlert.setContentText("Thank you!");
                associatedPartsAlert.showAndWait();
        }

        if (((!productsTable.getSelectionModel().isEmpty()) && (productsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty())) == true) { //If not empty
            Inventory.deleteProduct(selectedProduct);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("The selected product was deleted");
            alert.setContentText(null);
            alert.showAndWait();
        }

    }


    /**
     * The product selected from the product table
     */
    private static Product selectedProduct;

    /**
     * gets the selected product to modify - Is used in the ModifyProductFormCont
     * @return selected product from the product table
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Sets the selected product
     * @param selectedProduct product selected to modify
     */
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }


    /**
     * The part selected from the parts table
     */
    private static Part selectedPart;

    /**
     * gets the part selected from the part table to modify - is used in the ModifyPartFormCont
     * @return selected part
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * Sets the selected part from the part table
     * @param selectedPart part selected on the part table to modify
     */
    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }


    /**
     * Gets the selection for parts
     * @param event is the mouse click when the user selects a part from the part table on the main screen
     */
    @FXML
    public void OnClickGetSelection(MouseEvent event) {
        if (!partTable.getSelectionModel().isEmpty()) { //if empty is not true (not empty)
            Part selectedPart = partTable.getSelectionModel().getSelectedItem(); // get the object
            setSelectedPart(selectedPart);
        }
        if (partTable.getSelectionModel().isEmpty()){// if empty

                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("No Part Selected");
                infoRequiredAlert.setHeaderText("Please select a part");
                infoRequiredAlert.setContentText("Thank you");
                infoRequiredAlert.showAndWait();

        }
    }

    /**
     * Checks if a product has been selected, throws an alert if not
     * @param event triggering event: user selects a product from the product table
     */
    @FXML
    public void OnClickGetSelectedProduct(MouseEvent event) {
        if (!productsTable.getSelectionModel().isEmpty()) { //If not empty
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem(); // get the object
            setSelectedProduct(selectedProduct);
        }
        if(productsTable.getSelectionModel().isEmpty()){ //If empty throw alert
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("No Product Selected");
            infoRequiredAlert.setHeaderText("Please select a product");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }
    }


    /**
     * Changes the page to the "Add Part" page
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event triggering event: User presses the "Add" button under parts
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayAddPartMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the "Add Product" menu
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     *
     * @param event triggering event User selects the "Add" button under products
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayAddProductMenu(ActionEvent event) throws IOException{

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the "Modify Part" menu
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event triggering event: User selects the "modify" button under parts
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayModifyPartMenu(ActionEvent event) throws IOException{

        if(!partTable.getSelectionModel().isEmpty()) { //If not empty
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((getClass().getResource("/view/ModifyPartForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(partTable.getSelectionModel().isEmpty()){ //If empty throw alert
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("No Part Selected");
            infoRequiredAlert.setHeaderText("Please select a part");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }
    }

    /**
     * Displays the "Modify Product" menu
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event triggering event: User selects the "modify" button under products
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayModifyProductMenu(ActionEvent event) throws IOException{

        if(!productsTable.getSelectionModel().isEmpty()) { //If not empty
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((getClass().getResource("/view/ModifyProductForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if(productsTable.getSelectionModel().isEmpty()){
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("No Product Selected");
            infoRequiredAlert.setHeaderText("Please select a product");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }
    }

    /**
     * Populates parts table
     * Calls getId() and assigns it to the column, which populates the table cells
     */
    void populatePartTable() {
        partTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }


    /**
     * Initializes the controller
     * Populates Parts table
     * @param url path of the file
     * @param resourceBundle the file resource
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        populatePartTable();
    }
}




