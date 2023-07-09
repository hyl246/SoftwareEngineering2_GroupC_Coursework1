package newbank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	
	public void run() {
		// keep getting requests from the client and processing them
		try {
			// ask for user name
			out.println("Enter Username");
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them 
			if(customer != null) {
				out.println("Log In Successful. What do you want to do?\n");
				out.println("Select an option and type a command: \n For example, type SHOWMYACOUNTS to show your accounts.\n");
				out.println("1. SHOWMYACCOUNTS - Show my accounts");
				out.println("2. NEWACCOUNT - Create new account (Savings/Checking)");
				out.println("3. MOVE - Transfer money to other accounts");
				out.println("4. QUIT\n");
				while(true) {
					String request = in.readLine();
					if(request.equals("QUIT")){
						out.println("*  Thank you for using NewBank  *\n Have a good day");
						System.exit(0);
					}
					System.out.println("Request from " + customer.getKey());
					String responce = bank.processRequest(customer, request);
					out.println(responce);
					out.println("Continue to other service, select an option and type a command: \n");
					out.println("1. SHOWMYACCOUNTS - Show my accounts");
					out.println("2. NEWACCOUNT - Create new account (Savings/Checking)");
					out.println("3. MOVE - Transfer money to other accounts");
					out.println("4. QUIT\n");
					if(request.equals("QUIT")){
						out.println("*  Thank you for using NewBank  *\n Have a good day");
						System.exit(0);
					}
				}
			}
			else {
				out.println("Log In Failed");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
