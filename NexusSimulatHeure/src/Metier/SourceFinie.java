/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author Utilisateur
 */
public class SourceFinie extends Source {
    private int nombreMax;
    
    public SourceFinie(int nombreMax, Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
        super(point, heureDepart, frequence, distribution);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
}
