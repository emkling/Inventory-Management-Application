package model;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Models the inventory of parts and products
 *
 */
public class Inventory
{
    /**
     * List of all the parts in the inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds new part to the inventory
     */
    public static void addPart (Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds new product to the inventory
     */
    public static void addProduct (Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Searches for the specified part by ID
     *
     * @return Part that has a matching ID
     */
    public static Part lookupPart(int partID)
    {
        Part specifiedPart = null;

        for (Part part : allParts)
        {
            if (partID == part.getId())
            {
                specifiedPart = part;
            }
        }
        return specifiedPart;
    }

    /**
     * Searches for the specified part by name
     *
     * @return List of parts that had a matching name
     */
    public static ObservableList<Part> lookupPart (String partName)
    {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        for (Part part: allParts)
        {
            if (part.getName() == partName)
            {
                searchResults.add(part);
            }
        }

        return searchResults;
    }

    /**
     * Searches for the product by ID
     *
     * @return Product that has a matching ID
     */
    public static Product lookupProduct(int productID)
    {
        Product specifiedProduct = null;

        for (Product product: allProducts)
        {
            if (product.getId() == productID)
            {
                specifiedProduct = product;
            }
        }
        return specifiedProduct;
    }

    /**
     * Searches for a product by name
     *
     * @return List of products that had a matching name
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        for (Product product: allProducts)
        {
            if (product.getName() == productName)
            {
                searchResults.add(product);
            }
        }

        return searchResults;
    }

    /**
     * Updates a specific part by finding its index
     */
    public static void updatePart(int index, Part selectedPart) { allParts.set(index, selectedPart); }

    /**
     * Updates a specific product by finding its index
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a part from the inventory
     *
     * @return Boolean indicating the success of the deletion
     */
    public static boolean deletePart(Part selectedPart)
    {
        for (Part part : allParts) {
            if (part.getId() == selectedPart.getId()) {
                allParts.remove(selectedPart);
                return true;
            }
        }
                return false;
    }

    /**
     * Deletes a product from the inventory
     *
     * @return Boolean indicating the success of the deletion
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        for (Product product : allProducts)
        {
            if (product.getId() == selectedProduct.getId())
            {
                allProducts.remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the list of all parts in the inventory
     *
     * @return List of all parts
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Getter for all the products in the inventory
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

}
