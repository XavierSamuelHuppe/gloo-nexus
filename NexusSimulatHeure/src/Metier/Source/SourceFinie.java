package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;
import Metier.Circuit.ConteneurPassagers;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class SourceFinie extends Source {
    private int nombreMax;
    private int nombreCree;
    
    public SourceFinie(int nombreMax, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        super(pointDepart, heureDebut, frequence, distribution, passagers, circuit, sim);
        this.nombreMax = nombreMax;
        this.nombreCree = 0;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }

    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(heureDebut.isBefore(heureCourante) && nombreCree < nombreMax){
            genererVehicule();
            nombreCree++;
        }
    }

    @Override
    public void reInitialiserValeursDepartSimulation() {
        nombreCree = 0;
    }
    
    
}