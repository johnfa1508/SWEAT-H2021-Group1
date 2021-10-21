package GroupProject.model;

public class Antique {
    private String name;
    private String type;
    private String description;
    private double price;
    private String status;
    private boolean sold;

    public Antique() {

    }

    // CONSTRUCTOR
    public Antique(String name, String type, String description, double price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        if (getSold()) {
            setStatus("Sold");
        } else {
            setStatus("Not sold");
        }

        // TODO: Put buyer's name here?
        if (getSold()) {
            return String.format("""
                
                Name: %s
                Type: %s
                Description: %s
                Price: %s nok
                Status: %s
                Buyer: userBuyer
                """, getName(), getType(), getDescription(), getPrice(), getStatus());
            }

        return String.format("""
                
                Name: %s
                Type: %s
                Description: %s
                Price: %s nok
                Status: %s
                """, getName(), getType(), getDescription(), getPrice(), getStatus());
    }
}
