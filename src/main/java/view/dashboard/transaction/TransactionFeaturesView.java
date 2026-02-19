package view.dashboard.transaction;

import model.Account;
import model.Transaction;
import model.Users;
import repository.transaction.TransactionRepoImplementation;
import repository.transaction.TransactionRepoInterface;
import service.transaction.TransactionImplementation;
import service.transaction.TransactionInterface;
import view.dashboard.account.ExistingAccountView;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransactionFeaturesView {
    public static void depositView(int accountID, Users user){
        TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);
        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        while(!isValid){
            try{
                System.out.print("Input amount to be deposited: ");
                double amount = scanner.nextDouble();

                if(amount == 0){
                    TransactionDashboardView.transactionDashBoardView(accountID, user);
                    return;
                }

                transactionInterface.depositInAccount(accountID, amount);
                TransactionDashboardView.transactionDashBoardView(accountID, user);

                isValid = true;
            } catch (InputMismatchException e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    public static void withdrawView(int accountID, double accountBalance, Users user){
        TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);
        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        while(!isValid){
            try{
                System.out.print("Input amount to withdraw: ");
                double amount = scanner.nextDouble();

                if(amount == 0){
                    TransactionDashboardView.transactionDashBoardView(accountID, user);
                    return;
                }

                transactionInterface.withdrawInAccount(accountID, accountBalance, amount);
                TransactionDashboardView.transactionDashBoardView(accountID, user);

                isValid = true;
            } catch (InputMismatchException e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    public static void transferView(int accountID, double accountBalance, Users user){
        TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);
        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        while(!isValid){
            try {

                System.out.print("Input bank account recipient: ");
                int recipientAccountID = scanner.nextInt();

                System.out.print("Input amount: ");
                double amount = scanner.nextDouble();;

                if(amount == 0){
                    System.out.println("Exiting bank transfer");
                    TransactionDashboardView.transactionDashBoardView(accountID, user);
                    return;
                }

                transactionInterface.transferToAnAccount(accountID, recipientAccountID, accountBalance, amount);
                TransactionDashboardView.transactionDashBoardView(accountID, user);

                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please choose correct option");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    public static void deleteAccountView(int accountID, Users user){
        TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);
        Scanner scanner = new Scanner(System.in);

        boolean isValid = false;
        while(!isValid){
            try{
                System.out.println();
                System.out.println();
                System.out.println("Are you sure you want to delete? ");
                System.out.println("Choose 1 if yes, choose 2 if no");
                System.out.print("Input option: ");
                int option = scanner.nextInt();
                switch (option){
                    case 1 ->{
                        transactionInterface.deleteAccount(accountID);

                        ExistingAccountView.existingAccountView(user);
                        isValid = true;
                        return;
                    }
                    case 2 -> {
                        TransactionDashboardView.transactionDashBoardView(accountID, user);
                        return;
                    }
                    default -> System.out.println("Please choose correct option");
                }
            } catch(InputMismatchException e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void transactionHistoryView(int accountID, Users user){
        TransactionRepoInterface transactionRepository = new TransactionRepoImplementation();
        TransactionInterface transactionInterface = new TransactionImplementation(transactionRepository);
        Scanner scanner = new Scanner(System.in);

        try{
            System.out.println();
            System.out.println();
            System.out.println("Transaction History for past 5 Transactions:");
            List<Transaction> transactionList = transactionInterface.viewTransactionHistory(accountID);

            for(Transaction transaction : transactionList){
                System.out.println("Transaction ID: " + transaction.getTransactionID());
                System.out.println("Account ID: " + transaction.getAccountID());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Transaction Type: " + transaction.getTransactionType());

                int recipientAccountID = transaction.getTransfer().getRecipientAccountID();
                if(recipientAccountID != -1){
                    System.out.println("Recipient Account ID: " + transaction.getTransfer().getRecipientAccountID());
                }

                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        boolean isValid = false;
        while (!isValid) {
            try{
                System.out.println();
                System.out.print("Press 0 to Go Back: ");
                int option = scanner.nextInt();

                switch (option){
                    case 0 -> {
                        TransactionDashboardView.transactionDashBoardView(accountID, user);
                        return;
                    }
                    default -> System.out.println("Please choose correct option");
                }
            } catch (Exception e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            }
        }
    }
}
