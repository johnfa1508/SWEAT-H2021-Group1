import GroupProject.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import GroupProject.repository.*;

public class New_User_Test {
    // TEST IF NEW USER IS USER REPOSITORY
    @Test
    public void New_User_Exists() {
        UserRepository userRepository = new UserJSONRepository("usersTest.json");
        User newUser = new User("userTest", 100);
        userRepository.addUser(newUser);

        Assertions.assertTrue(userRepository.userExists(newUser.getName()));
    }

    // TEST IF NEW USER GETS RETURNED WHEN USING getUser(String userKey)-FUNCTION IN UserJSONRepository
    @Test
    public void User_Gets_Returned_From_getUser_Function() {
        UserRepository userRepository = new UserJSONRepository("usersTest.json");
        User newUser = new User("userTest", 100);
        userRepository.addUser(newUser);

        Assertions.assertNotNull(userRepository.getUser(newUser.getName()));
    }
}
