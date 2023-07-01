package org.stefan.tuc.dao;

import org.stefan.tuc.connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the "abstract" class which performs the CRUD (Create, Read, Update and Delete) operations on the tables found in the database.
 * @param <T> A generic class called Type, denoted by T.
 */
public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    protected final Class<T> type;

    /**
     * Creates an AbstractDAO constructor.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates the specific query to search by a field in a database.
     * @param field The database field from a table.
     * @return The specific query generated.
     */
    private String createSelectQuery(String field) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT * FROM ").append(type.getSimpleName().toLowerCase() + "s");
        stringBuilder.append(" WHERE ").append(field).append(" = ?");
        return stringBuilder.toString();
    }

    /**
     * Creates the specific query to insert into the database.
     * @param t An object of Type of the generic class.
     * @return The specific query generated.
     */
    protected String createInsertQuery(T t) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("INSERT INTO ")
                .append(type.getSimpleName().toLowerCase() + "s")
                .append(" (");

        for (Field field: type.getDeclaredFields()) {
            if (field.getName().equals("id")) {
                continue;
            }

            stringBuilder.append(field.getName()).append(", ");
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), ") VALUES (");

        for (Field field: type.getDeclaredFields()) {
            if (field.getName().equals("id")) {
                continue;
            }

            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                if (propertyDescriptor.getPropertyType().equals(String.class)) {
                    stringBuilder.append("'")
                            .append(propertyDescriptor.getReadMethod().invoke(t))
                            .append("'");
                } else {
                    stringBuilder.append(propertyDescriptor.getReadMethod().invoke(t));
                }
                stringBuilder.append(", ");
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length() - 1, ")");

        return stringBuilder.toString();
    }

    /**
     * Creates the specific query to update into the database.
     * @param t An object of Type of the generic class.
     * @return The specific query generated.
     */
    protected String createUpdateQuery(T t) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("UPDATE ")
                .append(type.getSimpleName().toLowerCase() + "s")
                .append(" SET ");

        for (Field field: type.getDeclaredFields()) {
            if (field.getName().equals("id")) {
                continue;
            }

            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                stringBuilder.append(field.getName())
                        .append(" = ");
                if (propertyDescriptor.getPropertyType().equals(String.class)) {
                    stringBuilder.append("'")
                            .append(propertyDescriptor.getReadMethod().invoke(t))
                            .append("'");
                } else {
                    stringBuilder.append(propertyDescriptor.getReadMethod().invoke(t));
                }
                stringBuilder.append(", ");
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        try {
            final Field field = type.getDeclaredField("id");
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);

            stringBuilder.append(" WHERE id = ")
                    .append(propertyDescriptor.getReadMethod().invoke(t));

        } catch (NoSuchFieldException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    /**
     * The method which search in the entire table.
     * @return An object set containing the data.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + "s";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return Collections.emptyList();
    }

    /**
     * The method which searches in tables by id.
     * @param id The id field.
     * @return An object set containing the data.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * The method which inserts a new field into the database.
     * @param t An object of Type of the generic class.
     * @return The new object set inserted in the database. If it is already inserted then it returns the user already found.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        return findById(id);
                    }
                }
            }

            return null;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    /**
     * The method which updates the contents of the database.
     * @param t An object of Type of the generic class.
     * @return An updated object set containing the updated data.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return t;
    }

    public boolean deleteByName(String name) {
        return true;
    }

    /**
     * The method accessing the data of the database using reflection technique.
     * @param resultSet A table of data representing a database result set.
     * @return The list of the created objects.
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
}
