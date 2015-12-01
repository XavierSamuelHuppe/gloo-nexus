
package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import Metier.Simulation.Statistiques;
import java.time.LocalTime;

public abstract class ProfilPassager {
    private Point pointDepart;
    protected LocalTime heureDepart;
    private double frequence;
    public Distribution distributionAUtiliser;
    public Statistiques statistiques;
    
    public ProfilPassager(Point point, LocalTime heureDepart, Distribution distribution, Simulation simulation){
        this.heureDepart = heureDepart;
        this.pointDepart = point;
        this.distributionAUtiliser = distribution;
        
    }
    public Point getPointDepart(){
        return pointDepart;
    }
    public Distribution getDistribution()
    {
        return distributionAUtiliser;
    }
    public LocalTime getHeureDepart(){
        return heureDepart;
    }
    public double getFrequence(){
        return frequence;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setHeureDepart(LocalTime heureD){
        heureDepart = heureD;
    }
    public void setFrequence(int freq){
        frequence = freq;
    }
    public void setDistribution(Distribution d) {
        this.distributionAUtiliser = d;
    }
    
    
    
    public void pigerDonneesDepart()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
    }
    /*
    public void rafraichirDonneesDepart(){
        frequence = 0;
        reInitialiserValeursDepartSimulation();
    }*/
    
    
    
    /*
    public Passager genererPassager()
    {
        Passager unPieton = new Passager();
        
        return unPieton;
    }*/
    
    public abstract String obtenirDescriptionProfil();
    @Override
    public String toString()
    {
        return this.obtenirDescriptionProfil();
    }
}
