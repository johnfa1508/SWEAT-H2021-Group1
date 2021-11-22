package GroupProject.repository;

import GroupProject.model.User;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserRepository {
    User getUser(String userKey);
    HashMap<String, User> showUsers();
    ArrayList<String> showUserNames();
    void addUser(User newUser);
    void depositMoney(User user, double money);
    void withdrawMoney(User user, double money);
    boolean userExists(User user);
}
