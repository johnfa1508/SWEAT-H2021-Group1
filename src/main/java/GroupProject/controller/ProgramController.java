package GroupProject.controller;

import GroupProject.model.Antique;
import GroupProject.model.Store;
import GroupProject.model.User;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.StoreRepository;
import GroupProject.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProgramController {
    // REPOSITORIES
    private AntiqueRepository antiqueRepository;
    private UserRepository userRepository;
    private StoreRepository storeRepository;

    // VARIABLES USED TO TRACK WHICH USER/ STORE IS CURRENTLY LOGGED IN
    private User currentUser;
    private Store currentStore;

    // BOOLEANS USED TO GO BACK TO CORRECT PREVIOUS PANEL
    boolean isUser;
    boolean isAdmin;
    boolean isStore;

    // CONSTRUCTOR
    public ProgramController(AntiqueRepository antiqueRepository, UserRepository userRepository,
                             StoreRepository storeRepository) {
        this.antiqueRepository = antiqueRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    // LOGIN SCREENS
    public void loginPanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("""
                
                ================== LOGIN ===============
                   1. Log in as admin
                   2. Log in as user
                   3. Log in as store
                   4. Leave
                   5. Make user (TEST PURPOSES)
                ========================================
                """);

        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> adminPanel();      // Go to admin-screen
            case 2 -> loginUserPanel();  // Go to user-login screen
            case 3 -> loginStorePanel(); // Go to store-screen
            case 4 -> {}                 // Leave
            case 5 -> makeUser();        // Make new user (TEST PURPOSES)
        }
    }

    // LOG IN AS USER
    public void loginUserPanel() {
        System.out.println("Which user would you like to log in to?:");
        ArrayList<String> userNamesArray = userRepository.showUserNames();

        for (String userNames : userNamesArray) {
            System.out.println(userNames);
        }

        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        userInput = inputScanner.nextLine();

        userPanel(userRepository.getUser(userInput));
    }

    // LOG IN AS STORE
    public void loginStorePanel() {
        System.out.println("Which store would you like to log in to?:");
        ArrayList<String> storeNamesArray = storeRepository.showStoreNames();

        for (String storeNames : storeNamesArray) {
            System.out.println(storeNames);
        }

        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        userInput = inputScanner.nextLine();

        storePanel(storeRepository.getStore(userInput));
    }

    // USER SCREEN. RECEIVES User-OBJECT TO TRACK WHICH USER IS CURRENTLY LOGGED IN
    public void userPanel(User user) {
        currentUser = user;

        isUser = true;
        isAdmin = false;
        isStore = false;

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
            case 1 -> showAntiques();     // Show antique screen
            case 2 -> purchaseAntique();        // Buy an antique
            case 3 -> makeAntique(false); // Make new antique
            case 4 -> showBalance("user");      // Show balance
            case 5 -> loginPanel();             // Log out
        }
    }

    // STORE SCREEN. RECEIVES Store-OBJECT TO TRACK WHICH STORE IS CURRENTLY LOGGED IN
    public void storePanel(Store store) {
        currentStore = store;

        isUser = false;
        isAdmin = false;
        isStore = true;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("""
                
                ================= STORE =================
                   1. See antiques
                   2. Sell an antique
                   3. See bank balance
                   4. Log out
                =========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();     // Show antique screen
            case 2 -> makeAntique(false); // Make new antique
            case 3 -> showBalance("STORE");    // Show balance
            case 4 -> loginPanel();       // Log out
        }
    }

    // ADMIN SCREEN
    public void adminPanel() {
        isUser = false;
        isAdmin = true;
        isStore = false;

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

    // SHOW-ANTIQUE SCREEN
    public void showAntiques() {
        // Checks if there are items for sale
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
            case 1 -> showAntiquesForSale(); // Show all antiques if there's antiques for sale
            case 2 -> showSpecificAntique(); // Show specific antique type
            case 3 -> goBack();              // Go back
        }
    }

    // SHOW ANTIQUES FOR SALE
    public void showAntiquesForSale() {
        System.out.println("============ ITEMS FOR SALE ============");

        // HashMap to store hashmap received from repository
        HashMap<String, Antique> antiques;
        antiques = antiqueRepository.showAntiquesForSale();

        // Go through hashmap and print out
        for (Map.Entry<String, Antique> antiqueSet : antiques.entrySet()) {
            System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
        }

        System.out.println("========================================");

        // Go back to show antiques screen
        showAntiques();
    }

    // CHOOSE ANTIQUE-TYPE SCREEN
    public void showSpecificAntique() {
        // Show types of antiques for sale
        System.out.println("The types of antiques for sale are: ");
        ArrayList<String> antiqueTypes = antiqueRepository.showAntiqueTypes();

        for (String antiqueType : antiqueTypes) {
            System.out.println(antiqueType);
        }

        // Get input from user which type to show
        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich one would you like to see?: ");
        userInput = inputScanner.nextLine();

        // If user wants to cancel, go back to show antiques screen
        if (userInput.equalsIgnoreCase("CANCEL")) {
            showAntiques();
        }

        System.out.println("\n============ ITEMS FOR SALE ============");

        // Show antiques of that type
        HashMap<String, Antique> specificAntiqueTypes = antiqueRepository.showSpecificAntique(userInput);
        for (Map.Entry<String, Antique> antiqueSet : specificAntiqueTypes.entrySet()) {
            System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
        }

        System.out.println("========================================");

        // Go back to antiques-screen
        showAntiques();
    }

    // FUNCTION TO SHOW USER-BALANCE
    public void showBalance(String userType) {
        if (userType.equals("STORE")) {
            System.out.println("\nYour bank balance is: " + currentStore.getBankBalance() + " nok");
        } else {
            System.out.println("\nYour bank balance is " + currentUser.getBankBalance() + " nok");
        }

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
        ArrayList<String> antiqueNamesArray = antiqueRepository.showAntiqueNames(false);

        for (String antiqueNames : antiqueNamesArray) {
            System.out.println(antiqueNames);
        }

        String itemInCart;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to buy? (CANCEL to cancel): ");
        itemInCart = inputScanner.nextLine();

        // If user wants to cancel, go back to user panel
        if (itemInCart.equalsIgnoreCase("CANCEL")) {
            userPanel(currentUser);
        }

        // Checks if user is trying to buy an item they're selling
        if (currentUser.getName().equalsIgnoreCase(antiqueRepository.getAntique(itemInCart).getSellerName())) {
            System.out.println("You can not buy an item you are selling. Please try again.\n");
        } else {
            // If the buyer does not have enough money, program will send the buyer back
            if (currentUser.getBankBalance() < antiqueRepository.getAntique(itemInCart).getPrice()) {
                System.out.println("Your bank balance is insufficient. Please try again.\n");
            } else {
                // If item is already sold, user will be sent back
                if (antiqueRepository.getAntique(itemInCart).getSold()) {
                    System.out.println("That item is already sold! Please try again.\n");
                } else {
                    System.out.println("Antique was bought successfully.");
                    // Updates the antique(boolean sold) and sends the buyer's name
                    antiqueRepository.purchaseAntique(antiqueRepository.getAntique(itemInCart),
                            currentUser.getName());

                    // Gives money to the seller and deducts money from buyer's account
                    userRepository.moneyTransaction(antiqueRepository.getAntique(itemInCart));
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

        // If user wants to cancel, go back to user panel
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
        } else if (isStore) {
            storePanel(currentStore);
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
            case 1 -> deleteAntique(); // Delete an antique
            case 2 -> editAntique();   // Edit an antique
            case 3 -> goBack();        // Go back to previous panel
        }
    }

    // FUNCTION TO DELETE ANTIQUE
    public void deleteAntique() {
        // Show antique names
        System.out.println("Antiques that can be deleted: ");
        ArrayList<String> antiqueNamesArray = antiqueRepository.showAntiqueNames(true);

        for (String antiqueNames : antiqueNamesArray) {
            System.out.println(antiqueNames);
        }

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
        // Checks if there are items for sale
        if (antiqueRepository.isEmpty()) {
            System.out.println("*** There are no items that can be edited. ***\n");
            updatePanel();
        }

        System.out.println("Antiques that can be edited: ");
        antiqueRepository.showAntiqueNames(false);

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to edit (CANCEL to cancel): ");
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
        System.out.println("\n================ HISTORY ================");
        HashMap<String, Antique> purchaseHistory = antiqueRepository.showPurchaseHistory();

        for (Map.Entry<String, Antique> antiqueSet : purchaseHistory.entrySet()) {
            System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
        }

        System.out.println("=========================================");

        goBack();
    }

    // FUNCTION TO SHOW ALL USERS
    public void showUsers() {
        System.out.println("============== ALL USERS ===============");

        HashMap<String, User> userMap = userRepository.showUsers();

        for (Map.Entry<String, User> userSet : userMap.entrySet()) {
            System.out.println(userSet.getKey() + " = " + userSet.getValue());
        }

        System.out.println("========================================");

        goBack();
    }
}
