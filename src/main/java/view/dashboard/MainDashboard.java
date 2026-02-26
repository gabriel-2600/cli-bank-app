package view.dashboard;

// JAVA IMPORT
import java.util.*;

// PROJECT IMPORT
import model.Users;
import view.MainMenuView;
import view.dashboard.account.AccountCreationView;
import view.dashboard.account.ExistingAccountView;

public class MainDashboard {
    public static void mainDashboardView(Users user) {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while(!isValid) {
            try {
                mainDashboardHeading(user);

                System.out.print("Choose an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> {
                        System.out.println("Proceeding to bank account creation");

                        AccountCreationView.accountCreationView(user);
                        isValid = true;
                    }
                    case 2 -> {
                        System.out.println("Proceeding to existing bank accounts");

                        ExistingAccountView.existingAccountView(user);
                        isValid = true;
                    }
                    case 3 -> MainMenuView.mainMenuView();
                    default -> System.out.println("Please choose correct option");

                }
            } catch (InputMismatchException e){
                System.out.println("Please choose correct option");
                scanner.nextLine();
            } 
        }

        scanner.close();
    }

    private static void mainDashboardHeading(Users user){
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Bank App " + user.getFullName());
        System.out.println("1. Create a new Bank Account: ");
        System.out.println("2. Choose from existing bank accounts");
        System.out.println("3. Logout");
    }
}
