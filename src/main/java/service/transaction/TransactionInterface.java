package service.transaction;

import model.Account;
import model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionInterface {
    void depositInAccount(int accountID, double amount) throws Exception;

    void withdrawInAccount(int accountID, double accountBalance, double amount) throws Exception;

    void transferToAnAccount(int accountID, int recipientAccountID, double accountBalance, double amount) throws Exception;

    void deleteAccount(int accountID) throws Exception;

     int createTransaction(int accountID, double amount, String transactionType) throws Exception;

    List<Transaction> viewTransactionHistory(int accountID) throws Exception;

    void createTransfer(int transactionID, int recipientAccountID) throws Exception;
}
