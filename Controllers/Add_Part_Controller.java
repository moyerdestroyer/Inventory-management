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

public class Add_Part_Controller {
    Inventory inv;

    public void add_data(Inventory inv){
        this.inv = inv;
        //have In-house selected by default
        ToggleGroup sourcedRadio = new ToggleGroup();
        inHouseRadio.setToggleGroup(sourcedRadio);
        OutsourcedRadio.setToggleGroup(sourcedRadio);
        inHouseRadio.setSelected(true);
    }

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton OutsourcedRadio;

    @FXML
    private TextField IdText;

    @FXML
    private Label MachineLabel;

    @FXML
    private TextField InvText;

    @FXML
    private TextField PriceText;

    @FXML
    private TextField MaxText;

    @FXML
    private TextField MinText;

    @FXML
    private TextField MachineText;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelButton;

    @FXML
    void InhouseAction(ActionEvent event) {

    }

    @FXML
    void OutsourcedAction(ActionEvent event) {

    }

    @FXML
    void SaveButtonAction(ActionEvent event) {

    }

    @FXML
    void CancelButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_form.fxml"));
        Stage addPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene((Parent) loader.load());
        Main_form_controller controller = loader.getController();
        controller.add_data(inv);
        addPartStage.setScene(mainScene);
    }

}
