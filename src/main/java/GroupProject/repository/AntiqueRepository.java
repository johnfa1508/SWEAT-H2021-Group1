package GroupProject.repository;

import GroupProject.model.Antique;

public interface AntiqueRepository {
    // FIXME: Delete getAntique()?
    void getAntique(String antiqueName);

    void getAllAntiques();
    void deleteAntique(String antiqueKey);
    void addAntique(Antique antique);
    void editAntique(String antiqueKey, Antique antique);
    void purchaseAntique(String antiqueKey);
}
