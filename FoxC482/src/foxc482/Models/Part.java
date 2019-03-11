
package foxc482.Models;

import javafx.beans.property.*;

/**
 *
 * @author dcfox
 */
public abstract class Part {
    
    private final IntegerProperty partID;
    private final StringProperty partName;
    private final DoubleProperty partPrice;
    private final IntegerProperty partInStock;
    private final IntegerProperty partMin;
    private final IntegerProperty partMax;
    
// Class Constructor
    
    public Part() {
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partPrice = new SimpleDoubleProperty();
        partInStock = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
    }

// Getters
    public IntegerProperty partIDproperty() {
        return partID;
    }

    public StringProperty partNameProperty() {
        return partName;
    }

    public DoubleProperty partPriceProperty() {
        return partPrice;
    }

    public IntegerProperty partInvProperty() {
        return partInStock;
    }

    public IntegerProperty partMinProperty() {
        return partMin;
    }

    public IntegerProperty partMaxProperty() {
        return partMax;
    }

    public int getPartID() {
        return this.partID.get();
    }

    public String getPartName() {
        return this.partName.get();
    }

    public double getPartPrice() {
        return this.partPrice.get();
    }

    public int getPartInStock() {
        return this.partInStock.get();
    }

    public int getPartMin() {
        return this.partMin.get();
    }

    public int getPartMax() {
        return this.partMax.get();
    }
    


    // Setters
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartName(String name) {
        this.partName.set(name);
    }

    public void setPartPrice(double price) {
        this.partPrice.set(price);
    }

    public void setPartInStock(int inStock) {
        this.partInStock.set(inStock);
    }

    public void setPartMin(int min) {
        this.partMin.set(min);
    }

    public void setPartMax(int max) {
        this.partMax.set(max);
    }

    
    // Method used to check for part validation
    public static String isPartValid(String name, int min, int max, int inv, double price, String errorMessage){
        if (name == null) {
            errorMessage = errorMessage + "The name field is required. ";
        }
        if (inv < 1) {
            errorMessage = errorMessage + "Must have at least one item in inventory. ";
        }
        if (price <= 0) {
            errorMessage = errorMessage + "The price must be greater than $0. ";
        }
        if (max < min) {
            errorMessage = errorMessage + "The Max must be greater than or equal to the Min. ";
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + "The inventory must be between the Min and Max values. ";
        }
        return errorMessage;
    }
    
    
}
