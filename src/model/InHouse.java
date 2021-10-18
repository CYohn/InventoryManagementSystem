package model;

public class InHouse extends Part{

    private int machineId;

/** This is the constructor for the subclass of Part, In House:
 * InHouse inherits the constructor from Part
 *
 * @param id The id of the part.
 * @param name The name of the part.
 * @param price The price of the part.
 * @param stock The quantity currently in inventory for the part.
 * @param min The inventory minimum for the part.
 * @param max The inventory maximum for the part.
 * @param machineId The Part's machine Id.
 */


    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);

        this.machineId = machineId;
    }


/** Gets the machine ID,
 * @return is the machine ID of the part*/
    public int getMachineId() {
        return machineId;
    }
/** Sets the machine ID,
 * @param machineId is the ID to set*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
