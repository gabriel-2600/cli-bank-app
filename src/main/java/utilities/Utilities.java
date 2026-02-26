package utilities;

import exceptions.*;

public class Utilities {
    public static void closeApplication(String message){
        System.out.println(message);
        System.exit(0);
    }

    public static void validateInput(String input) throws InputLessThanMinimumException {
        if(input.isBlank()){
            throw new InputLessThanMinimumException("Input can not be empty!");
        } else if(input.length() < 4){
            throw new InputLessThanMinimumException("Input must be more than 3 characters!");
        }
    }

    public static void validateNegativeBalance(double balance) throws AmountLessThanZeroException {
        if(balance < 0){
            throw new AmountLessThanZeroException("Balance can not be negative!");
        }
    }

    // public static void validateMinimumBalance(double balance) throws Exception {
    //     if (balance < 200) {
    //         throw new Exception("Minimum is 200!");
    //     }
    // }

    public static void validateAccountBalance(double accountBalance, double amount) throws AmountLessThanBalanceException{
        if(accountBalance < amount){
            throw new AmountLessThanBalanceException("Insufficient funds!");
        }
    }
}
