package service.transaction;

import model.Account;
import model.Transaction;
import repository.transaction.TransactionRepoInterface;
import utilities.Utilities;

import java.util.List;

public  class TransactionImplementation implements TransactionInterface {
    private TransactionRepoInterface transactionRepository;

    public TransactionImplementation(TransactionRepoInterface transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public  void depositInAccount(int accountID, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);

        transactionRepository.depositInDatabase(accountID, amount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("withdraw");
        createTransaction(accountID, amount, transaction.getTransactionType());
    }

    @Override
    public void withdrawInAccount(int accountID, double accountBalance, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);
        Utilities.validateAccountBalance(accountBalance, amount);

        transactionRepository.withdrawAmount(accountID, amount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("deposit");
        createTransaction(accountID, amount, transaction.getTransactionType());
    }

    @Override
    public void transferToAnAccount(int accountID, int recipientAccountID, double accountBalance, double amount) throws Exception {
        Utilities.validateNegativeBalance(amount);
        Utilities.validateAccountBalance(accountBalance, amount);

        if(accountID == recipientAccountID){
            throw new RuntimeException("Can not transfer to same bank account");
        }

        transactionRepository.transferMoney(accountID, recipientAccountID, amount);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("transfer");
        int transactionID = createTransaction(accountID, amount, transaction.getTransactionType());

        createTransfer(transactionID, recipientAccountID);
    }

    @Override
    public void deleteAccount(int accountID) throws Exception {
        transactionRepository.deleteBankAccount(accountID);
    }

    @Override
    public int createTransaction(int accountID, double amount, String transactionType) throws Exception {

        return transactionRepository.createTransactionData(accountID, amount, transactionType);
    }

    @Override
    public List<Transaction> viewTransactionHistory(int accountID) throws Exception {
        List<Transaction> transactionList = transactionRepository.retrieveTransaction(accountID);

        if(transactionList.isEmpty()){
            throw new RuntimeException("No Past Transactions Yet");
        }

        return transactionList;
    }

    @Override
    public void createTransfer(int transactionID, int recipientAccountID) throws Exception {
        transactionRepository.createTransferData(transactionID, recipientAccountID);
    }
}
