
package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Circuit.Vehicule;
import Metier.Carte.Segment;
import Metier.Circuit.ConteneurPassagers;
import Metier.Exceptions.CreationInvalideException;
import Metier.Simulation.Simulation;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;


public abstract class Source implements Serializable{
    
    private double frequence;
    protected LocalTime heureDebut;
    private Point pointDepart;
    private Circuit circuitSource;
    private ConteneurPassagers passagers;
    private Distribution distributionAUtiliser;
    private Simulation simulation;

    public Source(Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit, Simulation simulation){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
        this.passagers = passagers;
        this.circuitSource = circuit;
        this.simulation = simulation;
    }
    
    public Distribution getDistribution()
    {
        return distributionAUtiliser;
    }
    
    public Point getPointDepart(){
        return pointDepart;
    }
    public LocalTime getheureDebut(){
        return heureDebut;
    }
    public double getFrequence(){
        return frequence;
    }
    public ConteneurPassagers getcapacite(){
        return passagers;
    }
    public Circuit getCircuit(){
        return circuitSource;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setheureDebut(LocalTime heureD){
        heureDebut = heureD;
    }
    
    public void setCapacite(ConteneurPassagers conteneurPassagers){
        passagers = conteneurPassagers;
    }
    public void setCircuit(Circuit circuit){
        circuitSource = circuit;
    }
    public void setDistribution(Distribution d) {
        this.distributionAUtiliser = d;
    }
    
    public void retirerTempsGeneration(){
        frequence = 0;
    }
    
    public void pigerDonneesDepartNouvelleJournee()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
        reInitialiserValeursDepartSimulation();
    }
    
    public boolean estSurCircuit(Circuit circuit){
        if(circuit.equals(this.circuitSource)){
            return true;
        }else{
            return false;
        }
    }
    
    public Vehicule genererVehicule()
    {
        Segment segment = circuitSource.obtenirProchainSegment(pointDepart);
        Vehicule vehicule = new Vehicule(circuitSource, segment, passagers.obtenirCopieVide());
        vehicule.embarquerAuPointDepart();
        this.simulation.ajouterVehicule(vehicule);
        return vehicule;
    }
    
    public abstract void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde);
    public abstract void reInitialiserValeursDepartSimulation();
    
    public abstract String obtenirDescriptionSource();
    
    @Override
    public String toString()
    {
        return this.obtenirDescriptionSource();
    }
}
