package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllProjectsPage extends JFrame implements ActionListener {
    JSONArray projects;
    private OrganizerAgent organizerAgent;
    JTextField field;
    private JButton button;

    public AllProjectsPage(OrganizerAgent organizerAgent) {
        super("Your Projects");
        DefaultTableModel dm = new DefaultTableModel();
        projects = organizerAgent.getAllProjects();
        setTableModel(dm, projects);
        JTable table = new JTable(dm);
        JScrollPane scroll = new JScrollPane(table);
        setSize(1000, 500);
        setVisible(true);
        getContentPane().add(scroll);


        button = new JButton("Update Project");
        button.addActionListener(this);
        button.setBounds(270, 380, 150, 30);

        JLabel label = new JLabel("Enter Project ID:");
        label.setBounds(20, 380, 100, 30);
        field = new JTextField();
        field.setBounds(150, 380, 100 ,30);
        getContentPane().add(button);
        getContentPane().add(label);
        getContentPane().add(field);
        getContentPane().add(scroll);
    }

    private void setTableModel(DefaultTableModel dm, JSONArray projects) {
        int rows =projects.length();
        int cols = 9;
        String [][] data = new String[rows][cols];
        int i = 0;
        for (Object p : projects) {
            JSONObject project = (JSONObject) p;
            data[i][0] = String.valueOf(project.getInt("projectId"));
            data[i][1] = project.getString("providerId");
            data[i][2] = project.getString("clientId");
            data[i][3] = String.valueOf(project.getDouble("hourlyRate"));
            data[i][4] = project.getString("deadline");
            data[i][5] = String.valueOf(project.getDouble("progressPercentage"));
            data[i][6] = project.getString("projectName");
            data[i][7] = project.getString("projectDescription");
            data[i][8] = project.getString("projectStatus");
            i++;
        }
        dm.setDataVector(data, new Object[]{"Project ID", "Provider Id", "Client Id", "Rate/hr", "Deadline", "Progress %", "Project Name", "Description", "Status"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ProjectId = field.getText();
        if (e.getSource() == button) {
            if (ProjectId != null && ProjectId != "") {
                dispose();
                //new CreteProjectPage(this.organizerAgent, ProjectId);  //need to change
                new main.java.GUI.UpdateProjectPage(this.organizerAgent);
            }
        } else {
            dispose();
            new ClientPage(this.organizerAgent);
        }
    }
}
