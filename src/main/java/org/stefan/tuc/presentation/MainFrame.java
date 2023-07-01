package org.stefan.tuc.presentation;

import org.stefan.tuc.model.Client;

import javax.swing.*;
import java.awt.*;

/**
 The MainFrame class represents the main frame of the application, which displays the main menu options for managing
 clients, products, and orders. It utilizes the Swing framework to create the graphical user interface.
 @author Chichisan Stefan
 */
public class MainFrame {

    private Client client;
    private static JPanel mainPanel;
    private static JFrame mainFrame;
    private static JLabel actions;
    private static JButton clients, products, orders;

    /**
     Constructs a new instance of the MainFrame class.
     Initializes the main frame and sets up the main menu options.
     */
    public MainFrame() {
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame.setSize(290, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainPanel.setLayout(null);
        mainFrame.setTitle("Orders Management");

        actions = new JLabel("Actions:");
        actions.setFont(new Font("SansSerif", Font.BOLD, 13));
        actions.setBounds(10, 20, 200, 25);
        mainPanel.add(actions);

        clients = new JButton("Clients");
        clients.setFont(new Font("SansSerif", Font.BOLD, 13));
        clients.setBounds(10, 80, 250, 30);
        clients.addActionListener(e -> {
            new ClientFrame();
            mainFrame.setVisible(false);
        });
        mainPanel.add(clients);

        products = new JButton("Products");
        products.setFont(new Font("SansSerif", Font.BOLD, 13));
        products.setBounds(10, 200, 250, 30);
        products.addActionListener(e -> {
            new ProductFrame();
            mainFrame.setVisible(false);
        });
        mainPanel.add(products);

        orders = new JButton("Orders");
        orders.setFont(new Font("SansSerif", Font.BOLD, 13));
        orders.setBounds(10, 140, 250, 30);
        orders.addActionListener(e -> {
            new OrderFrame();
            mainFrame.setVisible(false);
        });
        mainPanel.add(orders);

        mainFrame.setVisible(true);
    }
}
