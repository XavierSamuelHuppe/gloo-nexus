
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Circuit.ConteneurPassagers;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class SourceBuilder {
    public Source ConstruireSource(int nombreMax, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        Source nouvelleSource = new SourceFinie(nombreMax , pointDepart, heureDebut, frequence, distribution, passagers, circuit, sim);
        return nouvelleSource;
    }
    
    public Source ConstruireSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        Source nouvelleSource = new SourceHeureFin(heureFin , pointDepart, heureDebut, frequence, distribution, passagers, circuit, sim);
        return nouvelleSource;
    }
}
