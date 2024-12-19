import java.io.Serializable;

public class Loan implements Serializable{
    private static final long serialVresionUID = 1;
    private static int lastLoanId = 100;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
    
    private int loanId;
    private double amount;
    private double interestRate;
    private Status status;
    private double emi;
    private int tenureYears;
    public Loan(double amount, double interestRate, int emi, int tenureYears) {
        this.loanId = ++lastLoanId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.emi = emi;
        this.tenureYears = tenureYears;
        this.status = Status.PENDING;
    }
    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public int getLoanId() {
        return loanId;
    }


    public double getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    

    public void displayLoan(int accountId) {
        System.out.println("Account ID : " + accountId + "\t Loan ID: " + loanId + "\t Amount: " + amount + "\t Interest Rate: " + interestRate + "\t Status: " + status);
        if (status == Status.APPROVED) {
            System.out.println("This loan has been approved.");
        } else if (status == Status.PENDING) {
            System.out.println("This loan is pending approval.");
        } else if (status == Status.REJECTED) {
            System.out.println("This loan has been rejected.");
        }
    }

}
