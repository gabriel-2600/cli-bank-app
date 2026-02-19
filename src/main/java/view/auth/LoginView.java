package view.auth;
import model.Users;
import repository.user.UserRepoImplementation;
import repository.user.UserRepoInterface;
import service.auth.AuthImplementation;
import service.auth.AuthInterface;

import java.util.*;

import view.dashboard.MainDashboard;

public class LoginView {
    public static void loginView(){
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        UserRepoInterface userRepoInterface = new UserRepoImplementation();
        AuthInterface authInterface = new AuthImplementation(userRepoInterface);

        while(!isValid){
            try{
                loginHeading();

                System.out.print("Username: ");
                String username = scanner.nextLine();

                System.out.print("Password: ");
                String password = scanner.nextLine();

                Users user = authInterface.login(username, password);
                System.out.println("Login successfully");

                MainDashboard.mainDashboardView(user);
                isValid = true;
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }

    private static void loginHeading(){
        System.out.println();
        System.out.println();
        System.out.println("Login to an Account");
    }
}
