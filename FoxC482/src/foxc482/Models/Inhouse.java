
package foxc482.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author dcfox
 */
public class Inhouse extends Part {
    private IntegerProperty machineID;
    
    public Inhouse(){
        super();
        machineID = new SimpleIntegerProperty();
    }

    public int getMachineID() {
        return this.machineID.get();
    }

    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
}
