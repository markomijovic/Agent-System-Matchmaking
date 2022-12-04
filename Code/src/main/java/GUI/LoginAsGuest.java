package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import main.java.DB.User;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.event.*;

public class LoginAsGuest extends JFrame implements ActionListener {
    //JLabel l2, l3;   //label for email and password
    //JTextField tf1; // email field
    JButton btn1; // login button
    //JPasswordField p1; // password field
    private OrganizerAgent organizerAgent;

    public LoginAsGuest(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setTitle("Login As A Guest");
        setVisible(true);
        setSize(350, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //l2 = new JLabel("Enter Username:");
       // l3 = new JLabel("Enter Password:");
        //tf1 = new JTextField();
       // p1 = new JPasswordField();
        btn1 = new JButton("Confirm");
        //l2.setBounds(80, 70, 200, 30);
       // l3.setBounds(80, 110, 200, 30);
        //tf1.setBounds(300, 70, 200, 30);
        //p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(120, 85, 100, 30);
        //add(l2);
        //add(tf1);
        //add(l3);
        //add(p1);
        add(btn1);
        btn1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = "Guest";
        String password = "12345";
        if (isValidInput(username, password)) {
            JSONObject userObject = organizerAgent.loginUser(username, password);
            if (userObject != null) {
                dispose();
                User.setCurrentUser(username); // Singleton pattern to set the current user
                if (userObject.getString("userType").equals("Guest")) {
                    new GuestView(organizerAgent);
                } else {
                    new AllProjectsPage(organizerAgent);
                }
            }
        }
    }

    private boolean isValidInput(String username, String password) {
        return (username != null && username != "" && password != null && password != "");
    }
}
