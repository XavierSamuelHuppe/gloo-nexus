
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

    
    @Override
    public void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(!doitSpawnerVehicule(heureCourante))
            return;
        
        if(prochaineGeneration.isBefore(heureCourante)){
            genererVehicule();
            nombreCree++;
            prochaineGeneration = prochaineGeneration.plusSeconds((long)/*(*/getFrequence()/* * (double)nombreCree)*/);
        }
    }
    
    private boolean doitSpawnerVehicule(LocalTime heureCourante){
        LocalTime heureDebutNouvelleJournee = ParametreSimulation.HEURE_DEBUT_NOUVELLE_JOURNEE;
        LocalTime minuit = LocalTime.MIDNIGHT;
        LocalTime justeAvantMinuit = LocalTime.MAX;
        if(heureFin.isBefore(justeAvantMinuit) && heureFin.isAfter(heureDebutNouvelleJournee)){
            return heureCourante.isBefore(heureFin);
        }else if(heureFin.isAfter(minuit) && heureFin.isBefore(heureDebutNouvelleJournee)){
            if(heureCourante.isBefore(justeAvantMinuit) && heureCourante.isAfter(heureDebutNouvelleJournee))
                return true;
            else
                return heureCourante.isBefore(heureFin);
        }
        throw new DateTimeException("Les paramètres de la simulation ne sont pas bien réglés.");
    }
    
    @Override
    public void reInitialiserValeursDepartSimulation() {
        prochaineGeneration = heureDebut;
        nombreCree = 0;
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
        while(doitSpawnerVehicule(prochaineGeneration))
        {
            vehicules.put(genererVehicule(), prochaineGeneration);
            nombreCree += 1;
            prochaineGeneration = heureDebut.plusSeconds((long)(getFrequence() * (double)nombreCree));
        }
        return vehicules;
    }
}
