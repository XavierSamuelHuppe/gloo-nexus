
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;

public class SourceHeureFin extends Source {
    private double heureFin;
    
    public SourceHeureFin(double heureFin, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        super(pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        this.heureFin = heureFin;
    }
    
    public double getheureFin(){
        return heureFin;
    }
    public void setheureFin(int Fin){
        heureFin = Fin;
    }
}
