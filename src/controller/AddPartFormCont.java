package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPartFormCont implements Initializable {

    Stage stage;
    Parent scene;


    /**Assigns the toggle group, ensure only one can be selected at a time in the group*/
    @FXML
    private ToggleGroup addPartToggle;

    /**Radio button variables*/
    @FXML
    private RadioButton selectedInHouse;

    @FXML
    private RadioButton selectedOutsourced;

    boolean inHouse;

    /**Label variables for Machine Id or Company name*/
    @FXML
    private Label labelPartCategory;

    @FXML
    private Label outsourced;

    /**Text field variables*/
    @FXML
    private TextField partCostTxt;

    @FXML
    private TextField partInventoryTxt;

    @FXML
    private TextField partMachineIdTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;
    private int id;


    /**Checks the radio buttons, and changes the label on Machine ID*/
    @FXML
    void isPartInHouse(ActionEvent event) {
        if (selectedInHouse.isSelected())
        {labelPartCategory.setText("Machine ID");}
        else
        {labelPartCategory.setText("Company Name");}
        return;
    }


    /**
     * Displays the main menu when the user presses the cancel button
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
    public void AssignId(){
        ObservableList<Part> allParts = Inventory.getAllParts();
        FXCollections.sort(allParts,
                new Comparator<Part>() {
                    @Override
                    public int compare(Part o1, Part o2) {
                        return Integer.compare(o1.getId(),o2.getId());
                    }

                });
        System.out.println(allParts);
    }
*/

    /**
     * Saves the part to the Observable list "Parts"
     */
    @FXML
    void OnActionSavePart(ActionEvent event) {

        /** Retrieves user input and converts the data types*/
        double price = Double.parseDouble(partCostTxt.getText());
        int stock = Integer.parseInt(partInventoryTxt.getText());
        int machineId = Integer.parseInt(partMachineIdTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        String name = partNameTxt.getText();
        int id = 0;


        /** Checks if the part is in house and adds the new part to either
         * In-sourced to outsourced parts
         */
        if (inHouse == true) {
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

            for (int j = 0; j < Inventory.getAllParts().size(); ++j) {
                Part tempPart = Inventory.getAllParts().get(id);
            }
        } else {
            labelPartCategory = outsourced;
            String companyName = "testing";
            Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
        }
        /** Calls method to assign the id*/
        //AssignId();


    }

    /**
     * Initializes the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}