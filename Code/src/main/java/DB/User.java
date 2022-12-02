package main.java.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class User {
    public String username;
    private final static DBLoader db = DBLoader.getInstance();
    private static User instance;

    private User(String username) {
        this.username = username;
    }

    public static User getCurrentUser(String username) {
        if (instance == null) {
            instance = new User(username);
        }
        return instance;
    }

    public static JSONArray getAllUsers(String type) {
        // Works - Tested
        JSONArray users = new JSONArray();
        try {
            String query = "SELECT * FROM user WHERE userType=? order by isVerified desc";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, type);
            ResultSet res = statement.executeQuery();
            if (res != null) {
                while (res.next()) {
                    JSONObject user = sqlSchemaToJSON(res);
                    users.put(user);
                }
            }
        } catch(Exception e) {
            System.out.println("In getAllUsers exception");
            System.out.println(e);
        }
        return users;
    }

    public static String registerClient(JSONObject req) {
        // Works - Tested
        String username = req.getString("username");
        String password = req.getString("password");
        String firstName = req.getString("firstName");
        String lastName = req.getString("lastName");
        String userType = "Client";
        double hourlyRate = req.getDouble("hourlyRate");
        String portfolio = req.getString("portfolio");
        boolean isVerified = req.getBoolean("isVerified");
        String paymentEmail = req.getString("paymentEmail");
        try {
            String query = "INSERT INTO user (username, password, firstname, " +
                    "lastname, userType, isVerified, paymentEmail, hourlyRate, portfolioLink) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, userType);
            statement.setBoolean(6, isVerified);
            statement.setString(7, paymentEmail);
            statement.setDouble(8, hourlyRate);
            statement.setString(9, portfolio);
            statement.execute();
        } catch(Exception e) {
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static String registerProvider(JSONObject req) {
        // Works - Tested
        String username = req.getString("username");
        String password = req.getString("password");
        String firstName = req.getString("firstName");
        String lastName = req.getString("lastName");
        String userType = "Provider";
        double hourlyRate = req.getDouble("hourlyRate");
        String portfolio = req.getString("portfolio");
        boolean isVerified = req.getBoolean("isVerified");
        String paymentEmail = req.getString("paymentEmail");
        try {
            String query = "INSERT INTO user (username, password, firstname, " +
                    "lastname, userType, isVerified, paymentEmail, hourlyRate, portfolioLink) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, userType);
            statement.setBoolean(6, isVerified);
            statement.setString(7, paymentEmail);
            statement.setDouble(8, hourlyRate);
            statement.setString(9, portfolio);
            statement.execute();
        } catch(Exception e) {
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static JSONObject loginUser(JSONObject req) {
        // Works - Tested
        JSONObject user = null;
        try {
            String username = req.getString("username");
            String password = req.getString("password");
            String query = "SELECT * FROM user WHERE username=? and password=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                user = sqlSchemaToJSON(resultSet);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public static JSONObject sqlSchemaToJSON(ResultSet res) throws  SQLException {
        String username = res.getString("username");
        String password = res.getString("password");
        String firstName = res.getString("firstName");
        String lastName = res.getString("lastName");
        String userType = res.getString("userType");
        double rate = res.getDouble("hourlyRate");
        boolean verified = res.getBoolean("isVerified");
        String paymentEmail = res.getString("paymentEmail");
        String portfolioLink = res.getString("portfolioLink");
        JSONObject user = new JSONObject();
        user.put("username", username);
        user.put("password", password);
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("userType", userType);
        user.put("hourlyRate", rate);
        user.put("isVerified", verified);
        user.put("paymentEmail", paymentEmail);
        user.put("portfolioLink", portfolioLink);
        return user;
    }
}
