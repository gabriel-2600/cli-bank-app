package view;
import utilities.Utilities;
import view.auth.LoginView;
import view.auth.RegisterView;

import java.util.*;

public class MainMenuView {
    public static void mainMenuView() {
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        while(!isValid){
            try{
                mainMenuHeadingAndOptions();

                System.out.print("Choose an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> {
                        isValid = true;
                        System.out.println("Proceeding to register");

                        RegisterView.registerView();
                    }
                    case 2 -> {
                        isValid = true;
                        System.out.println("Proceeding to login");

                        LoginView.loginView();
                    }
                    case 3 -> {
                        isValid = true;
                        Utilities.closeApplication("Exiting App...");
                    }
                    default -> System.out.println("Please choose correct option");
                }
            } catch(InputMismatchException e) {
                System.out.println("Please choose the correct option");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void mainMenuHeadingAndOptions(){
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Bank App");
        System.out.println("Choose an option by pressing the number");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }
}
