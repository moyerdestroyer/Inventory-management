package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.naming.Name;
import java.io.IOException;
import java.util.Optional;

public class Modify_Product_Controller {
    Inventory inv;
    int selectedRow;
    ObservableList<Part> associatedParts;

    /**
     * @param inv Called during loading, gets inventory variable
     * @param selectedIndex index of currently selected product the user wishes to modify
     */
    public void add_data(Inventory inv, int selectedIndex) {
        this.inv = inv;
        this.selectedRow = selectedIndex;
        associatedParts = inv.getAllProducts().get(selectedRow).getAssociatedParts();

        ID_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        Name_column.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        Inventory_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        cost_column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //Make a part filter list, and add a listener to search
        FilteredList<Part> partFilteredData = new FilteredList<>(inv.getAllParts(), p -> true);
        Search_text.textProperty().addListener((observable, oldValue, newValue) -> {
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

        Id_column_bottom.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        Name_column_bottom.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        Inventory_column_bottom.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        Cost_column_bottom.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        Part_table_bottom.setItems(associatedParts);
        //Fill in product info
        Id_text.setText(String.valueOf(inv.getAllProducts().get(selectedRow).getId()));
        Name_text.setText(inv.getAllProducts().get(selectedRow).getName());
        Inv_text.setText(String.valueOf(inv.getAllProducts().get(selectedRow).getStock()));
        Price_text.setText(String.valueOf(inv.getAllProducts().get(selectedRow).getPrice()));
        Max_text.setText(String.valueOf(inv.getAllProducts().get(selectedRow).getMax()));
        Min_text.setText(String.valueOf(inv.getAllProducts().get(selectedRow).getMin()));
    }

    @FXML
    private TextField Id_text;

    @FXML
    private TextField Name_text;

    @FXML
    private TextField Inv_text;

    @FXML
    private TextField Price_text;

    @FXML
    private TextField Max_text;

    @FXML
    private TextField Min_text;

    @FXML
    private TextField Search_text;

    @FXML
    private TableView<Part> Part_table;

    @FXML
    private TableColumn<Part, Integer> ID_column;

    @FXML
    private TableColumn<Part, String> Name_column;

    @FXML
    private TableColumn<Part, Integer> Inventory_column;

    @FXML
    private TableColumn<Part, Double> cost_column;

    @FXML
    private Button Add_button;

    @FXML
    private TableView<Part> Part_table_bottom;

    @FXML
    private TableColumn<Part, Integer> Id_column_bottom;

    @FXML
    private TableColumn<Part, String> Name_column_bottom;

    @FXML
    private TableColumn<Part, Integer> Inventory_column_bottom;

    @FXML
    private TableColumn<Part, Double> Cost_column_bottom;

    @FXML
    private Button Remove_associated_button;

    @FXML
    private Button Save_button;

    @FXML
    private Button Cancel_button;

    /**
     * @param event Attempts to add selected part to the associated parts list/table
     */
    @FXML
    void AddButtonAction(ActionEvent event) {
        int selectedRow = Part_table.getSelectionModel().getSelectedIndex();
        try {
            associatedParts.add(inv.getAllParts().get(selectedRow));
        } catch (Exception e) {
            associatedParts = FXCollections.observableArrayList(inv.getAllParts().get(selectedRow));
        }
        Part_table_bottom.setItems(associatedParts);
    }

    /**
     * @param event Cancels modification attempt, and returns to the main screen, passing the inventory back
     * @throws IOException
     */
    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene((Parent) loader.load());
        Main_form_controller controller = loader.getController();
        controller.add_data(inv);
        mainStage.setScene(mainScene);
    }

    /**
     * @param event Attempts to remove the selected part from associated part table,
     */
    @FXML
    void RemovePartButtonAction(ActionEvent event) {
        int selectedRow = Part_table_bottom.getSelectionModel().getSelectedIndex();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Remove Part Association");
        a.setContentText("Are you sure you want to remove: " + associatedParts.get(selectedRow).getName());
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK)  {
            boolean delete = associatedParts.remove(associatedParts.get(selectedRow));
            if (delete) {
                System.out.println("Associated Part Removed");
            }
        } else if (result.get() == ButtonType.CANCEL) {
            System.out.println("Delete Cancelled");
        }
    }

    /**
     * @param event Upon clicking save, calls verifyData(), then attempts to save the modified product into the inventory
     *              CancelButtonAction() is then called to return to the main screen
     * @throws IOException
     */
    @FXML
    void SaveButtonAction(ActionEvent event) throws IOException {
        if (verify_data()) {
            Product newProduct = new Product(Integer.parseInt(Id_text.getText()), Name_text.getText(), Double.parseDouble(Price_text.getText()), Integer.parseInt(Inv_text.getText()), Integer.parseInt(Min_text.getText()), Integer.parseInt(Max_text.getText()));
            inv.updateProduct(selectedRow, newProduct);
            //for loop to add all products in associated parts to the new Product
            if (associatedParts != null) {
                for (int i = 0; i < associatedParts.size(); i++) {
                    inv.getAllProducts().get(selectedRow).addAssociatedPart(associatedParts.get(i));
                }
            }
            CancelButtonAction(event);
        }
    }

    /**
     * @return returns true if all data in forms is passable, also min, max, stock verification
     */
    boolean verify_data() {
        //Temp storage of data
        String name;
        double price;
        int stock;
        int min;
        int max;

        //Try the name
        try {
            name = Name_text.getText();
        } catch (Exception e){
            errorMessage("Please enter a real name");
            return false;
        }
        //Store the price
        try {
            price = Double.parseDouble(Price_text.getText());
        } catch (Exception e) {
            errorMessage("Please enter a valid price");
            return false;
        }
        //Store the stock
        try {
            stock = Integer.parseInt(Inv_text.getText());
        } catch (Exception e) {
            errorMessage("Please enter a valid inventory count");
            return false;
        }
        //Store the min
        try {
            min = Integer.parseInt(Min_text.getText());
        } catch (Exception e) {
            errorMessage("Enter a valid minimum value");
            return false;
        }
        //Store the max
        try {
            max = Integer.parseInt(Max_text.getText());
        } catch (Exception e) {
            errorMessage("Enter a valid Maximum value");
            return false;
        }
        //Check if Name is empty
        if (name.isEmpty()) {
            errorMessage("Please enter name");
            return false;
        }

        //Check if price is positive
        if (price <= 0) {
            errorMessage("Please enter a valid price range");
        }


        //Check if all ints are positive
        if (stock < 0 | min < 0 | max < 0) {
            errorMessage("Please enter positive values for the stock, min and max");
            return false;
        }
        if (min <= stock && stock <= max) {
            return true;
        } else {
            errorMessage("Min, Max, and Stock values are impossible");
            return false;
        }
    }

    /**
     * @param message Displays an error alert with the passed string variable as the message
     */
    void errorMessage(String message) {
        //Display error messages in Console and with an Alert
        System.out.println(message);
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(message);
        a.show();
    }
}
