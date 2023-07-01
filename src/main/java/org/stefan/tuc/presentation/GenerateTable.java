package org.stefan.tuc.presentation;

import org.stefan.tuc.dao.AbstractDAO;
import org.stefan.tuc.model.ParentModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 The GenerateTable class provides methods to generate a table based on a list of objects and a DAO (Data Access Object).
 It utilizes the Swing framework to display the generated table in a JFrame.
 @author Chichisan Stefan
 */
public class GenerateTable {

    /**
     Generates a table based on the provided list of objects and DAO, and displays it in a JFrame.
     @param objects The list of objects to be displayed in the table.
     @param dao The DAO (Data Access Object) used to interact with the data source.
     @param <DaoType> The type of the DAO, extending AbstractDAO.
     @param <ObjectType> The type of the objects, extending ParentModel.
     */
    public <DaoType extends AbstractDAO, ObjectType extends ParentModel> void generateTable(List<ObjectType> objects, DaoType dao) {
        JTable table = getTable(objects, dao);
        JFrame frame = new JFrame("Generated Table");
        JPanel jPanel = new JPanel();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        AtomicReference<JScrollPane> jScrollPane = new AtomicReference<>(new JScrollPane(table));
        jPanel.add(jScrollPane.get());
        JButton deleteButton = new JButton("Delete.");
        deleteButton.addActionListener(e -> {
            int line = table.getSelectedRow();
            ObjectType selectedObject = objects.get(line);
            dao.deleteByName(selectedObject.name1);
            objects.remove(selectedObject);
            JTable newTable = getTable(objects, dao);
            jPanel.remove(jScrollPane.get());
            jScrollPane.set(new JScrollPane(newTable));
            jPanel.add(jScrollPane.get());
        });
        jPanel.add(deleteButton);
        frame.setContentPane(jPanel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     Generates a JTable based on the provided list of objects and DAO.
     @param objects The list of objects to be displayed in the table.
     @param dao The DAO (Data Access Object) used to interact with the data source.
     @param <DaoType> The type of the DAO, extending AbstractDAO.
     @param <ObjectType> The type of the objects, extending ParentModel.
     @return The generated JTable.
     */
    public <DaoType extends AbstractDAO, ObjectType extends ParentModel> JTable getTable(List<ObjectType> objects, DaoType dao) {
        Class<?> objectClass = objects.get(0).getClass();
        Field[] fields = objectClass.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }

        Object[][] data = new Object[objects.size()][fields.length];
        for (int i = 0; i < objects.size(); i++) {
            Object obj = objects.get(i);
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                try {
                    data[i][j] = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        return new JTable(model);
    }
}
