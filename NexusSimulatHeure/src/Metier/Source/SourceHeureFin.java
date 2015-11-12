
package Metier.Source;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

public class SourceHeureFin extends Source {
    private int heureFin;
    
    public SourceHeureFin(int heureFin, Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
        super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public int getheureFin(){
        return heureFin;
    }
    public void setheureFin(int Fin){
        heureFin = Fin;
    }
}
