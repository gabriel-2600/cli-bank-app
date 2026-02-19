package model;

import java.util.HashMap;
import java.util.Map;

public class Transaction {
    private int transactionID;
    private int accountID;
    private double amount;
    private final Map<String, String> transactionTypes = new HashMap<>();
    private String transactionType;
    private Transfer transfer;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public void setTransactionType(String transaction){
        putTransactionType();
        this.transactionType = transactionTypes.get(transaction);
    }

    private void putTransactionType(){
        transactionTypes.put("deposit", "deposit");
        transactionTypes.put("withdraw", "withdraw");
        transactionTypes.put("transfer", "transfer");
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
}
