/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

/**
 *
 * @author Pierre
 */
public abstract class Source {
    
    
    private double frequence;
    private double heureDebut;
    private Point pointDepart;
    private Circuit circuit;
    
    
    //private int capacite;
    
    
    private DistributionTriangulaire distributionAUtiliser;
    //Vrm pas sure pour les distribution, on verra
    public Source(Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
    }
    
    public Point getPointDepart(){
        return pointDepart;
    }
    public double getheureDebut(){
        return heureDebut;
    }
    public double getFrequence(){
        return frequence;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setheureDebut(double heureD){
        heureDebut = heureD;
    }
    public void setFrequence(double freq){
        frequence = freq;
    }
    
    public void pigerDonneesDepart()
    {
        //
    }/*  
    public Vehicule genererVehicule()
    {
        //pas mal certain j'avais fait des modifs ici il y a 2 semaines, ils se sont perdu dans le void?
        Vehicule unChar = new Vehicule();
        return unChar;
    }*/
}
