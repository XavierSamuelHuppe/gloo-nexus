package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import Metier.Simulation.Statistiques;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ProfilPassagerFini extends ProfilPassager{
    
    private int nombreMax;
    private int nombreCree;
    
    private LocalTime prochaineGeneration;
    
    public ProfilPassagerFini(int nombreMax, Point pointDepart, LocalTime heureDepart, Distribution distribution, Trajet trajet, Simulation simulation){
        super(pointDepart, heureDepart, distribution, trajet, simulation);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
    
    @Override
    public void avancerGeneration(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(nombreCree == nombreMax)
            return;
        
        if(this.simulation.heureEstPassee(heureCourante, prochaineGeneration)){
            genererPassager(prochaineGeneration);
            nombreCree++;
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
        }
    }
    
    @Override
    public void reInitialiserValeursDepartSimulation() {
        nombreCree = 0;
        prochaineGeneration = heureDebut;
    }
    
    @Override
    public String obtenirDescriptionProfil() {
        return ((Integer)nombreMax).toString() + " p., à partir de " + heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE) + " : " + trajet.toString();
    }
    
    @Override
    public Map<Passager, LocalTime> genererTousPassagersAvecMoment()
    {
        Map<Passager, LocalTime> vehicules = new HashMap<>();
        prochaineGeneration = this.heureDebut;
        nombreCree = 0;
        while(nombreCree < nombreMax  && !this.simulation.heureEstPassee(prochaineGeneration, this.simulation.getParametres().getHeureFin()))
        {
            vehicules.put(genererPassager(prochaineGeneration), prochaineGeneration);
            nombreCree += 1;
            prochaineGeneration = prochaineGeneration.plusSeconds((long)getFrequence());
        }
        return vehicules;
    }
}
