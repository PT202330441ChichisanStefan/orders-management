package org.stefan.tuc.presentation;

import org.stefan.tuc.bll.ClientBLL;
import org.stefan.tuc.bll.ProductBLL;
import org.stefan.tuc.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 The ProductFrame class represents the frame for managing products in the application.
 It provides functionality for displaying a list of products, adding or updating products, and navigating back to the main menu.
 It utilizes the Swing framework to create the graphical user interface.
 @author Chichisan Stefan
 */
public class ProductFrame implements ActionListener {

    private static Client clientCopy;
    private static JPanel mainPanel;
    private static JFrame mainFrame;
    private static JLabel actions, nameLabel, priceLabel, quantityLabel;
    private static JTextField textFieldName, textFieldPrice, textFieldQuantity;
    private static JButton productsList, product, back;

    /**
     Constructs a new instance of the ProductFrame class.
     Initializes the frame and sets up the UI components for managing products.
     */
    public ProductFrame() {
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

        nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLabel.setBounds(120, 250, 100, 30);
        mainPanel.add(nameLabel);
        textFieldName = new JTextField("");
        textFieldName.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldName.setBounds(10, 250, 100, 30);
        mainPanel.add(textFieldName);

        priceLabel = new JLabel("Price");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        priceLabel.setBounds(320, 250, 100, 30);
        mainPanel.add(priceLabel);
        textFieldPrice = new JTextField("");
        textFieldPrice.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldPrice.setBounds(210, 250, 100, 30);
        mainPanel.add(textFieldPrice);

        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        quantityLabel.setBounds(520, 250, 100, 30);
        mainPanel.add(quantityLabel);
        textFieldQuantity = new JTextField("");
        textFieldQuantity.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldQuantity.setBounds(410, 250, 100, 30);
        mainPanel.add(textFieldQuantity);

        productsList = new JButton("Show all products");
        productsList.setFont(new Font("SansSerif", Font.BOLD, 13));
        productsList.setBounds(10, 80, 250, 30);
        productsList.addActionListener(this);
        mainPanel.add(productsList);

        product = new JButton("Add/Update products");
        product.setFont(new Font("SansSerif", Font.BOLD, 13));
        product.setBounds(10, 140, 250, 30);
        product.addActionListener(this);
        mainPanel.add(product);

        back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 13));
        back.setBounds(10, 200, 250, 30);
        back.addActionListener(this);
        mainPanel.add(back);

        mainFrame.setVisible(true);
    }

    /**
     Handles the actions performed by the UI components.
     @param e The ActionEvent representing the action performed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == productsList) {
            ProductBLL.generateProductTable();
        }

        if (e.getSource() == back) {
            MainFrame mainFrame1 = new MainFrame();
            mainFrame.setVisible(false);
        }

        if (e.getSource() == product) {
            String name = textFieldName.getText();
            String price = textFieldPrice.getText();
            String quantity = textFieldQuantity.getText();

            if (!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                new ProductBLL().insertProduct(name, price, quantity);
                textFieldName.setText("");;
                textFieldPrice.setText("");
                textFieldQuantity.setText("");
            }
        }
    }
}
