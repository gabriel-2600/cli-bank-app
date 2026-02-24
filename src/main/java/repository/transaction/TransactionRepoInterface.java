package repository.transaction;

import model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionRepoInterface {

    int createTransactionData(int accountID, double amount, String transactionType) throws SQLException;

    void createTransferData(int transactionID, int recipientAccountID) throws SQLException;

    List<Transaction> retrieveTransactions(int accountID) throws SQLException;

    
}
