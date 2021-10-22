package GroupProject.repository;

import GroupProject.model.Antique;

public interface AntiqueRepository {
    Antique getAntique(String antiqueKey);

    void showAntiquesForSale();
    void deleteAntique(String antiqueKey);
    void addAntique(Antique antique);
    void editAntique(String antiqueKey, Antique antique);
    void purchaseAntique(Antique antique, String buyerName);
    void showAntiqueTypes();
    void showSpecificAntique(String antiqueType);
    void showAntiqueNames(boolean showAll);
    void showPurchaseHistory();
    boolean isEmpty();
}
