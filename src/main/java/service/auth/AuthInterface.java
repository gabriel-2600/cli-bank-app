package service.auth;

import model.Users;

public interface AuthInterface {
    void register(String fullName, String username, String password) throws Exception;

    Users login(String username, String password) throws Exception;
}
