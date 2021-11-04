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

import static java.lang.String.valueOf;

public class ModifyProductFormCont implements Initializable {

    Stage stage;
    Parent scene;

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


    @FXML
    private TextField SearchTextBox;

/** Text fields for the previously chosen product*/
@FXML
private TextField iDTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    /**Table and columns for the associated parts table*/

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


    /** Populated the part table for all parts (on top)
     * Calls getId() and assigns it to the column, which populates the table cells*/
    void populatePartTable() {
        partTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Populates the associated parts table for the parts associated with the product (on bottom)
     *Calls getId() and assigns it to the column, which populates the table cells */
    void populateAssociatedPartsTable(){

        associatedPartsTable.setItems(MainFormCont.getSelectedProduct().getAllAssociatedParts());

        if(!MainFormCont.getSelectedProduct().getAllAssociatedParts().isEmpty()){ //If not empty

            associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        else if (MainFormCont.getSelectedProduct().getAllAssociatedParts().isEmpty()){ //If empty throw alert
            Alert noPartsAssocAlert = new Alert(Alert.AlertType.INFORMATION);
            noPartsAssocAlert.setTitle("No Parts Are Associated With This Product Yet");
            noPartsAssocAlert.setHeaderText("This product has no parts associated with it yet.");
            noPartsAssocAlert.setContentText("Thank you");
            noPartsAssocAlert.showAndWait();
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
            String partNameInput = SearchTextBox.getText();
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


        Product selectedProduct = MainFormCont.getSelectedProduct();

    /** Adds the selected part to the associated parts list.  */
    @FXML
    void OnActionAddAssociatedPart(ActionEvent event) {
        Product selectedProduct = MainFormCont.getSelectedProduct();
        Part storedPart = partTable.getSelectionModel().getSelectedItem();

        if (!partTable.getSelectionModel().isEmpty()){ //if a selection is made
        selectedProduct.addAssociatedPart(storedPart);
        System.out.println("Associated Parts from add function: " + (selectedProduct.getAllAssociatedParts())); // Testing if the parts are saving to the list
        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
        populateProductTable();}
    else if (partTable.getSelectionModel().isEmpty()){ //If empty throw alert
        Alert noPartsAssocAlert = new Alert(Alert.AlertType.INFORMATION);
        noPartsAssocAlert.setTitle("No Part Selected");
        noPartsAssocAlert.setHeaderText("Please select a part.");
        noPartsAssocAlert.setContentText("Thank you");
        noPartsAssocAlert.showAndWait();
    }
    }


    /** Calls getId() and assigns it to the column, which populates the table cells
     */
    void populateProductTable() {
        associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());

        associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Removes the associated part from the associated parts list for the product
     * This is an example of dependency
     * @param event triggering event
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> tempList = selectedProduct.getAllAssociatedParts();
        if (selectedAssociatedPart != null) {
            for (Part tempPart : tempList) {
                if (tempPart.getId() == selectedAssociatedPart.getId()) {
                    selectedProduct.getAllAssociatedParts().remove(selectedAssociatedPart);
                }
            }
        }
    }

    /**Opens the main menu page
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event triggering event
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void alert() {
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }
    public void emptyFieldAlert() {
        try {
            if ((nameTxt.getText().isEmpty())) {
                alert();
            }
            if (invTxt.getText().isEmpty()) {
                alert();
            }
            if (priceTxt.getText().isEmpty()) {
                alert();
            }
            if (minTxt.getText().isEmpty()) {
                alert();
            }
            if (maxTxt.getText().isEmpty()) {
                alert();
            }

        } catch (Exception exception) {
            //do nothing
        }
    }
    /**
     * @return returns the name entered by the user or a default name if none is entered
     */
    public String AssignName() {
        String name;
        name = nameTxt.getText();
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
            if (!name.trim().isEmpty()) { //If the user responds to the dialog box by entering a name
                System.out.println(name);
                return name;
            } else if (name.trim().isEmpty()) { //Set a default name if the user did not enter a name in the dialog box
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

        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());


        if (stock > min & stock < max) {
            return stock;
        } else {
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
     *
     * @return returns part price
     */
    public double assignPrice() {
        double price;
        price = Double.parseDouble(priceTxt.getText());
        return price;
    }


    /**
     * @return the inventory minimum
     */
    public int assignMin() {

        int min = 0;
        if (Integer.parseInt(minTxt.getText()) < Integer.parseInt(maxTxt.getText())) {
            min = Integer.parseInt(minTxt.getText());
            return min;
        } else {
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries. Minimum inventory must be below the maximum. The minimum will save as zero.");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }

        return min;
    }


    /**
     * @return the inventory maximum
     */
    public int assignMax() {

        int max = 0;
        if (Integer.parseInt(maxTxt.getText()) > Integer.parseInt(minTxt.getText())) {
            max = Integer.parseInt(maxTxt.getText());
            return max;
        } else {
            Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
            infoRequiredAlert.setTitle("Something went wrong.");
            infoRequiredAlert.setHeaderText("Please check your entries. Maximum inventory must be larger than minimum");
            infoRequiredAlert.setContentText("Thank you");
            infoRequiredAlert.showAndWait();
        }

        return max;
    }


    /**Redirects user to the main screen*/
    public void RedirectToMainScreen () throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Saves the product to the observable list products
     * @param event triggering event
     * @throws IOException catches exception
     *  Retrieves user input and converts the data types
     */
    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException {

        Product selectedProduct = MainFormCont.getSelectedProduct();

        //ObservableList<Product> loadAllProducts = Inventory.getAllProducts();
        //int index = loadAllProducts.indexOf(selectedProduct);

            try {
                // Retrieves user input and converts the data types
                double price = assignPrice();
                int max = assignMax();
                int min = assignMin();
                int stock = assignInventory();
                String name = AssignName();
                int id = selectedProduct.getId();
                ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();

                emptyFieldAlert();

                Product modifiedProduct = new Product( id, name, price, stock, min, max);
                Inventory.addProduct(modifiedProduct);
                //System.out.println("AssociatedParts from save function (1st time, modified product): " + (modifiedProduct.getAllAssociatedParts()));//Testing to see if the associateParts are saving
                //System.out.println("AssociatedParts from save function (1st time, selected product): " + (selectedProduct.getAllAssociatedParts()));//Testing to see if the associateParts are saving
                for (model.Part Part : selectedProduct.getAllAssociatedParts()){
                    modifiedProduct.addAssociatedPart(Part);
                }

                Inventory.deleteProduct(selectedProduct);
                associatedParts.setAll(modifiedProduct.getAllAssociatedParts());
                System.out.println("AssociatedParts from save function (2nd time, modified product): " + (modifiedProduct.getAllAssociatedParts()));//Testing to see if the associateParts are saving


                RedirectToMainScreen();
            }
            catch(
                    Exception e)

            {
                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("Something went wrong.");
                infoRequiredAlert.setHeaderText("Please check your entries.");
                infoRequiredAlert.setContentText("Thank you");
                infoRequiredAlert.showAndWait();
            }
    }


//    /**Searches for the product by the name or ID provided by the user input*/
//    @FXML
//    void OnActionSearchProduct(ActionEvent event) {
//
//    }

    /**Initializes the page
     *
     * @param url file path
     * @param resourceBundle file resource
     * Populates the text fields with the previously selected product in the main form
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        populatePartTable();
        populateAssociatedPartsTable();

    Product selectedProduct = MainFormCont.getSelectedProduct();

    if (selectedProduct != null)
        {
        iDTxt.setText(valueOf(selectedProduct.getId()));
        invTxt.setText(valueOf(selectedProduct.getStock()));
        nameTxt.setText(selectedProduct.getName());
        maxTxt.setText(valueOf(selectedProduct.getMax()));
        minTxt.setText(valueOf(selectedProduct.getMin()));
        priceTxt.setText(valueOf(selectedProduct.getPrice()));
        }
//    else{
//        //
//    }
       //
    }
}
