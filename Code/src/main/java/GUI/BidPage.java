package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BidPage extends JFrame implements ActionListener {
    private OrganizerAgent organizerAgent;
    JSONArray OpenProjects;
    JTextField field;
    private JButton button, button2;

    public BidPage(OrganizerAgent organizerAgent) {
        super("List of Available Projects");
        this.organizerAgent = organizerAgent;
        DefaultTableModel dm = new DefaultTableModel();
        OpenProjects = organizerAgent.getOpenProjects();
        setTableModel(dm, OpenProjects);
        JTable table = new JTable(dm);
        JScrollPane scroll = new JScrollPane(table);
        setSize(1000, 500);
        setVisible(true);

        button = new JButton("Place Bid");
        button.addActionListener(this);
        button.setBounds(270, 380, 100, 30);
        button2 = new JButton("Current Project");
        button2.addActionListener(this);
        button2.setBounds(400, 380, 150, 30);
        JLabel label = new JLabel("Enter Project ID:");
        label.setBounds(20, 380, 100, 30);
        field = new JTextField();
        field.setBounds(150, 380, 100 ,30);
        getContentPane().add(button);
        getContentPane().add(button2);
        getContentPane().add(label);
        getContentPane().add(field);
        getContentPane().add(scroll);
    }

    private void setTableModel(DefaultTableModel dm, JSONArray OpenProjects) {
        int rows =OpenProjects.length();
        int cols = 8;
        String [][] data = new String[rows][cols];
        int i = 0;
        for (Object p : OpenProjects) {
            JSONObject project = (JSONObject) p;
            data[i][0] = String.valueOf(project.getInt("projectId"));
            data[i][1] = project.getString("providerId");
            data[i][2] = project.getString("clientId");
            data[i][3] = String.valueOf(project.getDouble("hourlyRate"));
            data[i][4] = project.getString("deadline");
            data[i][5] = String.valueOf(project.getDouble("progressPercentage"));
            data[i][6] = project.getString("projectName");
            data[i][7] = project.getString("projectDescription");

            i++;
        }
        dm.setDataVector(data, new Object[]{"Project ID", "STATUS", "Client Id", "Rate/hr", "Deadline", "Progress %", "Project Name", "Description" });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ProjectId = field.getText();
        if (e.getSource() == button) {
            if (ProjectId != null && ProjectId != "") {
                //dispose();
                //new CreteProjectPage(this.organizerAgent, ProjectId);  //need to change
                new ProjectCreate(this.organizerAgent);
            }
        } else {
            dispose();
            new AllProjectsPage(this.organizerAgent);
        }
    }
}