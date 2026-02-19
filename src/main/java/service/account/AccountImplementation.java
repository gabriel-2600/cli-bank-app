package service.account;


import java.util.List;

import model.Account;
import model.Users;
import repository.account.AccountRepoInterface;
import utilities.Utilities;

public class AccountImplementation implements AccountInterface{
    private AccountRepoInterface accountRepository;

    public AccountImplementation(AccountRepoInterface accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Users user, String accountName, double balance) throws Exception {
        Utilities.validateInput(accountName);
        Utilities.validateNegativeBalance(balance);

        Account account = new Account();
        account.setUserID(user.getUserID());
        account.setAccountName(accountName);
        account.setBalance(balance);

        accountRepository.storeAccountInDatabase(account);
    }

    @Override
    public List<Account> viewAllAccountsOfUser(Users userData) throws Exception {
        List<Account> userAccount = accountRepository.retrieveUserAccountsInDatabase(userData);

        if(userAccount.isEmpty()){
            throw new Exception("No existing accounts!");
        }

        return userAccount;
    }

    @Override
    public Account viewAccount(int accountID) throws Exception {
        return accountRepository.retrieveAccount(accountID);
    }
}
