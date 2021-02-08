package Controllers;

import Model.Inventory;
import Model.Part;
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

public class Modify_Product_Controller {
    Inventory inv;
    int selectedRow;
    public void add_data(Inventory inv, int selectedIndex) {
        this.inv = inv;
        this.selectedRow = selectedIndex;



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
    private TableColumn<?, ?> ID_column;

    @FXML
    private TableColumn<?, ?> Name_column;

    @FXML
    private TableColumn<?, ?> Inventory_column;

    @FXML
    private TableColumn<?, ?> cost_column;

    @FXML
    private Button Add_button;

    @FXML
    private TableColumn<?, ?> Id_column_bottom;

    @FXML
    private TableColumn<?, ?> Name_column_bottom;

    @FXML
    private TableColumn<?, ?> Inventory_column_bottom;

    @FXML
    private TableColumn<?, ?> Cost_column_bottom;

    @FXML
    private Button Remove_associated_button;

    @FXML
    private Button Save_button;

    @FXML
    private Button Cancel_button;

    @FXML
    void AddButtonAction(ActionEvent event) {

    }

    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        Parent addMainParent = FXMLLoader.load(getClass().getResource("/Views/main_form.fxml"));
        Scene addMainScene = new Scene(addMainParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addMainScene);
    }

    @FXML
    void RemovePartButtonAction(ActionEvent event) {

    }

    @FXML
    void SaveButtonAction(ActionEvent event) {

    }

}
