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

    public static Payment getPaymentWithId(int id) {
        // Tested - Works
        Payment payment = null;
        try {
            String query = "SELECT * from payment WHERE paymentId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            payment = sqlSchemaToPayment(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return payment;
    }

    public static Payment getPaymentWithProjectId(int id) {
        // Tested - Works
        Payment payment = null;
        try {
            String query = "SELECT * from payment WHERE projectId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            payment = sqlSchemaToPayment(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return payment;
    }

    public static String addNewPayment(JSONObject req) {
        // Tested - Works
        try {
            String query="INSERT into payment (paymentId, projectId, amount, paymentStatus) " +
                    "values (null, ?, ?, ?)";
            PreparedStatement statement=db.getDBConnection().prepareStatement(query);
            statement.setInt(1, req.getInt("projectId"));
            statement.setDouble(2, req.getDouble("amount"));
            statement.setString(3, req.getString("paymentStatus"));
            statement.execute();
        } catch(Exception e){
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static Payment sqlSchemaToPayment(ResultSet res) throws SQLException {
        // Tested - Works
        if (res != null) {
            res.next();
            int paymentId = res.getInt("paymentId");
            int projectId = res.getInt("projectId");
            double amount = res.getDouble("amount");
            String paymentStatus = res.getString("paymentStatus");
            return new Payment(paymentId, projectId, amount, paymentStatus);
        }
        return null;
    }

    public static void main(String[] args) {
        Payment payment1 = Payment.getPaymentWithId(1);
        System.out.println(payment1.paymentStatus);

        Payment payment2 = Payment.getPaymentWithProjectId(1);
        System.out.println(payment2.amount);

        JSONObject testObject = new JSONObject();
        testObject.put("projectId", 2);
        testObject.put("amount", 163);
        testObject.put("paymentStatus", "Processing");
        System.out.println(Payment.addNewPayment(testObject));
    }
}
