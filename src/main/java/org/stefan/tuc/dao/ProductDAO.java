package org.stefan.tuc.dao;

import org.stefan.tuc.connection.ConnectionFactory;
import org.stefan.tuc.model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Represents the specific class of Product which uses basic CRUD methods from superclass. Has only client specific queries.
 * @author Chichisan Stefan
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Creates a ProductDAO constructor.
     */
    public ProductDAO() {
        super();
    }

    /**
     * Gets a specific field from a table to form a desired query.
     * @param prefix The query keyword.
     * @param name The field which has the name
     * @return The format to form the desired query.
     */
    private String getFieldQuery(String prefix, String name) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(prefix).append(" ");
        stringBuilder.append("FROM ").append(type.getSimpleName().toLowerCase() + "s");
        stringBuilder.append(" WHERE ");

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(name)) {
                stringBuilder.append(fields[i].getName()).append(" = ?");
                break;
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Creates the specific query to search in the database by name.
     * @return The format of the query.
     */
    private String createFindByNameQuery() {
        String prefix = "SELECT *";
        return getFieldQuery(prefix, "name");
    }

    /**
     * Creates the specific query to delete in the database by name.
     * @return The format of the query.
     */
    private String createDeleteByNameQuery() {
        String prefix = "DELETE";
        return getFieldQuery(prefix, "name");
    }

    /**
     * Searches for the {@link Product} with name.
     * @param name The name of the Product.
     * @return The {@link Product} with the name specified if it exists or null otherwise.
     */
    public Product findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindByNameQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            List<Product> objects = createObjects(resultSet);
            if (objects != null && objects.size() > 0) {
                return objects.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "ProductDAO:findByName" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Deletes the {@link Product} with the name specified from the database.
     * @param name The name of the product.
     * @return True if it was deleted, false otherwise.
     */
    @Override
    public boolean deleteByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteByNameQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);

            int affectedRows = statement.executeUpdate();

            return affectedRows != 0;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "ProductDAO:deleteByName" + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return false;
    }
}
