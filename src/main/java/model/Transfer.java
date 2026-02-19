package model;

public class Transfer {
    private int transferID;
    private int transactionID;
    private int recipientAccountID;


    public int getTransferID() {
        return transferID;
    }

    public void setTransferID(int transferID) {
        this.transferID = transferID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getRecipientAccountID() {
        return recipientAccountID;
    }

    public void setRecipientAccountID(int recipientAccountID) {
        this.recipientAccountID = recipientAccountID;
    }
}
