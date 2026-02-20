package view.dashboard.account;

import model.Users;
import repository.account.AccountRepoImplementation;
import repository.account.AccountRepoInterface;
import service.account.AccountImplementation;
import service.account.AccountInterface;

import java.util.Scanner;

import view.dashboard.MainDashboard;

public class AccountCreationView {
    public static void accountCreationView(Users user){
        AccountRepoInterface accountRepository = new AccountRepoImplementation();
        AccountInterface accountInterface = new AccountImplementation(accountRepository);

        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while(!isValid){
            try{
                accountCreationHeading();

                System.out.print("Input Account Name: ");
                String accountName = scanner.nextLine();

                if(accountName.equalsIgnoreCase("back")){
                    MainDashboard.mainDashboardView(user);
                    isValid = true;
                }

                System.out.print("Input balance: ");
                double balance = scanner.nextDouble();

                accountInterface.createAccount(user, accountName, balance);

                System.out.println("Account created successfully");
                MainDashboard.mainDashboardView(user);

                isValid = true;
            } catch (Exception e) {
                if(e.getMessage() == null){
                    System.out.println("Balance should be a digit!");
                } else{
                    System.out.println(e.getMessage());
                }
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void accountCreationHeading(){
        System.out.println();
        System.out.println();
        System.out.println("Create a new Bank Account");
        System.out.println("Type back in Account Name to go back");
    }
}
