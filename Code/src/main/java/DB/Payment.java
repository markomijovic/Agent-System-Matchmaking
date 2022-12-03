package main.java.DB;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Payment {
    public int id;
    public int projectId;
    public double amount;
    public String paymentStatus;
    private final static DBLoader db = DBLoader.getInstance();

    public Payment(int id, int projectId, double amount, String paymentStatus) {
        this.id = id;
        this.projectId = projectId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public static String addNewPayment(JSONObject req) {
        // Tested - Works
        try {
            String query="INSERT into payment (paymentId, clientId, providerId, amount, paymentStatus) " +
                    "values (null, ?, ?, ?, ?)";
            PreparedStatement statement=db.getDBConnection().prepareStatement(query);
            statement.setString(1, req.getString("clientId"));
            statement.setString(2, req.getString("providerId"));
            statement.setDouble(3, req.getDouble("amount"));
            statement.setString(4, req.getString("paymentStatus"));
            statement.execute();
        } catch(Exception e){
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }
}
