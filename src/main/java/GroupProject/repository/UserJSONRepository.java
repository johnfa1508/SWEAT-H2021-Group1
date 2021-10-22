package GroupProject.repository;

import GroupProject.model.Antique;
import GroupProject.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserJSONRepository implements UserRepository {
    private String fileName;
    HashMap<String, User> userMap = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    // CONSTRUCTOR
    public UserJSONRepository(String fileName) {
        this.fileName = fileName;

        readJSON(fileName);
        writeJSON(fileName);
    }

    // FUNCTION TO READ JSON-FILE
    public void readJSON(String fileName) {
        User[] userArray = new User[0];
        HashMap<String, User> returnMap = new HashMap<>();
        File file = new File(fileName);

        try {
            userArray = objectMapper.readValue(file, User[].class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        for (User value : userArray) {
            returnMap.put(value.getName(), value);
        }

        userMap = returnMap;
    }

    // FUNCTION TO WRITE JSON-FILE
    public void writeJSON(String fileName) {
        ArrayList<User> userArray = new ArrayList<>(userMap.values());

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), userArray);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // FUNCTION TO ADD USER TO HASHMAP OF USERS
    @Override
    public void addUser(User newUser) {
        // Add new element to hashmap with key:value pair
        userMap.put(newUser.getName(), newUser);

        writeJSON(fileName);
    }

    // FUNCTION TO GET SPECIFIC USER
    @Override
    public User getUser(String userKey) {
        // For-loop that iterates through userMap and finds user value using userKey
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            if (set.getValue().getName().equalsIgnoreCase(userKey)) {
                return set.getValue();
            }
        }

        return null;
    }

    // FUNCTION TO SHOW ALL USERS
    @Override
    public void showUsers() {
        System.out.println("============== ALL USERS ===============");

        for (Map.Entry<String, User> set : userMap.entrySet()) {
            System.out.println(set.getKey() + " = " + set.getValue());
        }

        System.out.println("========================================");
    }

    // FUNCTION TO SHOW USERNAMES
    @Override
    public void showUserNames(){
        // Arraylist to store names of users
        ArrayList<String> userNames = new ArrayList<>();

        // For-loop that iterates through userMap and adds names/keys to arraylist
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            userNames.add(set.getValue().getName());
        }

        // Print names/keys
        for (String names : userNames) {
            System.out.println(names);
        }
    }

    // FUNCTION TO HANDLE TRANSACTION BETWEEN SELLER AND BUYER
    @Override
    public void moneyTransaction(Antique antique, String buyerKey) {
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            // If user is the seller, add to balance
            if (set.getValue().getName().equalsIgnoreCase(antique.getSellerName())) {
                getUser(antique.getSellerName()).depositMoney(antique.getPrice());
            }

            // If user is the buyer, deduct from balance
            if (set.getValue().getName().equalsIgnoreCase(buyerKey)) {
                getUser(buyerKey).withdrawMoney(antique.getPrice());
            }
        }

        writeJSON(fileName);
    }
}
