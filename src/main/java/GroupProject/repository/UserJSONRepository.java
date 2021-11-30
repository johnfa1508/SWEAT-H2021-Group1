package GroupProject.repository;

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

        for (User user : userArray) {
            returnMap.put(user.getName(), user);
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

    // FUNCTION TO REMOVE USER FROM HASHMAP OF USERS
    @Override
    public void removeUser(User newUser) {
        // Add new element to hashmap with key:value pair
        userMap.remove(newUser.getName());

        writeJSON(fileName);
    }

    // FUNCTION TO GET SPECIFIC USER
    @Override
    public User getUser(String userKey) {
        // For-loop that iterates through userMap and finds user value using userKey
        for (Map.Entry<String, User> userSet : userMap.entrySet()) {
            if (userSet.getValue().getName().equalsIgnoreCase(userKey)) {
                return userSet.getValue();
            }
        }

        return null;
    }

    // FUNCTION TO SHOW ALL USERS
    @Override
    public HashMap<String, User> showUsers() {
        return new HashMap<>(userMap);
    }

    // FUNCTION TO SHOW USERNAMES
    @Override
    public ArrayList<String> showUserNames(){
        // Arraylist to store names of users
        ArrayList<String> userNamesArray = new ArrayList<>();

        // For-loop that iterates through userMap and adds names/keys to arraylist
        for (Map.Entry<String, User> userSet : userMap.entrySet()) {
            userNamesArray.add(userSet.getValue().getName());
        }

        return userNamesArray;
    }

    // FUNCTION TO DEPOSIT MONEY TO BANK BALANCE
    @Override
    public void depositMoney(User user, double money) {
        getUser(user.getName()).depositMoney(money);

        writeJSON(fileName);
    }

    // FUNCTION TO WITHDRAW MONEY FROM BANK BALANCE
    @Override
    public void withdrawMoney(User user, double money) {
        getUser(user.getName()).withdrawMoney(money);

        writeJSON(fileName);
    }

    // FUNCTION TO CHECK IF USER EXISTS
    @Override
    public boolean userExists(String userName) {
        return userMap.containsKey(userName);
    }
}
