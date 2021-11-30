// THIS JAVA FILE CONTAINS TESTS WHICH INVOLVES ANTIQUE-OBJECTS

import GroupProject.model.Antique;
import GroupProject.repository.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class Antique_Tests {
    // Use antiquesTest.json as test-file
    AntiqueRepository antiqueRepository = new AntiqueJSONRepository("antiquesTest.json");

    // Make new antique for testing
    Antique newAntique = new Antique("antiqueTest", "table",
            "tableDescription", 200, "ikea", "SALE");

    // Make new antique of another type for testing
    Antique newAntique2 = new Antique("antiqueTest2", "chair",
            "chairDescription", 150, "ikea", "AUCTION");

    // TEST IF isEmpty()-FUNCTIONS RETURN TRUE IF THERE ARE NO ANTIQUES IN REPOSITORY
    @Test
    public void isEmpty_returns_true_if_there_are_no_antiques() {
        antiqueRepository.deleteAntique(newAntique.getName());
        antiqueRepository.deleteAntique(newAntique2.getName());

        Assertions.assertTrue(antiqueRepository.isEmpty());
    }

    // TEST IF isEmpty()-FUNCTION RETURNS FALSE IF THERE ARE ANTIQUES IN REPOSITORY
    @Test
    public void isEmpty_returns_false_if_there_are_antiques() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertFalse(antiqueRepository.isEmpty());
    }

    // TEST IF antiqueExists()-FUNCTION RETURNS TRUE IF ANTIQUE EXISTS
    @Test
    public void antiqueExists_returns_true_if_antique_exists() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.antiqueExists(newAntique.getName()));
    }

    // TEST IF antiqueExists()-FUNCTION RETURNS FALSE IF ANTIQUE DOESN'T EXIST
    @Test
    public void antiqueExists_returns_false_if_antique_does_not_exist() {
        antiqueRepository.deleteAntique(newAntique.getName());

        Assertions.assertFalse(antiqueRepository.antiqueExists(newAntique.getName()));
    }

    // TEST IF showAntiquesForSale()-MAP CONTAINS ANTIQUE IF IT'S FOR SALE
    @Test
    public void showAntiquesForSale_returns_antique_if_it_is_not_sold() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.showAntiquesForSale().containsKey(newAntique.getName()));
    }

    // TEST IF showAntiquesForSale()-MAP DOES NOT CONTAIN ANTIQUE WHEN IT'S SOLD
    @Test
    public void showAntiquesForSale_does_not_return_antique_if_it_is_sold() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.purchaseAntique(newAntique);

        Assertions.assertFalse(antiqueRepository.showAntiquesForSale().containsKey(newAntique.getName()));
    }

    // TEST IF showSpecificAntique() RETURNS A MAP THAT CONTAINS ANTIQUE
    @Test
    public void showSpecificAntique_returns_specific_type_of_antique() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.showSpecificAntique("table").
                containsKey(newAntique.getName()));
    }

    // TEST IF showSpecificAntique() RETURNS A MAP THAT CONTAINS ANTIQUE OF WRONG TYPE
    @Test
    public void showSpecificAntique_does_not_return_another_type() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);

        Assertions.assertFalse(antiqueRepository.showSpecificAntique("table").
                containsKey(newAntique2.getName()));
    }

    // TEST IF showAntiqueTypes() RETURNS LIST IF THERE ARE ANTIQUES FOR SALE
    @Test
    public void showAntiqueTypes_returns_list_if_items_are_for_sale() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);

        Assertions.assertNotNull(antiqueRepository.showAntiqueTypes());
    }

    // TEST IF showAntiqueTypes() RETURNS EMPTY LIST IF THERE ARE NO ANTIQUES FOR SALE
    @Test
    public void showAntiqueTypes_does_not_return_list_if_items_are_not_for_sale() {
        antiqueRepository.deleteAntique(newAntique.getName());
        antiqueRepository.deleteAntique(newAntique2.getName());

        Assertions.assertTrue(antiqueRepository.showAntiqueTypes().isEmpty());
    }

    // TEST IF showAntiqueNames() RETURNS NAMES OF ANTIQUES FOR SALE
    @Test
    public void showAntiqueNames_returns_items_for_sale() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.showAntiqueNames(false, "SALE").
                contains(newAntique.getName()));
    }

    // TEST IF showAntiqueNames() RETURNS NAMES OF ANTIQUES FOR AUCTION WHEN WE WANT ANTIQUES FOR SALE
    @Test
    public void showAntiqueNames_does_not_return_antiques_for_auction_if_SALE_is_sent_through_parameter() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);

        Assertions.assertFalse(antiqueRepository.showAntiqueNames(false, "SALE").
                contains(newAntique2.getName()));
    }

    // TEST IF showAntiqueNames() RETURNS NAMES OF ALL ANTIQUES IF showAll IN PARAMETER RECEIVES true
    @Test
    public void showAntiqueNames_returns_all_antiques_including_sold_antiques_if_true_is_sent_through_first_parameter() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.purchaseAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);

        ArrayList<String> returnArray = antiqueRepository.showAntiqueNames(true, "");

        Assertions.assertTrue(returnArray.contains(newAntique.getName()) &&
                returnArray.contains(newAntique2.getName()));
    }

    // TEST IF showPurchaseHistory() RETURNS SOLD ANTIQUES
    @Test
    public void showPurchaseHistory_returns_sold_antiques() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.purchaseAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.showPurchaseHistory().containsKey(newAntique.getName()));
    }

    // TEST IF showPurchaseHistory() RETURNS ANTIQUES THAT ARE NOT SOLD
    @Test
    public void showPurchaseHistory_returns_not_sold_antiques() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.purchaseAntique(newAntique);
        antiqueRepository.addAntique(newAntique2);

        Assertions.assertFalse(antiqueRepository.showPurchaseHistory().containsKey(newAntique2.getName()));
    }

    // TEST IF getAntique() RETURNS ANTIQUE
    @Test
    public void getAntique_returns_antique() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertNotNull(antiqueRepository.getAntique(newAntique.getName()));
    }

    // TEST IF getAntique() RETURNS ANYTHING IF ANTIQUE DOESN'T EXIST
    @Test
    public void getAntique_returns_null_if_antique_does_not_exist() {
        Assertions.assertNull(antiqueRepository.getAntique("adadadadad"));
    }

    // TEST IF addAntique() ADDS NEW ANTIQUE TO MAP OF ANTIQUES
    @Test
    public void addAntique_adds_antique_to_hashmap_of_antiques() {
        antiqueRepository.addAntique(newAntique);

        Assertions.assertTrue(antiqueRepository.antiqueExists(newAntique.getName()));
    }

    // TEST IF removeAntique() REMOVES ANTIQUE FROM MAP OF ANTIQUES
    @Test
    public void deleteAntique_removes_antique_from_hashmap_of_antiques() {
        antiqueRepository.addAntique(newAntique);
        antiqueRepository.deleteAntique(newAntique.getName());

        Assertions.assertFalse(antiqueRepository.antiqueExists(newAntique.getName()));
    }

    // TEST IF editAntique() REPLACES ANTIQUE FROM MAP OF ANTIQUES
    // FIXME: this
//    @Test
//    public void editAntique_replaces_antique_from_hashmap_of_antiques() {
//        antiqueRepository.addAntique(newAntique);
//
//        Antique replaceAntique = new Antique("antiqueTest1", "table",
//                "tableDescription", 200, "ikea", "SALE");
//
//        antiqueRepository.editAntique(newAntique.getName(), replaceAntique);
//
//        Assertions.assertTrue(antiqueRepository.antiqueExists(replaceAntique.getName()));
//    }
}
