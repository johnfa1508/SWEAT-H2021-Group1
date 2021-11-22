package GroupProject.repository;

import GroupProject.model.Antique;
import GroupProject.model.User;
import java.util.ArrayList;
import java.util.HashMap;

public interface AntiqueRepository {
    Antique getAntique(String antiqueKey);

    boolean isEmpty();
    ArrayList<String> showAntiqueTypes();
    HashMap<String, Antique> showAntiquesForSale();
    HashMap<String, Antique> showPurchaseHistory();
    void addAntique(Antique antique);
    void deleteAntique(String antiqueKey);
    ArrayList<String> showAntiqueNames(boolean showAll);
    HashMap<String, Antique> showSpecificAntique(String antiqueType);
    void editAntique(String antiqueKey, Antique antique);
    void purchaseAntique(Antique antique, User user);
    void addFavorite(Antique antique, User user);
    void removeFavorite(Antique antique, User user);
    HashMap<String, Antique> favoritedByUser(User user);
    boolean antiqueExists(Antique antique);
}
