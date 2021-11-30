package GroupProject.model;

public class User {
    private String name;
    private double bankBalance;

    // OVERLOAD
    public User() {

    }

    // CONSTRUCTOR
    public User(String name, double bankBalance) {
        this.name = name;
        this.bankBalance = bankBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    // FUNCTION TO DEPOSIT TO BANK BALANCE
    public void depositMoney(double money) {
        this.bankBalance += money;
    }

    // FUNCTION TO WITHDRAW BANK BALANCE
    public void withdrawMoney(double money) {
        this.bankBalance -= money;
    }

    @Override
    public String toString() {
        return String.format("\n\nName: %s\n" +
                "Bank balance: %s nok", getName(), getBankBalance());
    }
}
