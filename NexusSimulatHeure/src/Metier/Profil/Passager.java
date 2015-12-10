package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Passager implements Serializable{
    private ProfilPassager profilPassager;
    private Trajet trajet;
    private int etapeActuelle; // Vu que le trajet est un array..
    private Point pointActuel;
    private double tempsVie = 0.0; // Pour stats plus tard
    private LocalTime heureCreation;
    
    public Passager(ProfilPassager profilPassagerGenerateur, Trajet trajet, Point pointDepart, LocalTime momentCreation){
        this.profilPassager = profilPassagerGenerateur;
        this.trajet = trajet;
        this.etapeActuelle = 0;
        this.pointActuel = pointDepart;
        this.heureCreation = momentCreation;
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

    public void comptabiliserPassagerDansStatistiquesProfilPassager(LocalTime heureDestination)
    {
        this.profilPassager.comptabiliserPassager(ChronoUnit.SECONDS.between(heureCreation, heureDestination));
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
