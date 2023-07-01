package org.stefan.tuc.presentation;

import org.stefan.tuc.bll.OrderBLL;
import org.stefan.tuc.model.Client;
import org.stefan.tuc.model.Order;
import org.stefan.tuc.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 The OrderFrame class represents the frame for managing orders in the application.
 It provides functionality for displaying a list of orders, adding new orders, and navigating back to the main menu.
 It utilizes the Swing framework to create the graphical user interface.
 @author Chichisan Stefan
 */
public class OrderFrame implements ActionListener {

    private final OrderBLL orderBLL;
    private static JPanel mainPanel;
    private static JFrame mainFrame;
    private static JLabel actions, quantityLabel;
    private static JComboBox<String> jComboBoxClients;
    private static JComboBox<String> jComboBoxProducts;
    private static JTextField textFieldQuantity;
    private static JButton ordersList, order, back;

    /**
     Constructs a new instance of the OrderFrame class.
     Initializes the frame and sets up the UI components for managing orders.
     */
    public OrderFrame() {
        orderBLL = new OrderBLL();
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame.setSize(600, 340);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainPanel.setLayout(null);
        mainFrame.setTitle("Orders Management");

        actions = new JLabel("Actions:");
        actions.setFont(new Font("SansSerif", Font.BOLD, 13));
        actions.setBounds(10, 20, 200, 25);
        mainPanel.add(actions);

        jComboBoxClients = new JComboBox<>();
        jComboBoxClients.setFont(new Font("SansSerif", Font.BOLD, 13));
        jComboBoxClients.setBounds(50, 50, 100, 20);
        readAllClients().forEach(i -> jComboBoxClients.addItem(i.getName()));
        mainPanel.add(jComboBoxClients);

        jComboBoxProducts = new JComboBox<>();
        jComboBoxProducts.setFont(new Font("SansSerif", Font.BOLD, 13));
        jComboBoxProducts.setBounds(200, 50, 100, 20);
        readAllProducts().forEach(i -> jComboBoxProducts.addItem(i.getName()));
        mainPanel.add(jComboBoxProducts);

        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        quantityLabel.setBounds(460, 45, 100, 30);
        mainPanel.add(quantityLabel);
        textFieldQuantity = new JTextField("");
        textFieldQuantity.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldQuantity.setBounds(350, 50, 100, 20);
        mainPanel.add(textFieldQuantity);

        ordersList = new JButton("Show all orders");
        ordersList.setFont(new Font("SansSerif", Font.BOLD, 13));
        ordersList.setBounds(10, 80, 250, 30);
        ordersList.addActionListener(this);
        mainPanel.add(ordersList);

        order = new JButton("Add order");
        order.setFont(new Font("SansSerif", Font.BOLD, 13));
        order.setBounds(10, 140, 250, 30);
        order.addActionListener(this);
        mainPanel.add(order);

        back = new JButton("Back");
        back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 13));
        back.setBounds(10, 200, 250, 30);
        back.addActionListener(this);
        mainPanel.add(back);

        mainFrame.setVisible(true);

    }

    /**
     Reads all clients from the database.
     @return A list of Client objects representing all clients in the database
     */
    public List<Client> readAllClients() {
        return orderBLL.getClientDAO().findAll();
    }

    /**
     Reads all products from the database.
     @return A list of Product objects representing all products in the database
     */
    public List<Product> readAllProducts() {
        return orderBLL.getProductDAO().findAll();
    }

    /**
     Handles the actions performed by the UI components.
     @param e The ActionEvent representing the action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ordersList) {
            OrderBLL.generateOrderTable();
        }
        if (e.getSource() == order) {
            String client = (String) jComboBoxClients.getSelectedItem();
            String product = (String) jComboBoxProducts.getSelectedItem();
            String quantity = textFieldQuantity.getText();

            if (!quantity.isEmpty()) {
                Order result = orderBLL.insert(client, product, quantity);
                if (result == null) {
                    JOptionPane.showMessageDialog(null, "Quantity exceeded!");
                }
                textFieldQuantity.setText("");
            }
        }

        if (e.getSource() == back) {
            MainFrame mainFrame1 = new MainFrame();
            mainFrame.setVisible(false);
        }
    }
}
