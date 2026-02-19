package repository.user;

import java.sql.*;
import repository.DatabaseConnection;

import model.Users;

public class UserRepoImplementation implements UserRepoInterface {
    @Override
    public void registerInDatabase(Users user) {
        String sqlQuery = "INSERT INTO users (full_name, username, password) VALUES  (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(String.valueOf(e.getErrorCode()).equalsIgnoreCase("1062")){
                throw new RuntimeException("Username is already existing!");
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Users validateLoginInDatabase(String username, String password) {
        String sqlQuery = "SELECT * FROM users WHERE BINARY username = ? AND password = ?";
        Users user = new Users();

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

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
            throw new RuntimeException(e);
        }
    }
}
