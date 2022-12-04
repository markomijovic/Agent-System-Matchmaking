package main.java.GUI;

//import GUI.ClientPage;
//import GUI.ProviderPage;
import main.java.Agents.OrganizerAgent;
import main.java.DB.User;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    JLabel l2, l3;   //label for email and password
    JTextField tf1; // email field
    JButton btn1; // login button
    JPasswordField p1; // password field
    private OrganizerAgent organizerAgent;

    public Login(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setTitle("Login Form in Windows Form");
        setVisible(true);
        setSize(700, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        l2 = new JLabel("Enter Username:");
        l3 = new JLabel("Enter Password:");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Login");
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(200, 160, 100, 30);
        add(l2);
        add(tf1);
        add(l3);
        add(p1);
        add(btn1);
        btn1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = tf1.getText();
        String password = String.valueOf(p1.getPassword());
        if (isValidInput(username, password)) {
            JSONObject userObject = organizerAgent.loginUser(username, password);
            if (userObject != null) {
                dispose();
                User.setCurrentUser(username); // Singleton pattern to set the current user
                if (userObject.getString("userType").equals("Client")) {
                    new ClientPage(organizerAgent);
                } else {
                    new ProviderPage(organizerAgent);
                }
            }
        }
    }

    private boolean isValidInput(String username, String password) {
        return (username != null && username != "" && password != null && password != "");
    }
}