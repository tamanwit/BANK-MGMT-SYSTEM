import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;


public class BankAccount implements Serializable {
    private static final long serialVersionUID = 1;
    
    private int accountId;
    private double balance;
    private String name;
    private String profession;
    private List<Transaction> transactionHistory;
    private List<Loan> loans;
    // Static registry to store all accounts by account ID
    private static Map<Integer, BankAccount> accountRegistry = new HashMap<>();
    
    public BankAccount(int accountId, double balance, String name, String profession) {
        this.accountId = accountId;
        this.balance = balance;;
        this.name = name;
        this.profession = profession;
        this.transactionHistory = new ArrayList<>();
        this.loans = new ArrayList<>();
        accountRegistry.put(accountId, this);
    }
    
    public static Map<Integer, BankAccount> getAccountRegistry() {
        return accountRegistry;
    }

    public static void setAccountRegistry(Map<Integer, BankAccount> registry) {
        accountRegistry = registry;
    }

    public int getAccountId() {
        return accountId;
    }

    /**public static Map<Integer, BankAccount> getAccountRegistry() {
        return accountRegistry;
    }*/

    public double getBalance() {
        return balance;
    }
    
    public String getName(){
        return name;
    }
    
    public String getProfession(){
        return profession;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction(Transaction.TransactionType.DEPOSIT, amount, "23-11-2024", -1));
        System.out.println("Deposit successful.");
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction(Transaction.TransactionType.WITHDRAWAL, amount, "23-11-2024", -1));
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void sendMoney(int targetAccountId, double amount) {
        // checing for target and initial same or not
        if (targetAccountId == this.accountId) {
            System.out.println("Transfer failed. You cannot transfer money to your own account.");
            return;
        }
        BankAccount targetAccount = accountRegistry.get(targetAccountId);
        if (targetAccount != null && amount <= balance) {
            balance -= amount;
            targetAccount.deposit(amount);
            transactionHistory.add(new Transaction(Transaction.TransactionType.TRANSFER, amount, "23-11-2024", targetAccountId));
            System.out.println("Transfer successful to Account ID " + targetAccountId);
        } else {
            System.out.println("Transfer failed. Either the target account does not exist or you have insufficient funds.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History for Account ID " + accountId + ":");
        for (Transaction t : transactionHistory) {
            t.displayTransaction();
        }
    }

    public void applyForLoan(double amount, double interestRate, int emi, int tenureYears) {
        Loan loan = new Loan(amount, interestRate, emi, tenureYears); 
        // loanId auto-generated
        loans.add(loan);
        System.out.println("Loan application submitted for approval with Loan ID: " + loan.getLoanId());
    }

    public double calculateEMI(double amount, double interestRate, int tenureYears) {
        double ratePerMonth = (interestRate / 100) / 12;
        int numberOfMonths = tenureYears * 12;
        return (amount * ratePerMonth * Math.pow(1 + ratePerMonth, numberOfMonths)) / (Math.pow(1 + ratePerMonth, numberOfMonths) - 1);
    }

    public List<Loan> getPendingLoans() {
        List<Loan> pendingLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getStatus() == Loan.Status.PENDING) {
                pendingLoans.add(loan);
            }
        }
        return pendingLoans;
    }

    public Loan findLoanById(int loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId() == loanId) {
                return loan;
            }
        }
        return null;
    }
    
    public void displayAccountInfo() {
        System.out.println("Account ID: " + accountId + ", Name: " + name + 
                           ", Profession: " + profession + ", Balance: " + balance);
    }
}
