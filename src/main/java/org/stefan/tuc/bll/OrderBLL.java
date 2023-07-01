package org.stefan.tuc.bll;

import org.stefan.tuc.dao.ClientDAO;
import org.stefan.tuc.dao.OrderDAO;
import org.stefan.tuc.dao.ProductDAO;
import org.stefan.tuc.model.Order;
import org.stefan.tuc.model.Product;
import org.stefan.tuc.presentation.GenerateTable;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents the class where it handles the processing data of Order.
 */
public class OrderBLL {

    private final OrderDAO orderDAO;
    private final ClientDAO clientDAO;
    private final ProductDAO productDAO;

    /**
     * Creates an OrderBLL constructor.
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        clientDAO = new ClientDAO();
        productDAO = new ProductDAO();
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    /**
     * Finds an order by his id.
     * @param id The id of the order.
     * @return The instance of the order.
     */
    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + " was not found!");
        }

        return order;
    }

    /**
     * Finds all the orders in the database.
     * @return A list containing details about the orders.
     */
    public List<Order> findAllOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null || orders.isEmpty()) {
            throw new NoSuchElementException("There are no orders in the database.");
        }

        return orders;
    }

    /**
     * If there's enough stock for {@link Product} with the specified name then
     *      Creates a new {@link Order}, updates the quantity for {@link Product} and generates a bill .pdf file
     * Else
     *      Generates a .pdf file containing the fail reason
     * @param clientName The name of the Client that place the order.
     * @param productName The product the order is for.
     * @param quantity Number of product items.
     */
    public Order insert(String clientName, String productName, String quantity) {
        int intQuantity = Integer.parseInt(quantity);

        Product product = productDAO.findByName(productName);
        if (product == null) {
            throw new NoSuchElementException("The order with the product name=" + productName + " was not found!");
        }

        if (product.getQuantity() < intQuantity) {
            return null;
        }

        Order order = new Order();
        order.setClient(clientDAO.findByName(clientName));
        order.setProduct(product);
        order.setQuantity(intQuantity);

        order = orderDAO.insert(order);

        if (order != null) {
            product = order.getProduct();
            product.setQuantity(product.getQuantity() - order.getQuantity());
            productDAO.update(product);
        }

        return order;
    }

    /**
     Generates an order table.
     Retrieves all orders from the database, converts them into a list,
     and generates a table based on the retrieved data.
     */
    public static void generateOrderTable() {
        OrderDAO orderDAO1 = new OrderDAO();
        GenerateTable table = new GenerateTable();
        List<Order> orders = orderDAO1.findAll();
        table.generateTable(orders, orderDAO1);
    }
}
