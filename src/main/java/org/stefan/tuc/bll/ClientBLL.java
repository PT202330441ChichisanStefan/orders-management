package org.stefan.tuc.bll;

import org.stefan.tuc.dao.ClientDAO;
import org.stefan.tuc.model.Client;
import org.stefan.tuc.presentation.GenerateTable;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents the class where it handles the processing data of Client.
 */
public class ClientBLL {

    private final ClientDAO clientDAO;

    /**
     * Creates an ClientBLL constructor.
     */
    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * Finds all the clients in the database.
     * @return A list containing details about the clients.
     */
    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null || clients.isEmpty()) {
            throw new NoSuchElementException("There are no clients in the database.");
        }

        return clients;
    }

    /**
     * Finds a client by his id.
     * @param id The id of the client.
     * @return The instance of the client.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }

        return client;
    }

    /**
     * If a client with the name specified doesn't exist then
     *      Creates a new {@link Client}
     * Else
     *      Updates the existing {@link Client}
     * @param name The name of the client.
     * @param address The address of the client.
     * @param email The email of the client.
     */
    public Client insertClient(String name, String address, String email) {
        Client client = clientDAO.findByName(name);
        boolean clientAlreadyExists = true;
        if (client == null) {
            client = new Client();
            clientAlreadyExists = false;
        }

        client.setName(name);
        client.setAddress(address);
        client.setEmail(email);

        if (clientAlreadyExists) {
            clientDAO.update(client);
        } else {
            clientDAO.insert(client);
        }

        return client;
    }

    /**
     * Deletes the {@link Client} with the name specified if it exists.
     * @param name The name of the client.
     */
    public void delete(String name) {
        clientDAO.deleteByName(name);
    }


    /**
     Generates a client table.
     Retrieves all clients from the database, converts them into a list,
     and generates a table based on the retrieved data.
     */
    public static void generateClientTable() {
        ClientDAO clientDAO1 = new ClientDAO();
        GenerateTable generateTable = new GenerateTable();
        List<Client> clients = clientDAO1.findAll();
        clients.forEach(i -> {
            i.name1 = i.getName();
            i.id1 = i.getId();
        });
        generateTable.generateTable(clients, clientDAO1);
    }
}
