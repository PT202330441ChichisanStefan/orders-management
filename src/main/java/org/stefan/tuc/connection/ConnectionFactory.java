package org.stefan.tuc.connection;

import org.stefan.tuc.model.Client;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the class which makes the connection to the database
 * @author Chichisan Stefan
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/orderdb";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Creates a database connection factory.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a connection to the database.
     * @return An object of type Connection, representing the result of the connection to the database.
     */
    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Returns the connection previously created.
     * @return An object of type Connection, representing the result of the connection to the database.
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the current connection to the database.
     * @param connection An object of type Connection to be closed.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    /**
     * Closes the current statement.
     * @param statement An object of type Statement to be closed.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    /**
     * Closes the current result set.
     * @param resultSet An object of type ResultSet to be closed.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
            }
        }
    }
}
