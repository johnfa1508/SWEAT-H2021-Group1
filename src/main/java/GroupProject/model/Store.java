package GroupProject.model;

public class Store {
    private String name;
    private double bankBalance;

    // OVERLOAD
    public Store() {

    }

    // CONSTRUCTOR
    public Store(String userName){
        this.name = userName;
        this.bankBalance = 0;
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
    public void depositMoney(double money){
        this.bankBalance += money;
    }

    @Override
    public String toString() {
        return String.format("\n" +
                "\nName: %s" +
                "\nBank balance: %s nok", getName(), getBankBalance());
    }
}
