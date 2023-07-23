package server;

import java.util.ArrayList;
import java.util.List;

public class Customer {

	// manage payees
	private ArrayList<Payee> payees;
	// manage daily transaction limit
	private double dailyLimit = 50000;
	private ArrayList<Account> accounts;

	public Customer() {
		accounts = new ArrayList<>();
		payees = new ArrayList<>();
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