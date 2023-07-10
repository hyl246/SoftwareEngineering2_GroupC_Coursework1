import server.NewBankServer;
import client.ExampleClient;
import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Welcome to NewBank."); // Test Output

        // Start the NewBankServer
        try {
            new NewBankServer(14002).start();
            System.out.println("New Bank Server started on port 14002.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start the ExampleClient
        try {
            new ExampleClient("localhost", 14002).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
