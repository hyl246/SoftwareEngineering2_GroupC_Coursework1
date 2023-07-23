package server;

public class Payee {
    private String name;
    private String bankName;
    private String accountNumber;

    public Payee(String name, String bankName, String accountNumber) {
        this.name = name;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
