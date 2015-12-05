package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import java.io.Serializable;

public class Passager implements Serializable{
    private Trajet trajet;
    private int etapeActuelle; // Vu que le trajet est un array..
    private Point pointActuel;
    private int tempsAttente; // Pour stats plus tard
    
    
    public Passager(Trajet trajet, Point pointDepart){
        this.trajet = trajet;
        this.etapeActuelle = 0;
        this.pointActuel = pointDepart;
    }
    
    // On pourrait donc faire le changement d'étape séprarément appelé par le 
    public boolean veut(Point p){
        // Assume que si on demande au passager s'il attend cet arrêt, il est dans un véchicule
        if(trajet.obtenirEtape(etapeActuelle).getPointDescente() == p){
            etapeActuelle++; //TODO: behaviour de fin de trajet (arrivé)
            return true;
        }
        return false;
    }
    
    public boolean veut(Circuit c){
        // Assume que si on demande au passager s'il attend ce circuit, il n'est pas dans un véhicule
        return trajet.obtenirEtape(etapeActuelle).getCircuit() == c;
    }
}
