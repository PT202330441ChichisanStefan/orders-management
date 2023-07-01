package org.stefan.tuc.model;

/**
 * Represents a product.
 * @author Chichisan Stefan
 */
public class Product extends ParentModel {

    private int id;
    private String name;
    private double price;
    private int quantity;

    /**
     * Creates a product.
     */
    public Product() {
        super();
    }

    /**
     * Creates a product with specified attributes (id is included).
     * @param id The client's id.
     * @param name The client's name.
     * @param price The client's address.
     * @param quantity The client's email.
     */
    public Product(int id, String name, double price, int quantity) {
        super(id, name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Creates a product with specified attributes.
     * @param name
     * @param price
     * @param quantity
     */
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Gets the product's id.
     * @return The product's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product's id.
     * @param id The product's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the product's name.
     * @return The product's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product's name.
     * @param name The product's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product's price.
     * @return The product's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product's price.
     * @param price The product's price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the product's quantity.
     * @return The product's quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the product's quantity.
     * @param quantity The product's quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Creates a string representation of all product's attributes.
     * @return Representation of the product's attributes.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
