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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormCont implements Initializable {
    Stage stage;
    Parent scene;

    Product newProduct = new Product(1, "new test", 100, 25, 0, 100);


    /**
     * TextField variables
     */
    @FXML
    private TextField prodInvTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodPriceTxt;

    @FXML
    private TextField SearchTextBox;


    /**
     * Table column variables for Parts table
     */
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

    /** Variables for the associated parts table*/

    @FXML
    private TableColumn<Part, Double> associatedCost;

    @FXML
    private TableColumn<Part, Integer> associatedId;

    @FXML
    private TableColumn<Part, Integer> associatedInv;

    @FXML
    private TableColumn<Part, String> associatedName;

    @FXML
    private TableView<Part> associatedPartsTable;


//    /**
//     * Saves the product to the Product Observable list
//     */
//    @FXML
//    private Button SaveProduct;

    /**
     * Adds the product to the add product observable list
     */
    @FXML
    void OnActionAddAssociatedPart(ActionEvent event) {
        if(!partTable.getSelectionModel().isEmpty()){ //If a selection is made (not empty)
            Part storedPart = partTable.getSelectionModel().getSelectedItem();
            newProduct.addAssociatedPart(storedPart);
            associatedPartsTable.setItems(newProduct.getAllAssociatedParts());
            System.out.println("Testing associatedParts in the add function: " + newProduct.getAllAssociatedParts());
            populateProductTable();
        }
        else if (partTable.getSelectionModel().isEmpty()){ //If empty throw alert
            Alert noPartsAssocAlert = new Alert(Alert.AlertType.INFORMATION);
            noPartsAssocAlert.setTitle("No Part Selected");
            noPartsAssocAlert.setHeaderText("Please select a part.");
            noPartsAssocAlert.setContentText("Thank you");
            noPartsAssocAlert.showAndWait();
        }
    }


    /**Populates parts table.Calls getId() and assigns it to the column, which populates the table cells.
     *
     */
    void populatePartTable() {
        partTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Populates the associated products table.Calls getId() and assigns it to the column, which populates the table cells.
     */
    void populateProductTable(){
        associatedPartsTable.setItems(newProduct.getAllAssociatedParts());
        associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Assigns the unique id to the part. First the method sorts the parts by id
     * then gets the highest id on the list and increments it
     * @return is the id to be assigned to the part in OnActionSavePart(ActionEvent event)
     */
    public int AssignId(){

        ObservableList<Product> sortedProducts = Inventory.getAllProducts();
        sortedProducts.sort((a, b) -> Integer.compare(a.getId(), b.getId())); // Sorts the parts by id
        int listLength = sortedProducts.size(); // get the size of the list
        Product lastProduct = sortedProducts.get(listLength - 1); //get the last part - minus 1 because indexes start at 0
        int highestId = lastProduct.getId(); // get the highest id (the id of the last part)
        int id = highestId + 1; // increment the highest id
        return id; //return the id assigned in OnActionSavePart(ActionEvent event)
    }


    /**
     *  Displays the main menu when the user presses the cancel button.
     *  The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event Casts the event to the button
     * @throws IOException catches exception
     */

    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

//    /**
//     * Deletes an object from inventory
//      * @param id the product id
//     * @return removes the product at index i and returns the updated list
//     */
//    public boolean delete(int id){
//        ObservableList<Part> loadAllProducts = Inventory.getAllParts();
//        for (int i = 0; i < loadAllProducts.size(); i++) {
//            Part tempPart = loadAllProducts.get(i);
//
//            if (tempPart.getId() == id) {
//                return Inventory.getAllProducts().remove(tempPart);
//            }
//        }
//        return false;
//    }

    /**
     * Removes the associated part from the associated parts list for the product
     * This is an example of dependency
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> tempList = newProduct.getAllAssociatedParts();
        if (selectedAssociatedPart != null){
            for (Part tempPart : tempList) {
                if (tempPart.getId() == selectedAssociatedPart.getId()) {
                    newProduct.getAllAssociatedParts().remove(selectedAssociatedPart);
                }
            }
        }
    }





    /**
     * Searches the observable PARTS list using the user supplied Part Name
     */
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> results = FXCollections.observableArrayList();

        ObservableList<Part> loadAllParts = Inventory.getAllParts();
        for (Part tempPart : loadAllParts) {
            if (tempPart.getName().contains(partialName)) {
                results.add(tempPart);
            }
        }
        return results;
    }


    /**
     * ID Search Feature: Searches the observable PARTS list using the user supplied Part ID
     * @param userId user supplied ID
     * @return returns the matching associated parts
     */

    private Part getPartWithId(int userId) {
        ObservableList<Part> loadAllProducts = Inventory.getAllParts();
        for (Part tempPart : loadAllProducts) {
            if (tempPart.getId() == userId) {
                return tempPart;
            }
        }
        return null;
    }

    /**
     * Uses the search methods above to populate the search results
     */
    @FXML
    void OnActionSearchParts(ActionEvent event) {
        String partNameInput = ((SearchTextBox.getText()));
        ObservableList<Part> parts = searchByPartName(partNameInput);

        if (parts.size() == 0) {
            try {
                int userID = Integer.parseInt(partNameInput);
                Part tempPart = getPartWithId(userID);
                if (tempPart != null) {
                    parts.add(tempPart);
                }
            } catch (NumberFormatException e) {
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


    /**
     * Method redirects users to the main screen after a Part is saved to inventory
     */
    public void RedirectToMainScreen() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Assigns the product name
     * @return returns the name entered by the user or a default name if none is entered
     */
    public String AssignName() {
        String name;
        name = prodNameTxt.getText();
        if (!name.trim().isEmpty()) { //If not empty return the name
            System.out.println(name);
            return name;
        }
        //User did not enter text
        else if (name.trim().isEmpty()) { //if empty show dialog box

            TextInputDialog nameEmptyAlert = new TextInputDialog("");
            nameEmptyAlert.setTitle("Name Entry Required");
            nameEmptyAlert.setHeaderText("Please enter a part name. If a name is not entered, the name will default to 'name not entered'. Thank you! ");
            nameEmptyAlert.setContentText("Part Name");
            System.out.println((String) null);
            nameEmptyAlert.showAndWait();
            name = nameEmptyAlert.getEditor().getText(); //get input from the dialog box
            if (name.trim().isEmpty() == false) { //If the user responds to the dialog box by entering a name
                System.out.println(name);
                return name;
            } else if (name.trim().isEmpty() == true) { //Set a default name if the user did not enter a name in the dialog box
                name = "Name not entered";
            }
        }
        return name;
    }


    /**
     * Reads the user input for the inventory amount (stock), the maximum inventory allowed (max) and the minimum inventory allowed (min)
     * Checks that the inventory amount is in between the min and max stock amount
     * Catches the number format exception if the input is something other than an int for inventory
     * Provides dialog boxes for the user to correct errors
     *
     * @return inventory amount
     */
    public int assignInventory() {

        int stock = Integer.parseInt(prodInvTxt.getText());
        int min = Integer.parseInt(prodMinTxt.getText());
        int max = Integer.parseInt(prodMaxTxt.getText());


        if (stock > min & stock < max){
            return stock;
        }
        else {
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries. Inventory must be in between max and min. Inventory will save as zero.");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
            return 0;
        }
        //return stock;
    }





    /**
     * Checks if the price is empty, throws dialog box if true. Checks if the input is a double, throws dialog box if false
     * @return returns part price
     */
    public double assignPrice() {
        double price;
        price = Double.parseDouble(prodPriceTxt.getText());
        return price;
    }



    /**
     *
     * @return the inventory minimum
     */
    public int assignMin() {

        int min = 0;
        if (Integer.parseInt(prodMinTxt.getText()) < Integer.parseInt(prodMaxTxt.getText())) {
            min = Integer.parseInt(prodMinTxt.getText());
            return min;
        }

        else{
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries. Minimum inventory must be below the maximum. The minimum will save as zero.");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();}

        return min;
    }


    /**
     *
     * @return the inventory maximum
     */
    public int assignMax() {

        int max = 0;
        if (Integer.parseInt(prodMaxTxt.getText()) > Integer.parseInt(prodMinTxt.getText())) {
            max = Integer.parseInt(prodMaxTxt.getText());
            return max;
        }

        else{
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries. Maximum inventory must be larger than minimum");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();}

        return max;
    }

    /**
     * Checks for empty fields, shows an alert if any are found
     */

    public void alert(){
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }



    public void emptyFieldAlert() {
        try {
            if ((prodNameTxt.getText().isEmpty())) {
                alert();
            }
            if (prodInvTxt.getText().isEmpty()) {
                alert();
            }
            if (prodPriceTxt.getText().isEmpty()) {
                alert();
            }
            if (prodMinTxt.getText().isEmpty()) {
                alert();
            }
            if (prodMaxTxt.getText().isEmpty()) {
                alert();
            }

        }
        catch (Exception exception){
            //do nothing
        }
    }

    /**
     * Saves the product to the Observable list "Products"
     * Retrieves user input and converts the data types
     * @param actionEvent is the triggering event
     * @throws IOException catches exception
     */
    @FXML
    void OnActionSaveProduct (ActionEvent actionEvent) throws IOException {
        try{

            double price = assignPrice();
            int max = assignMax();
            int min = assignMin();
            int stock = assignInventory();
            String name = AssignName();
            int id = AssignId();

            emptyFieldAlert();

            Product productToSave = new Product(id, name, price, stock, min, max);

           // newProduct.getAllAssociatedParts();
            System.out.println("Testing save AllAssociatedParts in the save function: " + newProduct.getAllAssociatedParts());
            for (model.Part Part : newProduct.getAllAssociatedParts()){
                productToSave.addAssociatedPart(Part);
            }
            Inventory.addProduct(productToSave);

            RedirectToMainScreen ();
        }
        catch (Exception e){
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries.");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }
    }


    /**
     * Initializes the page, populates the parts table
     * @param url the path to the file
     * @param resourceBundle the file itself
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populatePartTable();

    }

}