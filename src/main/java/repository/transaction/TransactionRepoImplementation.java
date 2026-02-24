package repository.transaction;

import model.Transaction;
import model.Transfer;
import repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepoImplementation implements TransactionRepoInterface {
    @Override
    public int createTransactionData(int accountID, double amount, String transactionType) throws SQLException {
        final String CREATE_TRANSACTION_QUERY = "INSERT INTO transactions (account_id, amount, transaction_type) VALUE (?, ?, ?)";
        int transactionID = 0;

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement createTransactionPS = connection.prepareStatement(CREATE_TRANSACTION_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            createTransactionPS.setInt(1, accountID);
            createTransactionPS.setDouble(2, amount);
            createTransactionPS.setString(3, transactionType);

            createTransactionPS.executeUpdate();

            try(ResultSet resultSet = createTransactionPS.getGeneratedKeys()){
                if(resultSet.next()){
                    transactionID = resultSet.getInt(1);
                }
            }catch (SQLException e) {
                throw new SQLException(e);
            }
            
            return transactionID;
        } catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<Transaction> retrieveTransactions(int accountID) throws SQLException {
        final String RETRIEVE_TRANSACTION = "SELECT transactions.*, transfers.* FROM transactions LEFT JOIN transfers ON transactions.transaction_id = transfers.transaction_id  WHERE account_id = ? ORDER BY transactions.transaction_id DESC LIMIT 5";
        List<Transaction> transactionList = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement createTransactionPS = connection.prepareStatement(RETRIEVE_TRANSACTION);
            createTransactionPS.setInt(1, accountID);

            ResultSet result = createTransactionPS.executeQuery();
            while(result.next()){
                Transaction transaction = new Transaction();
                transaction.setTransactionID(result.getInt("transactions.transaction_id"));
                transaction.setAccountID(result.getInt("account_id"));
                transaction.setAmount(result.getDouble("amount"));
                transaction.setTransactionType(result.getString("transaction_type"));

                Transfer transfer = new Transfer();
                int recipientAccountId = result.getInt("recipient_account_id");
                if(recipientAccountId != 0){
                    transfer.setTransferID(result.getInt("transfer_id"));
                    transfer.setTransactionID(result.getInt("transfers.transaction_id"));
                    transfer.setRecipientAccountID(recipientAccountId);
                } else{
                    transfer.setRecipientAccountID(-1);
                }
                transaction.setTransfer(transfer);

                transactionList.add(transaction);
            }

            return transactionList;
        } catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void createTransferData(int transactionID, int recipientAccountID) throws SQLException {
        final String CREATE_TRANSFER_QUERY = "INSERT INTO transfers (transaction_id, recipient_account_id) VALUE (?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement createTransactionPS = connection.prepareStatement(CREATE_TRANSFER_QUERY);
            createTransactionPS.setInt(1, transactionID);
            createTransactionPS.setInt(2, recipientAccountID);

            createTransactionPS.executeUpdate();
        } catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
