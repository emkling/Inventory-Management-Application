package model;

/**
 * Supplied class Part.java
 *
 * Models the attributes of a part
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for the creation of a part
     *
     * @param id
     * @param name
     * @param min
     * @param max
     * @param stock
     * @param price
     */
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getter for the ID of a part
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the ID of a part
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the name of a part
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of a part
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the price of a part
     *
     * @return The price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for the price of the part
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for the stock of a part
     *
     * @return The stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for the stock of a part
     *
     * @param stock The stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for the min of a part
     *
     * @return The min
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for the min of a part
     *
     * @param min The min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for the max of a part
     *
     * @return The max
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for the max of a part
     *
     * @param max The max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}

