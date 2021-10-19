package GroupProject.model;

public class Antique {
    private String name;
    private String type;
    private String description;
    private double price;

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

    @Override
    public String toString() {
        // TODO: Put buyer's name here?
        return String.format("""
                \nName: %s
                Type: %s
                Description: %s
                Price: %s nok
                """, getName(), getType(), getDescription(), getPrice());
    }
}


