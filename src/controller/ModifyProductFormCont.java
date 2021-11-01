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


    /** Populated the part table for all parts (on top)*/
    void populatePartTable() {
        partTable.setItems(Inventory.getAllParts());

        /** Calls getId() and assigns it to the column, which populates the table cells
         */
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

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
        for (int i = 0; i < loadAllProducts.size(); i++) {
            Part tempPart = loadAllProducts.get(i);

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

        partTable.setItems(parts);
    }

    /** Opens the "Add Product" Page when the user presses the add button */
    @FXML
    void OnActionAddProduct(ActionEvent event) {

    }

    /**Opens the main menu page*/
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Saves the product to the observable list*/
    @FXML
    void OnActionSaveProduct(ActionEvent event) {

    }

    /**Searches for the product by the name or ID provided by the user input*/
    @FXML
    void OnActionSearchProduct(ActionEvent event) {

    }
    /**Initializes the page*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        populatePartTable();


    /** Populates the fields with the previously selected product in the main form*/
    Product selectedProduct = MainFormCont.getSelectedProduct();

        {
        iDTxt.setText(valueOf(selectedProduct.getId()));
        invTxt.setText(valueOf(selectedProduct.getStock()));
        nameTxt.setText(selectedProduct.getName());
        maxTxt.setText(valueOf(selectedProduct.getMax()));
        minTxt.setText(valueOf(selectedProduct.getMin()));
        priceTxt.setText(valueOf(selectedProduct.getPrice()));
        }
    }
}
