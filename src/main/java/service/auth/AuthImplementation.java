package service.auth;

import org.mindrot.jbcrypt.*;
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

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        repository.registerInDatabase(fullName, username, hashedPassword);
    }

    @Override
    public Users login(String username, String password) throws Exception {
        String hashedPassword = repository.retrieveHashedPassword(username);
        if(hashedPassword == null){
            throw new Exception("No user found!");
        }


        boolean isValid = BCrypt.checkpw(password, hashedPassword);
        if(!isValid){
            throw new Exception("Invalid credentials!");
        }

        return repository.validateLoginInDatabase(username);
    }
}
