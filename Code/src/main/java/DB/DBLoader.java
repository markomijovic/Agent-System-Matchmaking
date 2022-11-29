package main.java.DB;

import java.sql.*;

public class DBLoader {
    private final String DB_URL = System.getenv("DB_URL");
    private final String USERNAME = System.getenv("DB_USERNAME");
    private final String PASSWORD = System.getenv("DB_PASSWORD");
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
