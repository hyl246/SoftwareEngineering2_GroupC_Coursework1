package server;

import java.util.ArrayList;
import java.util.List;

import client.LoanRequest;

public class Customer {
	private double dailyLimit = 50000;
	private double creditLimit = 0;

	private ArrayList<Account> accounts;
  private ArrayList<LoanRequest> loanRequests; // Attribute for loan requests

	public Customer() {
		accounts = new ArrayList<>();
	}

	public double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(double limit) {
		dailyLimit = limit;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double limit) {
		creditLimit = limit;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public boolean exceedsDailyLimit(double amount) {
		return dailyLimit < amount;
	}

	public String accountsToString() {
		String s = "";
		for (Account a : accounts) {
			s += a.toString();
		}
		return s;
	}
}