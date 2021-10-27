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
import javafx.stage.WindowEvent;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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

    /**Redirects user to the main screen*/
    public void RedirectToMainScreen () throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Brings up a dialog box if the user does not enter an integer for a machine id*/
    public int assignMachineId(){
        int machineID = 0;
    try {
        machineID = Integer.parseInt(machineIdTxt.getText());
    } catch (NumberFormatException e) {
        //Input was not an integer
        TextInputDialog machineIdAlert = new TextInputDialog("Machine ID Number");
        machineIdAlert.setTitle("Machine ID Required");
        machineIdAlert.setHeaderText("Please enter a whole number for the machine ID. If no number is input, the ID will save as 0. Thank you! ");
        //machineIdAlert.setHeaderText("If no number is input, the ID will save as 0. Thank you!");
        machineIdAlert.setContentText("Machine ID");
       // String userInput = machineIdAlert.getEditor().getText();
        System.out.println(machineID);
        machineIdAlert.showAndWait();
/**
 * Second "try-catch" for if the user still does not enter a machine ID. will save machine Id to 0 as a default
 */
        try {
            machineID = Integer.parseInt(machineIdAlert.getEditor().getText());
            System.out.println(machineID);
            return machineID;}
        catch(NumberFormatException exception){
            machineID = 0;
        }

        //TextField newMachineTxt = new TextField();
        //new Label("Machine ID");
        //machineID = Integer.parseInt(newMachineTxt.getText());

    }
    return machineID;
    }

    public void handleAlert(WindowEvent event) throws IOException {
        Alert machineIdAlert = new Alert(Alert.AlertType.INFORMATION);
        machineIdAlert.setTitle("Reminder");
        machineIdAlert.setHeaderText("Please enter a whole number for the machine ID. Thank you!");
        machineIdAlert.setContentText(null);
        ButtonType okButton = new ButtonType("OK");
        TextField newMachineTxt = new TextField();
        new Label("Machine ID");
        machineIdAlert.getButtonTypes().setAll(ButtonType.OK);
        Optional<ButtonType> result = machineIdAlert.showAndWait();
        /*if(result.isPresent()){
            scene = FXMLLoader.load((getClass().getResource("/view/ModifyPartForm.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }*/
    }

    /**
     * Saves the part to the observable inventory list when the user saves modification
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
        RedirectToMainScreen ();
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

