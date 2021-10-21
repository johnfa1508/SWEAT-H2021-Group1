package GroupProject.model;

public class User {
    private String name;
    private double bankBalance;
    private String type;

    // OVERLOAD
    public User() {

    }

    // CONSTRUCTOR
    public User(String userName, double bankBalance, String type) {
        this.name = userName;
        this.bankBalance = bankBalance;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addToBalance(double money) {
        this.bankBalance += money;
    }

    public void deductFromBalance(double money) {
        this.bankBalance -= money;
    }
}
