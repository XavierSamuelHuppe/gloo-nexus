package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Point;
import Metier.Circuit.Circuit;

public class Passager {
    private ElementTrajet[] trajet;
    private int etapeActuelle; // Vu que le trajet est un array..
    private Point pointActuel;
    private int tempsAttente; // Pour stats plus tard
    
    
    public Passager(ElementTrajet[] trajet, Point pointDepart){
        this.trajet = trajet;
        this.etapeActuelle = 0;
        this.pointActuel = pointDepart;
    }
    
    // On pourrait donc faire le changement d'étape séprarément appelé par le 
    public boolean veut(Point p){
        // Assume que si on demande au passager s'il attend cet arrêt, il est dans un véchicule
        if(trajet[etapeActuelle].getPoint() == p){
            etapeActuelle++; //TODO: behaviour de fin de trajet (arrivé)
            return true;
        }
        return false;
    }
    
    public boolean veut(Circuit c){
        // Assume que si on demande au passager s'il attend ce circuit, il n'est pas dans un véhicule
        return trajet[etapeActuelle].getCircuit() == c;
    }
}
