package GroupProject;

import GroupProject.controller.ProgramController;
import GroupProject.repository.AntiqueJSONRepository;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.UserJSONRepository;

public class Main {
    public static void main(String[] args) {
        // Make JSON-file and controller to access commands for file
        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiques.json");

        // Repository for debugging
//        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("testFile.json");

        // FIXME: Make UserRepository-interface and make classes for Admin, Buyer, Seller etc.
        UserJSONRepository userJSONRepository = new UserJSONRepository("users.json");
        ProgramController programController = new ProgramController(antiqueRepository, userJSONRepository);

        // Go to login-screen
        programController.loginPanel();
    }
}
