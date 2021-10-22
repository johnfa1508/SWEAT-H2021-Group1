package GroupProject.model;

public class User {
    private String name;
    private double bankBalance;

    // OVERLOAD
    public User() {

    }

    // CONSTRUCTOR
    public User(String userName, double bankBalance) {
        this.name = userName;
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

    // FUNCTION TO WITHDRAW FROM BANK BALANCE
    public void withdrawMoney(double money) {
        this.bankBalance -= money;
    }

    @Override
    public String toString() {
        return String.format("""
                
                Name: %s
                Bank balance: %s nok
                """, getName(), getBankBalance());
    }
}
