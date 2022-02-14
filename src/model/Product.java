package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models the attributes of a product
 */
public class Product {
    /**
     * ID of the product
     */
    private int id;

    /**
     * Name of the  product
     */
    private String name;

    /**
     * Price of the product
     */
    private double price;

    /**
     * Stock of the product
     */
    private int stock;

    /**
     * Min of the product
     */
    private int min;

    /**
     * Max of the product
     */
    private int max;

    /**
     * List of parts that are associated with the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor for the creation a product
     *
     * @param min
     * @param max
     * @param id
     * @param name
     * @param stock
     *
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /**
     * Setter for the product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for the price of a product
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter for the stock of a product
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter for the min of a product
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter for the max of a product
     *
     * @param max
     * */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter for the ID of a product
     *
     * @return Product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of a product
     *
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for the price of a product
     *
     * @return Price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Getter for stock of a product
     *
     * @return Stock
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * Getter for the min of a product
     *
     * @return Min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * Getter for the max of a product
     *
     * @return max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Adds a part to the associated list of a product
     *
     * @param part
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);

    }

    /**
     * Deletes a part from an associated parts list
     *
     * @param selectedAssociatedPart
     * @return Boolean indicating whether removal was successful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        if(associatedParts.contains(selectedAssociatedPart))
        {

            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Getter for the list of associated parts of a product
     *
     * @return List of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;

    }
}
