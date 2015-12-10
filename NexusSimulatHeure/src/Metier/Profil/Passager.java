package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import java.io.Serializable;

public class Passager implements Serializable{
    private ProfilPassager profilPassager;
    private Trajet trajet;
    private int etapeActuelle; // Vu que le trajet est un array..
    private Point pointActuel;
    private double tempsAttente; // Pour stats plus tard
    
    
    public Passager(ProfilPassager profilPassagerGenerateur, Trajet trajet, Point pointDepart){
        this.profilPassager = profilPassagerGenerateur;
        this.trajet = trajet;
        this.etapeActuelle = 0;
        this.pointActuel = pointDepart;
    }
    
    // On pourrait donc faire le changement d'étape séprarément appelé par le 
    public boolean veut(Point p){
        // Assume que si on demande au passager s'il attend cet arrêt, il est dans un véchicule
        if(trajet.obtenirEtape(etapeActuelle).getPointDescente().equals(p)){
            etapeActuelle++; //TODO: behaviour de fin de trajet (arrivé)
            return true;
        }
        return false;
    }
    
    public boolean veut(Circuit c){
        // Assume que si on demande au passager s'il attend ce circuit, il n'est pas dans un véhicule
        return trajet.obtenirEtape(etapeActuelle).getCircuit().equals(c);
    }
    
    public boolean estArriveADestination(Point p)
    {
        return trajet.obtenirPointArrivee().equals(p);
    }

    public void incrementerTempsAttente(double tempsEcouleParRatioEnSeconde)
    {
        this.tempsAttente += tempsEcouleParRatioEnSeconde;
    }
   
    public void comptabiliserTempsAttenteDansProfilPassager()
    {
        this.profilPassager.comptabiliserTempsAttente(tempsAttente);
    }
    
    public Trajet getTrajet()
    {
        return this.trajet;
    }
    
    @Override
    public String toString()
    {
        return this.trajet.toString();
    }
}
