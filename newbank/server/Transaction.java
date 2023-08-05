package server;

import java.util.Date;

public class Transaction {
    private String accountNumber;
    private String sortCode;
    private String accountOwner;
    private double amount;
    private Date date;
    private String currency;

    public Transaction(String accountNumber, String sortCode, String accountOwner, double amount, Date date, String currency) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.accountOwner = accountOwner;
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }

    // Getters and setters for the attributes
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
