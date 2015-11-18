package Metier.Simulation;
import Metier.Carte.*;
import Metier.Circuit.Circuit;
import java.time.*;
import java.util.*;
import Metier.Exceptions.*;
import Metier.Profil.*;
import Metier.Source.*;
import Metier.Circuit.Vehicule;
import java.time.temporal.TemporalUnit;

public class Simulation extends Observable{
    private ParametreSimulation parametres;
    
    private Thread boucleThread;
    private BoucleSimulation boucle;
    private LocalTime heureCourante;
    private int JourneeCourante;
    
    private Carte carte;
    private List<Circuit> circuits;
    private List<Vehicule> vehicules;
    private List<Source> sources;
    private List<ProfilPassager> profils;
    
    public Simulation(Carte carte){
        parametres = new ParametreSimulation();
        this.carte = carte;
        
        this.circuits = new LinkedList<Circuit>();
    }
    
    public ParametreSimulation getParametres(){
        return parametres;
    }
    
    public void demarrer(){
        if(!(parametres.estEnArret()))
            throw new SimulationEnMauvaisEtatException();
        
        initialiserDepartSimulation();
        parametres.mettreEnAction();
        boucle = new BoucleSimulation(this);
        boucleThread = new Thread(boucle, "boucle de la simulation");
        boucleThread.start();
        notifyObservers();
    }
    
    public void arreter(){
        if(parametres.estEnArret())
            throw new SimulationEnMauvaisEtatException();
        
        terminerSimulation();
        parametres.mettreEnArret();
        boucleThread.interrupt();
        
        notifyObservers();
    }
    public void pauser(){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnPause();
        
        notifyObservers();
    }
    public void redemarrer(){
        if(!(parametres.estEnPause()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnAction();
        
        notifyObservers();
    }
            
    private void terminerSimulation(){
        carte.terminerSimulation();
        
        for(Source s: sources){
            s.retirerDonneesDepart();
        }
        //+ dist profils
        
        //Fermer les statistiques
        
        notifyObservers();
    }
    private void initialiserDepartSimulation(){
        JourneeCourante = 1;
        initialiserDepartNouvelleJournee();
    }
    private void initialiserDepartNouvelleJournee(){
        heureCourante = parametres.getHeureDebut();
        carte.initialiserDepartSimulation();
        for(Source s: sources){
            s.pigerDonneesDepart();
        }
        //+ dist profils
    }
    
    
    public void faireAvancerSimulation(long tempsEcouleParRatioEnNanos, double tempsEcouleParRatioEnSeconde){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        heureCourante = heureCourante.plusNanos(tempsEcouleParRatioEnNanos);
        
        faireAvancerToutLesVehicules(tempsEcouleParRatioEnSeconde);
        faireAvancerCreationVehicule(heureCourante, tempsEcouleParRatioEnSeconde);
        //faire spawner les gens
        
        if(heureCourante.isAfter(parametres.getHeureFin()) && !(JourneeCourante == parametres.getNombreJourSimulation())){
            JourneeCourante += 1;
            initialiserDepartNouvelleJournee();
        }else{
            arreter();
        }
                
        notifyObservers();
    }
    private void faireAvancerToutLesVehicules(double tempsEcouleParRatioEnSeconde){
        List<Vehicule> vehiculesAEnlever = new ArrayList();
        for(Vehicule v: vehicules){
            try{
                v.avancer(tempsEcouleParRatioEnSeconde);
            }catch(FinDeCircuitException e){
                vehiculesAEnlever.add(v);
            }
        }
        vehicules.removeAll(vehiculesAEnlever);
    }
    
    private void faireAvancerCreationVehicule(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde){
        for(Source s: sources){
            s.avancerCreation(heureCourante, tempsEcouleParRatioEnSeconde);
        }
    }
    
    
    public void ajouterCircuit(Circuit circuit){
        circuits.add(circuit);
    }
    
    public void retirerCircuit(Circuit circuit){
        circuits.remove(circuit);
    }
    
    public void ajouterSource(Source source){
        sources.add(source);
    }
    
    public void retirerSource(Source source){
        sources.remove(source);
    }
}
