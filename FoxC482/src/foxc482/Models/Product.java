
package foxc482.Models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dcfox
 */
public class Product {
    
    // Class Variables
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty productID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    // Constructors

    public Product() {
        productID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    // Getters

   public IntegerProperty productIDProperty() {
        return productID;
    }

    public StringProperty productNameProperty() {
        return name;
    }

    public DoubleProperty productPriceProperty() {
        return price;
    }

    public IntegerProperty productInvProperty() {
        return inStock;
    }

    public IntegerProperty productMinProperty() {
        return min;
    }

    public IntegerProperty productMaxProperty() {
        return max;
    }

    public int getProductID() {
        return this.productID.get();
    }

    public String getProductName() {
        return this.name.get();
    }

    public double getProductPrice() {
        return this.price.get();
    }

    public int getProductInStock() {
        return this.inStock.get();
    }

    public int getProductMin() {
        return this.min.get();
    }

    public int getProductMax() {
        return this.max.get();
    }

    public ObservableList getProductParts() {
        return associatedParts;
    }
    
    // Setters
    
    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setProductName(String name) {
        this.name.set(name);
    }

    public void setProductPrice(double price) {
        this.price.set(price);
    }

    public void setProductInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public void setProductMin(int min) {
        this.min.set(min);
    }

    public void setProductMax(int max) {
        this.max.set(max);
    }

    public void setProductParts(ObservableList<Part> parts) {
        this.associatedParts = parts;
    }

    
    
    
    // Methods for adding, removing, or looking up a part
    
    public void addAssociatedPart(Part associatedPart){
        this.associatedParts.add(associatedPart);
    }
    
    public boolean removeAssociatedPart(int partID){
        for (Part part : associatedParts){
            if(part.getPartID() == partID){
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }
    
    public Part lookupAssociatedPart(int partID){
        for(Part part : associatedParts){
            if(part.getPartID() == partID){
                return part;
            }
        }
        return null;
    }
    
    
    // Check for validation of the product
    
    public static String validProduct(String name, int min, int max, int inStock, double price, ObservableList<Part> parts, String message){
        
        double partsPrice = 0.0;
        for (Part part : parts){
            partsPrice = partsPrice + part.getPartPrice();
        }
        
        if(name.equals("") || name == null){
            message = message + "You must enter a name. ";
        }
        if(inStock < 1){
            message = message + "That is not a valid number in inventory. ";
        }
        if(price <=0){
            message = message + "The price has to be greater than $0.";
        }
        if(max < min){
            message = message + "There must be a greater maximum inventory. ";
        }
        if(partsPrice > price){
            message = message + "The price of the product, must be greater than the price of it's parts. ";
        }
        if(inStock < min || inStock > max){
            message = message + " The Inventory must be between the minimum and maximum values.";
        }
        System.out.println("From in the product class" + " " + inStock + " " + min + " " + max);
        return message;
    }
    
}
