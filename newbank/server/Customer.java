package server;

import java.util.ArrayList;
import java.util.List;

import client.LoanRequest;

public class Customer {

	// manage payees
	private ArrayList<Payee> payees;
	// manage daily transaction limit
	private double dailyLimit = 50000;
	private ArrayList<Account> accounts;
    private ArrayList<LoanRequest> loanRequests; // Attribute for loan requests

	public Customer() {
		accounts = new ArrayList<>();
		payees = new ArrayList<>();
		loanRequests = new ArrayList<>(); // Initialize the loanRequests list

	}

	// Method to add a loan request to the customer's loan requests
	public void addLoanRequest(LoanRequest loanRequest) {
		loanRequests.add(loanRequest);
	}

	// Method to get all loan requests of the customer
	public List<LoanRequest> getLoanRequests() {
		return loanRequests;
	}

	// method for adding payee
	public void addPayee(Payee payee) {
		payees.add(payee);
	}

	// method to set daily limit
	public void setDailyLimit(double limit) {
		dailyLimit = limit;
	}

	// method to get daily limit
	public double getDailyLimit() {
		return dailyLimit;
	}

	// method to update daily limit
	public void updateDailyLimit(double amount) {
		dailyLimit = amount;
	}

	// method to check if a transaction would exceed the daily limit
	public boolean exceedsDailyLimit(double amount) {
		return dailyLimit - amount < 0;
	}

	public Account getAccount(int index) {
		return accounts.get(index);
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public String accountsToString() {
		String s = "";
		for (Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

}