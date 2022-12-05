package main.java.GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProgressChanger
{
    private Integer PID;

    private Double Progress;
    public ProgressChanger(Integer id, Double progressP)
    {   this.PID = id;

        this.Progress = progressP;
        try
        {
            // create a java mysql database connection

            String myUrl = "jdbc:mysql://localhost:3306/matchmaking";

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // create the java mysql update preparedstatement
            String query = "update project  set progressPercentage = ? where projectId = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDouble(1, Progress);
            preparedStmt.setInt(2, PID);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}