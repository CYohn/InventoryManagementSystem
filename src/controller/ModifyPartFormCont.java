package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormCont implements Initializable {

    Stage stage;
    Parent scene;


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
    private Label labelPartCategory;

    /**
     * Assigns the toggle group, ensures that only one radio in this group can be selected at a time
     */
    @FXML
    public ToggleGroup modifyPartToggle;

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

/**Performs update on a specified part*/
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
     * Saves the part to the observable list
     */
    @FXML

    public void OnActionModifyPart(ActionEvent actionEvent) {
    }

    /**
     * Initializes the page
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update(2, new InHouse(2, "Widget2", 20.95, 3, 0, 15, 345));
    }
}