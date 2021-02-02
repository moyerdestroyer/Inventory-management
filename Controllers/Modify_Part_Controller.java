package Controllers;
import Model.Inventory;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class Modify_Part_Controller {
    Inventory inv;
    public void add_data(Inventory inv) {
        this.inv = inv;
    }

    @FXML
    private RadioButton Inhouse_radio;

    @FXML
    private RadioButton Outsourced_radio;

    @FXML
    private TextField Id_text;

    @FXML
    private Label Machine_label;

    @FXML
    private TextField Inv_text;

    @FXML
    private TextField Price_text;

    @FXML
    private TextField Max_text;

    @FXML
    private TextField Min_text;

    @FXML
    private TextField Machine_text;

    @FXML
    private Button Save_button;

    @FXML
    private Button Cancel_button;

    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        Parent addMainParent = FXMLLoader.load(getClass().getResource("/Views/main_form.fxml"));
        Scene addMainScene = new Scene(addMainParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addMainScene);
    }

}
