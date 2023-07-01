package org.stefan.tuc.presentation;

import org.stefan.tuc.bll.ClientBLL;
import org.stefan.tuc.dao.ClientDAO;
import org.stefan.tuc.model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 The ClientFrame class represents the frame for managing clients in the application.
 It provides functionality for displaying a list of clients, adding/updating clients, and navigating back to the main menu.
 It utilizes the Swing framework to create the graphical user interface.
 @author Chichisan Stefan
 */
public class ClientFrame implements ActionListener {

    private static JPanel mainPanel;
    private static JFrame mainFrame;
    private static JLabel actions, nameLabel, addressLabel, emailLabel;
    private static JTextField textFieldName, textFieldAddress, textFieldEmail;
    private static JButton clientsList, user, back;

    /**
     Constructs a new instance of the ClientFrame class.
     Initializes the frame and sets up the UI components for managing clients.
     */
    public ClientFrame() {
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

        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        addressLabel.setBounds(320, 250, 100, 30);
        mainPanel.add(addressLabel);
        textFieldAddress = new JTextField("");
        textFieldAddress.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldAddress.setBounds(210, 250, 100, 30);
        mainPanel.add(textFieldAddress);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        emailLabel.setBounds(520, 250, 100, 30);
        mainPanel.add(emailLabel);
        textFieldEmail = new JTextField("");
        textFieldEmail.setFont(new Font("SansSerif", Font.BOLD, 13));
        textFieldEmail.setBounds(410, 250, 100, 30);
        mainPanel.add(textFieldEmail);

        clientsList = new JButton("Show all clients");
        clientsList.setFont(new Font("SansSerif", Font.BOLD, 13));
        clientsList.setBounds(10, 80, 250, 30);
        clientsList.addActionListener(this);
        mainPanel.add(clientsList);

        user = new JButton("Add/Update client");
        user.setFont(new Font("SansSerif", Font.BOLD, 13));
        user.setBounds(10, 140, 250, 30);
        user.addActionListener(this);
        mainPanel.add(user);

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
        if (e.getSource() == clientsList) {
            ClientBLL.generateClientTable();
        }

        if (e.getSource() == back) {
            MainFrame mainFrame1 = new MainFrame();
            mainFrame.setVisible(false);
        }

        if (e.getSource() == user) {
            String name = textFieldName.getText();
            String address = textFieldAddress.getText();
            String email = textFieldEmail.getText();

            if (!name.isEmpty() && !address.isEmpty() && !email.isEmpty()) {
                new ClientBLL().insertClient(name, address, email);
                textFieldName.setText("");
                textFieldAddress.setText("");
                textFieldEmail.setText("");
            }
        }
    }
}
