
package foxc482.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dcfox
 */
public class Outsourced extends Part {
    
    private StringProperty companyName;
    
    public Outsourced(){
        super();
        companyName = new SimpleStringProperty();
    }

    public String getCompanyName() {
        return this.companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
}
