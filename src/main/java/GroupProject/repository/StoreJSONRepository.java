package GroupProject.repository;

import GroupProject.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreJSONRepository implements StoreRepository {
    private String fileName;
    HashMap<String, Store> storeMap = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    // CONSTRUCTOR
    public StoreJSONRepository(String fileName) {
        this.fileName = fileName;

        readJSON(fileName);
        writeJSON(fileName);
    }

    // FUNCTION TO READ JSON-FILE
    public void readJSON(String fileName) {
        Store[] storeArray = new Store[0];
        HashMap<String, Store> returnMap = new HashMap<>();
        File file = new File(fileName);

        try {
            storeArray = objectMapper.readValue(file, Store[].class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (Store store : storeArray) {
            returnMap.put(store.getName(), store);
        }

        storeMap = returnMap;
    }

    // FUNCTION TO WRITE JSON-FILE
    public void writeJSON(String fileName) {
        ArrayList<Store> storeArray = new ArrayList<>(storeMap.values());

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), storeArray);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // FUNCTION TO ADD STORE TO HASHMAP OF STORES
    public void addStore(Store newStore) {
        // Add new element to hashmap with key:value pair
        storeMap.put(newStore.getName(), newStore);

        writeJSON(fileName);
    }

    // FUNCTION TO GET SPECIFIC STORE
    public Store getStore(String userKey) {
        // For-loop that iterates through userMap and finds user value using userKey
        for (Map.Entry<String, Store> storeSet : storeMap.entrySet()) {
            if (storeSet.getValue().getName().equalsIgnoreCase(userKey)) {
                return storeSet.getValue();
            }
        }

        return null;
    }
}