/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier.Profil;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

/**
 *
 * @author Utilisateur
 */
public class ProfilPassagerFini extends ProfilPassager{
    
    private int nombreMax;
    
    public ProfilPassagerFini(int nombreMax, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
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
