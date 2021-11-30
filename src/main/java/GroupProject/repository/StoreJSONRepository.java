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
    @Override
    public void addStore(Store newStore) {
        // Add new element to hashmap with key:value pair
        storeMap.put(newStore.getName(), newStore);

        writeJSON(fileName);
    }

    // FUNCTION TO REMOVE STORE FROM HASHMAP OF STORES
    @Override
    public void removeStore(Store store) {
        // Remove element from hashmap using key
        storeMap.remove(store.getName());

        writeJSON(fileName);
    }

    // FUNCTION TO GET SPECIFIC STORE
    @Override
    public Store getStore(String storeKey) {
        // For-loop that iterates through userMap and finds store value using storeKey
        for (Map.Entry<String, Store> storeSet : storeMap.entrySet()) {
            if (storeSet.getValue().getName().equalsIgnoreCase(storeKey)) {
                return storeSet.getValue();
            }
        }

        return null;
    }

    // FUNCTION TO SHOW ALL STORES
    @Override
    public HashMap<String, Store> showStores() {
        return new HashMap<>(storeMap);
    }

    // FUNCTION TO SHOW STORE NAMES
    @Override
    public ArrayList<String> showStoreNames(){
        // Arraylist to store names of stores
        ArrayList<String> storeNamesArray = new ArrayList<>();

        // For-loop that iterates through storeMap and adds names/keys to arraylist
        for (Map.Entry<String, Store> storeSet : storeMap.entrySet()) {
            storeNamesArray.add(storeSet.getValue().getName());
        }

        return storeNamesArray;
    }

    // FUNCTION TO DEPOSIT MONEY TO BANK BALANCE
    @Override
    public void depositMoney(Store store, double money) {
        getStore(store.getName()).depositMoney(money);

        writeJSON(fileName);
    }

    // FUNCTION TO WITHDRAW MONEY FROM BANK BALANCE
    @Override
    public void withdrawMoney(Store store, double money) {
        getStore(store.getName()).depositMoney(money);

        writeJSON(fileName);
    }

    // FUNCTION TO CHECK IF STORE EXISTS
    @Override
    public boolean storeExists(String storeName) {
        return storeMap.containsKey(storeName);
    }
}
