package GroupProject.repository;

import GroupProject.model.Antique;

public interface AntiqueRepository {
    Antique getAntique(String antiqueKey);

    boolean isEmpty();
    void showAntiqueTypes();
    void showAntiquesForSale();
    void showPurchaseHistory();
    void addAntique(Antique antique);
    void deleteAntique(String antiqueKey);
    void showAntiqueNames(boolean showAll);
    void showSpecificAntique(String antiqueType);
    void editAntique(String antiqueKey, Antique antique);
    void purchaseAntique(Antique antique, String buyerName);
}
