
package Metier.Profil;

import Metier.Carte.Point;
import Metier.Carte.Segment;
import Metier.Circuit.Vehicule;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import Metier.Simulation.Statistiques;
import java.io.Serializable;
import java.time.LocalTime;

public abstract class ProfilPassager implements Serializable{
    private Trajet trajet;
    private Point pointDepart;
    protected LocalTime heureDebut;
    private double frequence;
    public Distribution distributionAUtiliser;
    public Statistiques statistiques;
    
    public ProfilPassager(Point point, LocalTime heureDepart, Distribution distribution, Trajet trajet, Simulation simulation){
        this.heureDebut = heureDepart;
        this.pointDepart = point;
        this.trajet = trajet;
        this.distributionAUtiliser = distribution;
    }
    public Point getPointDepart(){
        return pointDepart;
    }
    public boolean estSurPoint(Point point){
        return point.equals(pointDepart);
    }
    public Distribution getDistribution(){
        return distributionAUtiliser;
    }
    public LocalTime getHeureDepart(){
        return heureDebut;
    }
    public double getFrequence(){
        return frequence;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setHeureDepart(LocalTime heureD){
        heureDebut = heureD;
    }
    public void setFrequence(int freq){
        frequence = freq;
    }
    public void setDistribution(Distribution d) {
        this.distributionAUtiliser = d;
    }
    
    public Trajet getTrajet(){
        return this.trajet;
    }
        
    public void pigerDonneesDepartNouvelleJournee()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
        reInitialiserValeursDepartSimulation();
    }
    
    public Passager genererPassager(){
        Passager nouveauPassager = new Passager(null, pointDepart);
        pointDepart.faireArriverNouveauPassager(nouveauPassager);
        return nouveauPassager;
    }
    
    public void retirerTempsGeneration(){
        frequence = 0;
    }
    
    public abstract void avancerGeneration(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde);
    protected abstract void reInitialiserValeursDepartSimulation();
    
    public abstract String obtenirDescriptionProfil();
    @Override
    public String toString()
    {
        return this.obtenirDescriptionProfil();
    }
}
