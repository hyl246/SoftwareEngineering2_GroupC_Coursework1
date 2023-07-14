package server;

public class Account {

	private String accountName;
	private String accountType; // added this for new accounts to specify the account type
	private double openingBalance;

	public Account(String accountName, String accountType, double openingBalance) { // modified for account type
		this.accountName = accountName;
		this.accountType = accountType;
		this.openingBalance = openingBalance;

	}

	public String toString() {
		return (accountName + "(" + accountType + "): " + openingBalance + System.lineSeparator());
	}

}
