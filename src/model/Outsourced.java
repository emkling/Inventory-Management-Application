package model;

/**
 * Models an outsourced part
 */
public class Outsourced extends Part{

    /**
     * Company name of partd
     */
    private String companyName;

    /**
     * Constructor for the creation of an outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter for the company name
     *
     * @return Company Name
     */
    public String getCompanyName()
    {
        return companyName;
    }
}
