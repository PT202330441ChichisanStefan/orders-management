package org.stefan.tuc.dao;

import org.stefan.tuc.connection.ConnectionFactory;
import org.stefan.tuc.model.Client;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * Represents the specific class of Client which uses basic CRUD methods from superclass. Has only client specific queries.
 * @author Chichisan Stefan
 */
public class ClientDAO extends AbstractDAO<Client> {

    /**
     * Creates an ClientDAO constructor.
     */
    public ClientDAO() {
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
     * Searches for the {@link Client} with the name specified.
     * @param name The name of the Client.
     * @return The {@link Client} with the name specified if it exists or null otherwise.
     */
    public Client findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createFindByNameQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            List<Client> objects = createObjects(resultSet);
            return objects.size() > 0 ? objects.get(0) : null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "ClientDAO:findByName" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Deletes the {@link Client} with the name specified from the database.
     * @param name The name of the client to be deleted.
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
            LOGGER.log(Level.WARNING, type.getName() + "ClientDAO:deleteByName" + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return false;
    }
}
