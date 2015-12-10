package Metier.Profil;

import Metier.Carte.Point;
import Metier.Circuit.Vehicule;
import Metier.Distribution;
import Metier.Simulation.ParametreSimulation;
import Metier.Simulation.Simulation;
import Metier.Simulation.Statistiques;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ProfilPassagerHeureFin extends ProfilPassager {
    
    private LocalTime heureFin;
    private LocalTime prochaineGeneration;
    
    public ProfilPassagerHeureFin(LocalTime heureFin, Point pointDepart, LocalTime heureDepart, Distribution distribution, Trajet trajet, Simulation simulation){
        super(pointDepart, heureDepart, distribution, trajet, simulation);
        this.heureFin = heureFin;
    }
    
    public LocalTime getHeureFin(){
        return heureFin;
    }
    public void setHeureFin(LocalTime Fin){
        heureFin = Fin;
    }
    @Override
    public String obtenirDescriptionProfil() {
        return heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE) + " à " + heureFin.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }

    private boolean generationTerminee = false;
    
    @Override
    public void avancerGeneration(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(generationTerminee)
            return;
        
        if(doitSpawnerPassager(heureCourante)){
            genererPassager(prochaineGeneration);
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
            if(this.simulation.heureEstPassee(prochaineGeneration, heureFin))
            {
                generationTerminee = true;
            }
            System.out.println(prochaineGeneration);
        }
    }
    
    private boolean doitSpawnerPassager(LocalTime heureCourante){
        return this.simulation.heureEstPassee(heureCourante, prochaineGeneration);
    }

    @Override
    protected void reInitialiserValeursDepartSimulation() {
        prochaineGeneration = heureDebut;
        statistiques = new Statistiques();
        generationTerminee = false;
    }
    
    
    @Override
    public Map<Passager, LocalTime> genererTousPassagersAvecMoment()
    {
        Map<Passager, LocalTime> passagers = new HashMap<Passager, LocalTime>();
        prochaineGeneration = this.heureDebut;
        int nombreCree = 0;
        while(!simulation.heureEstPassee(prochaineGeneration, heureFin))
        {
            passagers.put(genererPassager(prochaineGeneration), prochaineGeneration);
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
        }
        return passagers;
    }
        
}
