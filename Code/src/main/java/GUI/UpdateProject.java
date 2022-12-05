
package main.java.GUI;
import java.sql.*;

public class UpdateProject
{
    private Integer PID;
    public UpdateProject(Integer id)
    { this.PID = id;
        try
        {
            // create a java mysql database connection

            String myUrl = "jdbc:mysql://localhost:3306/matchmaking";

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // create the java mysql update preparedstatement
            String query = "delete from project  where projectId = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //preparedStmt.setString   (1, "Sample");
            preparedStmt.setInt(1, PID);

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