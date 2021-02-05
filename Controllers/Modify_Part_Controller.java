package Controllers;
import Model.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Modify_Part_Controller {
    Inventory inv;
    int selectedRow;

    public void add_data(Inventory inv, int row) {
        this.inv = inv;
        this.selectedRow = row;

        //Define the Toggle Button
        ToggleGroup sourcedRadio = new ToggleGroup();
        Inhouse_radio.setToggleGroup(sourcedRadio);
        Outsourced_radio.setToggleGroup(sourcedRadio);

        if (inv.getAllParts().get(selectedRow).getClass().getSimpleName().equals("InhousePart")) {
            Inhouse_radio.setSelected(true);
            Machine_text.setText(String.valueOf(((InhousePart) inv.getAllParts().get(selectedRow)).getMachineId()));
        } else {
            Outsourced_radio.setSelected(true);
            Machine_label.setText("Company Name:");
            Machine_text.setText(((OutsourcedPart) inv.getAllParts().get(selectedRow)).getCompanyName());
        }

        Id_text.setText(String.valueOf(inv.getAllParts().get(selectedRow).getId()));
        Name_Text.setText(inv.getAllParts().get(selectedRow).getName());
        Inv_text.setText(String.valueOf(inv.getAllParts().get(selectedRow).getStock()));

        Price_text.setText(String.valueOf(inv.getAllParts().get(selectedRow).getPrice()));
        Max_text.setText(String.valueOf(inv.getAllParts().get(selectedRow).getMax()));
        Min_text.setText(String.valueOf(inv.getAllParts().get(selectedRow).getMin()));

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
    private TextField Name_Text;

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
    void SaveButtonAction (ActionEvent event) {

    }

    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Stage modifyPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene((Parent) loader.load());
        Main_form_controller controller = loader.getController();
        controller.add_data(inv);
        modifyPartStage.setScene(mainScene);
    }

    @FXML
    void outsourcedAction (ActionEvent event) {
        Machine_label.setText("Company Name:");
    }

    @FXML
    void inhouseAction (ActionEvent event) {
        Machine_label.setText("Machine Id:");
    }

}
