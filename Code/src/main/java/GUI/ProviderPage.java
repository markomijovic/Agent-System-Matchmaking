package main.java.GUI;

import main.java.Agents.OrganizerAgent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProviderPage extends JFrame implements ActionListener {
    private JButton CurrentProject, AllProject;
    private OrganizerAgent organizerAgent;


    public ProviderPage( OrganizerAgent organizerAgent) {


       this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(370, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Welcome Provider");

        CurrentProject = new JButton("Current Project");
        AllProject = new JButton("All Projects");

        CurrentProject.addActionListener(this);
        AllProject.addActionListener(this);

        CurrentProject.setBounds(190, 50, 150, 30);
        AllProject.setBounds(20, 50, 150, 30);

        add(CurrentProject);
        add(AllProject);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        if (e.getSource() == CurrentProject) {
            new AllProjectsPage(this.organizerAgent);
        }
        else {
            new BidPage(organizerAgent);
        }
    }
}
