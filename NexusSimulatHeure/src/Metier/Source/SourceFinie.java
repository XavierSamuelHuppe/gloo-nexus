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
    
    private LocalTime prochaineGeneration;
    
    public SourceFinie(int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        super(pointDepart, heureDebut, distribution, passagers, circuit, sim);
        this.nombreMax = nombreMax;
        this.nombreCree = 0;
        
        prochaineGeneration = heureDebut;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }

    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(nombreCree == nombreMax)
            return;
        
        if(prochaineGeneration.isBefore(heureCourante)){
            genererVehicule();
            nombreCree++;
            prochaineGeneration = heureDebut.plusSeconds((long)(getFrequence() * (double)nombreCree));
        }
    }

    @Override
    protected void reInitialiserValeursDepartSimulation() {
        nombreCree = 0;
        prochaineGeneration = heureDebut;
    }
    
    @Override
    public String obtenirDescriptionSource() {
        return this.getCircuit().getNom() + " : " + ((Integer)nombreMax).toString() + " v., à partir de " + heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }
}