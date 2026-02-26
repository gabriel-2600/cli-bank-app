package view.dashboard.account;

import model.Account;
import model.Users;
import repository.account.AccountRepoImplementation;
import repository.account.AccountRepoInterface;
import service.account.AccountImplementation;
import service.account.AccountInterface;
import view.dashboard.MainDashboard;
import view.dashboard.transaction.TransactionDashboardView;

import java.util.List;
import java.util.Scanner;

import exceptions.NoExistingAccountException;

public class ExistingAccountView {
    public static void existingAccountView(Users user){
        AccountRepoInterface accountRepository = new AccountRepoImplementation();
        AccountInterface accountInterface = new AccountImplementation(accountRepository, null);

        try{
            List<Account> userAccount = accountInterface.viewAllAccountsOfUser(user.getUserID());
            chooseAnAccount(userAccount, user);
        } catch(NoExistingAccountException e){
            System.out.println(e.getMessage());
            MainDashboard.mainDashboardView(user);
        } 
        catch (Exception e){
            System.out.println(e.getMessage());
            MainDashboard.mainDashboardView(user);
        }
    }

    private static void chooseAnAccount(List<Account> userAccount, Users user){
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while(!isValid){
            try{
                existingAccountHeading(userAccount);

                System.out.print("Choose an account: ");
                int option = scanner.nextInt();

                if(option == 0){
                    MainDashboard.mainDashboardView(user);
                    return;
                } else if(option > userAccount.size() || option < 0){
                    throw new Exception();
                } else{
                    int accountID = userAccount.get(option - 1).getAccountID();
                    TransactionDashboardView.transactionDashBoardView(accountID, user);
                    isValid = true;
                }
            } catch (Exception e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void existingAccountHeading(List<Account> userAccount){
        System.out.println();
        System.out.println();
        System.out.println("Your Accounts");
        System.out.println("0. Go Back to Main Dashboard");
        int number = 1;

        for(Account account : userAccount){
            System.out.println(number + ". Account ID: " + account.getAccountID() + " Account Name: " + account.getAccountName());
            number++;
        }
    }
}
