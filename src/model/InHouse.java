package model;

/**
 * Models attributes of In-House parts
 *
 */
public class InHouse extends Part{

    /**
     * Machine ID of part
     */
    private int machineID;

    /**
     * Constructor for the creation of an In-House object
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID)
    {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Sets the machine ID
     *
     * @param machineID
     */
    public void setMachineID(int machineID)
    {
        this.machineID = machineID;
    }

    /**
     * Getter for the machine ID
     *
     * @return machineID for desired part
     */
    public int getMachineID()
    {
        return machineID;
    }
}
