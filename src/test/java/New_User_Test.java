import GroupProject.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import GroupProject.repository.*;

public class New_User_Test {
    // TEST LOG IN AS ADMIN
    @Test
    public void Test_If_New_User_Is_In_User_Repository() {
        UserRepository userRepository = new UserJSONRepository("users.json");
        User newUser = new User("userTest", 100);
        userRepository.addUser(newUser);

        Assertions.assertTrue(userRepository.userExists(newUser.getName()));
    }
}
