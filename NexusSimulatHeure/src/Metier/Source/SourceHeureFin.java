
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;
import Metier.Circuit.ConteneurPassagers;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class SourceHeureFin extends Source {
    private LocalTime heureFin;
    
    public SourceHeureFin(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        super(pointDepart, heureDebut, frequence, distribution, passagers, circuit, sim);
        this.heureFin = heureFin;
    }
    
    public LocalTime getheureFin(){
        return heureFin;
    }
    public void setheureFin(LocalTime Fin){
        heureFin = Fin;
    }
    
    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(heureDebut.isBefore(heureCourante) && heureCourante.isAfter(heureFin)){
            genererVehicule();
        }
    }
    
    @Override
    public void reInitialiserValeursDepartSimulation() {
        //Cette implémentation de réinitialise rien.
    }
}
