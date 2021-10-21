package GroupProject.controller;

import GroupProject.model.Antique;
import GroupProject.model.User;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.UserJSONRepository;
import java.util.Scanner;

public class ProgramController {
    private AntiqueRepository antiqueRepository;
    private UserJSONRepository userJSONRepository;

    // BOOLEANS USED TO GO BACK TO CORRECT PREVIOUS PANEL
    boolean isUserSeller;
    boolean isUserBuyer;
    boolean isAdmin;

    // CONSTRUCTOR
    public ProgramController(AntiqueRepository antiqueRepository, UserJSONRepository userJSONRepository) {
        this.antiqueRepository = antiqueRepository;
        this.userJSONRepository = userJSONRepository;
    }

    // LOGIN SCREENS
    public void loginPanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("""
                
                ================== LOGIN ===============
                   1. Log in as admin
                   2. Log in as user
                   3. Leave
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> adminPanel();     // Go to admin-screen
            case 2 -> loginUserPanel(); // Go to user-login screen
        }
    }

    public void loginUserPanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("""
                
                ============== USER - LOGIN ============
                   1. Log in as buyer
                   2. Log in as seller
                   3. Go back
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> userBuyerPanel();  // Go to buyer-screen
            case 2 -> userSellerPanel(); // Go to seller-screen
            case 3 -> loginPanel();      // Go to login-screen
        }
    }

    // ADMIN SCREEN
    public void adminPanel() {
        isUserSeller = false;
        isUserBuyer = false;
        isAdmin = true;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================ ADMIN =================
                   1. See antiques
                   2. Update an antique
                   3. Purchase history
                   4. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();    // Show antique screen
            case 2 -> updatePanel();     // Go to admin update panel
            case 3 -> purchaseHistory(); // See purchase history
            case 4 -> loginPanel();      // Go back
        }
    }

    // USER - BUYER SCREEN
    public void userBuyerPanel() {
        isUserSeller = false;
        isUserBuyer = true;
        isAdmin = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ============== USER - BUYER ============
                   1. See antiques
                   2. Purchase an antique (WIP)
                   3. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();     // Show antique screen
            case 2 -> purchaseAntique();  // Buy an antique
            case 3 -> loginUserPanel();   // Go back
        }
    }

    // USER - SELLER SCREEN
    public void userSellerPanel() {
        isUserSeller = true;
        isUserBuyer = false;
        isAdmin = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ============ USER - SELLER =============
                   1. See antiques
                   2. Sell an antique
                   3. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();                   // Show all antiques
            case 2 -> makeAntique(false); // Make an antique
            case 3 -> loginUserPanel();                 // Go back
        }
    }

    // SHOW-ANTIQUE SCREEN
    public void showAntiques() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                =============== ANTIQUES ===============
                   1. See all antiques for sale
                   2. See specific types
                   3. Go back
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> {
                antiqueRepository.showAllAntiques();
                goBack();
            }                                // Show all antiques
            case 2 -> showSpecificAntique(); // Show specific antique type
            case 3 -> goBack();              // Go back
        }
    }

    // CHOOSE ANTIQUE-TYPE SCREEN
    public void showSpecificAntique() {
        // Show types of antiques for sale
        System.out.println("The types of antiques for sale are: ");
        antiqueRepository.showAntiqueTypes();

        // Get input from user which type to show
        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich one would you like to see?: ");
        userInput = inputScanner.nextLine();

        // Show antiques of that type
        antiqueRepository.showSpecificAntique(userInput);

        // Go back to antiques-screen
        showAntiques();
    }

    // PURCHASE AN ANTIQUE FOR SALE
    public void purchaseAntique(){
        String boughtItem;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to buy?: ");
        boughtItem = inputScanner.nextLine();

        antiqueRepository.purchaseAntique(antiqueRepository.getAntique(boughtItem),
                                            userJSONRepository.getBuyer().getName());

        // FIXME: Crashes???
//        userJSONRepository.moneyTransaction(antiqueRepository.getAntique(boughtItem),
//                                            userJSONRepository.getBuyer().getName());
        userBuyerPanel();
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
        Antique newAntique = new Antique(name, type, description, price, userJSONRepository.getSeller().getName());

        if (antiqueReplace) {
            return newAntique;
        } else {
            antiqueRepository.addAntique(newAntique);
        }

        goBack();
        return null;
    }

    // FUNCTION USED TO GO BACK TO CORRECT PREVIOUS PANEL
    public void goBack() {
        if (isUserSeller) {
            userSellerPanel();
        } if (isUserBuyer){
            userBuyerPanel();
        } else {
            adminPanel();
        }
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
        System.out.println("Antiques that can be deleted: ");
        antiqueRepository.showAntiqueNames();

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to delete: ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // Send antiqueName to deleteAntique()-method in repository
        antiqueRepository.deleteAntique(antiqueName);

        goBack();
    }

    // FUNCTION TO EDIT ANTIQUE
    public void editAntique() {
        System.out.println("Antiques that can be edited: ");
        antiqueRepository.showAntiqueNames();

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
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
        antiqueRepository.showPurchaseHistory();

        goBack();
    }
}
