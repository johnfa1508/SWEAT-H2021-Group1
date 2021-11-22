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
                ========================================
                """);

        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> adminPanel();      // Go to admin-screen
            case 2 -> loginUserPanel();  // Go to user-login screen
            case 3 -> loginStorePanel(); // Go to store-screen
            case 4 -> {}                 // Leave
        }
    }

    // LOG IN AS USER
    public void loginUserPanel() {
        System.out.println("Which user would you like to log in to?: ");
        ArrayList<String> userNamesArray = userRepository.showUserNames();

        for (String userNames : userNamesArray) {
            System.out.println(userNames);
        }

        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        userInput = inputScanner.nextLine();

        // If the user doesn't exist, user will get sent back to login panel
        if (userRepository.userExists(userInput)) {
            userPanel(userRepository.getUser(userInput));
        } else {
            System.out.println("\n*** That user does not exist. Please try again. ***");
            loginPanel();
        }
    }

    // LOG IN AS STORE
    public void loginStorePanel() {
        System.out.println("Which store would you like to log in to?: ");
        ArrayList<String> storeNamesArray = storeRepository.showStoreNames();

        for (String storeNames : storeNamesArray) {
            System.out.println(storeNames);
        }

        String userInput;
        Scanner inputScanner = new Scanner(System.in);
        userInput = inputScanner.nextLine();

        // If the user doesn't exist, user will get sent back to login panel
        if (storeRepository.storeExists(userInput)) {
            storePanel(storeRepository.getStore(userInput));
        } else {
            System.out.println("\n*** That store does not exist. Please try again. ***");
            loginPanel();
        }
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
                   3. Add antique as favorite
                   4. Remove antique as favorite
                   5. Show favorites
                   6. See bank balance
                   7. Deposit money
                   8. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();      // Show antique screen
            case 2 -> purchaseAntique();   // Buy an antique
            case 3 -> addFavorite();       // Add antique to favorites
            case 4 -> removeFavorite();    // Remove antique from favorite list
            case 5 -> showFavorites();     // Show favorited antiques
            case 6 -> showBalance("USER"); // Show balance
            case 7 -> depositMoney();      // Deposit money
            case 8 -> loginPanel();        // Log out
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
            case 1 -> showAntiques();       // Show antique screen
            case 2 -> makeAntique(false);   // Make new antique
            case 3 -> showBalance("STORE"); // Show balance
            case 4 -> loginPanel();         // Log out
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
                   5. See all stores
                   6. Make new store
                   7. Make new user
                   8. Log out
                ========================================
                """);
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1 -> showAntiques();    // Show antique screen
            case 2 -> updatePanel();     // Go to admin update panel
            case 3 -> purchaseHistory(); // See purchase history
            case 4 -> showUsers();       // Show all users
            case 5 -> showStores();      // Show all stores
            case 6 -> makeStore();       // Make a new store
            case 7 -> makeUser();        // Make a new user
            case 8 -> loginPanel();      // Go back
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
        HashMap<String, Antique> antiques = antiqueRepository.showAntiquesForSale();

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

        if (!antiqueTypes.contains(userInput)) {
            System.out.println("\n*** That type does not exist. Please try again ***");
        } else {
            // Program will continue if user doesn't write cancel or CANCEL
            if (!userInput.equalsIgnoreCase("CANCEL") ||
                    !userInput.equalsIgnoreCase("cancel")) {
                System.out.println("\n============ ITEMS FOR SALE ============");

                // Show antiques of that type
                HashMap<String, Antique> specificAntiqueTypes = antiqueRepository.showSpecificAntique(userInput);
                for (Map.Entry<String, Antique> antiqueSet : specificAntiqueTypes.entrySet()) {
                    System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
                }

                System.out.println("========================================");
            }
        }

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

    // FUNCTION TO DEPOSIT MONEY TO USER-BALANCE
    public void depositMoney() {
        double money;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nEnter the amount you would like to deposit: ");
        money = inputScanner.nextDouble();

        userRepository.depositMoney(currentUser, money);

        goBack();
    }

    // FUNCTION TO SHOW NAMES OF ANTIQUES FOR SALE
    public void showAntiqueNames() {
        ArrayList<String> antiqueNamesArray = antiqueRepository.showAntiqueNames(false);

        for (String antiqueNames : antiqueNamesArray) {
            System.out.println(antiqueNames);
        }
    }

    // PURCHASE AN ANTIQUE FOR SALE
    public void purchaseAntique(){
        // Checks if there are items for sale
        if (antiqueRepository.isEmpty()) {
            System.out.println("*** There are currently no items for sale. ***");

            goBack();
        }

        // Show antiques for sale
        System.out.println("Antiques that are for sale are: ");
        showAntiqueNames();

        String itemInCart;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to buy? (CANCEL to cancel): ");
        itemInCart = inputScanner.nextLine();

        // If antique exists program will continue
        if (antiqueRepository.antiqueExists(itemInCart)) {
            // If the buyer does not have enough money, program will send the buyer back
            if (currentUser.getBankBalance() < antiqueRepository.getAntique(itemInCart).getPrice()) {
                System.out.println("Your bank balance is insufficient. Please try again.\n");
            } else {
                // If item is already sold, user will be sent back
                if (antiqueRepository.getAntique(itemInCart).getSold()) {
                    System.out.println("That item is already sold! Please try again.\n");
                } else {
                    System.out.println("\nAntique was bought successfully.");

                    // FIXME: Make function for transaction

                    // Put antique-object in variable for easier handling
                    Antique antique = antiqueRepository.getAntique(itemInCart);

                    // Updates the antique(boolean sold) and sends the buyer's name
                    antiqueRepository.purchaseAntique(antique, currentUser);

                    // Remove money from buyer's balance
                    userRepository.withdrawMoney(userRepository.getUser(antique.getBuyerName()), antique.getPrice());

                    // Add money to seller's balance
                    storeRepository.depositMoney(storeRepository.getStore(antique.getSellerName()), antique.getPrice());
                }
            }
        } else if (itemInCart.equalsIgnoreCase("CANCEL")){
            goBack();
        } else {
            // If antique written does not exist, user will get sent back
            System.out.println("\n*** That antique does not exist. Please try again ***");
        }

        goBack();
    }

    // FUNCTION TO ADD ANTIQUE TO FAVORITES
    public void addFavorite() {
        // Show antiques for sale
        System.out.println("Antiques that are for sale are: ");
        showAntiqueNames();

        String favoriteItem;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to add to favorites (CANCEL to cancel): ");
        favoriteItem = inputScanner.nextLine();

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(favoriteItem)) {
            // If user has already favorited the item, user will get sent back
            if (antiqueRepository.getAntique(favoriteItem).getFavorites().contains(currentUser.getName())) {
                System.out.println("*** You have already favorited that item. ***\n");
            } else {
                antiqueRepository.addFavorite(antiqueRepository.getAntique(favoriteItem), currentUser);
            }
        } else if (favoriteItem.equalsIgnoreCase("CANCEL") ||
                favoriteItem.equalsIgnoreCase("cancel")) {
            goBack();
        } else {
            // If antique doesn't exist, user will get sent back
            System.out.println("\n*** That antique does not exist. Please try again ***");
        }

        goBack();
    }

    // FUNCTION TO REMOVE ANTIQUE FROM FAVORITES
    public void removeFavorite() {
        // Show antiques favorited
        HashMap<String, Antique> favorites = antiqueRepository.favoritedByUser(currentUser);

        // Check if user has favorited anything, if not user will be sent back
        if (favorites.isEmpty()) {
            System.out.println("*** You have no favorites ***");
        } else {
            System.out.println("\n========== ANTIQUES FAVORITED ==========");

            for (Map.Entry<String, Antique> antiqueSet : favorites.entrySet()) {
                System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
            }

            System.out.println("\n========================================");

            String removeFavoriteItem;
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("\nWhich item would you like to remove from favorites? (CANCEL to cancel): ");
            removeFavoriteItem = inputScanner.nextLine();

            // If antique exists, antique will get removed from favorites
            if (antiqueRepository.antiqueExists(removeFavoriteItem)) {
                antiqueRepository.removeFavorite(antiqueRepository.getAntique(removeFavoriteItem), currentUser);
            } else if (removeFavoriteItem.equalsIgnoreCase("CANCEL") ||
                    removeFavoriteItem.equalsIgnoreCase("cancel")) {
                goBack();
            } else {
                // If antique does not exist, user will be sent back
                System.out.println("\n*** That antique does not exist. Please try again ***");
            }
        }

        goBack();
    }

    // FUNCTION TO SHOW ALL FAVORITES
    public void showFavorites() {
        HashMap<String, Antique> favorites = antiqueRepository.favoritedByUser(currentUser);

        // Check if user has favorited anything, if not user will be sent back
        if (favorites.isEmpty()) {
            System.out.println("*** You have no favorites ***");
        } else {
            System.out.println("\n========== ANTIQUES FAVORITED ==========");

            for (Map.Entry<String, Antique> antiqueSet : favorites.entrySet()) {
                System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
            }

            System.out.println("\n========================================");
        }

        goBack();
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

    // COMMANDS FOR STORE USERS
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

        // If antique doesn't exist, program will continue
        if (!antiqueRepository.antiqueExists(name)) {
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
            Antique newAntique = new Antique(name, type, description, price, currentStore.getName());

            if (antiqueReplace) {
                return newAntique;
            } else {
                antiqueRepository.addAntique(newAntique);
            }
        } else if (name.equalsIgnoreCase("CANCEL") || name.equalsIgnoreCase("cancel")) {
            // If user wants to cancel, go back to user panel
            goBack();
        } else {
            // If antique already exists, user will get sent back
            System.out.println("\n*** That antique already exists. Please try again. ***");
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

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(antiqueName)) {
            // Send antiqueName to deleteAntique()-method in repository
            antiqueRepository.deleteAntique(antiqueName);
        } else if (antiqueName.equalsIgnoreCase("CANCEL") ||
                antiqueName.equalsIgnoreCase("cancel")) {
            // If user wants to cancel, go back to update panel
            updatePanel();
        } else {
            // If antique doesn't exist, user will get sent back
            System.out.println("\n*** That antique does not exist. Please try again. ***");
        }

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
        showAntiqueNames();

        // Get antique name(key) and store it in antiqueName variable
        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to edit (CANCEL to cancel): ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(antiqueName)) {
            // If antique is already sold, admin will get sent back
            if (antiqueRepository.getAntique(antiqueName).getSold()) {
                System.out.println("That item is already sold!\n");
                updatePanel();
            } else {
                // Make new antique using makeAntique()-function
                Antique newAntique = makeAntique(true);

                // Replace antique with new antique
                antiqueRepository.editAntique(antiqueName, newAntique);
            }
        } else if (antiqueName.equalsIgnoreCase("CANCEL") ||
                antiqueName.equalsIgnoreCase("cancel")) {
            // If user wants to cancel, go back to update panel
            updatePanel();
        } else {
            // If antique doesn't exist, user will get sent back.
            System.out.println("\n*** That antique does not exist. Please try again. ***");
        }

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

    // FUNCTION TO SHOW ALL STORES
    public void showStores() {
        System.out.println("============== ALL STORES ===============");

        HashMap<String, Store> storeMap = storeRepository.showStores();

        for (Map.Entry<String, Store> storeSet : storeMap.entrySet()) {
            System.out.println(storeSet.getKey() + " = " + storeSet.getValue());
        }

        System.out.println("=========================================");

        goBack();
    }

    // FUNCTION TO MAKE NEW USER
    public void makeUser() {
        String name;
        double bankBalance;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWrite the name of the new user (CANCEL to cancel): ");
        name = inputScanner.nextLine();

        if (userRepository.userExists(name)) {
            System.out.println("\n*** That username already exists. Please try again. ***");
        } else if (name.equalsIgnoreCase("CANCEL") || name.equalsIgnoreCase("cancel")) {
            goBack();
        } else {
            System.out.println("\nWrite how much there's in the bank balance: ");
            bankBalance = inputScanner.nextDouble();

            User newUser = new User(name, bankBalance);
            userRepository.addUser(newUser);
        }

        goBack();
    }

    // FUNCTION TO MAKE NEW STORE
    public void makeStore() {
        String name;

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWrite the name of the new store (CANCEL to cancel): ");
        name = inputScanner.nextLine();

        if (storeRepository.storeExists(name)) {
            System.out.println("\n*** That store name already exists. Please try again. ***");
        } else if (name.equalsIgnoreCase("CANCEL") || name.equalsIgnoreCase("cancel")) {
            goBack();
        } else {
            Store newStore = new Store(name);
            storeRepository.addStore(newStore);
        }

        goBack();
    }
}
