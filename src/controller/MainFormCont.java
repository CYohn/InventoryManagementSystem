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



public class MainFormCont implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField prodSearchBoxTxt;

    /** The following are TableView columns, which will populate data from the "Parts" observable list*/

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

    /**TableView columns for the products observable list*/
    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryCol;


    /**Search  Feature: Searches the observable Products list using the user supplies Part Id or Name */
    private ObservableList<Product>searchByProductName(String partialName){
        ObservableList<Product>results = FXCollections.observableArrayList();

        ObservableList<Product>loadAllProducts = Inventory.getAllProducts();
        for(Product tempProd : loadAllProducts){
            if (tempProd.getName().contains(partialName)){
                results.add(tempProd);
            }
        }
        return results;
    }


    /**Searches the observable list "Products" using the user supplied name or Id*/
    @FXML
    void OnActionSearchProducts(ActionEvent event) {
        String prodNameInput = prodSearchBoxTxt.getText();
        ObservableList<Product> products = searchByProductName(prodNameInput);
        productsTable.setItems(products);
    }







    /**Event handlers for the buttons*/

    /**Closes the program when the user presses the exit button*/
    @FXML
    void OnActionCloseProgram(ActionEvent event) {
        System.exit(0);
    }

    /** Deletes the part from the observable list*/
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        System.out.println("Delete Part Button Clicked");
    }

    /**Deletes the product from the observable list*/
    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete Product Button Clicked");
    }


    /**Changes the page to the "Add Part" page*/
    @FXML
    void OnActionDisplayAddPartMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
        */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Displays the "Add Product" menu*/
    @FXML
    void OnActionDisplayAddProductMenu(ActionEvent event) throws IOException{

        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Displays the "Modify Part" menu*/
    @FXML
    void OnActionDisplayModifyPartMenu(ActionEvent event) throws IOException {
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Displays the "Modify Product" menu*/
    @FXML
    void OnActionDisplayModifyProductMenu(ActionEvent event) throws IOException{
        /** The following code casts the event to let the application know that the event was triggered by a button on a stage
         */
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/ModifyProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Searches the observable Parts list using the user supplies Part Id or Name */
    @FXML
    void OnActionSearchParts(ActionEvent event) {
        System.out.println("Search Parts Clicked");
    }




    /**Initializes the controller*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        /**Populates Parts table*/
        partTable.setItems(Inventory.getAllParts());
        /** Calls getId() and assigns it to the column, which populates the table cells
         */
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        /** Calls getId() and assigns it to the column, which populates the table cells
         */
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


    }




