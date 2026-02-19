package repository.user;
import model.Users;

public interface UserRepoInterface {
    void registerInDatabase(Users user);

     Users validateLoginInDatabase(String username, String password);
}
