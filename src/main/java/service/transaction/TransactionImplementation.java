package service.transaction;

import java.util.List;

import model.Transaction;

import repository.account.AccountRepoInterface;
import repository.transaction.TransactionRepoInterface;



public  class TransactionImplementation implements TransactionInterface {
    private TransactionRepoInterface transactionRepository;
    private AccountRepoInterface accountRepository;

    public TransactionImplementation(TransactionRepoInterface transactionRepository, AccountRepoInterface accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public int createTransaction(int accountID, double amount, String transactionType) throws Exception {
        return transactionRepository.createTransactionData(accountID, amount, transactionType);
    }

    @Override
    public void createTransfer(int transactionID, int recipientAccountID) throws Exception {
        transactionRepository.createTransferData(transactionID, recipientAccountID);
    }

    @Override
    public List<Transaction> viewTransactionHistory(int accountID) throws Exception {
        List<Transaction> transactionList = transactionRepository.retrieveTransactions(accountID);

        if(transactionList.isEmpty()){
            throw new RuntimeException("No Past Transactions Yet");
        }

        return transactionList;
    }

    
}
