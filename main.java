import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileHandler.loadAccounts();
        // admin crd.
        String adminId = "admin";
        String password = "admin123";
        
        // predefined accounts
        //BankAccount account1 = new BankAccount(1000.0, "Hari Pal", "Doctor");
        //BankAccount account2 = new BankAccount(2000.0, "Corona Singh", "Virus");
        //account1.applyForLoan(101, Loan.LoanType.PERSONAL, 5000, 5.5);
        //account2.applyForLoan(102, Loan.LoanType.HOME, 20000, 4.5);
        while (true) {
            System.out.println(":: BANKING MANAGEMENT SYSTEM ::");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int loginChoice = scanner.nextInt();

            if (loginChoice == 1) {
                System.out.print("Enter Account ID: ");
                int accountId = scanner.nextInt();
                //check whether account exists or not
                BankAccount account = BankAccount.getAccountRegistry().get(accountId);
                
                if (account != null) {
                    while (true) {
                        System.out.println(":: CUSTOMER MENU ::");
                        System.out.println("1. Deposit Money");
                        System.out.println("2. Withdraw Money");
                        System.out.println("3. Transfer Money");
                        System.out.println("4. View Transaction History");
                        System.out.println("5. Apply for Loan");
                        System.out.println("6. Check your Account Balance");
                        System.out.println("7. Check Pending Loan Status");
                        System.out.println("8. Log Out to Main Menu");
                        
                        int customerChoice = scanner.nextInt();
                        switch (customerChoice) {
                            case 1:
                                System.out.print("Enter amount to deposit: ");
                                double depositAmount = scanner.nextDouble();
                                account.deposit(depositAmount);
                                break;
                            case 2:
                                System.out.print("Enter amount to withdraw: ");
                                double withdrawAmount = scanner.nextDouble();
                                account.withdraw(withdrawAmount);
                                break;
                            case 3:
                                System.out.print("Enter recipient Account ID: ");
                                int recipientId = scanner.nextInt();
                                System.out.print("Enter amount to send: ");
                                double sendAmount = scanner.nextDouble();
                                account.sendMoney(recipientId, sendAmount);
                                break;
                            case 4:
                                account.viewTransactionHistory();
                                break;
                            case 5:
                                System.out.print("Enter Loan Amount: ");
                                double loanAmount = scanner.nextDouble();
                                System.out.print("Enter Loan Tenure (in years): ");
                                int tenureYears = scanner.nextInt();
                                //generating loanid
                                int loanId = account.getLoans().size() + 1;
                                int emi = (int)account.calculateEMI(loanAmount, 8.5, tenureYears);
                                account.applyForLoan(loanAmount, 8.5, emi, tenureYears);
                                break;
                            case 6:
                                System.out.println("Your current balance is: " + account.getBalance());
                                break;
                            case 7:        
                                List<Loan> loans = account.getLoans();
                                if (loans.isEmpty()) {
                                    System.out.println("No pending loan applications.");
                                } else {
                                    for (Loan loan : loans) {
                                        loan.displayLoan(accountId);
                                        if (loan.getStatus() == Loan.Status.APPROVED) {
                                            System.out.println("Loan has been approved. EMI: " + loan.getEmi());
                                        }
                                    }
                                }
                                break;
                            case 8:
                                break;
                        }

                        if (customerChoice == 8) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Account not found.");
                }
            } else if (loginChoice == 2) { // Admin Login
                System.out.print("Enter Admin ID: ");
                String inputAdminId = scanner.next();
                System.out.print("Enter Password: ");
                String inputPassword = scanner.next();
                if (inputAdminId.equals(adminId) && inputPassword.equals(password)) {
                    Admin admin = new Admin(adminId, password);  // Create Admin object
                    System.out.println("Admin login successful.");
        
        while (true) {
            System.out.println(":: ADMIN MENU ::");
            System.out.println("1. View Pending Loan Requests");
            System.out.println("2. Approve or Reject Loan");
            System.out.println("3. Create New Account");
            System.out.println("4. Display All Bank Accounts");
            System.out.println("5. Log Out to Main Menu");

            int adminChoice = scanner.nextInt();
            switch (adminChoice) {
                case 1: // Viewing Pending Loan Requests
                    admin.viewPendingLoanRequests();
                    break;
                case 2: // Approve or Reject Loan
                    System.out.print("Enter Account ID for loan approval/rejection: ");
                    int accountId = scanner.nextInt();
                    System.out.print("Enter Loan ID: ");
                    int loanId = scanner.nextInt();
                    System.out.print("Approve (1) or Reject (2): ");
                    int decision = scanner.nextInt();
                    admin.approveOrRejectLoan(accountId, loanId, decision == 1);
                    break;
                case 3: // Create New Account
                    admin.createNewAccount(scanner);
                    break;
                case 4: // Display All Bank Accounts
                    admin.displayAllBankAccounts();
                    break;
                case 5:
                    System.out.println("Logging out to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

            if (adminChoice == 5) {
                break;
            }
        }
    } else {
        System.out.println("Invalid admin credentials.");
    }
            } else if (loginChoice == 3) {
                FileHandler.saveAccounts();
                System.out.println("Exiting system.");
                scanner.close();
                return;
            }
        }
    }
}
