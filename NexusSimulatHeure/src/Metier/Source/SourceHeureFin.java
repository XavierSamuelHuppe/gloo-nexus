
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;
import Metier.Circuit.ConteneurPassagers;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class SourceHeureFin extends Source {
    private LocalTime heureFin;
    
    private LocalTime prochaineGeneration;
    
    public SourceHeureFin(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        super(pointDepart, heureDebut, distribution, passagers, circuit, sim);
        this.heureFin = heureFin;
        
        prochaineGeneration = heureDebut;
    }
    
    public LocalTime getheureFin(){
        return heureFin;
    }
    public void setheureFin(LocalTime Fin){
        heureFin = Fin;
    }
    private int nombreCree;
    
    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(prochaineGeneration.isAfter(heureFin))
            return;
        
        if(prochaineGeneration.isBefore(heureCourante)){
            genererVehicule();
            nombreCree++;
            prochaineGeneration = heureDebut.plusSeconds((long)(getFrequence() * (double)nombreCree));
        }
    }
    
    @Override
    public void reInitialiserValeursDepartSimulation() {
        //Cette implémentation de réinitialise rien.
    }
    
    @Override
    public String obtenirDescriptionSource() {
        return this.getCircuit().getNom() + " : " + heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE) + " à " + heureFin.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }
}
