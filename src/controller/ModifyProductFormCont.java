package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductFormCont implements Initializable {

    Stage stage;
    Parent scene;

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
    }
}
