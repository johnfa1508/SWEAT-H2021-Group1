package GroupProject.repository;

import GroupProject.model.Antique;
import GroupProject.model.User;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserRepository {
    User getUser(String userKey);
    HashMap<String, User> showUsers();
    ArrayList<String> showUserNames();
    void addUser(User newUser);
    void moneyTransaction(Antique antique);
    void depositMoney(User user, double money);
}
