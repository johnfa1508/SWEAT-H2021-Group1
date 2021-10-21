package GroupProject.model;

public class Antique {
    private String name;
    private String type;
    private String description;
    private double price;
    private boolean sold;
    private String sellerName;
    private String buyerName;

    // OVERLOAD
    public Antique() {

    }

    // CONSTRUCTOR
    public Antique(String name, String type, String description, double price, String sellerName) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.sellerName = sellerName;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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
            return String.format("""
                
                Name: %s
                Type: %s
                Description: %s
                Price: %s nok
                Seller: %s
                Buyer: %s
                """, getName(), getType(), getDescription(), getPrice(), getSellerName(), getBuyerName());
            }

        return String.format("""
                
                Name: %s
                Type: %s
                Description: %s
                Price: %s nok
                Seller: %s
                """, getName(), getType(), getDescription(), getPrice(), getSellerName());
    }
}
