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
    private TextField NameText;

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
        MachineLabel.setText("Machine Id:");
    }

    @FXML
    void OutsourcedAction(ActionEvent event) {
        MachineLabel.setText("Company Name:");
    }

    @FXML
    void SaveButtonAction(ActionEvent event) throws IOException {
        int numberOfParts = inv.getAllParts().size();

        if (verifyData()) {
            if (inHouseRadio.isSelected()){
                inv.addPart(new InhousePart(numberOfParts + 1, NameText.getText(), Double.parseDouble(PriceText.getText()), Integer.parseInt(InvText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), Integer.parseInt(MachineText.getText())));
            }
            if (OutsourcedRadio.isSelected()){
                inv.addPart(new OutsourcedPart(numberOfParts + 1, NameText.getText(), Double.parseDouble(PriceText.getText()), Integer.parseInt(InvText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), MachineText.getText()));
            }
            CancelButtonAction(event);
        }
        System.out.println("New Part Added Successfully");
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
            name = NameText.getText();
        } catch (Exception e){
            errorMessage("Please enter a real name");
            return false;
        }
        //Store the price
        try {
            price = Double.parseDouble(PriceText.getText());
        } catch (Exception e) {
            errorMessage("Please enter a valid price");
            return false;
        }
        //Store the stock
        try {
            stock = Integer.parseInt(InvText.getText());
        } catch (Exception e) {
            errorMessage("Please enter a valid inventory count");
            return false;
        }
        //Store the min
        try {
            min = Integer.parseInt(MinText.getText());
        } catch (Exception e) {
            errorMessage("Enter a valid minimum value");
            return false;
        }
        //Store the max
        try {
            max = Integer.parseInt(MaxText.getText());
        } catch (Exception e) {
            errorMessage("Enter a valid Maximum value");
            return false;
        }

        //check if in-house or outsourced, then store the data
        if (inHouseRadio.isSelected()) {
            try {
                machineId = Integer.parseInt(MachineText.getText());
            } catch (Exception e) {
                errorMessage("Please Enter a valid integer for the machine ID");
                return false;
            }
        }
        else if (OutsourcedRadio.isSelected()) {
            try {
                companyName = MachineText.getText();
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
        System.out.println(error);
    }

}
