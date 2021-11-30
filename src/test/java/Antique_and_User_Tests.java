// THIS JAVA FILE CONTAINS TESTS WHICH INVOLVES BOTH ANTIQUE- & USER-OBJECTS

import GroupProject.model.Antique;
import GroupProject.model.User;
import GroupProject.repository.AntiqueJSONRepository;
import GroupProject.repository.AntiqueRepository;
import GroupProject.repository.UserJSONRepository;
import GroupProject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Antique_and_User_Tests {
    // Use antiquesTest.json as test-file
    AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiquesTest.json");

    // Use usersTest.json as test-file
    UserRepository userRepository = new UserJSONRepository("usersTest.json");

    // Make new antique for testing
    Antique newAntique = new Antique("antiqueTest", "table",
            "tableDescription", 200, "ikea", "SALE");

    // Make new antique of another type for testing
    Antique newAntique2 = new Antique("antiqueTest2", "chair",
            "chairDescription", 150, "ikea", "AUCTION");

    // Make new user for testing
    User newUser = new User("userTest", 0);

    // TEST IF addFavorite() ADDS USER IN ANTIQUE FAVORITES LIST
    @Test
    public void addFavorite_adds_user_to_antique_list_of_favorites() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.addFavorite(newAntique, newUser);

        Assertions.assertTrue(newAntique.getFavorites().contains(newUser.getName()));
    }

    // TEST IF removeFavorite() REMOVES USER IN ANTIQUE FAVORITE LIST
    @Test
    public void removeFavorite_removes_user_from_antique_favorites() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.removeFavorite(newAntique, newUser);

        Assertions.assertFalse(newAntique.getFavorites().contains(newUser.getName()));
    }

    // TEST IF writeLastBidder() WRITES USER AS ANTIQUE'S LAST BIDDER
    @Test
    public void writeLastBidder_writes_user_as_last_bidder() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.writeLastBidder(newAntique, newUser);

        Assertions.assertTrue(newAntique.getLastBidder().equalsIgnoreCase(newUser.getName()));
    }

    // TEST IF writeBuyer() WRITES USER AS ANTIQUE'S BUYER
    @Test
    public void writeBuyer_writes_user_as_buyer() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.writeBuyer(newAntique, newUser);

        Assertions.assertTrue(newAntique.getBuyer().equalsIgnoreCase(newUser.getName()));
    }

    // TEST IF favoritedByUser() RETURNS MAP OF ANTIQUES FAVORITED BY USER
    @Test
    public void favoritedByUser_returns_map_of_antiques_favorited_by_user() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.addFavorite(newAntique, newUser);

        Assertions.assertTrue(antiqueRepository.favoritedByUser(newUser).containsKey(newAntique.getName()));
    }

    // TEST IF favoritedByUser() RETURNS MAP OF ANTIQUES NOT FAVORITED BY USER
    @Test
    public void favoritedByUser_does_not_return_antique_not_favorited_by_user() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);
        userRepository.addUser(newUser);

        antiqueRepository.addFavorite(newAntique, newUser);

        Assertions.assertFalse(antiqueRepository.favoritedByUser(newUser).containsKey(newAntique2.getName()));
    }

    // TEST IF userBids() RETURNS MAP OF ANTIQUES USER HAS BID ON
    @Test
    public void userBids_returns_map_of_antiques_user_has_bid_on() {
        antiqueRepository.addAntique(newAntique);
        userRepository.addUser(newUser);

        antiqueRepository.writeLastBidder(newAntique, newUser);
        antiqueRepository.writeLastBidder(newAntique2, newUser);

        Assertions.assertTrue(antiqueRepository.userBids(newUser).containsKey(newAntique.getName()));
    }

    // TEST IF userBids() RETURNS MAP OF ANTIQUES USER HAS NOT BID ON
    @Test
    public void userBids_does_not_return_antique_user_has_not_bid_on() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);
        userRepository.addUser(newUser);

        antiqueRepository.writeLastBidder(newAntique, newUser);

        Assertions.assertFalse(antiqueRepository.userBids(newUser).containsKey(newAntique2.getName()));
    }
}
