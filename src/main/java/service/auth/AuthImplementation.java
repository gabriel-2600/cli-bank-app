package service.auth;

import model.Users;
import repository.user.UserRepoInterface;

import  utilities.Utilities;

public class AuthImplementation implements AuthInterface {
    private UserRepoInterface repository;

    public AuthImplementation(UserRepoInterface repository) {
        this.repository = repository;
    }

    @Override
    public void register(String fullName, String username, String password) throws Exception {
        Utilities.validateInput(fullName);
        Utilities.validateInput(username);
        Utilities.validateInput(password);

        Users user = new Users();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);

        repository.registerInDatabase(user);
    }

    @Override
    public Users login(String username, String password) throws Exception {
        Users user = repository.validateLoginInDatabase(username, password);

        if(user == null){
            throw new Exception("Invalid credentials!");
        }

        return user;
    }
}
