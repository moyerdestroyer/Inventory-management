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

    /**
     * @param inv Stores inventory, and fills out parts inventory table
     */
    public void add_data(Inventory inv) {
        this.inv = inv;

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
        //Set Associated parts table up
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

    /**
     * @param event Cancel button sends inventory to main screen controller and loads it up
     * @throws IOException
     */
    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Stage addPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene((Parent) loader.load());
        Main_form_controller controller = loader.getController();
        controller.add_data(inv);
        addPartStage.setScene(mainScene);
    }

    /**
     * @param event Remove button deletes the selected part from the associated parts observable list
     */
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

    /**
     * @param event Adds a part to the bottom table, and on the associated part list
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
     * @param event calls verifyData(), then saves the new product into the inventory.
     *              Associated part list is then added via addAssociatedPart()
     * @throws IOException
     * <p>
     *     The save button function was difficult to work through on two fronts. First, generating a unique ID. At first, making an Id based on the previous ID seemed to work. But, if a user deleted an item, and resumed making products, there was potential to generate identical IDs.
     *     This was solved by first (#1.1) making an arraylist, and filling it with known IDs. While the arraylist contains the uniqueId (#1.2), id++ and see if that one is contained.
     * </p>
     * <p>
     *     The second issue was assigning the associated parts to the newly created product. First(#2.1), the data is verified, then a new product created and added to the inventory list.
     *     Once created and added, I wanted to call addAssociatedPart(), but did not know the index of the part. Thankfully, I did have the ID, so a search was performed for the newly created product, returning the index of the product just created (#2.2).
     *     With the index of the Product, it was still necessary to add the associated parts. This is done at (#2.3), where we quickly check if the partlist is empty, then go through the list, calling addAssociatedPart() for every item in the list.
     * </p>
     */
    @FXML
    void SaveButtonAction(ActionEvent event) throws IOException {
        int numberOfParts = inv.getAllProducts().size();
        //#1.1
        List<Integer> list = new ArrayList<>();
        int idToAssign = 1;
        for (int i = 0; i < numberOfParts; i++) {
            list.add(inv.getAllProducts().get(i).getId());
        }
        //#1.2
        while (list.contains(idToAssign)) {
            idToAssign++;
        }
        //#2.1
        if (verify_data()) {
            inv.addProduct(new Product(idToAssign, Name_text.getText(), Double.parseDouble(Price_text.getText()), Integer.parseInt(Inv_text.getText()), Integer.parseInt(Min_text.getText()), Integer.parseInt(Max_text.getText())));
            //#2.2
            int productIndex = inv.getAllProducts().indexOf(inv.lookupProduct(idToAssign));
            //#2.3
            if (associatedParts != null) {
                for (int i = 0; i < associatedParts.size(); i++) {
                    inv.getAllProducts().get(productIndex).addAssociatedPart(associatedParts.get(i));
                }
            }
            System.out.println("Product Saved");
            CancelButtonAction(event);
        }
    }

    /**
     * @return returns true if all data in forms is possible to create a product with
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
     * @param message Shows string message as an alert that the user has done something incorrectly
     */
    void errorMessage(String message) {
        //Display error messages in Console and with an Alert
        System.out.println(message);
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(message);
        a.show();
    }
}
