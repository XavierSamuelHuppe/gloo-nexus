
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.DistributionTriangulaire;

public class SourceHeureFin extends Source {
    private double heureFin;
    
    public SourceHeureFin(double heureFin, Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution, String typeSource, int capaciteVehicule, Circuit circuit){
        super(pointDepart, heureDebut, frequence, distribution, typeSource, capaciteVehicule, circuit);
        this.heureFin = heureFin;
    }
    
    public double getheureFin(){
        return heureFin;
    }
    public void setheureFin(int Fin){
        heureFin = Fin;
    }
}
