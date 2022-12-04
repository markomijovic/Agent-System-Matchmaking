package main.java.GUI;

import jade.domain.JADEAgentManagement.CreateAgent;
import main.java.Agents.OrganizerAgent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPage extends JFrame implements ActionListener {
    private JButton MyProfile, MyProject, CreateProject, ProviderList;
    private OrganizerAgent organizerAgent;


    public ClientPage( OrganizerAgent organizerAgent) {


        this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(370, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Welcome Client");


        MyProject = new JButton("Current Project");
        CreateProject = new JButton("Create Project");
        ProviderList = new JButton("Provider List");


        MyProject .addActionListener(this);
        CreateProject.addActionListener(this);
        ProviderList.addActionListener(this);


        MyProject.setBounds(190, 50, 150, 30);
        CreateProject.setBounds(20, 50, 150, 30);
        ProviderList.setBounds(110, 100, 150, 30);


        add(MyProject);
        add(CreateProject);
        add(ProviderList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        if (e.getSource() == CreateProject) {
            new InitiateProjectPage(this.organizerAgent);
        } else if (e.getSource() == MyProject) {
            new AllProjectsPage(this.organizerAgent);
        }

        else {
            new AllUsersPage(organizerAgent);
        }
    }
}
