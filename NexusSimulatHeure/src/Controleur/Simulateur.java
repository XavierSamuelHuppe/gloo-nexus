package Controleur;

import Metier.Circuit.ConteneurPassagersIllimite;
import Metier.Circuit.CircuitBuilder;
import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Carte.Segment;
import Metier.Simulation.Simulation;
import Metier.*;
import Metier.Source.SourceBuilder;
import Metier.Source.Source;
import Metier.Carte.Carte;
import java.time.LocalTime;
import java.util.*;

public class Simulateur {
    
    private Simulation simulation;
    
    public Simulateur(){
    }
    
    public Simulateur(Simulation simulation){
        this.simulation = simulation;
    }
    
    public Point ajouterPoint(Metier.Carte.Position pos, String nom){
        Point metierPoint = new Metier.Carte.Point(nom, new ConteneurPassagersIllimite());
        metierPoint.setPosition(pos);
        metierPoint.setNom(nom);
        return metierPoint;
    }
    
    public void modifierPoint(Metier.Carte.Point pointCible, Metier.Carte.Position pos, String nom){
        pointCible.setPosition(pos);
        pointCible.setNom(nom);
    }
    
    public void ajouterSource(int nombreMax, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        SourceBuilder builder = new SourceBuilder();
        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        simulation.ajouterSource(nouvelleSource);
    }
    public void ajouterSource(double heureFin, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        SourceBuilder builder = new SourceBuilder();
        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        simulation.ajouterSource(nouvelleSource);
    }
    public void retirerSource(Source source){
        simulation.retirerSource(source);
    }
    
    public void modifierSource(Source source, double heureFin, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        simulation.retirerSource(source);
        this.ajouterSource(heureFin, pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
    }
    
    public void modifierSource(Source source, int nombreMax, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        simulation.retirerSource(source);
        this.ajouterSource(nombreMax, pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
    }
    
    public void arreter(){
        simulation.arreter();
    }
    
    public void demarerRedemarer(){
        if(simulation.getParametres().estEnPause())
            simulation.redemarrer();
        else
            simulation.demarrer();
    }
    
    public void pauser(){
        simulation.pauser();
    }
    
    public void modfierVitesse(int pourcentage){
        simulation.getParametres().setVitesse(pourcentage);
    }
    
    public void modfierFramerate(int pourcentage){
        simulation.getParametres().setFramerate(pourcentage);
    }
    
    public void modifierDistributionTempsTransitSegment(Distribution dist){
        simulation.getParametres().setDistributionTempsTransitSegment(dist);
    }
    
    public void modifierDistributionTempsGenerationVehicule(Distribution dist){
        simulation.getParametres().setDistributionTempsGenerationVehicule(dist);
    }
    
    public void modifierDistributionTempsGenerationPassager(Distribution dist){
        simulation.getParametres().setDistributionTempsGenerationPassager(dist);
    }
    
    public void modifierNombreJourSimulation(int nombreJours){
        simulation.getParametres().setNombreJourSimulation(nombreJours);
    }
    
    public void modifierHeureDebut(LocalTime heure){
        simulation.getParametres().setHeureDebut(heure);
    }
    
    public void modifierHeureFin(LocalTime heure){
        simulation.getParametres().setHeureFin(heure);
    }
    
    public void ajouterCircuit(String nom, List<Segment> segments){
        CircuitBuilder builder = new CircuitBuilder();
        Circuit nouveauCircuit = builder.ConstruireCircuit(nom, segments);
        simulation.ajouterCircuit(nouveauCircuit);
    }
    
    public void ajouterSegment(Point depart, Point arrivee){
    }
    
    public void ajouterSegment(Point depart, Point arrivee, Distribution distribution){
        
    }
}
