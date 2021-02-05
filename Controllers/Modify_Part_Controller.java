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
    void SaveButtonAction (ActionEvent event) throws IOException {
        if (verifyData()) {
            if (Inhouse_radio.isSelected()) {
                InhousePart partToUpdate = new InhousePart((inv.getAllParts().get(selectedRow).getId()), Name_Text.getText(), Double.parseDouble(Price_text.getText()), Integer.parseInt(Inv_text.getText()), Integer.parseInt(Min_text.getText()), Integer.parseInt(Max_text.getText()), Integer.parseInt(Machine_text.getText()));
                inv.updatePart(selectedRow, partToUpdate);
            }
            else if (Outsourced_radio.isSelected()) {
                OutsourcedPart partToUpdate = new OutsourcedPart((inv.getAllParts().get(selectedRow).getId()), Name_Text.getText(), Double.parseDouble(Price_text.getText()), Integer.parseInt(Inv_text.getText()), Integer.parseInt(Min_text.getText()), Integer.parseInt(Max_text.getText()), Machine_text.getText());
                inv.updatePart(selectedRow, partToUpdate);
            }
            System.out.println("Part Successfully Modified");
            CancelButtonAction(event);
        }
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

    //Verify Data function
    boolean verifyData() {
        //Temp storage of data
        String name;
        double price;
        int stock;
        int min;
        int max;
        int machineId;
        String companyName;

        //Try the name
        try {
            name = Name_Text.getText();
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

        //check if in-house or outsourced, then store the data
        if (Inhouse_radio.isSelected()) {
        try {
            machineId = Integer.parseInt(Machine_text.getText());
        } catch (Exception e) {
            errorMessage("Please Enter a valid integer for the machine ID");
            return false;
        }
        }
        else if (Outsourced_radio.isSelected()) {
        try {
            companyName = Machine_text.getText();
        } catch (Exception e) {
            errorMessage("Please enter a valid Company Name");
            return false;
        }
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

    void errorMessage(String error) {
        //Display error messages in Console and with an Alert
        System.out.println(error);
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(error);
        a.show();
    }
}
