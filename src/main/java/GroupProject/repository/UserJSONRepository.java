package GroupProject.repository;

import GroupProject.model.Antique;
import GroupProject.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserJSONRepository {
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
    public void addUser(User newUser) {
        // Add new element to hashmap with key:value pair
        userMap.put(newUser.getName(), newUser);

        writeJSON(fileName);
    }

    public void moneyTransaction(Antique antique, String buyerKey) {
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            if (set.getValue().getName().equalsIgnoreCase(antique.getSellerName())) {
                getSeller().addToBalance(antique.getPrice());
            } if (set.getValue().getName().equalsIgnoreCase(buyerKey)) {
                getBuyer().deductFromBalance(antique.getPrice());
            }
        }

        writeJSON(fileName);
    }

    public User getSeller() {
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            if (set.getValue().getType().equalsIgnoreCase("seller")) {
                return set.getValue();
            }
        }

        return null;
    }

    public User getBuyer() {
        for (Map.Entry<String, User> set : userMap.entrySet()) {
            if (set.getValue().getType().equalsIgnoreCase("buyer")) {
                return set.getValue();
            }
        }

        return null;
    }
}
