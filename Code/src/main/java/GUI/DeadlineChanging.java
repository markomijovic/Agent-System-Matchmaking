
package main.java.GUI;
import java.sql.*;

public class DeadlineChanging
{
    private Integer PID;
    private String New_Deadline;
    //private Double Progress;
    public DeadlineChanging(Integer id, String Deadline)
    {   this.PID = id;
        this.New_Deadline= Deadline;
        //this.Progress = ProgressP;
        try
        {
            // create a java mysql database connection

            String myUrl = "jdbc:mysql://localhost:3306/matchmaking";

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // create the java mysql update preparedstatement
            String query = "update project  set deadline = ? where projectId = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString   (1, New_Deadline);
            //preparedStmt.setDouble(2, Progress);
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