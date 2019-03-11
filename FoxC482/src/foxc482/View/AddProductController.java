
package foxc482.View;

import foxc482.Models.Inventory;
import static foxc482.Models.Inventory.getPartInventory;
import foxc482.Models.Part;
import foxc482.Models.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class AddProductController implements Initializable {

    // FXML views
    @FXML
    private Label lblAddProductIDNumber;
    @FXML
    private TextField txtAddProductName;
    @FXML
    private TextField txtAddProductInv;
    @FXML
    private TextField txtAddProductPrice;
    @FXML
    private TextField txtAddProductMin;
    @FXML
    private TextField txtAddProductMax;
    @FXML
    private TextField txtAddProductSearch;
    @FXML
    private TableView<Part> tvAddProductAdd;
    @FXML
    private TableColumn<Part, Integer> tvAddProductAddIDColumn;
    @FXML
    private TableColumn<Part, String> tvAddProductAddNameColumn;
    @FXML
    private TableColumn<Part, Integer> tvAddProductAddInvColumn;
    @FXML
    private TableColumn<Part, Double> tvAddProductAddPriceColumn;
    @FXML
    private TableView<Part> tvAddProductDelete;
    @FXML
    private TableColumn<Part, Integer> tvAddProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, String> tvAddProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Integer> tvAddProductDeleteInvColumn;
    @FXML
    private TableColumn<Part, Double> tvAddProductDeletePriceColumn;
    
    // Class variables
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();
    private int productID = nextID();
    
    // Function to set sequential product ID
    private int nextID() {
        int listSize = Inventory.getProductInventory().size();
        if (listSize == 0){
            return 1;
        } else {
            return listSize + 1;
        }
    }
    
    // Constructor
    public AddProductController(){};
    
    // Table add and delete update
    
    public void updateAddPartTV() {
        tvAddProductAdd.setItems(getPartInventory());
    }

    public void updateDeletePartTV() {
        tvAddProductDelete.setItems(currentParts);
    }
    
    @FXML
    void addProductParts(ActionEvent event) {
        Part part = tvAddProductAdd.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDeletePartTV();
    }
    // Delete chosen Parts
    @FXML
    void deleteProductParts(ActionEvent event) {
        Part part = tvAddProductDelete.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Do you really want to delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            currentParts.remove(part);
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }
    // Search for Product
    @FXML
    void productSearch(ActionEvent event) {
        String searchPart = txtAddProductSearch.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search term entered does not match any known parts.");
            alert.showAndWait();
        }
        else {
            partIndex = Inventory.lookupPart(searchPart);
            Part tempPart = getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            tvAddProductAdd.setItems(tempPartList);
        }
    }

    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        String productName = txtAddProductName.getText();
        String productInv = txtAddProductInv.getText();
        String productPrice = txtAddProductPrice.getText();
        String productMin = txtAddProductMin.getText();
        String productMax = txtAddProductMax.getText();

        try{
            exceptionMessage = Product.validProduct(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), currentParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(productName);
                newProduct.setProductInStock(Integer.parseInt(productInv));
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(currentParts);
                Inventory.addProduct(newProduct);

                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error: ");
            alert.setContentText("You have not filled in all of the fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("You are about to cancel");
        alert.setContentText("Do you want to cancel adding the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addProductCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    
    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the Add Part TV
        tvAddProductAddIDColumn.setCellValueFactory(cell -> cell.getValue().partIDproperty().asObject());
        tvAddProductAddNameColumn.setCellValueFactory(cell -> cell.getValue().partNameProperty());
        tvAddProductAddInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tvAddProductAddPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        // Initialize the Delete Part TV
        tvAddProductDeleteIDColumn.setCellValueFactory(cell -> cell.getValue().partIDproperty().asObject());
        tvAddProductDeleteNameColumn.setCellValueFactory(cell -> cell.getValue().partNameProperty());
        tvAddProductDeleteInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tvAddProductDeletePriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        updateAddPartTV();
        updateDeletePartTV();
    }    
    
}
