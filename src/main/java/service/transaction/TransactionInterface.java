package service.transaction;

import java.util.List;

import model.Transaction;


public interface TransactionInterface {
    int createTransaction(int accountID, double amount, String transactionType) throws Exception;

    List<Transaction> viewTransactionHistory(int accountID) throws Exception;

    void createTransfer(int transactionID, int recipientAccountID) throws Exception;
}
