package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** <p><b>
 * LOGIC OR RUNTIME ERROR:
 *
 * Console Description:
 *
 *Exception in thread "JavaFX Application Thread" java.lang.RuntimeException: java.lang.reflect.InvocationTargetException
 *I was getting a: Caused by: java.lang.NullPointerException
 *
 *I went round and round with this error. I did not this it was the event handler method because when I tested the handler with a println()
 * statement it logged the action in the console. InteliJ suggested injecting a language reference, which I tried and it did not work.
 * So I "uninjected" the reference. I thought the issue was with my path to the file because of the null pointer. To test that,
 * I renamed and refactored the target file. The IDE had no problem finding the reference to the file in MainFormCont.java.
 *However, not knowing how the IDE goes about finding the reference I tried many different ways of stating the file path.
 * Finally, the null pointer exception was corrected by simply adding a "/" in front of the file path so that the file path reads:
 * "/view/AddPartForm.fxml".
</b></p>*/

/** <p><b>
 * FUTURE ENHANCEMENT: The program could in the future extend into financial information concerning the business.
 * For example, the program could include information about the costs from different suppliers,
 * how much the markup is, and the profitability of each product.
 * Additionally, the app should connect to a database to store the business data.
 </b></p>*/

public class Main extends Application {
    //Sets and shows the primary stage
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 1208, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        // This is test data to test the application on launch.
        // The data is located in the "Test Data" class in the main package
        TestData.addTestInHousePartsData();
        TestData.addTestProductsData();
        TestData.addTestOutsourcedParts();

        launch(args);
    }
}