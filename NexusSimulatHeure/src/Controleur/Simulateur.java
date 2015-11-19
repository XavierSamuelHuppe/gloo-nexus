package Controleur;

import Metier.Circuit.*;
import Metier.Carte.*;
import Metier.Simulation.Simulation;
import Metier.*;
import Metier.Source.*;
import java.time.LocalTime;
import java.util.*;

public class Simulateur {
    
    private Simulation simulation;
    private Carte carte;
    private ContexteEdition contexte;
    
    public Simulateur(){
        carte = new Carte();
        simulation = new Simulation(carte);
        contexte = new ContexteEdition();
    }
 
    public boolean estEnModePoint(){
        return contexte.estEnModePoint();
    }
    public boolean estEnModeSegment(){
        return contexte.estEnModeSegment();
    }
    public boolean estEnModeCircuit(){
        return contexte.estEnModeCircuit();
    }
    public boolean estEnModeSource(){
        return contexte.estEnModeSource();
    }
    public boolean estEnModePassager(){
        return contexte.estEnModePassager();
    }
    public void passerEnModePoint(){
        contexte.passerEnModePoint();
    }
    public void passerEnModeSegment(){
        contexte.passerEnModeSegment();
    }
    public void passerEnModeCircuit(){
        contexte.passerEnModeCircuit();
    }
    public void passerEnModeSource(){
        contexte.passerEnModeSource();
    }
    public void passerEnModePassager(){
        contexte.passerEnModePassager();
    }
    
    public void selectionnerPoint(Point p){
        contexte.setPointActif(p);
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
    
    public Point ajouterPoint(Position pos){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint().enPosition(pos).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(Position pos, String nom){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint().avecUnNom(nom).enPosition(pos).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(Position pos, String nom, ConteneurPassagers passagers){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPointAvecCapacite(passagers).avecUnNom(nom).enPosition(pos).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public void retirerPoint(Point point){
        carte.retirerPoint(point);
    }
    
    public void modifierPoint(Point pointCible, Position pos, String nom){
        carte.modifierPoint(pointCible, pos, nom);
    }
    
    public Segment ajouterSegment(Point depart, Point arrivee){
        Segment segment = new Segment(depart, arrivee, simulation.getParametres().getDistributionTempsTransitSegmentDefaut());
        carte.ajouterSegment(segment);
        return segment;
    }
    public Segment ajouterSegment(Point depart, Point arrivee, Distribution tempsTransit){
        Segment segment = new Segment(depart, arrivee, tempsTransit);
        carte.ajouterSegment(segment);
        return segment;
    }
    public void retirerSegment(Segment segment){
        carte.retirerSegment(segment);
    }
    public boolean verifierExistenceSegment(Point depart, Point arrivee) {
        return carte.verifierExistenceSegment(depart, arrivee);
    }
    public boolean estSegmentSortantDePoint(Point point, Segment segment){
        return this.carte.estSegmentSortantDePoint(point, segment);
    }
    
    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        SourceBuilder builder = new SourceBuilder();
        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, frequence, distribution, passagers, circuit);
        simulation.ajouterSource(nouvelleSource);
    }
    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        SourceBuilder builder = new SourceBuilder();
        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, frequence, distribution, passagers, circuit);
        simulation.ajouterSource(nouvelleSource);
    }
    public void retirerSource(Source source){
        simulation.retirerSource(source);
    }
    public void modifierSource(Source source, LocalTime heureFin, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        retirerSource(source);
        this.ajouterSource(heureFin, pointDepart, heureDebut, frequence, distribution, passagers, circuit);
    }
    public void modifierSource(Source source, int nombreMax, Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        retirerSource(source);
        this.ajouterSource(nombreMax, pointDepart, heureDebut, frequence, distribution, passagers, circuit);
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
}
