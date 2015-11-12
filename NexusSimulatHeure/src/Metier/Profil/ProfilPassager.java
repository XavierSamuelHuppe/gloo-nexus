/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier.Profil;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;
import Metier.Simulation.Statistiques;

/**
 *
 * @author Utilisateur
 */
public class ProfilPassager {
    private Point pointDepart;
    private int heureDepart;
    private int frequence;
    public DistributionTriangulaire distributionAUtiliser;
    public Statistiques statistiques;
    
    public ProfilPassager(Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
        this.frequence = frequence;
        this.heureDepart = heureDepart;
        this.pointDepart = point;
        this.distributionAUtiliser = distribution;
    }
    public Point getPointDepart(){
        return pointDepart;
    }
    public int getHeureDepart(){
        return heureDepart;
    }
    public int getFrequence(){
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
        //jajajajajaja
    }
    
    /*  décommenter quand on va travailler la dessus,  
    public Passager genererPassager()
    {
        Passager unPieton = new Passager();
        return unPieton;
    }
    */
}
