package server;

public class Account {
	
	private String accountName;
	private double openingBalance;
	private double currentBalance;
	
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.currentBalance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + openingBalance + System.lineSeparator());
	}

	public String getAccountName(){
		return accountName;
	}

	//Method for transfer balance (amount > 0)
	public void credit_balance(double amount){
		currentBalance = currentBalance - amount;
		openingBalance = currentBalance;
	}

	//Method for receiving balance (amount > 0)
	public void debit_balance(double amount){
		currentBalance = currentBalance + amount;
		openingBalance = currentBalance;
	}

	//Method for checking sufficient amount in balance for transfer
	public boolean checkBalance(double amount){
		if (amount>openingBalance){
			System.out.println("Insufficient amount in account.");
			return false;
		}
		return true;
	}

}
