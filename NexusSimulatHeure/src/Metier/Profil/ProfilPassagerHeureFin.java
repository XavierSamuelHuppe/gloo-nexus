package Metier.Profil;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

public class ProfilPassagerHeureFin extends ProfilPassager {
    
    private double heureFin;
    
    public ProfilPassagerHeureFin(double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public double getHeureFin(){
        return heureFin;
    }
    public void setHeureFin(int Fin){
        heureFin = Fin;
    }
}
