package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ExampleClient extends Thread{
	
	private Socket server;
	private PrintWriter bankServerOut;	
	private BufferedReader userInput;
	private Thread bankServerResponceThread; // Incorrect naming
	
	public ExampleClient(String ip, int port) throws UnknownHostException, IOException {
		server = new Socket(ip,port);
		userInput = new BufferedReader(new InputStreamReader(System.in)); 
		bankServerOut = new PrintWriter(server.getOutputStream(), true); 
		
		bankServerResponceThread = new Thread() {
			private BufferedReader bankServerIn = new BufferedReader(new InputStreamReader(server.getInputStream())); 
			public void run() {
				try {
					while(true) {
						String responce = bankServerIn.readLine();
						System.out.println(responce);
						if(responce.equals("EXIT")) {
							try {
								server.close();
							} catch (IOException e) {
								System.err.println("Could not close port: 14002.");
								System.exit(1);
							}
						}	
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		};
		bankServerResponceThread.start();
	}
	
	
	public void run() {
		while(true) {
			try {
				while(true) {
					String command = userInput.readLine();
					bankServerOut.println(command);
					if(command.equals("EXIT")) {
						try {
							server.close();
						} catch (IOException e) {
							System.err.println("Could not close port: 14002.");
							System.exit(1);
						}
					}	
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Method to send a loan request to the server
	public void createLoanRequest(double loanAmount, String purpose) {
		String command = "CREATELOAN " + loanAmount + " " + purpose;
		bankServerOut.println(command);
	}


    // Method to retrieve all loan requests for the client
    public ArrayList<LoanRequest> viewLoanRequests() {
        String command = "VIEWMYLOANREQUESTS";
        bankServerOut.println(command);
        // You need to implement parsing the response from the server to get the loan requests
        // and return them as an ArrayList<LoanRequest>
        // Parse the response here and return the loan requests.
        return new ArrayList<>(); // Placeholder, replace this with the actual loan requests
    }
	
	// Method to retrieve all available loan requests from the server
	public ArrayList<LoanRequest> viewAvailableLoanRequests() {
		String command = "VIEWAVAILABLELOANREQUESTS";
		bankServerOut.println(command);
		// You need to implement parsing the response from the server to get the
		// available loan requests
		// and return them as an ArrayList<LoanRequest>
		// Parse the response here and return the available loan requests.
		return new ArrayList<>(); // Placeholder, replace this with the actual available loan requests
	}

	// Method to approve a loan request received from the server
	public void approveLoanRequest(LoanRequest loanRequest) {
		String command = "APPROVELOAN " + loanRequest.getLoanAmount() + " " + loanRequest.getPurpose();
		bankServerOut.println(command);
	}	
}
