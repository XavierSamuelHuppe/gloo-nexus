/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author Pierre
 */
public class Source {
    
    
    private int frequence;
    private int heureDebut;
    private Point pointDepart;
    private Circuit circuit;
    
    private DistributionTriangulaire distributionAUtiliser;
    //Vrm pas sure pour les distribution, on verra
    public Source(Point pointDepart, int heureDebut, int frequence, DistributionTriangulaire distribution){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
    }
    
    public Point getPointDepart(){
        return pointDepart;
    }
    public int getheureDebut(){
        return heureDebut;
    }
    public int getFrequence(){
        return frequence;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setheureDebut(int heureD){
        heureDebut = heureD;
    }
    public void setFrequence(int freq){
        frequence = freq;
    }
    
    public void pigerDonneesDepart()
    {
        //jajajajajaja
    }/*  décommenter quand on va travailler la dessus, je sais pas trop quel genre de Mouseevent il veut. 
    public Vehicule genererVehicule()
    {
        Vehicule unChar = new Vehicule();
        return unChar;
    }*/
}
