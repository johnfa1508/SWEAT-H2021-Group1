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
    public void Return_true_if_user_exists() {
        userRepository.addUser(newUser);

        Assertions.assertTrue(userRepository.userExists(newUser.getName()));
    }

    // TEST IF userExists()-FUNCTION RETURNS FALSE IF USER DOES NOT EXIST
    @Test
    public void Return_false_if_user_does_not_exists() {
        userRepository.removeUser(newUser);

        Assertions.assertFalse(userRepository.userExists(newUser.getName()));
    }

    // TEST IF NEW USER GETS RETURNED WHEN USING getUser(String userKey)-FUNCTION IN UserJSONRepository
    @Test
    public void User_gets_returned_from_getUser_function() {
        userRepository.addUser(newUser);

        Assertions.assertNotNull(userRepository.getUser(newUser.getName()));
    }

    // TESTS IF PROGRAM RETURNS NOTHING IF USER-REPOSITORY IS EMPTY
    @Test
    public void Return_empty_userMap_if_user_repository_is_empty() {
        userRepository.removeUser(newUser);

        Assertions.assertTrue(userRepository.showUsers().isEmpty());
    }

    // TESTS IF PROGRAM RETURNS VALUES IF USER-REPOSITORY IS NOT EMPTY
    @Test
    public void Return_values_if_user_repository_is_not_empty() {
        Assertions.assertNotNull(userRepository.showUsers());
    }

    // TESTS IF PROGRAM RETURNS NOTHING IF USER-REPOSITORY IS EMPTY
    @Test
    public void Return_empty_userNameMap_if_user_repository_is_empty() {
        userRepository.removeUser(newUser);

        Assertions.assertTrue(userRepository.showUserNames().isEmpty());
    }

    // TESTS IF PROGRAM RETURNS USERNAMES IF USER-REPOSITORY IS NOT EMPTY
    @Test
    public void Return_usernames_if_user_repository_is_not_empty() {
        Assertions.assertNotNull(userRepository.showUserNames());
    }
}
