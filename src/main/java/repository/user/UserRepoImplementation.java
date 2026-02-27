package repository.user;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import repository.DatabaseConnection;

import model.Users;

public class UserRepoImplementation implements UserRepoInterface {
    @Override
    public void registerInDatabase(String fullName, String username, String hashedPassword) throws SQLException {
        String sqlQuery = "INSERT INTO users (full_name, username, password) VALUES  (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, hashedPassword);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(String.valueOf(e.getErrorCode()).equalsIgnoreCase("1062")){
                throw new RuntimeException("Username is already existing!");
            } else {
                throw new SQLException(e);
            }
        }
    }

    @Override
    public String retrieveHashedPassword(String username) throws SQLException {
        final String SELECT_PASSWORD_QUERY = "SELECT password FROM users WHERE BINARY username = ?";
        String hashedPassword = "";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PASSWORD_QUERY);
            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();
            if(!result.isBeforeFirst()){
                return null;
            }

            while(result.next()){
                hashedPassword = result.getString("password");
            }

            return hashedPassword;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Users validateLoginInDatabase(String username) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE BINARY username = ?";
        Users user = new Users();

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();
            if(!result.isBeforeFirst()){
                return null;
            }

            while(result.next()){
                user.setUserID(result.getInt("user_id"));
                user.setFullName(result.getString("full_name"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
            }

            return user;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
