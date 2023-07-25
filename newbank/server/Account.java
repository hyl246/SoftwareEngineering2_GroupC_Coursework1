package server;

public class Account {

	private String accountName;
	private String accountType; // added this for new accounts to specify the account type
	private double openingBalance;
	private double currentBalance;
	private String customerID; // added this for storing the ID of the customer who owns the account

	public Account(String accountName, String accountType, double openingBalance) {
		this.accountName = accountName;
		this.accountType = accountType;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
		this.customerID = null; // initialize customerID as null when creating account as no owner yet
	}

	// Method to read who owns this account
	public String getCustomerID() {
		return this.customerID;
	}

	// Method to set who owns this accoutn
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String toString() { // added customerID to this string
		return (accountName + "(" + accountType + ") owned by customerID" + customerID + ":" + openingBalance
				+ System.lineSeparator());
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
