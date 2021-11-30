import GroupProject.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import GroupProject.repository.*;

import java.io.*;

public class Login_Test {
    // TEST LOG IN AS ADMIN
    @Test
    public void Logged_in_as_admin() {
        UserRepository userRepository = new UserJSONRepository("users.json");

        Assertions.assertTrue(userRepository.userExists("john"));
    }
}
