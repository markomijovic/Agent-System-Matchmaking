package main.java.DB;

import java.sql.*;

public class DBLoader {
    private final String DB_URL = "jdbc:mysql://localhost:3306/matchmaking";
    private final String USERNAME = "root";
    private final String PASSWORD = "12345678";

    private Connection conn;
    private static DBLoader instance;

    private DBLoader() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        } catch(Exception e) {
            System.out.println("In db loader exception");
            System.out.println(e);
        }
    }

    public static DBLoader getInstance() {
        if (instance == null) {
            instance = new DBLoader();
        }
        return instance;
    }

    public Connection getDBConnection() {
        return conn;
    }
}
