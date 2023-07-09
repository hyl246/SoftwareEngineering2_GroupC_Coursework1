
//Import Packages
import newbank.server.*;
import newbank.client.*;

public class Main {
    public static void main(String[] args) {
        // Use classes from the server subfolder
        Account account = new Account();
        Customer customer = new Customer();
        CustomerID customerID = new CustomerID();
        NewBank newBank = new NewBank();
        NewBankClientHandler clientHandler = new NewBankClientHandler();
        NewBankServer server = new NewBankServer();

        // Use classes from the client subfolder
        ExampleClient exampleClient = new ExampleClient();

        // Rest of your code
    }
}
