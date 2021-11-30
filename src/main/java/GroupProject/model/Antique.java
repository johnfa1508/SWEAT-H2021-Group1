package GroupProject.model;

import java.util.ArrayList;

public class Antique {
    private String name;
    private String type;
    private String description;
    private double price;
    private boolean sold;
    private String sellerName;
    private String lastBidder;
    private ArrayList<String> favorites = new ArrayList<>();
    private String sellType;
    private String buyer;

    // OVERLOAD
    public Antique() {

    }

    // CONSTRUCTOR
    public Antique(String name, String type, String description, double price, String sellerName, String sellType) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.sellerName = sellerName;
        this.sellType = sellType;
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

    public String getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
        this.lastBidder = lastBidder;
    }

    public boolean getSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public ArrayList<String> getFavorites() {
        return new ArrayList<>(favorites);
    }

    public void addFavorites(String userName) {
        favorites.add(userName);
    }

    public void removeFavorites(String userName) {
        favorites.remove(userName);
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        if (getSellType().equalsIgnoreCase("AUCTION")) {
            return String.format("\n" +
                    "\nName: %s" +
                    "\nType: %s" +
                    "\nDescription: %s" +
                    "\nPrice: %s" +
                    "\nSeller: %s" +
                    "\nLast bidder: %s" +
                    "\nFavorited by: %s" +
                    "\nSelling type: %s", getName(), getType(), getDescription(), getPrice(), getSellerName(),
                    getLastBidder(), getFavorites(), getSellType());
        } else {
            return String.format("\n" +
                            "\nName: %s" +
                            "\nType: %s" +
                            "\nDescription: %s" +
                            "\nPrice: %s" +
                            "\nSeller: %s" +
                            "\nBuyer: %s" +
                            "\nFavorited by: %s" +
                            "\nSelling type: %s", getName(), getType(), getDescription(), getPrice(), getSellerName(),
                    getBuyer(), getFavorites(), getSellType());
        }
    }
}
