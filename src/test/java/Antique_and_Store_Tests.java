// THIS JAVA FILE CONTAINS TESTS WHICH INVOLVES BOTH ANTIQUE- & STORE-OBJECTS

import GroupProject.model.Antique;
import GroupProject.model.Store;
import GroupProject.model.User;
import GroupProject.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class Antique_and_Store_Tests {
    // Use antiquesTest.json as test-file
    AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiquesTest.json");

    // Use storesTest.json as test-file
    StoreRepository storeRepository = new StoreJSONRepository("storesTest.json");

    // Use usersTest.json as test-file
    UserRepository userRepository = new UserJSONRepository("usersTest.json");

    // Make new antique for testing
    Antique newAntique = new Antique("antiqueTest", "table",
            "tableDescription", 200, "ikea", "SALE");

    // Make new store for testing
    Store newStore = new Store("ikea");

    // Make new user for testing
    User newUser = new User("userTest", 0);

    // TEST IF storeBids RETURNS ANTIQUES THAT ARE SOLD BY THE STORE AND IS ACTIVE
    @Test
    public void writeLastBidder_writes_user_as_last_bidder() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);
        storeRepository.addStore(newStore);

        // Make store seller
        antiqueRepository.writeSeller(newAntique, newStore.getName());

        // Make user as last bidder
        antiqueRepository.writeLastBidder(newAntique, newUser);

        HashMap<String, Antique> returnMap = antiqueRepository.storeBids(newStore);

        for (Map.Entry<String, Antique> antiqueSet : returnMap.entrySet()) {
            Assertions.assertTrue(antiqueSet.getValue().getSellerName().equalsIgnoreCase(newStore.getName()));
        }
    }
}
