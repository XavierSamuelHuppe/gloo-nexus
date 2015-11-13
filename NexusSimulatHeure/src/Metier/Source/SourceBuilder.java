
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;

public class SourceBuilder {
    public Source ConstruireSource(int nombreMax, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        Source nouvelleSource = new SourceHeureFin(nombreMax , pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        return nouvelleSource;
    }
    
    public Source ConstruireSource(double heureFin, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        Source nouvelleSource = new SourceHeureFin(heureFin , pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        return nouvelleSource;
    }
}
