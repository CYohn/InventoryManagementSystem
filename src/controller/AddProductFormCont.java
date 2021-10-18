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

public class AddProductFormCont implements Initializable {
    Stage stage;
    Parent scene;


    /**Saves the product to the Product Observable list*/
    @FXML
    private Button OnActionSaveProduct;

    /**Adds the product to the add product observable list*/
    @FXML
    void OnActionAddProduct(ActionEvent event) {

    }

    /**Displays the main menu when the user presses the cancel button*/
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/MainForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Removes the associated part from the Observable list "Parts"
     *This is an example of dependency
     */
    @FXML
    void OnActionRemoveAssociatedPart(ActionEvent event) {

    }

    /**Searches the observable list "Products" using the user supplied name or Id*/
    @FXML
    void OnActionSearchProducts(ActionEvent event) {

    }

    /**Saves the product to the Observable list "Products"*/
    @FXML
    public void OnActionSaveProduct(ActionEvent actionEvent) {
    }

    /**Initializes the page*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }


}
