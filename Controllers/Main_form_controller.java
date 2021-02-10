package Controllers;

import Model.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Main_form_controller {
    Inventory inv;

    /**
     * @param inv function called during loading to ensure the inventory is passed into the form
     *            The part and product table is then setup, with a search functionality
     */
    public void add_data(Inventory inv) {
        this.inv = inv;

        Part_id_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        Part_name_column.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        Part_stock_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        Part_price_column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //Make a part filter list, and add a listener to search
        FilteredList<Part> partFilteredData = new FilteredList<>(inv.getAllParts(), p -> true);
        Part_search.textProperty().addListener((observable, oldValue, newValue) -> {
            partFilteredData.setPredicate(Part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (Part.getName().contains(newValue)) {
                    return true;
                } else if (String.valueOf(Part.getId()).contains(newValue)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Part> partSortedList = new SortedList<>(partFilteredData);
        partSortedList.comparatorProperty().bind(Part_table.comparatorProperty());
        Part_table.setItems(partSortedList);
        Part_table.getSelectionModel().selectFirst();

        Product_id_column.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        Product_name_column.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        Product_inventory_column.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        Product_price_column.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        FilteredList<Product> productFilteredData = new FilteredList<>(inv.getAllProducts(), p -> true);
        Product_search.textProperty().addListener((observable, oldValue, newValue) -> {
            productFilteredData.setPredicate(Product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (Product.getName().contains(newValue)) {
                    return true;
                } else if (String.valueOf(Product.getId()).contains(newValue)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Product> productSortedList = new SortedList<>(productFilteredData);
        productSortedList.comparatorProperty().bind(Product_table.comparatorProperty());
        Product_table.setItems(productSortedList);
        Product_table.getSelectionModel().selectFirst();
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

    /**
     * @param event Upon clicking the exit button, the program is closed, no further saves are performed
     */
    @FXML
    void ExitAction(ActionEvent event) {
        Platform.exit();
    }

    /**
     * @param event Loads the add part form, passing the inventory variable into the called controller
     * @throws IOException
     */
    @FXML
    void PartAddAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_part_form.fxml"));
        Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene partAddScene = new Scene((Parent) loader.load());
        Add_Part_Controller controller = loader.getController();
        controller.add_data(inv);
        mainStage.setScene(partAddScene);
    }

    /**
     * @param event Attempts to delete selected part from the part table, showing a confirmation alert to verify
     */
    @FXML
    void PartDeleteAction(ActionEvent event) {
        int selectedRow = Part_table.getSelectionModel().getSelectedIndex();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete Part");
        a.setContentText("Are you sure you want to delete: " + inv.getAllParts().get(selectedRow).getName());
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK)  {
            boolean delete = inv.deletePart(inv.getAllParts().get(selectedRow));
            if (delete) {
                System.out.println("Part Deleted");
            }
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Delete Cancelled");
        }

    }

    /**
     * @param event Loads the Modify part form, Passing the controller the currently selected part, and the inventory
     * @throws IOException
     */
    @FXML
    void PartModifyAction(ActionEvent event) throws IOException {
        int selectedRow = Part_table.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_part_form.fxml"));
        Stage modifyPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene modifyPartScene = new Scene((Parent) loader.load());
        Modify_Part_Controller controller = loader.getController();

        controller.add_data(inv, selectedRow);
        modifyPartStage.setScene(modifyPartScene);
    }

    /**
     * @param event Loads the Add Product form, passing the controller the inventory in the add_data() function
     * @throws IOException
     */
    @FXML
    void ProductAddAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_product_form.fxml"));
        Stage addProductStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene addProductScene = new Scene((Parent) loader.load());
        Add_Product_Controller controller = loader.getController();
        controller.add_data(inv);
        addProductStage.setScene(addProductScene);
    }

    /**
     * @param event Attempts to delete the selected product, showing a confirmation alert first
     */
    @FXML
    void ProductDeleteAction(ActionEvent event) {
        int selectedRow = Product_table.getSelectionModel().getSelectedIndex();
        //Check if product has associated parts
        if (inv.getAllProducts().get(selectedRow).getAssociatedParts() == null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Delete Product");
            a.setContentText("Are you sure you want to delete: " + inv.getAllProducts().get(selectedRow).getName());
            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK)  {
                boolean delete = inv.deleteProduct(inv.getAllProducts().get(selectedRow));
                if (delete) {
                    System.out.println("Product Deleted");
                }
            } else if (result.get() == ButtonType.CANCEL) {
                System.out.println("Delete Cancelled");
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Unable to delete product with parts associated");
            a.show();
        }
    }

    /**
     * @param event Attempts to load the modify product form, passing the inventory, and currently selected product
     * @throws IOException
     */
    @FXML
    void ProductModifyAction(ActionEvent event) throws IOException {
        int selectedRow = Product_table.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_product_form.fxml"));
        Stage modifyProductStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene modifyProductScene = new Scene((Parent) loader.load());
        Modify_Product_Controller controller = loader.getController();
        controller.add_data(inv, selectedRow);
        modifyProductStage.setScene(modifyProductScene);
    }
}