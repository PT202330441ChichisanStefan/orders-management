package org.stefan.tuc.start;

import org.stefan.tuc.bll.ClientBLL;
import org.stefan.tuc.bll.OrderBLL;
import org.stefan.tuc.bll.ProductBLL;
import org.stefan.tuc.model.Client;
import org.stefan.tuc.model.Order;
import org.stefan.tuc.model.Product;
import org.stefan.tuc.presentation.MainFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {

    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();

        Client client = null;
        List<Client> clients = new ArrayList<>();

        Product product = null;

        OrderBLL orderBLL = new OrderBLL();
        Order order = null;

        MainFrame mainFrame = new MainFrame();

        try {
            // client = clientBLL.findClientById(3);
            //product = productBLL.findProductById(1);
            //product = productBLL.insertProduct("Glue", "3", "10");
            //product = productBLL.findProductById(2);
            //order = orderBLL.insert("Catalin", "Glue", "20");
            client = clientBLL.insertClient("David", "davidescu", "david@email.com");
            //clientBLL.delete("Catalin");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }

        //ReflectionExample.retrieveProperties(product);
        //List<Object> properties = retrievePropertiesAsList(clients);

        //ReflectionExample.retrievePropertiesAsList(clients);

        /*List<Client> clientList = clientBLL.findAllClients();// ReflectionExample.retrieveProperties(Collections.singletonList(clients));
        for (Client client1 : clientList) {
            System.out.println(client1.getName() + " and " + client1.getAddress());
        }*/

        /*List<Product> productList = productBLL.findAllProducts();
        for (Product product1 : productList) {
            System.out.println(product1.getName() + " and " + product1.getQuantity());
        }*/

       /* List<Order> ordersList = orderBLL.findAllOrders();
        for (Order order1 : ordersList) {
            System.out.println(order1.getId());
        }*/

    }
}
