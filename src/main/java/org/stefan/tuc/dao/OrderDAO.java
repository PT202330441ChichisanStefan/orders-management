package org.stefan.tuc.dao;

import org.stefan.tuc.connection.ConnectionFactory;
import org.stefan.tuc.model.Order;

import java.lang.reflect.Field;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Represents the specific class of Order which uses basic CRUD methods from superclass. Has only client specific queries.
 * @author Chichisan Stefan
 */
public class OrderDAO extends AbstractDAO<Order> {

    private ClientDAO clientDAO;
    private ProductDAO productDAO;

    /**
     * Creates the OrderDAO constructor.
     */
    public OrderDAO() {
        clientDAO = new ClientDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Overrides the default behaviour from {@link AbstractDAO}.
     * @param order The order to create the query.
     * @return The query used for the insert operation.
     */
    @Override
    protected String createInsertQuery(Order order) {
        return new StringBuilder("INSERT INTO ")
                .append(type.getSimpleName().toLowerCase() + "s")
                .append(" (client_id, product_id, quantity) VALUES (")
                .append(order.getClient().getId())
                .append(", ")
                .append(order.getProduct().getId())
                .append(", ")
                .append(order.getQuantity())
                .append(")")
                .toString();
    }

    /**
     * Overrides the default behaviour from {@link AbstractDAO}.
     * @param order The order to update the query for.
     * @return The query used for the update operation.
     */
    @Override
    protected String createUpdateQuery(Order order) {
        return new StringBuilder("UPDATE ")
                .append(type.getSimpleName().toLowerCase() + "s")
                .append(" client_id = ")
                .append(order.getClient().getId())
                .append(", product_id = ")
                .append(order.getProduct().getId())
                .append(", quantity = ")
                .append(order.getQuantity())
                .append(" WHERE id = ")
                .append(order.getId())
                .toString();
    }

    /**
     * Overrides the default behaviour from {@link AbstractDAO}.
     * @param resultSet the set of results returned from the database.
     * @return A list of orders.
     */
    @Override
    protected List<Order> createObjects(ResultSet resultSet) {
        List<Order> orders = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order instance = new Order();
                instance.setId(resultSet.getInt("id"));
                instance.setClient(clientDAO.findById(resultSet.getInt("client_id")));
                instance.setProduct(productDAO.findById(resultSet.getInt("product_id")));
                instance.setQuantity(resultSet.getInt("quantity"));

                orders.add(instance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean deleteByName(String name) {
        return false;
    }
}
