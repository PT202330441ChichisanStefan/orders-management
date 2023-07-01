package org.stefan.tuc.model;

/**
 * Represents a client.
 * @author Chichisan Stefan
 */
public class Client extends ParentModel {

    private int id;
    private String name;
    private String address;
    private String email;

    /**
     * Creates a client.
     */
    public Client() {
        super();
    }

    /**
     * Creates a client with specified attributes (id is included).
     * @param id The client's id.
     * @param name The client's name.
     * @param address The client's address.
     * @param email The client's email.
     */
    public Client(int id, String name, String address, String email) {
        super(id, name);
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Creates a client with specified attributes.
     * @param name The client's name.
     * @param address The client's address.
     * @param email The client's email.
     */
    public Client(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Gets the client's id.
     * @return The id of the client.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the client's id.
     * @param id The client's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the client's name.
     * @return The client's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the client's name.
     * @param name The client's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the client's address.
     * @return The client's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the client's address.
     * @param address The client's address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the client's email.
     * @return The client's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the client's email.
     * @param email The client's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Creates a string representation of all client's attributes.
     * @return Representation of the client's attributes.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address +
                '}';
    }
}
