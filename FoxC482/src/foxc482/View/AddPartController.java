
package foxc482.View;

import foxc482.Models.Inhouse;
import foxc482.Models.Inventory;
import foxc482.Models.Outsourced;
import foxc482.Models.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dcfox
 */
public class AddPartController implements Initializable {

    
    @FXML
    private TextField txtAddPartName;
    @FXML
    private TextField txtAddPartInv;
    @FXML
    private TextField txtAddPartPrice;
    @FXML
    private TextField txtAddPartMin;
    @FXML
    private TextField txtAddPartMax;
    @FXML
    private Label lblAddPartDyn;
    @FXML
    private TextField txtAddPartOutIn;

    private boolean isOutsourced;
    private String exceptionMessage = new String();
    private int partID = nextID();
    
    //Radio Buttons
    @FXML
    private RadioButton radioAddPartInHouse;
    @FXML
    private RadioButton radioAddPartOutsourced;
    
    
    // Toggling of Radio Buttons of the radio buttons and setting of default values.
    
    ToggleGroup tg = new ToggleGroup();
    
    @FXML
    void addPartRadio(ActionEvent event) {
        if (radioAddPartInHouse.isSelected()){
            isOutsourced = false;
            lblAddPartDyn.setText("Machine ID");
            txtAddPartOutIn.setPromptText("Machine ID");
        }else{
            isOutsourced = true;
            lblAddPartDyn.setText("Company Name");
            txtAddPartOutIn.setPromptText("Company Name");
        }
    }
    
    // Function to set sequential ID's of parts in the list
    
    private int nextID() {
        int listSize = Inventory.getPartInventory().size();
        if (listSize == 0){
            return 1;
        } else {
            return listSize + 1;
        }
    }
    
    // Method to save the new part that has been added
    
    @FXML
    void savePart(ActionEvent event) throws IOException {
        String partName = txtAddPartName.getText();
        String partInv = txtAddPartInv.getText();
        String partPrice = txtAddPartPrice.getText();
        String partMin = txtAddPartMin.getText();
        String partMax = txtAddPartMax.getText();
        String partDyn = txtAddPartOutIn.getText();

        try {
            exceptionMessage = Part.isPartValid(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Could not add part...");
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
                    inPart.setPartPrice(Double.parseDouble(partPrice));
                    inPart.setPartInStock(Integer.parseInt(partInv));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setMachineID(Integer.parseInt(partDyn));
                    Inventory.addPart(inPart);
                } else {
                    System.out.println("Part name: " + partName);
                    Outsourced outPart = new Outsourced();
                    outPart.setPartID(partID);
                    outPart.setPartName(partName);
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartInStock(Integer.parseInt(partInv));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setCompanyName(partDyn);
                    Inventory.addPart(outPart);
                }

                Parent addPartSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addPartSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Could not add part....");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }
    
    // Cancel the adding of a part
    
    @FXML
    private void cancelPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }
    
   
    // Initialize the Controller Class
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radioAddPartInHouse.setSelected(true);
    }    
    
}
