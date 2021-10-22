package GroupProject.repository;

import GroupProject.model.Antique;
import GroupProject.model.User;

public interface UserRepository {
    User getUser(String userKey);

    void showUsers();
    void showUserNames();
    void addUser(User newUser);
    void moneyTransaction(Antique antique, String buyerKey);
}
