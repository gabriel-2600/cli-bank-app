package repository.account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import repository.DatabaseConnection;
import model.Users;
import model.Account;

public class AccountRepoImplementation implements AccountRepoInterface {

    @Override
    public void storeAccountInDatabase(Account account)  {
        String sqlQuery = "INSERT INTO accounts (user_id, account_name, balance) VALUES (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, account.getUserID());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getBalance());

            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> retrieveUserAccountsInDatabase(Users user) {
        String sqlQuery = "SELECT * FROM accounts WHERE user_id = ?";
        List<Account> userAccounts = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, user.getUserID());

            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                Account account = new Account();
                account.setAccountID(result.getInt("account_id"));
                account.setUserID(result.getInt("user_id"));
                account.setAccountName(result.getString("account_name"));
                account.setBalance(result.getDouble("balance"));

                userAccounts.add(account);
            }
            user.setUserAccounts(userAccounts);

            return user.getUserAccounts();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account retrieveAccount(int accountID) throws Exception {
        String sqlQuery = "SELECT * FROM accounts WHERE account_id = ?";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, accountID);

            ResultSet result = preparedStatement.executeQuery();
            Account account = new Account();

            while(result.next()){
                account.setAccountID(result.getInt("account_id"));
                account.setUserID(result.getInt("user_id"));
                account.setAccountName(result.getString("account_name"));
                account.setBalance(result.getDouble("balance"));
            }

            return account;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
