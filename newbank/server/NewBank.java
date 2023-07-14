package server;

import java.math.BigDecimal;
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
		
		//Split the input request to get different values for Command that has multiple input values
		String[] request_split = request.split("\\s+");

		if(customers.containsKey(customer.getKey())) {
			switch(request_split[0]) {

			//SHOWMYACCOUNTS Command	
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);

			//MOVE Command
			case "MOVE" : 
				try{
					boolean status = MOVE_MONEY(customer, request_split[1],request_split[2],request_split[3]);
					if(status){
						System.out.println("SUCCESS");
						System.out.println("Your updated account balance:\n");
						return showMyAccounts(customer);
					}
				} catch(NumberFormatException e) {
					System.out.println("FAIL\n");
				}			
			default : return "FAIL";
			}
		}
		return "";
	}
	
	private String showMyAccounts(CustomerID customer) {
    	TwoValues retrievedValues = customers.get(customer.getKey());
    	Customer account = retrievedValues.getCustomerValue();
    return account.accountsToString();
	}

	private boolean MOVE_MONEY(CustomerID customer, String amount, String from , String to) {

		//Initial status for accounts
		int return_status=0;
		boolean status_from=false;
		boolean status_to=false;
		Double amount_to_num;

    	TwoValues retrievedValues = customers.get(customer.getKey());
    	Customer account = retrievedValues.getCustomerValue();

		try {
			amount_to_num = Double.parseDouble(amount);
			if(amount_to_num<0){
				System.out.println("Invalid amount entered. Amount should be larger than 0.\n");
				return_status=1;
			}
			//Check if the amount is up to 2 decimal places
			if((BigDecimal.valueOf(amount_to_num).scale() > 2)){
				System.out.println("Invalid amount entered, give your amount to 2 decimal places.\n");
				return_status=1;
			}
		} catch(NumberFormatException e) {
			System.out.println("Invalid amount entered.\n");
			return_status=1;
			return false;
		}

		//Check if the input accounts from and to are the same
		if (from.equals(to)){
			System.out.println("Invalid account entered. Cannot transfer money within the same account name.\n");
			return_status=1;
		}			
		
		//Check if the accounts exist
		for (Account a : account.getAccounts()) {
			if (a.getAccountName().equals(from)) {
				status_from=true;
			}
			if (a.getAccountName().equals(to)) {
				status_to=true;
			}
		}
		if (!status_from) {
			System.out.println(from + " Account does not exist.");
			return_status=1;
		}
		if (!status_to) {
			System.out.println(to + " Account does not exist.");
			return_status=1;
		}		

		//Check if 'from' account has sufficient money
		//Move money from account "from" to account "to"
		for (Account a : account.getAccounts()){
			if(a.getAccountName().equals(from)){
				if(a.checkBalance(amount_to_num) == true){
				a.credit_balance(amount_to_num);
				}
				else{
				return_status=1;
				}
			}
			if(a.getAccountName().equals(to)){
				if(a.checkBalance(amount_to_num) == true){
					if(a.getAccountName().equals(to)){
						a.debit_balance(amount_to_num);
					}
				}
			}
		}
		if(return_status==0){
			return true;
		}
		if(return_status==1){
			return false;
		}
	return true;
	}
}