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
public class ProfilPassagerHeureFin extends ProfilPassager {
    
    private int heureFin;
    
    public ProfilPassagerHeureFin(int heureFin, Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
        //super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public int getheureFin(){
        return heureFin;
    }
    public void setheureFin(int Fin){
        heureFin = Fin;
    }
}
