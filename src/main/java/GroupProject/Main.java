package GroupProject;

import GroupProject.controller.ProgramController;
import GroupProject.repository.AntiqueJSONRepository;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.UserJSONRepository;
import GroupProject.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        // Make repositories and controller to access commands for file
        AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiques.json");
        UserRepository userRepository = new UserJSONRepository("users.json");
        ProgramController programController = new ProgramController(antiqueRepository, userRepository);

        // Go to login-screen
        programController.loginPanel();
    }
}
