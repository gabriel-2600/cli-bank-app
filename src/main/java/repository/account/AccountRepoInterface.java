package repository.account;

import java.sql.SQLException;
import java.util.List;

import model.Account;
import model.Users;

public interface AccountRepoInterface {
    void storeAccountInDatabase(Account account) throws SQLException;

    List<Account> retrieveUserAccountsInDatabase(Users user) throws SQLException;

    Account retrieveAccount(int accountID) throws SQLException;

    void addToBalance(int accountID, double amount) throws SQLException;

    void subtractFromBalance(int accountID, double amount) throws SQLException;

    void transferBetweenAccounts(int accountID, int recipientAccountID, double amount) throws SQLException;

    void deleteAccount(int accountID) throws SQLException;
}
