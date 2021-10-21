package GroupProject;

import GroupProject.controller.AntiqueController;
import GroupProject.repository.AntiqueJSONRepository;
import GroupProject.repository.AntiqueRepository;

public class Main {
    public static void main(String[] args) {
        // Make JSON-file and controller to access commands for file
//        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiques.json");

        // Repository for debugging
        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("testFile.json");
        AntiqueController antiqueController = new AntiqueController(antiqueRepository);

        // Go to login-screen
        antiqueController.loginPanel();
    }
}
