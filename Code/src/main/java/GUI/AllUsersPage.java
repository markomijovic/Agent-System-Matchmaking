package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllUsersPage extends JFrame implements ActionListener {
    private OrganizerAgent organizerAgent;
    JSONArray users;
    JTextField field;

    public AllUsersPage(OrganizerAgent organizerAgent) {
        super("List of Providers");
        this.organizerAgent = organizerAgent;
        DefaultTableModel dm = new DefaultTableModel();
        users = this.organizerAgent.getAllUsers("Provider");
        setTableModel(dm, users);
        JTable table = new JTable(dm);
        JScrollPane scroll = new JScrollPane(table);
        setSize(1000, 500);
        setVisible(true);

        JButton button = new JButton("Request");
        button.addActionListener(this);
        button.setBounds(20, 430, 100, 30);
        JLabel label = new JLabel("Enter Username:");
        label.setBounds(20, 380, 100, 30);
        field = new JTextField();
        field.setBounds(130, 380, 200 ,30);
        getContentPane().add(button);
        getContentPane().add(label);
        getContentPane().add(field);
        getContentPane().add(scroll);
    }

    private void setTableModel(DefaultTableModel dm, JSONArray users) {
        int rows = users.length();
        int cols = 8;
        String [][] data = new String[rows][cols];
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
        String username = field.getText();
        if (username != null && username != "") {
            dispose();
            new CreateProjectPage(this.organizerAgent, username);
        }
    }
}
