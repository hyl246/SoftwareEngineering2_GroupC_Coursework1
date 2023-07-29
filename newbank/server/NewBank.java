package server;

import java.math.BigDecimal;
import java.util.HashMap;

import client.LoanRequest;

import java.util.ArrayList; 

public class NewBank {

	private static final NewBank bank = new NewBank();

	// Create a HashMap to store the customers ID as Key and (Account, Password) as
	// values
	private HashMap<String, TwoValues> customers;
	// HashMap to store trusted Payees for each user
	private HashMap<String, ArrayList<Payee>> trustedPayees = new HashMap<>();
    // HashMap to store LoanRequests
    private HashMap<String, ArrayList<LoanRequest>> loanRequests;

	public HashMap<String, TwoValues> getCustomers() {
		return customers;
	}

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

		public Customer getCustomer() {
			return name;
		}

		public String getPassword() {
			return password;
		}
	}

	private NewBank() {
		customers = new HashMap<>();
		loanRequests = new HashMap<>();
		trustedPayees = new HashMap<>(); // Initialize the trustedPayee HashMap
		addTestData();
	}

	// Method to create a new loan request for a specific customer
	public synchronized boolean createLoanRequest(CustomerID customerID, double loanAmount, String purpose) {
		Customer customer = getCustomer(customerID);
		if (customer == null) {
			return false;
		}

		LoanRequest loanRequest = new LoanRequest(loanAmount, purpose);
		ArrayList<LoanRequest> customerLoanRequests = loanRequests.getOrDefault(customerID.getKey(), new ArrayList<>());
		customerLoanRequests.add(loanRequest);
		loanRequests.put(customerID.getKey(), customerLoanRequests);
		return true;
	}

	// Method to retrieve all loan requests for a specific customer
	public synchronized ArrayList<LoanRequest> getLoanRequests(CustomerID customerID) {
		return loanRequests.getOrDefault(customerID.getKey(), new ArrayList<>());
	}

	// Method to retrieve all available loan requests
	public synchronized ArrayList<LoanRequest> getAvailableLoanRequests() {
		ArrayList<LoanRequest> availableLoanRequests = new ArrayList<>();
		for (ArrayList<LoanRequest> requests : loanRequests.values()) {
			availableLoanRequests.addAll(requests);
		}
		return availableLoanRequests;
	}

	// Method to approve a loan request for a specific customer
	public synchronized boolean approveLoanRequest(CustomerID customerID, LoanRequest loanRequest) {
		Customer customer = getCustomer(customerID);
		if (customer == null) {
			return false;
		}

		ArrayList<LoanRequest> customerLoanRequests = loanRequests.getOrDefault(customerID.getKey(), new ArrayList<>());
		if (!customerLoanRequests.contains(loanRequest)) {
			return false;
		}

		loanRequest.setStatus("Approved");
		return true;
	}

	public static String isValidPassword(String password) {
		if (password.length() < 12) {
			return "Password must have at least 12 characters";
		}

		if (!password.matches(".*\\d.*")) {
			return "Password must contain at least one digit";
		}

		if (!password.matches(".*[a-z].*")) {
			return "Password must contain at least one lowercase letter";
		}

		if (!password.matches(".*[A-Z].*")) {
			return "Password must contain at least one uppercase letter";
		}

		if (!password.matches(".*[!@#$%^&*()].*")) {
			return "Password must contain at least one special character";
		}

		return "Password is valid";
	}



	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", "savings", 1000.0));
		bhagy.addAccount(new Account("Savings", "savings", 5000.0));
		customers.put("Bhagy", new TwoValues(bhagy, "myPassword123"));

		Customer cindy = new Customer();
		cindy.addAccount(new Account("Savings", "savings", 1500.0));
		customers.put("Cindy", new TwoValues(cindy, "myPassword123"));

		Customer john = new Customer();
		john.addAccount(new Account("Checking", "current", 250.0));
		customers.put("John", new TwoValues(john, "myPassword123"));

		Customer phoebe = new Customer();
		phoebe.addAccount(new Account("Test", "current", 0.0));
		customers.put("Phoebe", new TwoValues(phoebe, "ph"));
	}

	public static NewBank getBank() {
		return bank;
	}

	// Revised the code to allow checking for password
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		try {
			TwoValues retrievedValues = customers.get(userName);
			String pw = retrievedValues.getPasswordValue();
			if (customers.containsKey(userName) && pw.equals(password)) {
				return new CustomerID(userName);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	// check if a payee is in the trusted list
	public synchronized boolean isPayeeTrusted(String userName, Payee payee) {
		ArrayList<Payee> userPayees = trustedPayees.get(userName);
		if (userPayees != null) {
			for (Payee trustedPayee : userPayees) {
				if (trustedPayee.getBankName().equals(payee.getBankName())
						&& trustedPayee.getAccountNumber().equals(payee.getAccountNumber())) {
					return true;
				}
			}
		}
		return false;
	}

	// Set the daily transaction limit for a customer
	public synchronized String setDailyLimit(String userName, double newLimit) {
		CustomerID customerID = new CustomerID(userName);
		Customer customer = getCustomer(customerID);
		if (customer != null) {
			customer.setDailyLimit(newLimit);
			return "Daily limit updated successfully.";
		} else {
			return "Invalid customer.";
		}
	}

	private Customer getCustomer(CustomerID customerID) {
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customerId, String request) {

		// Split the input request to get different values for Command that has multiple
		// input values
		String[] request_split = request.split("\\s+");

		if (customers.containsKey(customerId.getKey())) {
			switch (request_split[0]) {

				// SHOWMYACCOUNTS Command
				case "SHOWMYACCOUNTS":
					return showMyAccounts(customerId);

				// MOVE Command
				case "MOVE":
					try {
						// Parse the amount from the request
						double amount = Double.parseDouble(request_split[3]);

						TwoValues customerInfo = customers.get(customerId.getKey());
						Customer customerObject = null;
						if (customerInfo != null) {
							customerObject = customerInfo.getCustomerValue();
						}

						if (customerObject != null && customerObject.exceedsDailyLimit(amount)) {
							return "FAIL: Transaction failed, amount exceeds daily limit.";
						} else {

							// proceed with transaction
							boolean status = MOVE_MONEY(customerId, request_split[1], request_split[2], request_split[3]);
							if (status) {
								System.out.println("SUCCESS");
								System.out.println("Your updated account balance:\n");
								customerObject.setDailyLimit(amount);
								return showMyAccounts(customerId);
							} else {
								return "FAIL";
							}
						}

					} catch (NumberFormatException e) {
						System.out.println("FAIL\n");
						return "FAIL";
					}

				case "ADDMONEYTOACCOUNT":
					try {
						boolean status = addMoneyToAccount(customerId, request_split[1], request_split[2]);
						if (status) {
							System.out.println("SUCCESS");
							System.out.println("Your updated account balance:\n");
							return showMyAccounts(customerId);
						} else {
							return "FAIL";
						}
					} catch (NumberFormatException e) {
						System.out.println("FAIL\n");
						return "FAIL";
					}

				case "SUBTRACTMONEYFROMACCOUNT":
					try {
						boolean status = subtractMoneyFromAccount(customerId, request_split[1], request_split[2]);
						if (status) {
							System.out.println("SUCCESS");
							System.out.println("Your updated account balance:\n");
							return showMyAccounts(customerId);
						} else {
							return "FAIL";
						}
					} catch (NumberFormatException e) {
						System.out.println("FAIL\n");
						return "FAIL";
					}

					// NEW ACCOUNT command
				case "NEWACCOUNT":
					System.out.println("To create a new account, type: NEWACCOUNT <Desired username>");
					if (request_split.length < 4) {
						return "FAIL";
					}
					try {
						double openingBalance = Double.parseDouble(request_split[3]);
						return newAccount(customerId, request_split[1], request_split[2], openingBalance);
					} catch (NumberFormatException e) {
						return "FAIL: Invalid amount for openingBalance";
					}

				case "CREDITLIMITCHECK":
					CreditChecker creditChecker = new CreditChecker();
					creditChecker.runCreditCheck();
					return "SUCCESS";

				default:
					return "FAIL";
			}
		}
		return "";
	}

	// NEW ACCOUNT command
	public synchronized String newAccount(CustomerID customer, String accountName, String accountType,
			double openingBalance) {
		if (openingBalance < 50) {
			return "FAIL: Minimum opening balance is 50";
		}
		if (accountName.length() > 30) {
			return "FAIL: Account name should be no longer than 30 characters";
		}
		if (!accountName.matches("[a-zA-Z0-9]+")) {
			return "FAIL:account name should be in alphanumerics";
		}
		if (!accountType.equals("savings") && !accountType.equals("current")) {
			return "FAIL: Invalid account type";
		}
		TwoValues retrievedValues = customers.get(customer.getKey());
		Customer customerObj = retrievedValues.getCustomerValue();
		customerObj.addAccount(new Account(accountName, accountType, openingBalance));
		return "SUCCESS: Account created successfully";
	}

	private String showMyAccounts(CustomerID customer) {
		TwoValues retrievedValues = customers.get(customer.getKey());
		Customer account = retrievedValues.getCustomerValue();
		return account.accountsToString();
	}

	private boolean subtractMoneyFromAccount(CustomerID customerId, String accountName, String amount) {
		Double amount_to_num;

		// TODO: Abstract this, duplicated from MOVE_MONEY method
		try {
			amount_to_num = Double.parseDouble(amount);

			if (amount_to_num < 0) {
				System.out.println("Invalid amount entered. Amount should be larger than 0.\n");
				return false;
			}
			// Check if the amount is up to 2 decimal places
			if ((BigDecimal.valueOf(amount_to_num).scale() > 2)) {
				System.out.println("Invalid amount entered, give your amount to 2 decimal places.\n");
				return false;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount entered.\n");
			return false;
		}

		Customer customer = customers.get(customerId.getKey()).getCustomerValue();

		for (Account a : customer.getAccounts()) {
			if (a.getAccountName().equals(accountName)) {
				a.credit_balance(amount_to_num);
				return true;
			}
		}

		return false;
	}

	private boolean addMoneyToAccount(CustomerID customerId, String accountName, String amount) {
		Double amount_to_num;

		// TODO: Abstract this, duplicated from MOVE_MONEY method
		try {
			amount_to_num = Double.parseDouble(amount);

			if (amount_to_num < 0) {
				System.out.println("Invalid amount entered. Amount should be larger than 0.\n");
				return false;
			}
			// Check if the amount is up to 2 decimal places
			if ((BigDecimal.valueOf(amount_to_num).scale() > 2)) {
				System.out.println("Invalid amount entered, give your amount to 2 decimal places.\n");
				return false;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount entered.\n");
			return false;
		}

		Customer customer = customers.get(customerId.getKey()).getCustomerValue();

		for (Account a : customer.getAccounts()) {
			if (a.getAccountName().equals(accountName)) {
				a.debit_balance(amount_to_num);
				return true;
			}
		}

		return false;
	}

	private boolean MOVE_MONEY(CustomerID customer, String amount, String from, String to) {

		// Initial status for accounts
		int return_status = 0;
		boolean status_from = false;
		boolean status_to = false;
		Double amount_to_num;

		TwoValues retrievedValues = customers.get(customer.getKey());
		Customer account = retrievedValues.getCustomerValue();

		try {
			amount_to_num = Double.parseDouble(amount);
			if (amount_to_num < 0) {
				System.out.println("Invalid amount entered. Amount should be larger than 0.\n");
				return_status = 1;
			}
			// Check if the amount is up to 2 decimal places
			if ((BigDecimal.valueOf(amount_to_num).scale() > 2)) {
				System.out.println("Invalid amount entered, give your amount to 2 decimal places.\n");
				return_status = 1;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount entered.\n");
			return_status = 1;
			return false;
		}

		// Check if the input accounts from and to are the same
		if (from.equals(to)) {
			System.out.println("Invalid account entered. Cannot transfer money within the same account name.\n");
			return_status = 1;
		}

		// Check if the accounts exist
		for (Account a : account.getAccounts()) {
			if (a.getAccountName().equals(from)) {
				status_from = true;
			}
			if (a.getAccountName().equals(to)) {
				status_to = true;
			}
		}
		if (!status_from) {
			System.out.println(from + " Account does not exist.");
			return_status = 1;
		}
		if (!status_to) {
			System.out.println(to + " Account does not exist.");
			return_status = 1;
		}

		// Check if 'from' account has sufficient money
		// Move money from account "from" to account "to"
		for (Account a : account.getAccounts()) {
			if (a.getAccountName().equals(from)) {
				if (a.checkBalance(amount_to_num) == true) {
					a.credit_balance(amount_to_num);
				} else {
					return_status = 1;
				}
			}
			if (a.getAccountName().equals(to)) {
				if (a.checkBalance(amount_to_num) == true) {
					if (a.getAccountName().equals(to)) {
						a.debit_balance(amount_to_num);
					}
				}
			}
		}
		if (return_status == 0) {
			return true;
		}
		if (return_status == 1) {
			return false;
		}
		return true;
	}
}
