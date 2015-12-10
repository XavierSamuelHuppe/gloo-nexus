
package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;
import Metier.Circuit.ConteneurPassagers;
import Metier.Circuit.Vehicule;
import Metier.Simulation.ParametreSimulation;
import Metier.Simulation.Simulation;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SourceHeureFin extends Source {
    private LocalTime heureFin;
    
    private LocalTime prochaineGeneration;
    private int nombreCree;
    
    public SourceHeureFin(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation sim){
        super(pointDepart, heureDebut, distribution, passagers, circuit, sim);
        this.heureFin = heureFin;
        nombreCree = 0;
        prochaineGeneration = heureDebut;
    }
    
    public LocalTime getheureFin(){
        return heureFin;
    }
    public void setheureFin(LocalTime Fin){
        heureFin = Fin;
    }

    private boolean generationTerminee = false;
    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(generationTerminee)
            return;
        
        if(doitSpawnerVehicule(heureCourante)){
            genererVehicule();
            nombreCree++;
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
            if(this.simulation.heureEstPassee(prochaineGeneration, heureFin))
            {
                generationTerminee = true;
            }
            System.out.println(prochaineGeneration);
        }
    }
    
    private boolean doitSpawnerVehicule(LocalTime heureCourante){
        return this.simulation.heureEstPassee(heureCourante, prochaineGeneration);
    }
    
    @Override
    public void reInitialiserValeursDepartSimulation() {
        prochaineGeneration = heureDebut;
        nombreCree = 0;
        generationTerminee = false;
    }
    
    @Override
    public String obtenirDescriptionSource() {
        return this.getCircuit().getNom() + " : " + heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE) + " à " + heureFin.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }
    
        @Override
    public Map<Vehicule, LocalTime> genererTousVehiculesAvecMoment()
    {
        Map<Vehicule, LocalTime> vehicules = new HashMap<Vehicule, LocalTime>();
        prochaineGeneration = this.heureDebut;
        nombreCree = 0;
        while(!simulation.heureEstPassee(prochaineGeneration, heureFin))
        {
            vehicules.put(genererVehicule(false), prochaineGeneration);
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
        }

        return vehicules;
    }
}
