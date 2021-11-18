package GroupProject.repository;

import GroupProject.model.Antique;

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
    void purchaseAntique(Antique antique, String buyerName);
}
