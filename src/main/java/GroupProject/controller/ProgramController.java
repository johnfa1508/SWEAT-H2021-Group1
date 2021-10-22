package GroupProject.controller;

import GroupProject.model.Antique;
import GroupProject.model.User;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.UserRepository;
import java.util.Scanner;

public class ProgramController {
    private AntiqueRepository antiqueRepository;
    private UserRepository userRepository;
    private User currentUser;

    // BOOLEANS USED TO GO BACK TO CORRECT PREVIOUS PANEL
    boolean isUser;
    boolean isAdmin;

    // CONSTRUCTOR
    public ProgramController(AntiqueRepository antiqueRepository, UserRepository userRepository) {
        this.antiqueRepository = antiqueRepository;
        this.userRepository = userRepository;
    }

    // LOGIN SCREENS
    public void loginPanel() {
        // Boolean to check if user still wants to log in
        boolean whileOn = true;

        int choice;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("""
                
                ================== LOGIN ===============
                   1. Log in as admin
                   2. Log in as user
                   3. Leave
                   4. Make user (TEST PURPOSES)
                ========================================
                """);

        while (whileOn) {
            choice = inputScanner.nextInt();

            switch (choice) {
                case 1 -> adminPanel();     // Go to admin-screen
                case 2 -> loginUserPanel(); // Go to user-login screen
                case 3 -> whileOn = false;  // Leave
                case 4 -> makeUser();       // Make new user (TEST PURPOSES)
            }
        }
    }

    public void loginUserPanel() {
        System.out.println("Which user would you like to log in to?:");
        userRepository.showUserNames();

        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        userInput = inputScanner.nextLine();

        userPanel(userRepository.getUser(userInput));
    }

    // ADMIN SCREEN
    public void adminPanel() {
        isUser = false;
        isAdmin = true;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================ ADMIN =================
                   1. See antiques
                   2. Update an antique
                   3. Purchase history
                   4. See all users
                   5. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();    // Show antique screen
            case 2 -> updatePanel();     // Go to admin update panel
            case 3 -> purchaseHistory(); // See purchase history
            case 4 -> showUsers();       // Show all users
            case 5 -> loginPanel();      // Go back
        }
    }

    // USER SCREEN
    public void userPanel(User user) {
        currentUser = user;

        isUser = true;
        isAdmin = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================= USER =================
                   1. See antiques
                   2. Purchase an antique
                   3. Sell an antique
                   4. See bank balance
                   5. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();           // Show antique screen
            case 2 -> purchaseAntique();        // Buy an antique
            case 3 -> makeAntique(false);       // Make an antique
            case 4 -> showBalance(currentUser); // Show balance
            case 5 -> loginPanel();             // Log out
        }
    }

    // SHOW-ANTIQUE SCREEN
    public void showAntiques() {
        if (antiqueRepository.isEmpty()) {
            System.out.println("*** There are currently no antiques to show. ***");
            goBack();
        }

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
                    antiqueRepository.showAntiquesForSale();
                    showAntiques();
            }                                // Show all antiques if there's antiques for sale
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

        // If user wants to cancel, go back to update panel
        if (userInput.equalsIgnoreCase("CANCEL")) {
            showAntiques();
        }

        // Show antiques of that type
        antiqueRepository.showSpecificAntique(userInput);

        // Go back to antiques-screen
        showAntiques();
    }

    // FUNCTION TO SHOW USER-BALANCE
    public void showBalance(User user) {
        System.out.println("\nYour bank balance is: " + user.getBankBalance() + " nok");

        goBack();
    }

    // FUNCTION TO MAKE NEW USER
    public void makeUser() {
        String name;
        double bankBalance;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWrite the name of the new user: ");
        name = inputScanner.nextLine();

        System.out.println("\nWrite how much there's in the bank balance: ");
        bankBalance = inputScanner.nextDouble();

        User newUser = new User(name, bankBalance);
        userRepository.addUser(newUser);

        loginPanel();
    }

    // PURCHASE AN ANTIQUE FOR SALE
    public void purchaseAntique(){
        // Checks if there are items for sale
        if (antiqueRepository.isEmpty()) {
            System.out.println("*** There are currently no items for sale. ***");
            userPanel(currentUser);
        }

        // Show antiques for sale
        System.out.println("Antiques that are for sale are: ");
        antiqueRepository.showAntiqueNames(false);

        String boughtItem;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to buy? (CANCEL to cancel): ");
        boughtItem = inputScanner.nextLine();

        // If user wants to cancel, go back to update panel
        if (boughtItem.equalsIgnoreCase("CANCEL")) {
            userPanel(currentUser);
        }

        // Checks if user is trying to buy an item they're selling
        if (currentUser.getName().equalsIgnoreCase(antiqueRepository.getAntique(boughtItem).getSellerName())) {
            System.out.println("You can not buy an item you are selling. Please try again.\n");
        } else {
            // If the buyer does not have enough money, program will send the buyer back
            if (currentUser.getBankBalance() < antiqueRepository.getAntique(boughtItem).getPrice()) {
                System.out.println("Your bank balance is insufficient. Please try again.\n");
            } else {
                // If item is already sold, user will be sent back
                if (antiqueRepository.getAntique(boughtItem).getSold()) {
                    System.out.println("That item is already sold! Please try again.\n");
                } else {
                    System.out.println("Antique was bought successfully.");
                    // Updates the antique(boolean sold) and sends the buyer's name
                    antiqueRepository.purchaseAntique(antiqueRepository.getAntique(boughtItem),
                            currentUser.getName());

                    // Gives money to the seller and deducts money from buyer's account
                    userRepository.moneyTransaction(antiqueRepository.getAntique(boughtItem),
                            currentUser.getName());
                }
            }
        }

        userPanel(currentUser);
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
        System.out.println("\nEnter the name of the item (CANCEL to cancel): ");
        name = inputScanner.nextLine();
        if (name.equalsIgnoreCase("CANCEL")) {
            userPanel(currentUser);
        }

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
        Antique newAntique = new Antique(name, type, description, price, currentUser.getName());

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
        if (isUser) {
            userPanel(currentUser);
        } else {
            adminPanel();
        }
    }

    // COMMANDS FOR ADMIN
    // FUNCTION TO SHOW ALL USERS
    public void showUsers() {
        userRepository.showUsers();

        goBack();
    }

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
            case 1 -> deleteAntique(); // Delete an antique
            case 2 -> editAntique();   // Edit an antique
            case 3 -> goBack();        // Go back to previous panel
        }
    }

    // FUNCTION TO DELETE ANTIQUE
    public void deleteAntique() {
        System.out.println("Antiques that can be deleted: ");
        antiqueRepository.showAntiqueNames(true);

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to delete (CANCEL to go back): ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // If user wants to cancel, go back to update panel
        if (antiqueName.equalsIgnoreCase("CANCEL")) {
            updatePanel();
        }

        // Send antiqueName to deleteAntique()-method in repository
        antiqueRepository.deleteAntique(antiqueName);

        goBack();
    }

    // FUNCTION TO EDIT ANTIQUE
    public void editAntique() {
        if (antiqueRepository.isEmpty()) {
            System.out.println("*** There are no items that can be edited. ***\n");
            updatePanel();
        }

        System.out.println("Antiques that can be edited: ");
        antiqueRepository.showAntiqueNames(false);

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to edit: ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // If user wants to cancel, go back to update panel
        if (antiqueName.equalsIgnoreCase("CANCEL")) {
            updatePanel();
        }

        if (antiqueRepository.getAntique(antiqueName).getSold()) {
            System.out.println("That item is already sold!\n");
            updatePanel();
        }

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
