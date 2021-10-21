// REPOSITORY FOR EARLY TESTING
package GroupProject.repository;

import GroupProject.model.Antique;
import java.util.ArrayList;

public class AntiqueDataRepository implements AntiqueRepository {
    private ArrayList<Antique> antiques = new ArrayList<>();

    public AntiqueDataRepository() {

    }

    @Override
    public void showAllAntiques() {
        for (Antique anAntique : antiques) {
            System.out.println(anAntique);
        }
    }

    @Override
    public Antique getAntique(String antiqueName) {
        return null;
    }

    @Override
    public void deleteAntique(String antiqueName) {
        antiques.removeIf(antique -> antique.getName().equals(antiqueName));
    }

    @Override
    public void addAntique(Antique antique) {
        antiques.add(antique);
    }

    @Override
    public void editAntique(String antiqueKey, Antique antique) {

    }

    @Override
    public void purchaseAntique(String antiqueKey) {

    }

    @Override
    public void getAntiqueTypes() {

    }

    @Override
    public void showSpecificAntique(String antiqueType) {

    }
}
