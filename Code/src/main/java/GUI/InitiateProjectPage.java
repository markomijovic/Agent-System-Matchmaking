package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import main.java.DB.User;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitiateProjectPage extends JFrame implements ActionListener {
    private JLabel nameLabel, descLabel, statusLabel, progressLabel, rateLabel, deadlineLabel;
    private JTextField nameField, descField, statusField, progressField, rateField, deadlineField;
    private JButton btn1, btn2; // submit and clear
    private OrganizerAgent organizerAgent;


    public InitiateProjectPage(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Create project for bid");
        nameLabel = new JLabel("Project Name");
        descLabel = new JLabel("Project Description");
        statusLabel = new JLabel("Project Status");
        progressLabel = new JLabel("Progress");
        deadlineLabel = new JLabel("Deadline Date");
        rateLabel = new JLabel("Expected Rate ");
        nameField = new JTextField();
        descField = new JTextField();
        statusField = new JTextField();
        progressField = new JTextField();
        deadlineField = new JTextField();
        rateField = new JTextField();
        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        nameLabel.setBounds(80, 30, 200, 30);
        descLabel.setBounds(80, 70, 200, 30);
        statusLabel.setBounds(80, 110, 200 ,30);
        progressLabel.setBounds(80, 150, 200, 30);
        deadlineLabel.setBounds(80, 190, 200, 30);
        rateLabel.setBounds(80, 230, 200, 30);

        nameField.setBounds(300, 30, 200, 30);
        descField.setBounds(300, 70, 200, 30);
        statusField.setBounds(300, 110, 200, 30);
        progressField.setBounds(300, 150, 200, 30);
        deadlineField.setBounds(300, 190, 200, 30);
        rateField.setBounds(300, 230, 200, 30);
        btn1.setBounds(50, 440, 100, 30);
        btn2.setBounds(170, 440, 100, 30);

        add(nameLabel);
        add(descLabel);
        add(statusLabel);
        add(progressLabel);
        add(deadlineLabel);
        add(rateLabel);
        add(nameField);
        add(descField);
        add(rateField);
        add(statusField);
        add(progressField);
        add(deadlineField);
        add(btn1);
        add(btn2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {

            String clientUsername = User.getCurrentUser().username;
            String providername= "OpenProject";
            double rate = Double.valueOf(rateField.getText());
            String name = nameField.getText();
            String desc = descField.getText();
            double progress = Double.valueOf(progressField.getText());
            String status = statusField.getText();
            String deadline = deadlineField.getText();
            JSONObject request = new JSONObject();
            request.put("providerId",providername);
            request.put("clientId", clientUsername);
            request.put("hourlyRate", rate);
            request.put("deadline", deadline);
            request.put("progressPercentage", progress);
            request.put("projectName", name);
            request.put("projectDescription", desc);
            request.put("projectStatus", status);
            String res = this.organizerAgent.addNewProject(request);
            if (res != "" && res != null) {
                JOptionPane.showMessageDialog(btn1, "Project Initiated Successfully");
                dispose();
                new AllProjectsPage(this.organizerAgent);
            } else {
                JOptionPane.showMessageDialog(btn1, "Something went wrong. Could not initiate project.");
            }
        } else {
            nameField.setText("");
            descField.setText("");
            statusField.setText("");
            progressField.setText("");
            deadlineField.setText("");
            rateField.setText("");
        }
    }
}