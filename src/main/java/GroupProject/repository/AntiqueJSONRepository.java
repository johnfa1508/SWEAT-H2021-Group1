package GroupProject.repository;

import GroupProject.model.Antique;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import GroupProject.model.Store;
import GroupProject.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AntiqueJSONRepository implements AntiqueRepository {
    private String fileName;
    HashMap<String, Antique> antiqueMap = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    // CONSTRUCTOR
    public AntiqueJSONRepository(String fileName) {
        this.fileName = fileName;

        readJSON(fileName);
        writeJSON(fileName);
    }

    // FUNCTION TO READ JSON-FILE
    public void readJSON(String fileName) {
        Antique[] antiquesArray = new Antique[0];
        HashMap<String, Antique> returnMap = new HashMap<>();
        File file = new File(fileName);

        try {
            antiquesArray = objectMapper.readValue(file, Antique[].class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (Antique antique : antiquesArray) {
            returnMap.put(antique.getName(), antique);
        }

        antiqueMap = returnMap;
    }

    // FUNCTION TO WRITE JSON-FILE
    public void writeJSON(String fileName) {
        ArrayList<Antique> antiquesArray = new ArrayList<>(antiqueMap.values());

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), antiquesArray);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // FUNCTION TO PRINT OUT ALL ANTIQUES
    @Override
    public HashMap<String, Antique> showAntiquesForSale() {
        HashMap<String, Antique> antiques = new HashMap<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!antiqueSet.getValue().getSold()) {
                antiques.put(antiqueSet.getKey(), antiqueSet.getValue());
            }
        }

        return antiques;
    }

    // FUNCTION TO SHOW SPECIFIC ANTIQUE TYPE
    @Override
    public HashMap<String, Antique> showSpecificAntique(String antiqueType) {
        HashMap<String, Antique> specificAntiqueTypes = new HashMap<>();

        // For-loop that prints out all antiques in antiqueMap
        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!antiqueSet.getValue().getSold()) {
                if (antiqueSet.getValue().getType().equalsIgnoreCase(antiqueType)) {
                    specificAntiqueTypes.put(antiqueSet.getKey(), antiqueSet.getValue());
                }
            }
        }

        return specificAntiqueTypes;
    }

    // FUNCTION TO SHOW ANTIQUE-TYPES CURRENTLY FOR SALE
    @Override
    public ArrayList<String> showAntiqueTypes() {
        // Arraylist to store types of antiques
        ArrayList<String> antiqueTypesArray = new ArrayList<>();

        // For-loop that goes through antiqueMap and only adds type to arraylist if it's not added yet
        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!antiqueSet.getValue().getSold()) {
                // Check if that type is already in list
                if (!antiqueTypesArray.contains(antiqueSet.getValue().getType())) {
                    antiqueTypesArray.add(antiqueSet.getValue().getType());
                }
            }
        }

        return antiqueTypesArray;
    }

    // FUNCTION TO SHOW ANTIQUE NAMES. RECEIVES BOOLEAN TO CHOOSE SHOW ALL OR NOT
    @Override
    public ArrayList<String> showAntiqueNames(boolean showAll, String sellType) {
        // Arraylist to store names of antiques
        ArrayList<String> antiqueNamesArray = new ArrayList<>();

        // For-loop that goes through antiqueMap and adds names/keys to arraylist
        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is not sold
            if (showAll) {
                antiqueNamesArray.add(antiqueSet.getValue().getName());
            } else {
                if (!antiqueSet.getValue().getSold()) {
                    // If antique sellType is equals to sellType in parameter, add to return list
                    if (antiqueSet.getValue().getSellType().equalsIgnoreCase(sellType)) {
                        antiqueNamesArray.add(antiqueSet.getValue().getName());
                    } else if (sellType.equalsIgnoreCase("ALL")) {
                        // Show all names
                        antiqueNamesArray.add(antiqueSet.getValue().getName());
                    }
                }
            }
        }

        return antiqueNamesArray;
    }

    // FUNCTION TO SHOW PURCHASE HISTORY
    @Override
    public HashMap<String, Antique> showPurchaseHistory() {
        HashMap<String, Antique> purchaseHistory = new HashMap<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is sold
            if (antiqueSet.getValue().getSold()) {
                purchaseHistory.put(antiqueSet.getKey(), antiqueSet.getValue());
            }
        }

        return purchaseHistory;
    }

    // FUNCTION TO CHECK IF LIST OF ITEMS FOR SALE IS EMPTY
    @Override
    public boolean isEmpty() {
        ArrayList<String> antiqueNamesArray = new ArrayList<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!antiqueSet.getValue().getSold()) {
                antiqueNamesArray.add(antiqueSet.getValue().getName());
            }
        }

        return antiqueNamesArray.isEmpty();
    }

    // FUNCTION TO GET SPECIFIC ANTIQUE
    @Override
    public Antique getAntique(String antiqueKey) {
        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            if (antiqueSet.getValue().getName().equalsIgnoreCase(antiqueKey)) {
                return antiqueSet.getValue();
            }
        }

        return null;
    }

    // FUNCTION TO RETURN ANTIQUES FAVORITED BY USER
    @Override
    public HashMap<String, Antique> favoritedByUser(User user) {
        HashMap<String, Antique> favoritedAntiquesMap = new HashMap<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            if (antiqueSet.getValue().getFavorites().contains(user.getName())) {
                favoritedAntiquesMap.put(antiqueSet.getKey(), antiqueSet.getValue());
            }
        }

        return favoritedAntiquesMap;
    }

    // FUNCTION TO ADD ANTIQUE TO HASHMAP OF ANTIQUES
    @Override
    public void addAntique(Antique newAntique) {
        // Add new element to hashmap with key:value pair
        antiqueMap.put(newAntique.getName(), newAntique);

        writeJSON(fileName);
    }

    // FUNCTION TO DELETE AN ANTIQUE
    @Override
    public void deleteAntique(String antiqueName) {
        // Remove element from hashmap using key
        antiqueMap.remove(antiqueName);

        writeJSON(fileName);
    }

    // FUNCTION TO EDIT/REPLACE AN ANTIQUE
    @Override
    public void editAntique(String antiqueKey, Antique antique) {
        // Replace values inside the antiqueKey with new antique
        antiqueMap.replace(antiqueKey, antique);

        writeJSON(fileName);
    }

    // FUNCTION TO PURCHASE AN ANTIQUE
    @Override
    public void purchaseAntique(Antique antique) {
        // Sets item-state to sold
        antique.setSold(true);

        writeJSON(fileName);
    }

    // FUNCTION TO ADD NAME OF USER TO FAVORITES
    @Override
    public void addFavorite(Antique antique, User user) {
        antique.addFavorites(user.getName());

        writeJSON(fileName);
    }

    // FUNCTION TO REMOVE NAME OF USER FROM FAVORITES
    @Override
    public void removeFavorite(Antique antique, User user) {
        antique.removeFavorites(user.getName());

        writeJSON(fileName);
    }

    // FUNCTION TO CHECK IF ANTIQUE EXISTS ALREADY
    @Override
    public boolean antiqueExists(String antiqueName) {
        return antiqueMap.containsKey(antiqueName);
    }

    // FUNCTION TO SET LAST BIDDER
    @Override
    public void writeLastBidder(Antique antique, User user) {
        antique.setLastBidder(user.getName());

        writeJSON(fileName);
    }

    // FUNCTION TO SET BUYER
    @Override
    public void setBuyer(Antique antique, User user) {
        antique.setBuyer(user.getName());

        writeJSON(fileName);
    }

    // FUNCTION THAT RETURNS STORE'S ACTIVE BIDS
    @Override
    public HashMap<String, Antique> storeBids(Store store) {
        HashMap<String, Antique> storeBidsMap = new HashMap<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is sold by the store name and last bidder exists
            if (Objects.equals(antiqueSet.getValue().getSellerName(),
                    store.getName()) && antiqueSet.getValue().getLastBidder() != null) {
                storeBidsMap.put(antiqueSet.getKey(), antiqueSet.getValue());
            }
        }

        return storeBidsMap;
    }

    // FUNCTION THAT RETURNS USER'S ACTIVE BIDS
    @Override
    public HashMap<String, Antique> userBids(User user) {
        HashMap<String, Antique> userBidsMap = new HashMap<>();

        for (Map.Entry<String, Antique> antiqueSet : antiqueMap.entrySet()) {
            // If antique is sold by the store name and last bidder exists
            if (Objects.equals(antiqueSet.getValue().getLastBidder(),
                    user.getName()) && antiqueSet.getValue().getLastBidder() != null) {
                userBidsMap.put(antiqueSet.getKey(), antiqueSet.getValue());
            }
        }

        return userBidsMap;
    }
}
