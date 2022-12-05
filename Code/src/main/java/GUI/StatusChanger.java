package main.java.GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StatusChanger
{

    private Double Progress;
    public StatusChanger(Double progressP)
    {
        this.Progress = progressP;

        try
        {
            // create a java mysql database connection

            String myUrl = "jdbc:mysql://localhost:3306/matchmaking";

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // create the java mysql update preparedstatement
            String query = "update project  set projectStatus =  ? where progresspercentage= ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, "Complete. Payment Due");
            preparedStmt.setDouble(2, Progress);

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