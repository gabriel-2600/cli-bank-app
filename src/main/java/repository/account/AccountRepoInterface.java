package repository.account;

import model.Account;
import model.Users;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepoInterface {
    void storeAccountInDatabase(Account account);

    List<Account> retrieveUserAccountsInDatabase(Users user);

    Account retrieveAccount(int accountID) throws Exception;
}
