package server;

public class Account {

	private String accountName;
	private String accountType; // added this for new accounts to specify the account type
	private double openingBalance;
	private double currentBalance;

	public Account(String accountName, String accountType, double openingBalance) {
		this.accountName = accountName;
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
	}

	public String toString() {
		return (accountName + "(" + accountType + "): " + openingBalance + System.lineSeparator());
	}

	public String getAccountName() {
		return accountName;
	}

	// Method for transfer balance (amount > 0)
	public void credit_balance(double amount) {
		currentBalance = currentBalance - amount;
		openingBalance = currentBalance;
	}

	// Method for receiving balance (amount > 0)
	public void debit_balance(double amount) {
		currentBalance = currentBalance + amount;
		openingBalance = currentBalance;
	}

	// Method for checking sufficient amount in balance for transfer
	public boolean checkBalance(double amount) {
		if (amount > openingBalance) {
			System.out.println("Insufficient amount in account.");
			return false;
		}
		return true;
	}

}
