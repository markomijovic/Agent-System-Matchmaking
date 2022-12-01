package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp extends JFrame implements ActionListener   {
    JLabel uNameLabel, fNameLabel, lNameLabel, passwordLabel, confirmPLabel, emailLabel, portfolioLabel, typeLabel, rateLabel, verifyLabel;  //all labels for textField
    JTextField userNameField, fNameField, lNameField, userTypeField, emailField, rateField, verifyField, linkField;
    JButton btn1, btn2;  //buttons for signup and clear
    JPasswordField p1, p2;  // password fields
    private OrganizerAgent organizerAgent;

    public SignUp(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(700, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registration Form in Java");
        uNameLabel = new JLabel("Username");
        fNameLabel = new JLabel("First Name:");
        lNameLabel = new JLabel("Last Name:");
        passwordLabel = new JLabel("Create Password:");
        confirmPLabel = new JLabel("Confirm Password:");
        emailLabel = new JLabel("Payment Email:");
        portfolioLabel = new JLabel("Portfolio:");
        typeLabel = new JLabel("Client/Provider:");
        rateLabel = new JLabel("Hourly Rate:");
        verifyLabel = new JLabel("Verify (yes/no):");
        userNameField = new JTextField();
        fNameField = new JTextField();
        lNameField = new JTextField();
        p1 = new JPasswordField();
        p2 = new JPasswordField();
        emailField = new JTextField();
        linkField = new JTextField();
        userTypeField = new JTextField();
        rateField = new JTextField();
        verifyField = new JTextField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        uNameLabel.setBounds(80, 30, 200, 30);
        fNameLabel.setBounds(80, 70, 200, 30);
        lNameLabel.setBounds(80, 110, 200, 30);
        passwordLabel.setBounds(80, 150, 200, 30);
        confirmPLabel.setBounds(80, 190, 200, 30);
        emailLabel.setBounds(80, 230, 200, 30);
        portfolioLabel.setBounds(80, 270, 200, 30);
        typeLabel.setBounds(80, 310, 200, 30);
        rateLabel.setBounds(80, 350, 200, 30);
        verifyLabel.setBounds(80, 390, 200, 30);

        userNameField.setBounds(300, 30, 200, 30);
        fNameField.setBounds(300, 70, 200, 30);
        lNameField.setBounds(300, 110, 200 ,30);
        p1.setBounds(300, 150, 150, 30);
        p2.setBounds(300, 190, 190, 30);
        emailField.setBounds(300, 230, 200, 30);
        linkField.setBounds(300, 270, 200, 30);
        userTypeField.setBounds(300, 310, 200, 30);
        rateField.setBounds(300, 350, 200, 30);
        verifyField.setBounds(300, 390, 200, 30);
        btn1.setBounds(50, 440, 100, 30);
        btn2.setBounds(170, 440, 100, 30);
        add(uNameLabel);
        add(fNameLabel);
        add(lNameLabel);
        add(passwordLabel);
        add(confirmPLabel);
        add(emailLabel);
        add(portfolioLabel);
        add(typeLabel);
        add(verifyLabel);
        add(rateLabel);
        add(userNameField);
        add(fNameField);
        add(lNameField);
        add(p1);
        add(p2);
        add(emailField);
        add(linkField);
        add(userTypeField);
        add(rateField);
        add(verifyField);
        add(btn1);
        add(btn2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            String username = userNameField.getText();
            String fName = fNameField.getText();
            String lName = lNameField.getText();
            String pass1 = String.valueOf(p1.getPassword());
            String pass2 = String.valueOf(p2.getPassword());
            String email = emailField.getText();
            String link = linkField.getText();
            String type = userTypeField.getText();
            boolean isVerified = verifyField.getText().equalsIgnoreCase("yes");
            double rate = Double.parseDouble(rateField.getText());
            if (pass1.equals(pass2)) {
                try {
                    String res = this.organizerAgent.registerUser(username, fName, lName, pass1, type,
                    email, link, rate, isVerified);
                    if (res != "") {
                        JOptionPane.showMessageDialog(btn1, "Data Saved Successfully");
                        dispose();
                        new ProfilePage(this.organizerAgent);
                    } else {
                        JOptionPane.showMessageDialog(btn1, "Something went wrong. Could not register.");
                    }
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(btn1, "Password Does Not Match");
            }
        }
        else {
            userNameField.setText("");
            fNameField.setText("");
            lNameField.setText("");
            p1.setText("");
            p2.setText("");
            emailField.setText("");
            linkField.setText("");
            rateField.setText("");
            verifyField.setText("");
            userTypeField.setText("");
        }
    }
}
