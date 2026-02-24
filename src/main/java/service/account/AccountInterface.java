package service.account;

import model.Users;
import model.Account;

import java.util.List;

public interface AccountInterface {
    void createAccount(Users user, String accountName, double balance) throws Exception;

    List<Account> viewAllAccountsOfUser(Users user) throws Exception;

    Account viewAccount(int accountID) throws Exception;

    void depositInAccount(int accountID, double amount) throws Exception;

    void withdrawInAccount(int accountID, double accountBalance, double amount) throws Exception;

    void transferToAnAccount(int accountID, int recipientAccountID, double accountBalance, double amount) throws Exception;

    void deleteAccount(int accountID) throws Exception;
}
