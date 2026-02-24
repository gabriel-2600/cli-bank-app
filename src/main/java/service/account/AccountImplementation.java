package service.account;


import java.util.List;

import model.Users;
import model.Account;

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

    @Override
    public  void depositInAccount(int accountID, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);

        accountRepository.addToBalance(accountID, amount);
    }

    @Override
    public void withdrawInAccount(int accountID, double accountBalance, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);
        Utilities.validateAccountBalance(accountBalance, amount);

        accountRepository.subtractFromBalance(accountID, amount);
    }

    @Override
    public void transferToAnAccount(int accountID, int recipientAccountID, double accountBalance, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);
        Utilities.validateAccountBalance(accountBalance, amount);

        if(accountID == recipientAccountID){
            throw new RuntimeException("Can not transfer to same bank account");
        }

        accountRepository.transferBetweenAccounts(accountID, recipientAccountID, amount);
    }

    @Override
    public void deleteAccount(int accountID) throws Exception {
        accountRepository.deleteAccount(accountID);
    }
}
