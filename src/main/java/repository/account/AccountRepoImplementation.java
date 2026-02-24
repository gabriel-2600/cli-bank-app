package repository.account;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import repository.DatabaseConnection;
import model.Users;
import model.Account;

public class AccountRepoImplementation implements AccountRepoInterface {
    @Override
    public void storeAccountInDatabase(Account account) throws SQLException {
        String sqlQuery = "INSERT INTO accounts (user_id, account_name, balance) VALUES (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, account.getUserID());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getBalance());

            preparedStatement.executeUpdate();
        } catch(SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<Account> retrieveUserAccountsInDatabase(Users user) throws SQLException {
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
            throw new SQLException(e);
        }
    }

    @Override
    public Account retrieveAccount(int accountID) throws SQLException {
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
        } catch (SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void addToBalance(int accountID, double amount) throws SQLException {
        final String ADD_TO_BALANCE_QUERY = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TO_BALANCE_QUERY);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountID);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void subtractFromBalance(int accountID, double amount) throws SQLException {
        final String SUBTRACT_FROM_BALANCE_QUERY = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SUBTRACT_FROM_BALANCE_QUERY);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountID);
            preparedStatement.setDouble(3, amount);

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void transferBetweenAccounts(int accountID, int recipientAccountID, double amount) throws SQLException {
        final String DEDUCT_FROM_BALANCE_QUERY = "UPDATE accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        final String ADD_TO_BALANCE_QUERY = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

        try(Connection connection = DatabaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement deductPrepared = connection.prepareStatement(DEDUCT_FROM_BALANCE_QUERY);
                PreparedStatement addPrepared = connection.prepareStatement(ADD_TO_BALANCE_QUERY);
            ){
                deductPrepared.setDouble(1, amount);
                deductPrepared.setInt(2, accountID);
                deductPrepared.setDouble(3, amount);
                deductPrepared.executeUpdate();

                addPrepared.setDouble(1, amount);
                addPrepared.setInt(2, recipientAccountID);
                int recipientRow = addPrepared.executeUpdate();
                if(recipientRow == 0){
                    throw new SQLException("Account not found!");
                }

                connection.commit();
            } catch (SQLException e){
                connection.rollback();
                throw new SQLException(e.getMessage());
            }
        }catch(SQLException e){
            throw new SQLException(e);
        }
    }
    @Override
    public void deleteAccount(int accountID) throws SQLException {
        final String DELETE_ACCOUNT_QUERY = "DELETE FROM accounts WHERE account_id = ?";

        try(Connection connection = DatabaseConnection.getConnection()){
            PreparedStatement deleteAccountPS = connection.prepareStatement(DELETE_ACCOUNT_QUERY);
            deleteAccountPS.setInt(1, accountID);

            deleteAccountPS.executeUpdate();
        } catch (SQLException e){
            throw new SQLException(e);
        }
    }
}
