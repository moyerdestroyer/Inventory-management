package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Main_form_controller implements Initializable {
    Inventory inv;

    public void add_data(Inventory inv) {
        this.inv = inv;
        Part_table.setItems(inv.getAllParts());
        Product_table.setItems(inv.getAllProducts());
    }

    @FXML
    private TextField Part_search;

    @FXML
    private TableView<Part> Part_table;

    @FXML
    private TableColumn<Part, Integer> Part_id_column;

    @FXML
    private TableColumn<Part, String> Part_name_column;

    @FXML
    private TableColumn<Part, Integer> Part_stock_column;

    @FXML
    private TableColumn<Part, Double> Part_price_column;

    @FXML
    private Button Part_add;

    @FXML
    private Button Part_modify;

    @FXML
    private Button Part_delete;

    @FXML
    private TextField Product_search;

    @FXML
    private TableView<Product> Product_table;

    @FXML
    private TableColumn<Product, Integer> Product_id_column;

    @FXML
    private TableColumn<Product, String> Product_name_column;

    @FXML
    private TableColumn<Product, Integer> Product_inventory_column;

    @FXML
    private TableColumn<Product, Double> Product_price_column;

    @FXML
    private Button Product_add;

    @FXML
    private Button Product_modify;

    @FXML
    private Button Product_delete;

    @FXML
    private Button Exit;

    @FXML
    void ExitAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void PartAddAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_part_form.fxml"));
        Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene partAddScene = new Scene((Parent) loader.load());
        Add_Part_Controller controller = loader.getController();
        controller.add_data(inv);
        mainStage.setScene(partAddScene);
    }

    @FXML
    void PartDeleteAction(ActionEvent event) {

    }

    @FXML
    void PartModifyAction(ActionEvent event) throws IOException {
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("/Views/modify_part_form.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene((modifyPartScene));
    }

    @FXML
    void ProductAddAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_product_form.fxml"));
        Stage addProductStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene addProductScene = new Scene((Parent) loader.load());
        Add_Product_Controller controller = loader.getController();
        controller.add_data(inv);
        addProductStage.setScene(addProductScene);
    }

    @FXML
    void ProductDeleteAction(ActionEvent event) {
    }

    @FXML
    void ProductModifyAction(ActionEvent event) throws IOException {
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("/Views/modify_product_form.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene((modifyProductScene));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hello");
        Part_id_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        Part_name_column.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        Part_stock_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        Part_price_column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        Product_id_column.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        Product_name_column.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        Product_inventory_column.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        Product_price_column.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
    }

}