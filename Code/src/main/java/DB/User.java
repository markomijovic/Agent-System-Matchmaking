package main.java.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String userType;
    public double rate;
    public boolean verified;
    public String paymentEmail;
    public String portfolioLink;
    private final static DBLoader db = DBLoader.getInstance();

    public User(String username, String password,
                String firstName, String lastName, String userType, double rate,
                boolean verified, String paymentEmail, String portfolioLink) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.rate = rate;
        this.verified = verified;
        this.paymentEmail = paymentEmail;
        this.portfolioLink = portfolioLink;
    }

    public static User getWithUsername(String username) {
        // Works Tested
        User user = null;
        try {
            String query = String.format("SELECT * FROM user WHERE username=?", username);
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            if (res != null) {
                res.next();
                user = sqlSchemaToUser(res);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return user;
    }
    public static ArrayList<User> getAllUsers() {
        // Works - Tested
        ArrayList<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM user";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            ResultSet res = statement.executeQuery();
            if (res != null) {
                while (res.next()) {
                    User user = sqlSchemaToUser(res);
                    users.add(user);
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
        boolean isVerified = req.getBoolean("isVerified");
        String paymentEmail = req.getString("paymentEmail");
        try {
            String query = "INSERT INTO user (username, password, firstname, " +
                    "lastname, userType, isVerified, paymentEmail) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, userType);
            statement.setBoolean(6, isVerified);
            statement.setString(7, paymentEmail);
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

    public static User sqlSchemaToUser(ResultSet res) throws SQLException {
        // Works - Tested
        String username = res.getString("username");
        String password = res.getString("password");
        String firstName = res.getString("firstName");
        String lastName = res.getString("lastName");
        String userType = res.getString("userType");
        double rate = res.getDouble("hourlyRate");
        boolean verified = res.getBoolean("isVerified");
        String paymentEmail = res.getString("paymentEmail");
        String portfolioLink = res.getString("portfolioLink");
        return new User(username, password, firstName, lastName, userType, rate,
                verified, paymentEmail, portfolioLink);
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

    public static void main(String[] args) {
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            System.out.println(user.username);
        }
        JSONObject testUser = new JSONObject();
        testUser.put("username", "test2");
        testUser.put("password", "test1");
        testUser.put("firstName", "fNameTest1");
        testUser.put("lastName", "lNameTest1");
        testUser.put("isVerified", false);
        testUser.put("paymentEmail", "fake@gmail.com");
        testUser.put("hourlyRate", 15.6);
        testUser.put("portfolio", "fake.com");
        User.registerProvider(testUser);
        User testClientUser = User.getWithUsername("test1");
        JSONObject testJson = User.loginUser(testUser);
        System.out.println(testClientUser.firstName + testClientUser.lastName);
        System.out.println(testJson.get("username"));
    }
}
