package org.stefan.tuc.model;

/**
 * Represents an order.
 * @author Chichisan Stefan
 */
public class Order extends ParentModel {

    private int id;
    private Client client;
    private Product product;
    private int quantity;

    /**
     * Creates an order.
     */
    public Order() {
        super();
    }

    /**
     * Creates an order with specified attributes (id is included).
     * @param id The order's id.
     * @param client The order's client.
     * @param product The order's product.
     * @param quantity The order's quantity.
     */
    public Order(int id, Client client, Product product, int quantity) {
        super(id, "");
        this.id = id;
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Creates an order with specified attributes.
     * @param client The order's client.
     * @param product The order's product.
     * @param quantity The client's quantity.
     */
    public Order(Client client, Product product, int quantity) {
        this.client = client;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Gets the order's id.
     * @return The id of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the order's id.
     * @param id The order's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the order's client.
     * @return The order's client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the order's client.
     * @param client The order's client.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Gets the order's product.
     * @return The order's product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the order's product.
     * @param product The order's product.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the order's quantity.
     * @return The order's quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the order's quantity.
     * @param quantity The order's quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Creates a string representation of all client's attributes.
     * @return Representation of the client's attributes.
     */
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
