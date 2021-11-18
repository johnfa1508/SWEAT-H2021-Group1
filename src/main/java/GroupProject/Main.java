package GroupProject;

import GroupProject.controller.ProgramController;
import GroupProject.repository.*;

public class Main {
    public static void main(String[] args) {
        // Make repositories and controller to access commands for file
        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiques.json");
        UserRepository userRepository = new UserJSONRepository("users.json");
        StoreRepository storeRepository = new StoreJSONRepository("stores.json");
        ProgramController programController = new ProgramController(antiqueRepository, userRepository, storeRepository);

        // Go to login-screen
        programController.loginPanel();
    }
}
