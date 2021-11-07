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
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;


public class AddPartFormCont implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * Radio button variables
     */
    @FXML
    private RadioButton selectedInHouse;

    @FXML
    private RadioButton selectedOutsourced;

    /**
     * Label variables for Machine ID or Company name
     */
    @FXML
    private Label labelPartCategory;


    /**
     * Text field variables
     */
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


    /**
     * Checks the radio buttons, and changes the label on Machine ID
     * @param event trigger event: if user selects in house or outsourced
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
     * Displays the main menu when the user presses the cancel button.
     * The following code casts the event to let the application know that the event was triggered by a button on a stage
     * @param event the triggering event: User presses cancel
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
     * @return returns the name entered by the user or a default name if none is entered
     */
    public String assignName() {
        String name;
        name = partNameTxt.getText();
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

        int stock = Integer.parseInt(partInventoryTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());

        if (stock > min && stock < max){
        return stock;
        }
        return stock;
        }


    /**
     * Checks if the price is empty, throws dialog box if true. Checks if the input is a double, throws dialog box if false
     * @return returns part price
     */
    public double assignPrice() {
        double price;
        price = Double.parseDouble(partCostTxt.getText());
        return price;
    }



    /**
     * Checks if the min is less than max, throws alert if not
     * @return the inventory minimum
     */
    public int assignMin() {

            int min = Integer.parseInt(partMinTxt.getText());
            return min;
    }


    /**
     * Checks if max is more than min, throws alert if not
     * @return the inventory maximum
     */
    public int assignMax() {

            int max = Integer.parseInt(partMaxTxt.getText());
            return max;

    }

    /**
     * Alert which is called in the empty field check
     */

    public void alert(){
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
        }

    void OnActionDisplayAddPartMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Alert for the levels of inventory, max, and min. Called in the save function.
     */
    public void alertInvMaxMin()  {
        Alert invAlert = new Alert(Alert.AlertType.WARNING);
        invAlert.setTitle("Please Check your Entries");
        invAlert.setHeaderText("Inventory must be between min and max. Min must be below max.");
        invAlert.setContentText("Please correct the inventory, max, and min levels. Thank you.");
        invAlert.showAndWait();
    }



    /**
     * Checks for empty fields, calls above alert if any are found
     */
    public void emptyFieldAlert() {
        try {
            if ((partNameTxt.getText().isEmpty())) {
                alert();
            }
            if (partInventoryTxt.getText().isEmpty()) {
                alert();
            }
            if (partCostTxt.getText().isEmpty()) {
                alert();
            }
            if (partMinTxt.getText().isEmpty()) {
                alert();
            }
            if (partMaxTxt.getText().isEmpty()) {
                alert();
            }
            if (partMachineIdTxt.getText().isEmpty()) {
                alert();
            }
        }
        catch (Exception exception){
            //do nothing
        }
    }



    /**
     * Assigns the unique id to the part. First the method sorts the parts by id
     * then gets the highest id on the list and increments it
     * @return is the id to be assigned to the part in OnActionSavePart(ActionEvent event)
     */
    public int assignId(){

        ObservableList<Part> sortedParts = Inventory.getAllParts();
        Collections.sort(sortedParts, Comparator.comparingInt(Part::getId)); // Sorts the parts by id
        int listLength = sortedParts.size(); // get the size of the list
        Part lastPart = sortedParts.get(listLength - 1); //get the last part - minus 1 because indexes start at 0
        int highestId = lastPart.getId(); // get the highest id (the id of the last part)
        int id = highestId + 1; // increment the highest id
        return id; //return the id assigned in OnActionSavePart(ActionEvent event)
        }


    /**
     * Saves the part to the Observable list "Parts"
     * Retrieves user input and converts the data types
     * @param event the triggering event: User clicks "save"
     * @throws IOException catches exception
     * Checks if the part is in house and adds the new part to either
     * In-sourced to outsourced parts
     */
    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException {

        double price = assignPrice();
        int max = assignMax();
        int min = assignMin();
        int stock = assignInventory();
        String name = assignName();
        int id = assignId();

        emptyFieldAlert();//Check for empty fields

        if((assignMin() > assignMax()) ||(assignInventory()<assignMin())||(assignInventory()>assignMax())){
            alertInvMaxMin();
            OnActionDisplayAddPartMenu(event);
        }

        else if ((assignMin() < assignMax()) && (assignInventory() > assignMin()) && (assignInventory() < assignMax())){

            try{

                if (selectedInHouse.isSelected()) {
                    int machineId = Integer.parseInt(partMachineIdTxt.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                }

                else if (selectedOutsourced.isSelected()){

                    String companyName = partMachineIdTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

                }

                OnActionDisplayMainMenu(event);
            }

            catch (Exception e){
                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("Something went wrong.");
                infoRequiredAlert.setHeaderText("Please check your entries.");
                infoRequiredAlert.setContentText("Thank you");
                infoRequiredAlert.showAndWait();
            }
        }
    }
    /**
     * Initializes the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}