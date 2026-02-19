package view.auth;
import java.util.*;

import repository.user.UserRepoImplementation;
import repository.user.UserRepoInterface;
import service.auth.AuthImplementation;
import service.auth.AuthInterface;
import view.MainMenuView;

public class RegisterView {
    public static void registerView(){
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        UserRepoInterface userRepoInterface = new UserRepoImplementation();
        AuthInterface authInterface = new AuthImplementation(userRepoInterface);

        while(!isValid){
            try{
                registerHeading();

                System.out.print("Full Name: ");
                String fullName = scanner.nextLine();

                System.out.print("Username: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                authInterface.register(fullName, username, password);

                System.out.println("Registered successfully");

                MainMenuView.mainMenuView();
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static void registerHeading(){
        System.out.println();
        System.out.println();
        System.out.println("Register an Account");
    }
}
