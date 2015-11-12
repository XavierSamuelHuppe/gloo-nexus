package Controleur;

import Metier.Circuit.ConteneurPassagersIllimite;
import Metier.Circuit.CircuitBuilder;
import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Carte.Segment;
import Metier.Simulation.Simulation;
import Metier.*;
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
    
    public void ajouterSourceHeureFin(double heureFin ,Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution){
        simulation.ajouterSourceHeureFin(heureFin , pointDepart, heureDebut, frequence, distribution);
    }
    
    public void ajouterSourceFinie(int nombreMax ,Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution){
        simulation.ajouterSourceFinie(nombreMax , pointDepart, heureDebut, frequence, distribution);
    }
    
    public void ajouterProfilPassagerFini(int nombreMax, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        simulation.ajouterProfilPassagerFini(nombreMax, point, heureDepart, frequence, distribution);
    }
    
    public void ajouterProfilPassagerHeureFin(double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        simulation.ajouterProfilPassagerHeureFin(heureFin, point, heureDepart, frequence, distribution);
    }
    
    public void modifierSourceHeureFin(Metier.Source.SourceHeureFin sourceHeureFin, double heureFin ,Point pointDepart, double heureDebut, double frequence){
        simulation.modifierSourceHeureFin(sourceHeureFin, heureFin , pointDepart, heureDebut, frequence);
    }
    
    public void modifierSourceFinie(Metier.Source.SourceFinie sourceFinie, int nombreMax ,Point pointDepart, double heureDebut, double frequence){
        simulation.modifierSourceFinie(sourceFinie, nombreMax ,pointDepart, heureDebut, frequence);
    }
    
    public void modifierProfilPassagerFini(Metier.Profil.ProfilPassagerFini profilPassagerFini, int nombreMax, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        simulation.modifierProfilPassagerFini(profilPassagerFini, nombreMax, point, heureDepart, frequence, distribution);
        
    }
    
    public void modifierProfilPassagerHeureFin(Metier.Profil.ProfilPassagerHeureFin ProfilPassagerHeureFin, double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        simulation.modifierProfilPassagerHeureFin(ProfilPassagerHeureFin,heureFin,point,heureDepart,frequence,distribution);
    }
    
    public void retirerSourceHeureFin(Metier.Source.SourceHeureFin sourceHeureFin){
        simulation.retirerSourceHeureFin(sourceHeureFin);
    }
    
    public void retirerSourceFinie(Metier.Source.SourceFinie sourceFinie){
        simulation.retirerSourceFinie(sourceFinie);
    }
    
    public void retirerProfilPassagerFini(Metier.Profil.ProfilPassagerFini profilPassagerFini){
        simulation.retirerProfilPassagerFini(profilPassagerFini);
    }
    
    public void retirerProfilPassagerHeureFin(Metier.Profil.ProfilPassagerHeureFin profilPassagerHeureFin){
        simulation.retirerProfilPassagerHeureFin(profilPassagerHeureFin);
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
