package server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	
	//Create a HashMap to store the customers ID as Key and (Account, Password) as values
	private HashMap<String,TwoValues> customers;

	class TwoValues {
		private String password;
		private Customer name;
		
		public TwoValues(Customer name, String password) {
			this.name = name;
			this.password = password;
		}
		
		public Customer getCustomerValue() {
			return name;
		}
		
		public String getPasswordValue() {
			return password;
		}
	}
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	//Revised the test data to include password
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		bhagy.addAccount(new Account("Savings", 5000.0));
		String bhagy_pw = "myPassword123";
		TwoValues values1 = new TwoValues(bhagy, bhagy_pw);
		customers.put("Bhagy", values1);
		
		Customer cindy = new Customer();
		cindy.addAccount(new Account("Savings", 1500.0));
		String cindy_pw = "myPassword123";
		TwoValues values2 = new TwoValues(cindy, cindy_pw);
		customers.put("Cindy", values2);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		String john_pw = "myPassword123";
		TwoValues values3 = new TwoValues(john, john_pw);
		customers.put("John", values3);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	//Revised the code to allow checking for password
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		try{
			TwoValues retrievedValues = customers.get(userName);
			String pw = retrievedValues.getPasswordValue();
			if(customers.containsKey(userName) && pw.equals(password)) {
				return new CustomerID(userName);
			}
		}catch (Exception e) {
			return null;
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
    	TwoValues retrievedValues = customers.get(customer.getKey());
    	Customer account = retrievedValues.getCustomerValue();
    return account.accountsToString();
	}

}
