import java.util.List;
import java.util.Map;
import java.util.*;

public class Admin {
    private String adminId;
    private String password;
    private Scanner scanner;
    
    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
        this.scanner = new Scanner(System.in);
    }

    public boolean login(String inputId, String inputPassword) {
        return adminId.equals(inputId) && password.equals(inputPassword);
    }
    
     void createNewAccount(Scanner scanner) {
        /**System.out.print("Enter New Account ID: ");
        int accountId = scanner.nextInt();*/
        scanner.nextLine();
        System.out.println("Enter Customer's Name: ");
        String name = scanner.nextLine();
        
        System.out.println("Enter Customer's Profession: ");
        String profession = scanner.nextLine();
        
        /**if (BankAccount.getAccountRegistry().containsKey(accountId)) {
            System.out.println("Account with this ID already exists.");
            return;
        }*/

        System.out.print("Enter Initial Balance: ");
        double initialBalance = scanner.nextDouble();
        
        int accountId = FileHandler.loadLastAccountId() + 1;

        // Create a new BankAccount instance
        BankAccount newAccount = new BankAccount(accountId, initialBalance, name, profession);
        BankAccount.getAccountRegistry().put(accountId, newAccount);
        FileHandler.saveAccounts();
        FileHandler.saveLastAccountId(accountId);
        System.out.println("New account created successfully with Account ID: " + accountId +
                           ", Name: " + name + ", Profession: " + profession +
                           ", and Initial Balance: " + initialBalance);
    }

    public void viewPendingLoanRequests() {
        System.out.println("Pending Loan Requests:");
         for (Map.Entry<Integer, BankAccount> entry : BankAccount.getAccountRegistry().entrySet()) {
            int accountId = entry.getKey();  // Obtain the account ID
            BankAccount account = entry.getValue();
            List<Loan> pendingLoans = account.getPendingLoans();  // Get pending loans for this account
            // Loop over each pending loan and display with account ID
            for (Loan loan : pendingLoans) {
                loan.displayLoan(accountId);  // Pass the account ID to the displayLoan method
            }
        }
    }

    public void approveOrRejectLoan(int accountId, int loanId, boolean approve) {
        BankAccount account = BankAccount.getAccountRegistry().get(accountId);
        if (account != null) {
            Loan loan = account.findLoanById(loanId);
            if (loan != null && loan.getStatus() == Loan.Status.PENDING) {
                loan.setStatus(approve ? Loan.Status.APPROVED : Loan.Status.REJECTED);
                System.out.println("Loan ID " + loanId + " has been " + (approve ? "approved" : "rejected") + ".");
            } else {
                System.out.println("Loan not found or already processed.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void displayAllBankAccounts() {
        System.out.println("Bank Accounts Overview:");
        for (BankAccount account : BankAccount.getAccountRegistry().values()) {
            System.out.println("Account ID: " + account.getAccountId() + 
                               ", Name: " + account.getName() + 
                               ", Profession: " + account.getProfession() + 
                               ", Balance: " + account.getBalance());
        }
    }
}
