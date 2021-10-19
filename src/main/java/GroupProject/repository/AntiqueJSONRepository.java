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
    public void getAllAntiques() {
        System.out.println("============ ITEMS FOR SALE ============");

        for (Map.Entry<String, Antique> set : antiqueMap.entrySet()) {
            System.out.println(set.getKey() + " = " + set.getValue());
        }

        System.out.println("========================================");
    }

    // FUNCTION TO PRINT SPECIFIC ANTIQUE
    @Override
    public void getAntique(String antiqueName) {
        System.out.println(antiqueMap.get(antiqueName));
    }

    // FUNCTION TO ADD ANTIQUE TO HASHMAP OF ANTIQUES
    @Override
    public void addAntique(Antique newAntique) {
        antiqueMap.put(newAntique.getName(), newAntique);

        writeJSON(fileName);
    }

    // FUNCTION TO DELETE AN ANTIQUE
    @Override
    public void deleteAntique(String antiqueName) {
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

    @Override
    public void purchaseAntique(String antiqueKey) {
        /* TODO: Make purchaseAntique()-function:
            - Receive key from perimeter. When bought remove from list of antiques for sale and give money to store/user
            - When bought put in purchase history? (JSON)?
         */
        System.out.println("purchase");
    }
}
