package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class ModifyPartFormCont implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Variable to hold the part selected in the Main Menu
     */
    @FXML
    private TextField iDTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private Label labelPartCategory;

    @FXML
    private ToggleGroup labelPartToggle;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    /**
     * Variables for the  radio buttons
     */
    public RadioButton selectedInHouse;

    @FXML
    public RadioButton selectedOutsourced;

    /**
     * Label variables for Machine ID or Company name
     */

    @FXML
    private TextField categoryTxtField;

    /**
     * Assigns the toggle group, ensures that only one radio in this group can be selected at a time
     */
    @FXML
    public ToggleGroup modifyPartToggle;

    public ModifyPartFormCont() {
    }

    /** Text fields for the prior selected  part attribute*/


    /**
     * Opens the main menu page
     */
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks the radio buttons, and changes the label on Machine ID
     */
    @FXML
    void isPartInHouse(ActionEvent event) {
        if (selectedInHouse.isSelected()) {
            labelPartCategory.setText("Machine ID");
        } else {
            labelPartCategory.setText("Company Name");
        }
        return;
    }


    /**
     * ID Search Feature: Searches the observable Products list using the user supplied Product ID
     */
    private Product getProductWithId(int id) {
        ObservableList<Product> loadAllProducts = Inventory.getAllProducts();
        for (int i = 0; i < loadAllProducts.size(); i++) {
            Product tempProduct = loadAllProducts.get(i);

            if (tempProduct.getId() == id) {
                return tempProduct;
            }
        }
        return null;
    }

    /**
     * Performs update on a specified part
     */
    public boolean update(int id, Part tempPart) {
        int index = -1;

        for (Part part : Inventory.getAllParts()) {
            index++;
            if (part.getId() == id) {
                Inventory.getAllParts().set(index, part);
                return true;
            }
        }
        return false;
    }

    /**
     * assigns a new id
     */
    public int AssignId() {

        ObservableList<Part> sortedParts = Inventory.getAllParts();
        Collections.sort(sortedParts, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        ; // Sorts the parts by id
        int listLength = sortedParts.size(); // get the size of the list
        Part lastPart = sortedParts.get(listLength - 1); //get the last part - minus 1 because indexes start at 0
        int highestId = lastPart.getId(); // get the highest id (the id of the last part)
        int id = highestId + 1; // increment the highest id
        return id; //return the id assigned in OnActionSavePart(ActionEvent event)
    }


    /**
     * Saves the part to the observable inventory list when the user saves modification
     */
    @FXML
    public void OnActionModifyPart(ActionEvent actionEvent) {
        Part selectedPart = MainFormCont.getSelectedPart();

        ObservableList<Part> loadAllParts = Inventory.getAllParts();
        int index = loadAllParts.indexOf(selectedPart);

        if (selectedInHouse.isSelected()) {

            int id = selectedPart.getId();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int machineID = Integer.parseInt(machineIdTxt.getText());

            Part modifiedPart = new InHouse(id, name, price, stock, min, max, machineID);

                if (selectedPart instanceof InHouse) {
                    Inventory.updatePart(index, modifiedPart);
                    }
                else if (selectedPart instanceof Outsourced) {
                    Inventory.addPart(modifiedPart);
                    Inventory.deletePart(selectedPart);
                    }
        }
        else if (selectedOutsourced.isSelected()) {
            int id = selectedPart.getId();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            String companyName = machineIdTxt.getText();

            Part modifiedPart = new Outsourced(id, name, price, stock, min, max, companyName);

                /** Checks the class type of the previous entry, if the class type is the same then just update the part.*/
                if (selectedPart instanceof Outsourced) {
                    Inventory.updatePart(index, modifiedPart);
                }

                /**If the class type is different from what is currently selected, add the part to the list and delete the previous entry*/
                else if (selectedPart instanceof InHouse) {
                    Inventory.addPart(modifiedPart);
                    Inventory.deletePart(selectedPart);
                }
        }
    }


    /**
     * Initializes the page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** Classifies the part selected in the main controller and assigns attributed to the fields, auto-populates fields*/
        Part selectedPart = MainFormCont.getSelectedPart();

        if (selectedPart instanceof InHouse) {
            iDTxt.setText(valueOf(selectedPart.getId()));
            invTxt.setText(valueOf(selectedPart.getStock()));
            nameTxt.setText(selectedPart.getName());
            machineIdTxt.setText(valueOf(((InHouse) selectedPart).getMachineId()));
            maxTxt.setText(valueOf(selectedPart.getMax()));
            minTxt.setText(valueOf(selectedPart.getMin()));
            priceTxt.setText(valueOf(selectedPart.getPrice()));

        } else if (selectedPart instanceof Outsourced) {
            iDTxt.setText(valueOf(selectedPart.getId()));
            invTxt.setText(valueOf(selectedPart.getStock()));
            nameTxt.setText(selectedPart.getName());
            machineIdTxt.setText(valueOf(((Outsourced) selectedPart).getCompanyName()));
            maxTxt.setText(valueOf(selectedPart.getMax()));
            minTxt.setText(valueOf(selectedPart.getMin()));
            priceTxt.setText(valueOf(selectedPart.getPrice()));
        }
    }
}

