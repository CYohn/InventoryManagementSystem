package controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormCont implements Initializable{

    Stage stage;
    Parent scene;


    /** Variables for the  radio buttons*/
    public RadioButton selectedInHouse;

    @FXML
    public RadioButton selectedOutsourced;

    /**Label variables for Machine ID or Company name*/

    @FXML
    private Label labelPartCategory;

    /** Assigns the toggle group, ensures that only one radio in this group can be selected at a time*/
    @FXML
    public ToggleGroup modifyPartToggle;

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

    /**Checks the radio buttons, and changes the label on Machine ID*/
    @FXML
    void isPartInHouse(ActionEvent event) {
        if (selectedInHouse.isSelected())
        {labelPartCategory.setText("Machine ID");}
        else
        {labelPartCategory.setText("Company Name");}
        return;
    }

    /**Saves the part to the observable list*/
    @FXML

    public void OnActionModifyPart(ActionEvent actionEvent) {
    }

    /**Initializes the page*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

}
