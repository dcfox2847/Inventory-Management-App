
package foxc482.View;

import foxc482.Models.Inhouse;
import foxc482.Models.Inventory;
import static foxc482.Models.Inventory.getPartInventory;
import foxc482.Models.Outsourced;
import foxc482.Models.Part;
import static foxc482.View.MainScreenController.indexOfModPart;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dcfox
 */
public class ModifyPartController implements Initializable {

    // FX GUI items
    
    @FXML
    private RadioButton radioModifyPartInHouse;
    @FXML
    private RadioButton radioModifyPartOutsourced;
    @FXML
    private Label lblModifyPartIDNumber;
    @FXML
    private TextField txtModifyPartName;
    @FXML
    private TextField txtModifyPartInv;
    @FXML
    private TextField txtModifyPartPrice;
    @FXML
    private TextField txtModifyPartMin;
    @FXML
    private TextField txtModifyPartMax;
    @FXML
    private Label lblModifyPartDyn;
    @FXML
    private TextField txtModifyPartDyn;
    
    // Class variables
    private boolean isOutsourced;
    int partIndex = indexOfModPart();
    private String exceptionMessage = new String();
    private int partID;
    
    
    // Choose to modify inhouse or outsourced and handle radio buttons
    
    ToggleGroup tg = new ToggleGroup();
    
    @FXML
    void modifyPartRadio(ActionEvent event) {
        if (radioModifyPartInHouse.isSelected()){
            isOutsourced = false;
            lblModifyPartDyn.setText("Machine ID");
            txtModifyPartDyn.setPromptText("Machine ID");
        }else{
            isOutsourced = true;
            lblModifyPartDyn.setText("Company Name");
            txtModifyPartDyn.setPromptText("Company Name");
        }
    }
    
    
    // Save the part after being modified
    
    @FXML
    void modSavePart(ActionEvent event) throws IOException {
        String partName = txtModifyPartName.getText();
        String partInv = txtModifyPartInv.getText();
        String partPrice = txtModifyPartPrice.getText();
        String partMin = txtModifyPartMin.getText();
        String partMax = txtModifyPartMax.getText();
        String partDyn = txtModifyPartDyn.getText();

        try {
            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                if (isOutsourced == false) {
                    System.out.println("Part name: " + partName);
                    Inhouse inPart = new Inhouse();
                    inPart.setPartID(partID);
                    inPart.setPartName(partName);
                    inPart.setPartInStock(Integer.parseInt(partInv));
                    inPart.setPartPrice(Double.parseDouble(partPrice));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setMachineID(Integer.parseInt(partDyn));
                    Inventory.updatePart(partIndex, inPart);
                }
                else {
                    System.out.println("Part name: " + partName);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartInStock(Integer.parseInt(partInv));
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partDyn);
                    Inventory.updatePart(partIndex, outPart);
                }

                Parent modifyProductSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(modifyProductSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }
  
    // User clicks the cancel button
    
    @FXML
    private void modCancelPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Would you like to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent modifyPartCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(modifyPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else {
            System.out.println("Cancel has been clicked.");
        }
    }
    
    // Initialze the modify part screen and class
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part part = getPartInventory().get(partIndex);
        partID = getPartInventory().get(partIndex).getPartID();
        lblModifyPartIDNumber.setText("Auto-Gen: " + partID);
        txtModifyPartName.setText(part.getPartName());
        txtModifyPartInv.setText(Integer.toString(part.getPartInStock()));
        txtModifyPartPrice.setText(Double.toString(part.getPartPrice()));
        txtModifyPartMin.setText(Integer.toString(part.getPartMin()));
        txtModifyPartMax.setText(Integer.toString(part.getPartMax()));
        if (part instanceof Inhouse) {
            lblModifyPartDyn.setText("Machine ID");
            txtModifyPartDyn.setText(Integer.toString(((Inhouse) getPartInventory().get(partIndex)).getMachineID()));
            radioModifyPartInHouse.setSelected(true);
        }
        else {
            lblModifyPartDyn.setText("Company Name");
            txtModifyPartDyn.setText(((Outsourced) getPartInventory().get(partIndex)).getCompanyName());
            radioModifyPartOutsourced.setSelected(true);
        }
    }    
    
}
