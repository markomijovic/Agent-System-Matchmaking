package main.java.GUI;

import main.java.Agents.OrganizerAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LandingPage extends JFrame implements ActionListener {
    private JButton loginButton, signUpButton;
    private OrganizerAgent organizerAgent;

    public LandingPage(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(350, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Welcome");
        loginButton = new JButton("Login");
        signUpButton = new JButton("Register");
        loginButton.addActionListener(this);
        signUpButton.addActionListener(this);
        loginButton.setBounds(50, 50, 100, 30);
        signUpButton.setBounds(200, 50, 100, 30);
        add(loginButton);
        add(signUpButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        if (e.getSource() == loginButton) {
            new Login();
        } else {
            new SignUp();
        }
    }
}
