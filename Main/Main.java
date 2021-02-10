package Main;

import Controllers.Main_form_controller;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     *
     * @param primaryStage Primary stage parameter
     * @throws Exception
     * The starting function creates a new Inventory, populates it with sample data, then loads the main screen
     * The inventory is passed to the controller via the "add_data" function
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory mainInventory = new Inventory();
        mainInventory.addPart(new InhousePart(1,"Part 1", 34.02, 3,2,5,5));
        mainInventory.addPart(new InhousePart(2, "Second Part", 24.24, 5, 3, 10, 22));
        mainInventory.addPart(new OutsourcedPart(3, "3rd Part", 220.10, 4, 0, 9, "Microtosh"));
        mainInventory.addProduct(new Product(1, "Product 1", 8.39, 2, 0, 10));
        mainInventory.addProduct(new Product(2, "Second Product", 22.2, 60, 2, 100));
        mainInventory.addProduct(new Product(3, "3rd Product", 199.23, 2, 1,7));
        mainInventory.getAllProducts().get(0).addAssociatedPart(mainInventory.getAllParts().get(0));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Parent root = (Parent) loader.load();
        Main_form_controller controller = loader.getController();
        controller.add_data(mainInventory);
        primaryStage.setTitle("Inventory Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
