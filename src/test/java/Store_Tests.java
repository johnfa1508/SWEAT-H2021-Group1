// THIS JAVA FILE CONTAINS TESTS WHICH INVOLVES STORE-OBJECTS

import GroupProject.model.Store;
import GroupProject.repository.StoreJSONRepository;
import GroupProject.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Store_Tests {
    // Use storesTest.json as test-file
    StoreRepository storeRepository = new StoreJSONRepository("storesTest.json");

    // Make new store for testing
    Store newStore = new Store("ikea");

    // TEST IF storeExists() RETURNS TRUE IF STORE EXISTS
    @Test
    public void storeExists_returns_true_if_user_exists() {
        storeRepository.addStore(newStore);

        Assertions.assertTrue(storeRepository.storeExists(newStore.getName()));
    }

    // TEST IF storeExists() RETURNS FALSE IF STORE DOESN'T EXIST
    @Test
    public void storeExists_returns_false_if_user_does_not_exist() {
        storeRepository.removeStore(newStore);

        Assertions.assertFalse(storeRepository.storeExists(newStore.getName()));
    }
}
