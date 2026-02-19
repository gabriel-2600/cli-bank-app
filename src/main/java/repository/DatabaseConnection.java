package repository;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bank_app?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // replace with your MySQL username
    private static final String PASSWORD = "mbam22026"; // replace with your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
