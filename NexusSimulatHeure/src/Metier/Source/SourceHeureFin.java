
package Metier.Source;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

public class SourceHeureFin extends Source {
    private double heureFin;
    
    public SourceHeureFin(double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public double getheureFin(){
        return heureFin;
    }
    public void setheureFin(int Fin){
        heureFin = Fin;
    }
}
