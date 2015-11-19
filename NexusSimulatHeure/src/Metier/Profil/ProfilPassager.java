
package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Statistiques;

public abstract class ProfilPassager {
    private Point pointDepart;
    private double heureDepart;
    private double frequence;
    public Distribution distributionAUtiliser;
    public Statistiques statistiques;
    
    public ProfilPassager(Point point, double heureDepart, double frequence, Distribution distribution){
        this.frequence = frequence;
        this.heureDepart = heureDepart;
        this.pointDepart = point;
        this.distributionAUtiliser = distribution;
    }
    public Point getPointDepart(){
        return pointDepart;
    }
    public double getHeureDepart(){
        return heureDepart;
    }
    public double getFrequence(){
        return frequence;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setHeureDepart(int heureD){
        heureDepart = heureD;
    }
    public void setFrequence(int freq){
        frequence = freq;
    }
    
    
    public void pigerDonneesDepart()
    {
        //
    }
    
    /*   
    public Passager genererPassager()
    {
        Passager unPieton = new Passager();
        return unPieton;
    }
    */
}
