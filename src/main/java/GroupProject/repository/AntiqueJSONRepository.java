package GroupProject.repository;

import GroupProject.model.Antique;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        for (Antique value : antiquesArray) {
            returnMap.put(value.getName(), value);
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
    public void showAllAntiques() {
        System.out.println("============ ITEMS FOR SALE ============");

        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!set.getValue().getSold()) {
                System.out.println(set.getKey() + " = " + set.getValue());
            }
        }

        System.out.println("========================================");
    }

    // FUNCTION TO SHOW SPECIFIC ANTIQUE TYPE
    @Override
    public void showSpecificAntique(String antiqueType) {
        System.out.println("\n============ ITEMS FOR SALE ============");

        // For-loop that prints out all antiques in antiqueMap
        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!set.getValue().getSold()) {
                if (set.getValue().getType().equalsIgnoreCase(antiqueType)) {
                    System.out.println(set.getKey() + " = " + set.getValue());
                }
            }
        }

        System.out.println("========================================");
    }

    // FUNCTION TO SHOW ANTIQUE-TYPES CURRENTLY FOR SALE
    @Override
    public void showAntiqueTypes() {
        // Arraylist to store types of antiques
        ArrayList<String> antiqueTypes = new ArrayList<>();

        // For-loop that goes through antiqueMap and only adds type to arraylist if it's not added yet
        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!set.getValue().getSold()) {
                // Check if that type is already in list
                if (!antiqueTypes.contains(set.getValue().getType())) {
                    antiqueTypes.add(set.getValue().getType());
                }
            }
        }

        // Print types of antiques currently for sale
        for (String type : antiqueTypes) {
            System.out.println(type);
        }
    }

    // FUNCTION TO SHOW ANTIQUE NAMES
    @Override
    public void showAntiqueNames() {
        // Arraylist to store names of antiques
        ArrayList<String> antiqueNames = new ArrayList<>();

        // For-loop that goes through antiqueMap and adds names/keys to arraylist
        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!set.getValue().getSold()) {
                antiqueNames.add(set.getValue().getName());
            }
        }

        // Print names/keys
        for (String names : antiqueNames) {
            System.out.println(names);
        }
    }

    @Override
    public void showPurchaseHistory() {
        System.out.println("\n================ HISTORY ================");

        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is sold
            if (set.getValue().getSold()) {
                System.out.println(set.getKey() + " = " + set.getValue());
            }
        }

        System.out.println("=========================================");
    }

    // FUNCTION TO GET SPECIFIC ANTIQUE
    @Override
    public Antique getAntique(String antiqueKey) {
        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            // If antique is not sold
            if (!set.getValue().getSold()) {
                if (set.getValue().getName().equalsIgnoreCase(antiqueKey)) {
                    return set.getValue();
                }
            }
        }

        return null;
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
    public void purchaseAntique(String antiqueKey) {
        /* TODO: Make purchaseAntique()-function:
            - Receive key from perimeter. When bought remove from list of antiques for sale and give money to store/user
            - When bought put in purchase history? (JSON)?
         */
        System.out.println("purchase");
    }
}
