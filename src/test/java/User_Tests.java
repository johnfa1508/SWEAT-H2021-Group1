import GroupProject.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import GroupProject.repository.*;

public class User_Tests {
    // Use usersTest.json as test-file
    UserRepository userRepository = new UserJSONRepository("usersTest.json");
    // Make new user for testing
    User newUser = new User("userTest", 100);

    // TESTS IF PROGRAM RETURNS NULL IF USER-REPOSITORY IS EMPTY
    @Test
    public void Return_Null_If_User_Repository_Is_Empty() {
        Assertions.assertNull(userRepository.showUsers());
    }

    // TEST IF NEW USER EXISTS IN REPOSITORY AFTER MAKING NEW USER AND ADDING IT
    @Test
    public void New_user_exists() {
        userRepository.addUser(newUser);

        Assertions.assertTrue(userRepository.userExists(newUser.getName()));
    }

    // TEST IF NEW USER GETS RETURNED WHEN USING getUser(String userKey)-FUNCTION IN UserJSONRepository
    @Test
    public void User_gets_returned_from_getUser_function() {
        userRepository.addUser(newUser);

        Assertions.assertNotNull(userRepository.getUser(newUser.getName()));
    }

    // TESTS IF PROGRAM RETURNS VALUES IF USER-REPOSITORY IS NOT EMPTY
    @Test
    public void Return_values_if_user_repository_is_not_empty(){
        Assertions.assertNotNull(userRepository.showUsers());
    }
}
