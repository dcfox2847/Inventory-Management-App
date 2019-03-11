
package foxc482.View;

import foxc482.Models.Inventory;
import static foxc482.Models.Inventory.deletePart;
import static foxc482.Models.Inventory.deletePartCheck;
import static foxc482.Models.Inventory.deleteProductCheck;
import static foxc482.Models.Inventory.getPartInventory;
import static foxc482.Models.Inventory.getProductInventory;
import static foxc482.Models.Inventory.removeProduct;
import foxc482.Models.Part;
import foxc482.Models.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dcfox
 */
public class MainScreenController implements Initializable {

    // FXML fields
    @FXML
    private TableView<Part> tableParts;
    @FXML
    private TableColumn<Part, Integer> tablePartsIDColumn;
    @FXML
    private TableColumn<Part, String> tablePartsNameColumn;
    @FXML
    private TableColumn<Part, Integer> tablePartsInventoryColumn;
    @FXML
    private TableColumn<Part, Double> tablePartsPriceColumn;
    @FXML
    private TableView<Product> tableProducts;
    @FXML
    private TableColumn<Product, Integer> tableProductsIDColumn;
    @FXML
    private TableColumn<Product, String> tableProductsNameColumn;
    @FXML
    private TableColumn<Product, Integer> tableProductsInventoryColumn;
    @FXML
    private TableColumn<Product, Double> tableProductsPriceColumn;
    @FXML
    private TextField txtPartSearch;
    @FXML
    private TextField txtProductSearch;
    @FXML
    private Button btnPartsSearch = new Button();
    
    // Class Variables
    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;
    
    // Update Table Views
    public void updatePartsTable(){
        tableParts.setItems(getPartInventory());
    }
    public void updateProductsTable(){
        tableProducts.setItems(getProductInventory());
    }
    
    //Find the index of parts/products
    public static int indexOfModPart(){
        return modifyPartIndex;
    }
    public static int indexOfModProduct(){
        return modifyProductIndex;
    }
    

    // Exit the program
    @FXML
    public void MainExitClick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }
    }
    
    // Add part or product handlers
    @FXML
    void addPart(ActionEvent event) throws IOException{
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    void addProduct(ActionEvent event) throws IOException{
        Parent addProducts = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(addProducts);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    // Modify part of product handlers
    
    @FXML
    void modifyPart(ActionEvent event) throws IOException{
        modifyPart = tableParts.getSelectionModel().getSelectedItem();
        modifyPartIndex = getPartInventory().indexOf(modifyPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }
    @FXML
    void modifyProduct(ActionEvent event) throws IOException {
        modifyProduct = tableProducts.getSelectionModel().getSelectedItem();
        modifyProductIndex = getProductInventory().indexOf(modifyProduct);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    }
    
    // Search parts or products
    @FXML
    void partsSearch(ActionEvent event) {
        String searchPart = txtPartSearch.getText();
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
            Part tempPart = Inventory.getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            tableParts.setItems(tempPartList);
        }
    }
    @FXML
    void productsSearch(ActionEvent event) {
        String searchProduct = txtProductSearch.getText();
        int prodIndex = -1;
        if (Inventory.lookupProduct(searchProduct) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search term entered does not match any known products.");
            alert.showAndWait();
        }
        else {
            prodIndex = Inventory.lookupProduct(searchProduct);
            Product tempProduct = Inventory.getProductInventory().get(prodIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            tableProducts.setItems(tempProductList);
        }
    }
    
    // Delete a part or product
    @FXML
    void partsDelete(ActionEvent event) {
        Part part = tableParts.getSelectionModel().getSelectedItem();
        if (deletePartCheck(part)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Part cannot be deleted!");
            alert.setContentText("Part is being used by one or more products.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deletePart(part);
                updatePartsTable();
                System.out.println("Part " + part.getPartName() + " was removed.");
            }
            else {
                System.out.println("Part " + part.getPartName() + " was not removed.");
            }
        }
    }
    @FXML
    void productsDelete(ActionEvent event) {
        Product product = tableProducts.getSelectionModel().getSelectedItem();
        if (deleteProductCheck(product)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Product cannot be deleted!");
            alert.setContentText("Product contains one or more parts.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Confirm Delete?");
            alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                removeProduct(product);
                updateProductsTable();
                System.out.println("Product " + product.getProductName() + " was removed.");
            } else {
                System.out.println("Product " + product.getProductName() + " was removed.");
            }
        }
    }
    
    // No parameter constructor
    public MainScreenController() {
    }

    //Initialize
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tablePartsIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        tablePartsNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        tablePartsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        tablePartsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        tableProductsIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        tableProductsNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        tableProductsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        tableProductsPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        updatePartsTable();
        updateProductsTable();
    }    
    
}
