package service.account;

import model.Users;
import model.Account;

import java.util.List;

public interface AccountInterface {
    void createAccount(Users user, String accountName, double balance) throws Exception;

    List<Account> viewAllAccountsOfUser(Users user) throws Exception;

    Account viewAccount(int accountID) throws Exception;
}
