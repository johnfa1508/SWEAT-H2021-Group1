package GroupProject.repository;

import GroupProject.model.Antique;

public interface AntiqueRepository {
    // FIXME: Delete getAntique()?
    Antique getAntique(String antiqueKey);

    void showAllAntiques();
    void deleteAntique(String antiqueKey);
    void addAntique(Antique antique);
    void editAntique(String antiqueKey, Antique antique);
    void purchaseAntique(String antiqueKey);
    void showAntiqueTypes();
    void showSpecificAntique(String antiqueType);
    void showAntiqueNames();
}
