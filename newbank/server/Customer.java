package server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	public Customer() {
		accounts = new ArrayList<>();
	}

	public Account getAccount(int index) {
        return accounts.get(index);
    }

    public List<Account> getAccounts() {
        return accounts;
    }	

	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

}