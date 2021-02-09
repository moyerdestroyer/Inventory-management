package Controllers;

import Model.*;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Add_Product_Controller {
    Inventory inv;
    ObservableList<Part> associatedParts;
    public void add_data(Inventory inv) {
        this.inv = inv;

        //Fill out top table
        Id_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        Name_column.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        Inventory_column.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        Cost_column.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
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
    private TableColumn<Part, Integer> Id_column;

    @FXML
    private TableColumn<Part, String> Name_column;

    @FXML
    private TableColumn<Part, Integer> Inventory_column;

    @FXML
    private TableColumn<Part, Double> Cost_column;

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
    private Button Remove_association_button;

    @FXML
    private Button Save_button;

    @FXML
    private Button Cancel_button;

    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Stage addPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene((Parent) loader.load());
        Main_form_controller controller = loader.getController();
        controller.add_data(inv);
        addPartStage.setScene(mainScene);
    }

    @FXML
    void RemoveButtonAction(ActionEvent event) {
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

    @FXML
    void SaveButtonAction(ActionEvent event) throws IOException {
        int numberOfParts = inv.getAllProducts().size();
        //Find what ID to assign, Make an integer list of all known IDs, then assign one to idToAssign
        List<Integer> list = new ArrayList<>();
        int idToAssign = 1;

        //Populate int list
        for (int i = 0; i < numberOfParts; i++) {
            list.add(inv.getAllProducts().get(i).getId());
        }
        //Go through the numbers, and see if the Integer List contains that number
        while (list.contains(idToAssign)) {
            idToAssign++;
        }
        //Verify Data, then add Product, and assign the parts.
        if (verify_data()) {
            inv.addProduct(new Product(idToAssign, Name_text.getText(), Double.parseDouble(Price_text.getText()), Integer.parseInt(Inv_text.getText()), Integer.parseInt(Min_text.getText()), Integer.parseInt(Max_text.getText())));
            int productIndex = inv.getAllProducts().indexOf(inv.lookupProduct(idToAssign));
            //for loop to add all products in associated parts to the new Product
            if (associatedParts != null) {
                for (int i = 0; i < associatedParts.size(); i++) {
                    inv.getAllProducts().get(productIndex).addAssociatedPart(associatedParts.get(i));
                }
            }
            System.out.println("Product Saved");
            CancelButtonAction(event);
        }
    }

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

    void errorMessage(String message) {
        //Display error messages in Console and with an Alert
        System.out.println(message);
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(message);
        a.show();
    }
}
