package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import client.LoanRequest;
import server.NewBank.TwoValues;

public class NewBankServer extends Thread{
	
	private ServerSocket server;
	
	public NewBankServer(int port) throws IOException {
		server = new ServerSocket(port);
	}
	
	public void run() {
		// starts up a new client handler thread to receive incoming connections and process requests
		System.out.println("New Bank Server listening on " + server.getLocalPort());
		try {
			while(true) {
				Socket s = server.accept();
				NewBankClientHandler clientHandler = new NewBankClientHandler(s);
				clientHandler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}
	 // Method to handle loan-related commands from clients
    public String handleCommand(CustomerID customerID, String command) {
        String response = "FAIL: Invalid command";
        Customer customer = findCustomer(customerID);
        if (customer == null) {
            return "FAIL: Invalid customer";
        }
        String[] commandSplit = command.split("\\s+");
        switch (commandSplit[0].toUpperCase()) {
            case "CREATELOANREQUEST":
                if (commandSplit.length >= 3) {
                    double loanAmount;
                    try {
                        loanAmount = Double.parseDouble(commandSplit[1]);
                        String purpose = command.substring(command.indexOf(commandSplit[2]));
                        LoanRequest loanRequest = new LoanRequest(loanAmount, purpose);
                        customer.addLoanRequest(loanRequest);
                        response = "SUCCESS: Loan request created with ID " + loanRequest.getId();
                    } catch (NumberFormatException e) {
                        response = "FAIL: Invalid loan amount";
                    }
                } else {
                    response = "FAIL: Insufficient parameters for creating a loan request";
                }
                break;
            case "GETLOANREQUESTS":
                StringBuilder loanRequestsList = new StringBuilder();
                List<LoanRequest> customerLoanRequests = customer.getLoanRequests();
                if (!customerLoanRequests.isEmpty()) {
                    for (LoanRequest loanRequest : customerLoanRequests) {
                        loanRequestsList.append(loanRequest.toString()).append("\n");
                    }
                    response = "SUCCESS: Loan requests for customer:\n" + loanRequestsList.toString();
                } else {
                    response = "SUCCESS: No loan requests for customer";
                }
                break;
            // Implement other loan-related commands such as getAvailableLoanRequests, approveLoanRequest, etc.
            // Need to access the NewBank's loan-related methods to perform the requested actions.
            default:
                // Handle other commands if needed
                break;
        }
        return response;
    }
	
	// Find the customer based on their ID
    private Customer findCustomer(CustomerID customerID) {
        // Access the NewBank's customer data structure to find the customer based on their ID.
        NewBank newBank = NewBank.getBank();
        return newBank.getCustomers().get(customerID.getKey()).getCustomer();
    }
}
	// Implemented in Main Class
	// public static void main(String[] args) throws IOException {
	// 	// starts a new NewBankServer thread on a specified port number
	// 	new NewBankServer(14002).start();
	// }

