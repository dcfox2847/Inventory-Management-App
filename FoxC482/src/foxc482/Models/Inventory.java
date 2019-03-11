
package foxc482.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dcfox
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int productIDCount = 0;
    
    
    
    //Find parts and products
    public static ObservableList<Part> getPartInventory(){
        return allParts;
    }
    public static ObservableList<Product> getProductInventory(){
        return allProducts;
    }
    
    // Parts
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static boolean deletePart(int partID){
        for(Part part : allParts){
            if(part.getPartID() == partID){
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }
    public static void deletePart(Part part) {
        allParts.remove(part);
    }
    public static int getPartIDCount(){
        partIDCount++;
        return partIDCount;
    }
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
    public static int lookupPart(String searchPart){
        boolean isFound = false;
        int index = 0;
        if(isInteger(searchPart)){
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(searchPart) == allParts.get(i).getPartID()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allParts.size(); i++) {
                searchPart = searchPart.toLowerCase();
                if (searchPart.equals(allParts.get(i).getPartName().toLowerCase())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if(isFound == true){
            return index;
        }
        else {
            System.out.println("No parts found.");
            return -1;
        }
    }
   public static boolean deletePartCheck(Part part){
       boolean isFound = false;
       for(int i = 0; i < allProducts.size(); i++){
           if(allProducts.get(i).getProductParts().isEmpty()){
               isFound = true;
           }
       }
       return isFound;
   }
  
    
    
    // Products
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    public static boolean removeProduct(int productID){
        for(Product product : allProducts){
            if(product.getProductID() == productID){
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }
    public static void removeProduct(Product product) {
        allProducts.remove(product);
    }
    public static int getProductIDCount(){
        productIDCount++;
        return productIDCount;
    }
    public static int lookupProduct(String searchTerm) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allProducts.get(i).getProductID()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (searchTerm.equals(allProducts.get(i).getProductName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
    }
    public static void updateProduct(int index, Product updatedProduct){
        allProducts.set(updatedProduct.getProductID(), updatedProduct);
    }
    public static boolean deleteProductCheck(Product product){
        boolean isFound = false;
        int productID = product.getProductID();
        for(int i=0; i < allProducts.size(); i++){
            if(allProducts.get(i).getProductID() == productID){
                if(!allProducts.get(i).getProductParts().isEmpty()){
                    isFound = true;
                }
            }
        }
        return isFound;
    }
    
    // Method to check whether a string is can be an integer value
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    // Constructor
    public Inventory(){
    }
}
