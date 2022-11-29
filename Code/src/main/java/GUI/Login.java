package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3;   //label for email and password
    JTextField tf1; // email field
    JButton btn1; // login button
    JPasswordField p1; // password field
    private OrganizerAgent organizerAgent;

    public Login(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setTitle("Login Form in Windows Form");
        setVisible(true);
        setSize(800, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        l1 = new JLabel("Login Form in Windows Form:");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l2 = new JLabel("Enter Email:");
        l3 = new JLabel("Enter Password:");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Submit");
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);
        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(p1);
        add(btn1);
        btn1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        organizerAgent.loginUser("ClientAdmin", "pass123");
    }

    public void showData() {
        JFrame f1 = new JFrame();
        JLabel l, l0;
        String str1 = tf1.getText();
        char[] p = p1.getPassword();
        String str2 = new String(p);
        try {
//            createFolder();
//            readFile();
//            countLines();
//            logic(str1, str2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}