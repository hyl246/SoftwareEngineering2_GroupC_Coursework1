package client;

public class LoanRequest {
    private double loanAmount;
    private String purpose;
    private String status;

    public LoanRequest(double loanAmount, String purpose) {
        this.loanAmount = loanAmount;
        this.purpose = purpose;
        this.status = "Pending"; // Set the initial status as "Pending" when creating a new loan request
    }

    // Getters and Setters for the attributes
    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString() to provide a string representation of the LoanRequest object
    @Override
    public String toString() {
        return "LoanRequest [loanAmount=" + loanAmount + ", purpose=" + purpose + ", status=" + status + "]";
    }
}

