// THIS JAVA FILE CONTAINS TESTS WHICH INVOLVES USER-OBJECTS

import GroupProject.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import GroupProject.repository.*;

public class User_Tests {
    // Use usersTest.json as test-file
    UserRepository userRepository = new UserJSONRepository("usersTest.json");

    // Make new user for testing
    User newUser = new User("userTest", 100);

    // TEST IF userExists()-FUNCTION RETURNS TRUE IF USER EXISTS
    @Test
    public void userExists_returns_true_if_user_exists() {
        userRepository.addUser(newUser);

        Assertions.assertTrue(userRepository.userExists(newUser.getName()));
    }

    // TEST IF userExists()-FUNCTION RETURNS FALSE IF USER DOES NOT EXIST
    @Test
    public void userExists_returns_false_if_user_does_not_exists() {
        userRepository.removeUser(newUser);

        Assertions.assertFalse(userRepository.userExists(newUser.getName()));
    }

    // TEST IF getUser() RETURNS USER
    @Test
    public void getUser_returns_user_if_user_exists() {
        userRepository.addUser(newUser);

        Assertions.assertNotNull(userRepository.getUser(newUser.getName()));
    }

    // TEST IF showUsers()-FUNCTION RETURNS NOTHING IF USER-REPOSITORY IS EMPTY
    @Test
    public void Return_empty_userMap_if_user_repository_is_empty() {
        userRepository.removeUser(newUser);

        Assertions.assertTrue(userRepository.showUsers().isEmpty());
    }

    // TEST IF showerUsers() RETURNS VALUES IF USER-REPOSITORY IS NOT EMPTY
    @Test
    public void showUsers_returns_values_if_user_repository_is_not_empty() {
        Assertions.assertNotNull(userRepository.showUsers());
    }

    // TEST IF showUserNames() RETURNS NOTHING IF USER-REPOSITORY IS EMPTY
    @Test
    public void showUserNames_returns_empty_userNameMap_if_user_repository_is_empty() {
        userRepository.removeUser(newUser);

        Assertions.assertTrue(userRepository.showUserNames().isEmpty());
    }

    // TEST IF showUserNames() RETURNS USERNAMES IF USER-REPOSITORY IS NOT EMPTY
    @Test
    public void showUserNames_returns_usernames_if_user_repository_is_not_empty() {
        Assertions.assertNotNull(userRepository.showUserNames());
    }

    // TEST IF depositMoney() ADDS MONEY FROM USER'S BALANCE
    @Test
    public void depositMoney_adds_money_to_user_balance() {
        userRepository.addUser(newUser);

        double oldMoney = newUser.getBankBalance();
        userRepository.depositMoney(newUser, 100);
        double newMoney = newUser.getBankBalance();

        Assertions.assertTrue(oldMoney < newMoney);
    }

    // TEST IF withdrawMoney() TAKES MONEY FROM USER'S BALANCE
    @Test
    public void withdrawMoney_takes_money_from_user_balance() {
        userRepository.addUser(newUser);

        double oldMoney = newUser.getBankBalance();
        userRepository.withdrawMoney(newUser, 100);
        double newMoney = newUser.getBankBalance();

        Assertions.assertTrue(oldMoney > newMoney);
    }
}
