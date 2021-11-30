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

    // TEST IF addStore() ADDS STORE TO HASHMAP OF STORES
    @Test
    public void addStore_adds_store_to_hashmap_of_stores() {
        storeRepository.addStore(newStore);

        Assertions.assertNotNull(storeRepository.getStore(newStore.getName()));
    }

    // TEST IF removeStore() REMOVES STORE FROM HASHMAP OF STORES
    @Test
    public void removeStore_removes_store_from_hashmap_of_stores() {
        storeRepository.removeStore(newStore);

        Assertions.assertFalse(storeRepository.showStores().containsKey(newStore.getName()));
    }

    // TEST IF getStore RETURNS STORE IF IT EXISTS
    @Test
    public void getStore_returns_store_if_it_exists() {
        storeRepository.addStore(newStore);

        Assertions.assertNotNull(storeRepository.getStore(newStore.getName()));
    }

    // TEST IF getStore RETURNS NULL IF STORE DOES NOT EXIST
    @Test
    public void getStore_returns_null_if_it_does_not_exist() {
        storeRepository.removeStore(newStore);

        Assertions.assertNull(storeRepository.getStore(newStore.getName()));
    }

    // TEST IF showStores RETURNS STORES IF STORES EXISTS
    @Test
    public void showStores_returns_stores_if_stores_exists() {
        storeRepository.addStore(newStore);

        Assertions.assertTrue(storeRepository.showStores().containsKey(newStore.getName()));
    }

    // TEST IF showStores RETURNS EMPTY LIST IF STORES DONT EXIST
    @Test
    public void showStores_returns_empty_list_if_stores_do_not_exist() {
        storeRepository.removeStore(newStore);

        Assertions.assertTrue(storeRepository.showStores().isEmpty());
    }

    // TEST IF showStoreNames RETURNS STORE NAMES IF STORE EXISTS
    @Test
    public void showStoreNames_returns_names_of_stores_that_exist() {
        storeRepository.addStore(newStore);

        Assertions.assertTrue(storeRepository.showStoreNames().contains(newStore.getName()));
    }

    // TEST IF depositMoney ADDS MONEY TO STORE'S BALANCE
    @Test
    public void depositMoney_adds_money_to_store_balance() {
       storeRepository.addStore(newStore);

       double oldMoney = newStore.getBankBalance();
       storeRepository.depositMoney(newStore, 100);
       double newMoney = newStore.getBankBalance();

       Assertions.assertTrue(oldMoney < newMoney);
    }

    // TEST IF withdrawMoney TAKES MONEY FROM STORE'S BALANCE
    @Test
    public void withdrawMoney_takes_money_from_store_balance() {
        storeRepository.addStore(newStore);

        double oldMoney = newStore.getBankBalance();
        storeRepository.withdrawMoney(newStore, 100);
        double newMoney = newStore.getBankBalance();

        Assertions.assertTrue(oldMoney > newMoney);
    }
}
