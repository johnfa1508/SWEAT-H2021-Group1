package GroupProject;

import GroupProject.repository.AntiqueDataRepository;
import GroupProject.controller.AntiqueController;
import GroupProject.repository.AntiqueJSONRepository;
import GroupProject.repository.AntiqueRepository;

public class Main {
    public static void main(String[] args) {
        // Repository for early testing
//        AntiqueRepository antiqueRepository = new AntiqueDataRepository();

        // Make JSON-file and controller to access commands for file
        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiques.json");
        AntiqueController antiqueController = new AntiqueController(antiqueRepository);

        // Go to login-screen
        antiqueController.loginPanel();
    }
}
