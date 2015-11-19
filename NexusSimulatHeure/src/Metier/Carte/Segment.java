package Metier.Carte;

import Metier.Distribution;
import java.util.Observable;

public class Segment extends Observable{
    private final Point pointDepart;
    private final Point pointArrivee;
    private Distribution distribution;
    
    private double tempsTransit;
    
    public Segment(Point pointDepart, Point pointArrivee, Distribution distribution) {
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
        this.distribution = distribution;
    }

    public Point getPointDepart() {
        return pointDepart;
    }

    public Point getPointArrivee() {
        return pointArrivee;
    }
    
    public double getTempsTransit() {
        return tempsTransit;
    }
    
    public double obtenirMoyenneTempsTransit() {
        return distribution.obtenirMoyenne();
    }
    
    public void setDistribution(Distribution distribution){
        this.distribution = distribution;
    }
    
    public void recevoirNouveauTempsTransit() {
        tempsTransit = distribution.obtenirProchaineValeurAleatoire();
        notifyObservers();
    }
    
    public void retirerTempsTransit() {
        tempsTransit = 0;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Segment))return false;
        Segment autreSegment = (Segment)o;
        return (pointDepart.equals(autreSegment.getPointDepart()) &&
                pointArrivee.equals(autreSegment.getPointArrivee()));
    }
}
