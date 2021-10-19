package model;

public class Outsourced extends Part{

    private String companyName;


    /** The following constructor is a subclass of Part and adds the company name to the subclass.
     * Outsourced inherits the constructor from Part
     *
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The quantity currently in inventory for the part.
     * @param min The inventory minimum for the part.
     * @param max The inventory maximum for the part.
     * @param companyName The company name associated with the part.
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }
/** Getter:
 * @return retrieves company name*/
        public String getCompanyName() {
        return companyName;
    }

    /**Setter: sets the company name
     * @param companyName is the company name to set*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
