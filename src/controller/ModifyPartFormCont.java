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
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class ModifyPartFormCont implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Variable to hold the part selected in the Main Menu, these are the text fields
     */
    @FXML
    private TextField iDTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private Label labelPartCategory;

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
     * Opens the main menu page
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event triggering event: User presses cancel
     * @throws IOException catches exception
     */
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks the radio buttons, and changes the label on Machine ID
     *
     * @param event Trigger event: User moves the radio button
     */

    @FXML
    void isPartInHouse(ActionEvent event) {
        if (selectedInHouse.isSelected()) {
            labelPartCategory.setText("Machine ID");
        } else {
            labelPartCategory.setText("Company Name");
        }

    }


    /**
     * Brings up a dialog box if the user does not enter an integer for a machine id
     * Second "try-catch" for if the user still does not enter a machine ID. It will default to zero.
     * @return returns the machine ID to the constructor (to save)
     */

    public int assignMachineId(){
        int machineID = 0;
    try {
        machineID = Integer.parseInt(machineIdTxt.getText());
    } catch (NumberFormatException e) {

        TextInputDialog machineIdAlert = new TextInputDialog("Machine ID Number");
        machineIdAlert.setTitle("Machine ID Required");
        machineIdAlert.setHeaderText("Please enter a whole number for the machine ID. If no number is input, the ID will save as 0. Thank you! ");
        machineIdAlert.setContentText("Machine ID");
        System.out.println(machineID);
        machineIdAlert.showAndWait();

        try {
            machineID = Integer.parseInt(machineIdAlert.getEditor().getText());
            System.out.println(machineID);
            return machineID;}
        catch(NumberFormatException exception){
            machineID = 0;
        }
    }
    return machineID;
    }


    /**
     * Saves the part to the observable inventory list when the user saves modification
     * @param actionEvent triggering event: User presses save
     * @throws IOException catches exception
     * Checks the class type of the previous entry, if the class type is the same then just update the part.
     * If the class type is different from what is currently selected, add the part to the list and delete the previous entry
     */
    @FXML
    public void OnActionModifyPart(ActionEvent actionEvent) throws IOException {
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
            int machineID = assignMachineId(); //the method checks if the input is an integer and throws a dialog box if not

        Part modifiedPart = new InHouse(id, name, price, stock, min, max, machineID);

        if (selectedPart instanceof InHouse) {
            Inventory.updatePart(index, modifiedPart);

        } else if (selectedPart instanceof Outsourced) {
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

                //** Checks the class type of the previous entry, if the class type is the same then just update the part.
                if (selectedPart instanceof Outsourced) {
                    Inventory.updatePart(index, modifiedPart);

                }

                //**If the class type is different from what is currently selected, add the part to the list and delete the previous entry*/
                else if (selectedPart instanceof InHouse) {
                    Inventory.addPart(modifiedPart);
                    Inventory.deletePart(selectedPart);

                }
        }

        OnActionDisplayMainMenu(actionEvent);


    }


    /**
     * Initializes the page.
     * @param url the path to the file
     * @param resourceBundle the resources needed
     * Classifies the part selected in the main controller and assigns attributed to the fields, auto-populates fields
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //** Classifies the part selected in the main controller and assigns attributed to the fields, auto-populates fields*/
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
            selectedOutsourced.setSelected(true);
            selectedOutsourced.requestFocus();
            labelPartCategory.setText("Company Name");
        }
    }
}

