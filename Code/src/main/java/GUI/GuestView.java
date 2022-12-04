package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuestView extends JFrame implements ActionListener {
    private OrganizerAgent organizerAgent;
    JSONArray users;

    private JButton button2;

    public GuestView(OrganizerAgent organizerAgent) {
        super("List of Providers");
        this.organizerAgent = organizerAgent;
        DefaultTableModel dm = new DefaultTableModel();
        users = this.organizerAgent.getAllUsers("Provider");
        setTableModel(dm, users);
        JTable table = new JTable(dm);
        JScrollPane scroll = new JScrollPane(table);
        setSize(1000, 500);
        setVisible(true);


        button2 = new JButton("Go Back");
        button2.addActionListener(this);
        button2.setBounds(800, 430, 120, 30);


        getContentPane().add(button2);


        getContentPane().add(scroll);
    }

    private void setTableModel(DefaultTableModel dm, JSONArray users) {
        int rows = users.length();
        int cols = 8;
        String[][] data = new String[rows][cols];
        int i = 0;
        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            data[i][0] = user.getString("username");
            data[i][1] = user.getString("firstName");
            data[i][2] = user.getString("lastName");
            data[i][3] = String.valueOf(user.getDouble("hourlyRate"));
            data[i][4] = user.getString("paymentEmail");
            data[i][5] = user.getString("portfolioLink");
            data[i][6] = user.getBoolean("isVerified") ? "Verified" : "Non-Verified";
            i++;
        }
        dm.setDataVector(data, new Object[]{"Username", "First Name", "Last Name", "Hourly Rate", "Email", "Portfolio", "Verified Status"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button2) {

            dispose();
            new LandingPage(this.organizerAgent);
        } else {
            dispose();
            new AllProjectsPage(this.organizerAgent);
        }
    }
}

