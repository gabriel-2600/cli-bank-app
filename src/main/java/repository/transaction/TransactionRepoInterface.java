package repository.transaction;

import model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionRepoInterface {
    void depositInDatabase(int accountID, double amount) throws SQLException;

    void withdrawAmount(int accountID, double amount) throws SQLException;

    void transferMoney(int accountID, int recipientAccountID, double amount) throws SQLException;

    void deleteBankAccount(int accountID) throws SQLException;

    int createTransactionData(int accountID, double amount, String transactionType) throws SQLException;

    List<Transaction> retrieveTransaction(int accountID) throws SQLException;

    void createTransferData(int transactionID, int recipientAccountID) throws SQLException;
}
