package repository.user;
import model.Users;

import java.sql.SQLException;
import java.util.Map;

public interface UserRepoInterface {
    void registerInDatabase(String fullName, String username, String hashedPassword) throws SQLException;

    String retrieveHashedPassword(String username) throws SQLException;

    Users validateLoginInDatabase(String username) throws SQLException;

}
