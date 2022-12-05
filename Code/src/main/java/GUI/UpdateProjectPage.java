
package main.java.GUI;

import main.java.Agents.OrganizerAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProjectPage extends JFrame implements ActionListener{
    private JLabel PID, progressLabel,  deadlineLabel;
    private JTextField CField, PField, nameField, rateField, descField, statusField, progressField, deadlineField;
    private JButton btn1, btn2; // submit and clear
    private OrganizerAgent organizerAgent;


    public UpdateProjectPage(OrganizerAgent organizerAgent) {
        this.organizerAgent = organizerAgent;
        setVisible(true);
        setSize(700, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fill the form to update");

        PID= new JLabel("Project ID");
        progressLabel = new JLabel("Progress");
        deadlineLabel = new JLabel("Deadline Date");

        PField =new JTextField();
        progressField = new JTextField();
        deadlineField = new JTextField();


        btn1 = new JButton("Submit");
        btn2 = new JButton("Clear");
        btn1.addActionListener(this);
        btn2.addActionListener(this);

        PID.setBounds(80, 30, 200, 30);
        progressLabel.setBounds(80, 70, 200, 30);
        deadlineLabel.setBounds(80, 110, 200 ,30);

        PField.setBounds(300, 30, 200, 30);
        progressField.setBounds(300, 70, 200, 30);
        deadlineField.setBounds(300, 110, 200, 30);



        btn1.setBounds(50, 440, 100, 30);
        btn2.setBounds(170, 440, 100, 30);


        add(progressLabel);
        add(deadlineLabel);

        add(PID);


        add(progressField);
        add(deadlineField);
        add(PField);


        add(btn1);
        add(btn2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {


            double progress = Double.valueOf(progressField.getText());
            String deadline = deadlineField.getText();
            int pid = Integer.valueOf(PField.getText());

            new main.java.GUI.DeadlineChanging(pid, deadline);
            new main.java.GUI.ProgressChanger(pid,progress);
            if( progress== 100.0)
            {

                new main.java.GUI.StatusChanger(progress);
            }

        } else {
            nameField.setText("");
            descField.setText("");
            statusField.setText("");
            progressField.setText("");
            deadlineField.setText("");
            PField.setText("");
            CField.setText("");
            rateField.setText("");
        }
    }
}
