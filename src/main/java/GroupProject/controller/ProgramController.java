package GroupProject.controller;

import GroupProject.model.Antique;
import GroupProject.model.Store;
import GroupProject.model.User;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.StoreRepository;
import GroupProject.repository.UserRepository;
import java.util.*;

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

        System.out.println("\n\n================== LOGIN ===============" +
                "\n1. Log in as admin" +
                "\n2. Log in as user" +
                "\n3. Log in as store" +
                "\n4. Make new user" +
                "\n5. Leave" +
                "\n========================================");

        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                adminPanel();      // Go to admin-screen
            case 2:
                loginUserPanel();  // Go to user-login screen
            case 3:
                loginStorePanel(); // Go to store-screen
            case 4:
                makeUser();        // Make a new user
            case 5:
                break;             // Leave
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

    // USER SCREEN. RECEIVES User-OBJECT TO TRACK WHICH USER IS CURRENTLY LOGGED IN
    public void userPanel(User user) {
        currentUser = user;

        isUser = true;
        isAdmin = false;
        isStore = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\n\n================= USER =================" +
                "\n1. See antiques" +
                "\n2. Bid on an antique" +
                "\n3. Purchase an antique" +
                "\n4. Show active bids" +
                "\n5. Add antique as favorite" +
                "\n6. Remove antique as favorite" +
                "\n7. Show favorites" +
                "\n8. See bank balance" +
                "\n9. Deposit money" +
                "\n10. Log out" +
                "\n========================================");
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                showAntiques();            // Show antique screen
            case 2:
                bidOnAntique();            // Bid on an antique
            case 3:
                purchaseAntique();      // Purchase an antique
            case 4:
                seeBids("USER");
                goBack();               // Show active bids
            case 5:
                addFavorite();  // Add antique to favorites
            case 6:
                removeFavorite();   // Remove antique from favorite list
            case 7:
                showFavorites();    // Show favorited antiques
            case 8:
                showBalance("USER"); // Show balance
            case 9:
                depositMoney();      // Deposit money
            case 10:
                loginPanel();   // Log out
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
        System.out.println("\n\n================= STORE =================" +
                "\n1. See antiques" +
                "\n2. Set antique for bidding" +
                "\n3. Set antique for sale" +
                "\n4. See active bids" +
                "\n5. End bidding on an antique" +
                "\n6. See bank balance" +
                "\n7. Log out");
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                showAntiques(); // Show antique screen
            case 2:
                makeAntique(false, "AUCTION");  // Make new antique for auction
            case 3:
                makeAntique(false, "SALE");     // Make new antique for sale
            case 4:
                seeBids("STORE");
                goBack();   // See active bids
            case 5:
                endBidding();   // End bidding on an antique
            case 6:
                showBalance("STORE");   // Show balance
            case 7:
                loginPanel();   // Log out
        }
    }

    // ADMIN SCREEN
    public void adminPanel() {
        isUser = false;
        isAdmin = true;
        isStore = false;

        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\n\n================ ADMIN =================" +
                "\n1. See antiques" +
                "\n2. Update an antique" +
                "\n3. Purchase history" +
                "\n4. See all users" +
                "\n5. See all stores" +
                "\n6. Make new store" +
                "\n7. Log out");
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                showAntiques(); // Show antique screen
            case 2:
                updatePanel();  // Go to admin update panel
            case 3:
                purchaseHistory();  // See purchase history
            case 4:
                showUsers();    // Show all users
            case 5:
                showStores();   // Show all stores
            case 6:
                makeStore();    // Make a new store
            case 7:
                loginPanel();   // Go back
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
        System.out.println("\n\n=============== ANTIQUES ===============" +
                "\n1. See all antiques for sale" +
                "\n2. See specific types" +
                "\n3. Go back" +
                "\n========================================");
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                showAntiquesForSale();  // Show all antiques if there's antiques for sale
            case 2:
                showSpecificAntique();  // Show specific antique type
            case 3:
                goBack();   // Go back
        }
    }

    // SHOW ANTIQUES FOR SALE
    public void showAntiquesForSale() {
        System.out.println("============ AVAILABLE ITEMS ============");

        // HashMap to store hashmap received from repository
        HashMap<String, Antique> antiques = antiqueRepository.showAntiquesForSale();

        // Go through hashmap and print out
        for (Map.Entry<String, Antique> antiqueSet : antiques.entrySet()) {
            System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
        }

        System.out.println("=========================================");

        // Go back to show antiques screen
        showAntiques();
    }

    // CHOOSE ANTIQUE-TYPE SCREEN
    public void showSpecificAntique() {
        // Show types of antiques for sale
        System.out.println("\nThe types of antiques for sale are: ");
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
                System.out.println("\n============ AVAILABLE ITEMS ============");

                // Show antiques of that type
                HashMap<String, Antique> specificAntiqueTypes = antiqueRepository.showSpecificAntique(userInput);
                for (Map.Entry<String, Antique> antiqueSet : specificAntiqueTypes.entrySet()) {
                    System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
                }

                System.out.println("=========================================");
            }
        }

        // Go back to antiques-screen
        showAntiques();
    }

    // PURCHASE AN ANTIQUE FOR SALE
    public void bidOnAntique(){
        // Show antiques in auction
        System.out.println("Antiques that are in auction are: ");
        showAntiqueNames("AUCTION");

        String itemInCart;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to bid on? (CANCEL to cancel): ");
        itemInCart = inputScanner.nextLine();

        // Put antique-object in variable for easier handling
        Antique antique = antiqueRepository.getAntique(itemInCart);

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(itemInCart)) {
            // If the bidder does not have enough money, program will send the bidder back
            if (currentUser.getBankBalance() < antique.getPrice()) {
                System.out.println("\n*** Your bank balance is insufficient. Please try again.\n");
            } else {
                // If item is already sold, user will be sent back
                if (antique.getSold()) {
                    System.out.println("\n*** That item is already sold! Please try again. ***\n");
                } else {
                    // If item is for sale and not for auction, user will be sent back
                    if (antique.getSellType().equalsIgnoreCase("SALE")) {
                        System.out.println("\n*** That item is for sale, not auction! Please try again. ***\n");
                    } else {
                        // If user tries to bid again after bidding last time, user will be sent back
                        if (antique.getLastBidder().equalsIgnoreCase(currentUser.getName())) {
                            System.out.println("\n*** You have already set a bid on that item! ***\n");
                        } else {
                            // If the antique has a last bidder, and it's not the current user; do this
                            if (antique.getLastBidder() != null &&
                                    !Objects.equals(antique.getLastBidder(), currentUser.getName())) {
                                // Last bidder will get money back
                                userRepository.getUser(antique.getLastBidder()).depositMoney(antique.getPrice());
                            }

                            double bidAmount;
                            System.out.printf("\nHow much would you like to bid? (Current price: %s): ", antique.getPrice());
                            bidAmount = inputScanner.nextDouble();

                            // If the bid amount is more than the current user's bank balance, program will send the bidder back
                            if (bidAmount > currentUser.getBankBalance()) {
                                System.out.println("\n*** Your bank balance is insufficient. Please try again. ***\n");
                            } else if (bidAmount < antique.getPrice()) {
                                // If the bidder bids less than what the current price is, program will send the bidder back
                                System.out.println("\n*** You have to bid higher than previous price. Please try again. ***\n");
                            } else {
                                System.out.println("\nBidding success.\n");

                                // Update antique's price
                                antiqueRepository.writeNewPrice(antique, bidAmount);

                                // Current user's balance will get deducted
                                userRepository.withdrawMoney(currentUser, bidAmount);

                                // Set current user as last bidder
                                antiqueRepository.writeLastBidder(antique, currentUser);
                            }
                        }
                    }
                }
            }
        } else if (itemInCart.equalsIgnoreCase("CANCEL")) {
            // If user wants to cancel, go back
            goBack();
        } else {
            // If antique written does not exist, user will get sent back
            System.out.println("\n*** That antique does not exist. Please try again ***");
        }

        goBack();
    }

    // FUNCTION TO PURCHASE ANTIQUE
    public void purchaseAntique() {
        // Show antiques for sale
        System.out.println("Antiques that are for sale are: ");
        showAntiqueNames("SALE");

        String itemInCart;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\nWhich item would you like to buy? (CANCEL to cancel): ");
        itemInCart = inputScanner.nextLine();

        // Put antique-object in variable for easier handling
        Antique antique = antiqueRepository.getAntique(itemInCart);

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(itemInCart)) {
            // If the bidder does not have enough money, program will send the bidder back
            if (currentUser.getBankBalance() < antique.getPrice()) {
                System.out.println("\n*** Your bank balance is insufficient. Please try again. ***\n");
            } else if (antique.getSold()) {
                // If item is already sold, user will be sent back
                System.out.println("\n*** That item is already sold! Please try again. ***\n");
            } else if (itemInCart.equalsIgnoreCase("CANCEL")) {
                // If user wants to cancel, go back
                goBack();
            } else if (antique.getSellType().equalsIgnoreCase("AUCTION")) {
                System.out.println("\n*** That item is for auction! Please try again. ***\n");
            } else {
                System.out.println("\n*** Item bought successfully. ***\n");

                // Set current user as buyer
                antiqueRepository.writeBuyer(antique, currentUser);

                // Deduct money from user's balance
                userRepository.withdrawMoney(currentUser, antique.getPrice());

                // Give money to store
                moneyTransaction(antique);
            }

            goBack();
        } else {
            // If antique written does not exist, user will get sent back
            System.out.println("\n*** That antique does not exist. Please try again ***");
        }

        goBack();
}

    // FUNCTION TO SHOW NAMES OF ANTIQUES FOR SALE
    public void showAntiqueNames(String sellType) {
        ArrayList<String> antiqueNamesArray = antiqueRepository.showAntiqueNames(false, sellType);

        // If the return list is empty, tell user
        if (antiqueNamesArray.isEmpty()) {
            System.out.println("\n*** There are currently no items for sale. ***\n");

            goBack();
        } else {
            for (String antiqueNames : antiqueNamesArray) {
                System.out.println(antiqueNames);
            }
        }
    }

    // FUNCTION TO SEE ACTIVE BIDS
    public void seeBids(String userType) {
        HashMap<String,Antique> bidMap;

        // Checks which usertype is asking for the bids
        if (userType.equalsIgnoreCase("STORE")) {
            bidMap = antiqueRepository.storeBids(currentStore);
        } else {
            bidMap = antiqueRepository.userBids(currentUser);
        }

        // If the map is empty, send user back
        if (bidMap.isEmpty()) {
            System.out.println("*** There are no active bids ***");
            goBack();
        } else {
            // Print out bids
            System.out.println("\n============= ACTIVE BIDS =============");

            for (Map.Entry<String, Antique> antiqueSet : bidMap.entrySet()) {
                System.out.println(antiqueSet.getKey() + " = " + antiqueSet.getValue());
            }

            System.out.println("\n=======================================");
        }
    }

    // FUNCTION TO ADD ANTIQUE TO FAVORITES
    public void addFavorite() {
        // Show antiques for sale
        System.out.println("Antiques that are for sale are: ");
        showAntiqueNames("ALL");

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

        if (money < 0) {
            System.out.println("\n*** You entered a negative number. ***");
        } else {
            userRepository.depositMoney(currentUser, money);
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
    public Antique makeAntique(boolean antiqueReplace, String sellType) {
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
            Antique newAntique = new Antique(name, type, description, price, currentStore.getName(), sellType);

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

    // FUNCTION FOR STORE TO END BIDDING ON AN ANTIQUE
    public void endBidding() {
        // Show active bids
        seeBids("STORE");

        String antiqueName;
        System.out.println("\nWrite the name of the antique you would like to end (CANCEL to go back): ");
        Scanner inputScanner = new Scanner(System.in);
        antiqueName = inputScanner.nextLine();

        // Store antique-object for easier handling
        Antique antique = antiqueRepository.getAntique(antiqueName);

        // If antique exists, program will continue
        if (antiqueRepository.antiqueExists(antiqueName)) {
            if (antique.getLastBidder() == null) {
                System.out.println("\n*** That antique does not have a bidder ***");
            } else if (!Objects.equals(antique.getSellerName(), currentStore.getName())) {
                System.out.println("\n*** That antique is not sold by you ***");
            }

            else {
                moneyTransaction(antique);
            }
        } else if (antiqueName.equalsIgnoreCase("CANCEL") ||
                antiqueName.equalsIgnoreCase("cancel")) {
            // If store wants to cancel, go back to store panel
            goBack();
        } else {
            // If antique doesn't exist, store will get sent back
            System.out.println("\n*** That antique does not exist. Please try again. ***");
        }

        goBack();
    }

    // FUNCTION TO HANDLE TRANSACTION FOR STORE
    public void moneyTransaction(Antique antique) {
        // Updates the antique(boolean sold)
        antiqueRepository.purchaseAntique(antique);

        // Add money to store's balance
        storeRepository.depositMoney(storeRepository.getStore(antique.getSellerName()), antique.getPrice());
    }

    // COMMANDS FOR ADMIN
    // ADMIN - UPDATE SCREEN
    public void updatePanel() {
        int choice;
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("\n\n============ ADMIN - UPDATE ============" +
                "\n1. Delete an antique" +
                "\n2. Edit an antique" +
                "\n3. Go back");
        choice = inputScanner.nextInt();

        switch (choice) {
            case 1:
                deleteAntique();    // Delete an antique
            case 2:
                editAntique();  // Edit an antique
            case 3:
                goBack();   // Go back to previous panel
        }
    }

    // FUNCTION TO DELETE ANTIQUE
    public void deleteAntique() {
        // Show antique names
        System.out.println("Antiques that can be deleted: ");
        ArrayList<String> antiqueNamesArray = antiqueRepository.showAntiqueNames(true, "ALL");

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
        showAntiqueNames("ALL");

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
                if (antiqueRepository.getAntique(antiqueName).getSellType().equalsIgnoreCase("AUCTION")) {
                    // Make new antique in auction using makeAntique()-function
                    Antique newAntique = makeAntique(true, "AUCTION");

                    // Replace antique with new antique
                    antiqueRepository.editAntique(antiqueName, newAntique);
                } else {
                    // Make new antique for sale using makeAntique()-function
                    Antique newAntique = makeAntique(true, "SALE");

                    // Replace antique with new antique
                    antiqueRepository.editAntique(antiqueName, newAntique);
                }
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
