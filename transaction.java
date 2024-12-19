public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    private TransactionType type;
    private double amount;
    private String date;
    private int targetAccountId;

    public Transaction(TransactionType type, double amount, String date, int targetAccountId) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.targetAccountId = targetAccountId;
    }

    public void displayTransaction() {
        System.out.println("Type: " + type + ", Amount: " + amount + ", Date: " + date + 
                           (targetAccountId != -1 ? ", Target Account: " + targetAccountId : ""));
    }
}
