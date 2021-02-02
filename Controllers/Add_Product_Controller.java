package Controllers;

import Model.Inventory;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Add_Product_Controller {
    Inventory inv;
    public void add_data(Inventory inv) {
        this.inv = inv;
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
    private TableColumn<?, ?> Id_column;

    @FXML
    private TableColumn<?, ?> Name_column;

    @FXML
    private TableColumn<?, ?> Inventory_column;

    @FXML
    private TableColumn<?, ?> Cost_column;

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

    }

    @FXML
    void SaveButtonAction(ActionEvent event) {

    }

}
