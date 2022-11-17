package main.java.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public double rate;
    public boolean verified;
    public String paymentEmail;
    public String portfolioLink;
    private final static DBLoader db = DBLoader.getInstance();

    public User(String username, String password,
                String firstName, String lastName, double rate,
                boolean verified, String paymentEmail, String portfolioLink) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rate = rate;
        this.verified = verified;
        this.paymentEmail = paymentEmail;
        this.portfolioLink = portfolioLink;
    }

    public static ArrayList<User> getAllUsers() {
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

    public static User sqlSchemaToUser(ResultSet res) throws SQLException {
        String username = res.getString("username");
        String password = res.getString("password");
        String firstName = res.getString("firstName");
        String lastName = res.getString("lastName");
        double rate = res.getDouble("hourlyRate");
        boolean verified = res.getBoolean("isVerified");
        String paymentEmail = res.getString("paymentEmail");
        String portfolioLink = res.getString("portfolioLink");
        return new User(username, password, firstName, lastName, rate,
                verified, paymentEmail, portfolioLink);
    }

    public static void main(String[] args) {
        ArrayList<User> users = User.getAllUsers();
        for (User user : users) {
            System.out.println(user.username);
        }
    }
}
