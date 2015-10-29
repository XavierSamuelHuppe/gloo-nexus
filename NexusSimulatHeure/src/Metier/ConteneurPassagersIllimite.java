package Metier;

import java.util.*;

public class ConteneurPassagersIllimite implements ConteneurPassagers{

    private List<Passager> passagers;
    
    public ConteneurPassagersIllimite(){
        passagers = new ArrayList();
    }
    
    @Override
    public int obtenirNombrePassagers() {
        return passagers.size();
    }

    @Override
    public void ajouterPassager(Passager passager) {
        passagers.add(passager);
    }

    @Override
    public void retirerPassager(Passager passager) {
        passagers.remove(passagers);
    }
    
}
