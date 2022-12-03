package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllProjectsPage extends JFrame {
    JSONArray projects;

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
    }

    private void setTableModel(DefaultTableModel dm, JSONArray projects) {
        int rows =projects.length();
        int cols = 8;
        String [][] data = new String[rows][cols];
        int i = 0;
        for (Object p : projects) {
            JSONObject project = (JSONObject) p;
            data[i][0] = project.getString("providerId");
            data[i][1] = project.getString("clientId");
            data[i][2] = String.valueOf(project.getDouble("hourlyRate"));
            data[i][3] = project.getString("deadline");
            data[i][4] = String.valueOf(project.getDouble("progressPercentage"));
            data[i][5] = project.getString("projectName");
            data[i][6] = project.getString("projectDescription");
            data[i][7] = project.getString("projectStatus");
            i++;
        }
        dm.setDataVector(data, new Object[]{"Provider Id", "Client Id", "Rate/hr", "Deadline", "Progress %", "Description", "Status"});
    }
}
