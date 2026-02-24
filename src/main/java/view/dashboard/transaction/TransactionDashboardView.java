package view.dashboard.transaction;

import model.Account;
import model.Users;

import repository.account.AccountRepoImplementation;
import repository.account.AccountRepoInterface;
import service.account.AccountImplementation;
import service.account.AccountInterface;

import service.transaction.TransactionImplementation;
import service.transaction.TransactionInterface;
import repository.transaction.TransactionRepoImplementation;
import repository.transaction.TransactionRepoInterface;

import view.dashboard.account.ExistingAccountView;

import java.util.Scanner;

public class TransactionDashboardView {
    public static void transactionDashBoardView(int accountID, Users user){
        AccountRepoInterface accountRepository = new AccountRepoImplementation();
        TransactionRepoInterface transactionRepoImplementation = new TransactionRepoImplementation();
        
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepoImplementation);
        AccountInterface accountInterface = new AccountImplementation(accountRepository, transactionInterface);

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;
        while(!isValid){
           try{
               Account account = accountInterface.viewAccount(accountID);
               accountDashboard(account.getAccountName(), account.getAccountID(), account.getBalance());

               System.out.print("Choose your option: ");
               int option = scanner.nextInt();

               switch(option){
                   case 0 -> {
                       ExistingAccountView.existingAccountView(user);
                       return;
                   }
                   case 1 -> {
                       System.out.println("Proceeding to deposit");

                       TransactionFeaturesView.depositView(accountID, user);
                       isValid = true;
                   }
                   case 2 -> {
                       System.out.println("Proceeding to withdraw");

                       TransactionFeaturesView.withdrawView(accountID, account.getBalance(), user);
                       isValid = true;
                   }
                   case 3 -> {
                       System.out.println("Proceeding to transfer");

                       TransactionFeaturesView.transferView(accountID, account.getBalance(), user);
                       isValid = true;
                   } case 4 -> {
                       System.out.println("Proceeding to Deleting account");

                       TransactionFeaturesView.deleteAccountView(accountID, user);
                       isValid = true;
                   } case 5 -> {
                       System.out.println("Proceeding to Account Transaction History");

                        TransactionFeaturesView.transactionHistoryView(accountID, user);
                       isValid = true;
                   }
                   default -> System.out.println("Please choose correct option");
               }
           }catch (Exception e){
               System.out.println("Please choose correct option");
               scanner.nextLine();
           }
        }

        scanner.close();
    }

    private static void accountDashboard(String accountName, int accountID, double balance){
        System.out.println();
        System.out.println();
        System.out.println("Account ID: " + accountID);
        System.out.println(accountName);
        System.out.println("Account Balance: " + balance);
        System.out.println("0. Go back");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Delete Bank Account");
        System.out.println("5. View Transaction History");
    }
}
