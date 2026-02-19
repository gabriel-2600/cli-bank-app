package repository;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USERNAME"); // replace with your MySQL username
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); // replace with your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
