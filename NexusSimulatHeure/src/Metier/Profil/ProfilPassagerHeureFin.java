package Metier.Profil;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

public class ProfilPassagerHeureFin extends ProfilPassager {
    
    private int heureFin;
    
    public ProfilPassagerHeureFin(int heureFin, Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
        super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public int getHeureFin(){
        return heureFin;
    }
    public void setHeureFin(int Fin){
        heureFin = Fin;
    }
}
