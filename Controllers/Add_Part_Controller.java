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
import java.util.ArrayList;
import java.util.List;

public class Add_Part_Controller {
    Inventory inv;

    /**
     * @param inv accepts inventory and sets the inhouse radio button to true by default
     *            This function is only called during loading
     */
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

    /**
     * @param event Will switch the text label to Machine label when the user selects the inhouse radio button
     */
    @FXML
    void InhouseAction(ActionEvent event) {
        MachineLabel.setText("Machine Id:");
    }

    /**
     * @param event Will switch the text label to company name when the user selects the outsourced radio button
     */
    @FXML
    void OutsourcedAction(ActionEvent event) {
        MachineLabel.setText("Company Name:");
    }

    /**
     * @param event When the save button is selected, this function creates a unique ID, adds the new part, then adds the part to the inventory
     *              The Cancel button action is then manually triggered to go back to the main screen
     * @throws IOException filepath exception
     */
    @FXML
    void SaveButtonAction(ActionEvent event) throws IOException {
        int numberOfParts = inv.getAllParts().size();
        //Find what ID to assign, Make an integer list of all known IDs, then assign one to idToAssign
        List<Integer> list = new ArrayList<>();
        int idToAssign = 1;

        //Populate int list
        for (int i = 0; i < numberOfParts; i++) {
            list.add(inv.getAllParts().get(i).getId());
        }
        //Go through the numbers, and see if the Integer List contains that number
        while (list.contains(idToAssign)) {
            idToAssign++;
        }

        if (verifyData()) {
            if (inHouseRadio.isSelected()) {
                inv.addPart(new InhousePart(idToAssign, NameText.getText(), Double.parseDouble(PriceText.getText()), Integer.parseInt(InvText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), Integer.parseInt(MachineText.getText())));
            }
            if (OutsourcedRadio.isSelected()) {
                inv.addPart(new OutsourcedPart(idToAssign, NameText.getText(), Double.parseDouble(PriceText.getText()), Integer.parseInt(InvText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), MachineText.getText()));
            }
            System.out.println("New Part Added Successfully");
            CancelButtonAction(event);
        }
    }

    /**
     * @param event When Cancel button is clicked, go to main screen, passing the inventory to the controller
     * @throws IOException due to /Views/main_form.fxml
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
     * Data verification method
     * Tries to store all data into variables, if unable to, trigger errorMessage()
     * @return true (if passes all verification)
     */
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
        //Check if Name is empty
        if (name.isEmpty()) {
            errorMessage("Please enter name");
            return false;
        }
        //Check if price is positive
        if (price <= 0) {
            errorMessage("Please enter a valid price range");
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
     * @param error requires a string describing the error to alert the user to
     */
    void errorMessage(String error) {

        System.out.println(error);
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(error);
        a.show();
    }

}
