package GroupProject.controller;

import GroupProject.model.Antique;
import GroupProject.repository.AntiqueRepository;
import java.util.Scanner;

public class AntiqueController {
    private AntiqueRepository antiqueRepository;

    // BOOLEANS USED TO GO BACK TO CORRECT PREVIOUS PANEL
    boolean isUser;
    boolean isAdmin;

    // CONSTRUCTOR
    public AntiqueController(AntiqueRepository antiqueRepository) {
        this.antiqueRepository = antiqueRepository;
    }

    // LOGIN SCREEN
    public void loginPanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("""
                
                ================== LOGIN ===============
                   1. Admin
                   2. User
                   3. Leave
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> adminPanel(); // Go to admin-screen
            case 2 -> userPanel();  // Go to user-screen
        }
    }

    // ADMIN SCREEN
    public void adminPanel() {
        // Clear previous screen
        // FIXME: Clear screen?

        isUser = false;
        isAdmin = true;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================ ADMIN =================
                   1. See antiques
                   2. Update antique
                   3. Purchase history (NOT WORKING)
                   4. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();    // Show all antiques
            case 2 -> updatePanel();     // Go to admin update panel
            case 3 -> purchaseHistory(); // See purchase history
            case 4 -> loginPanel();      // Go back
        }
    }

    // FUNCTION USED TO GO BACK TO CORRECT PREVIOUS PANEL
    public void goBack() {
        if (isUser) {
            userPanel();
        } else {
            adminPanel();
        }
    }

    // USER SCREEN
    public void userPanel() {
        // Clear previous screen
        // FIXME: Clear screen?

        isUser = true;
        isAdmin = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================= USER =================
                   1. See antiques
                   2. Purchase an antique (NOT WORKING)
                   3. Sell an antique
                   4. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques(); // Show all antiques
            case 2 -> purchaseAntique();   // Buy antique
            case 3 -> makeAntique(false);  // Add an antique
            case 4 -> loginPanel();   // Go back
        }
    }

    // PRINTS OUT ALL ANTIQUES FOR SALE
    public void showAntiques() {
         /* TODO: Make it so it shows specific antique-types??? Screen to choose table/chair/mirror etc
             and choose which one to show:
             - Put different types of antiques in an array
             - Display different types in console?
          */

        antiqueRepository.getAllAntiques();

        goBack();
    }

    // PURCHASE AN ANTIQUE FOR SALE
    public void purchaseAntique(){
        /* TODO: Make purchaseAntique()-function in controller:
            - Get name of antique(key) and send it to purchaseAntique()-function in repository
         */

        antiqueRepository.purchaseAntique("purchase");
    }

    // FUNCTION TO MAKE ANTIQUE-OBJECT. RECEIVES BOOLEAN TO KNOW IF IT'S FOR REPLACEMENT OR IF IT'S NEW
    public Antique makeAntique(boolean antiqueReplace) {
        // Variables used to create Antique object
        String name;
        String type;
        String description;
        double price;

        // Create scanner object to get inputs
        Scanner inputScanner = new Scanner(System.in);

        // Get name of antique
        System.out.println("\nEnter the name of the item: ");
        name = inputScanner.nextLine();

        // Get type of antique, make type-string lowercase for easier sorting/grouping later
        // FIXME: Need lowercase to sort?
        System.out.println("\nWhat kind of item is it (table, chair, mirror etc.)? ");
        type = inputScanner.nextLine();
        type = type.toLowerCase();

        // Get description for antique
        System.out.println("\nWrite the description for the item here: ");
        description = inputScanner.nextLine();

        // Get price for antique
        System.out.println("\nHow much will your item cost?: ");
        price = inputScanner.nextDouble();

        // Create antique and add to list of antiques for sale
        Antique newAntique = new Antique(name, type, description, price);

        if (antiqueReplace) {
            return newAntique;
        } else {
            antiqueRepository.addAntique(newAntique);
        }

        goBack();
        return null;
    }

    // COMMANDS FOR ADMIN

    // ADMIN - UPDATE SCREEN
    public void updatePanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ============ ADMIN - UPDATE ============
                   1. Delete an antique
                   2. Edit an antique
                   3. Go back
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> deleteAntique();  // Delete an antique
            case 2 -> editAntique();    // Edit an antique
            case 3 -> goBack();         // Go back to previous panel
        }
    }

    // FUNCTION TO DELETE ANTIQUE
    public void deleteAntique() {
        String antiqueName;

        // Get antique name(key) and store it in antiqueName variable
        System.out.println("\nWrite the name of the antique you would like to delete: ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // Send antiqueName to deleteAntique()-method in repository
        antiqueRepository.deleteAntique(antiqueName);

        goBack();
    }

    // FUNCTION TO EDIT ANTIQUE
    public void editAntique() {
        String antiqueName;

        // Get antique name(key) and store it in antiqueName variable
        System.out.println("\nWrite the name of the antique you would like to edit: ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // Make new antique using makeAntique()-function
        Antique newAntique = makeAntique(true);

        // Replace antique with new antique
        antiqueRepository.editAntique(antiqueName, newAntique);

        goBack();
    }

    // FUNCTION TO VIEW PURCHASE HISTORY
    public void purchaseHistory() {
        // TODO: Make purchase history?

    }
}
