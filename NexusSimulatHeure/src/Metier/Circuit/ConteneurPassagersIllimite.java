package Metier.Circuit;

import Metier.Carte.Point;
import Metier.Passager;
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
    public List<Passager> debarquer(Point point) {
        List<Passager> retour = new ArrayList();
        for(Passager p: passagers){
            if(p.veut(point)){
                retour.add(p);
            }
        }
        passagers.removeAll(retour);
        return retour;
    }

    @Override
    public List<Passager> embarquer(Circuit circuit) {
        List<Passager> retour = new ArrayList();
        for(Passager p: passagers){
            if(p.veut(circuit)){
                retour.add(p);
            }
        }
        passagers.removeAll(retour);
        return retour;
    }

    @Override
    public void octroyer(List<Passager> passagers) {
        for(Passager p: passagers){
            this.passagers.add(p);
        }
    }
    
}
