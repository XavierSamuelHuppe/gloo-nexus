
package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Circuit.Vehicule;
import Metier.Carte.Segment;
import Metier.Circuit.ConteneurPassagers;
import Metier.Exceptions.CreationInvalideException;
import java.time.LocalTime;
import java.util.*;


public abstract class Source {
    
    private double frequence;
    protected LocalTime heureDebut;
    private Point pointDepart;
    private Circuit circuitSource;
    private ConteneurPassagers passagers;
    private Distribution distributionAUtiliser;

    public Source(Point pointDepart, LocalTime heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
        this.passagers = passagers;
        this.circuitSource = circuit;
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
    public void setFrequence(double freq){
        frequence = freq;
    }
    public void setCapacite(ConteneurPassagers conteneurPassagers){
        passagers = conteneurPassagers;
    }
    public void setCircuit(Circuit circuit){
        circuitSource = circuit;
    }
    
    public void pigerDonneesDepart()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
    }
    
    public void rafraichirDonneesDepart(){
        frequence = 0;
        reInitialiserValeursDepartSimulation();
    }
    public boolean estSurCircuit(Circuit circuit){
        if(circuit == this.circuitSource){
            return true;
        }else{
            return false;
        }
    }
    
    public Vehicule genererVehicule()
    {
        Segment segment = circuitSource.obtenirProchainSegment(pointDepart);
        Vehicule vehicule = new Vehicule(circuitSource, segment, passagers);
        return vehicule;
    }
    
    public abstract void avancerCreation(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde);
    public abstract void reInitialiserValeursDepartSimulation();
}
