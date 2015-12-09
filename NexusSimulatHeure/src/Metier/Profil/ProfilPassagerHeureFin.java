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

    @Override
    public void avancerGeneration(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(!doitSpawnerPassager(heureCourante))
            return;
        
        if(prochaineGeneration.isBefore(heureCourante)){
            genererPassager();
            prochaineGeneration = prochaineGeneration.plusSeconds((long)/*(*/getFrequence()/* * (double)nombreCree)*/);
        }
    }
    
    private boolean doitSpawnerPassager(LocalTime heureCourante){
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
    protected void reInitialiserValeursDepartSimulation() {
        prochaineGeneration = heureDebut;
        statistiques = new Statistiques();
    }
    
    
    @Override
    public Map<Passager, LocalTime> genererTousPassagersAvecMoment()
    {
        Map<Passager, LocalTime> vehicules = new HashMap<Passager, LocalTime>();
        prochaineGeneration = this.heureDebut;
        int nombreCree = 0;
        while(doitSpawnerPassager(prochaineGeneration))
        {
            vehicules.put(genererPassager(), prochaineGeneration);
            nombreCree += 1;
            prochaineGeneration = heureDebut.plusSeconds((long)(getFrequence() * (double)nombreCree));
        }
        return vehicules;
    }
}
