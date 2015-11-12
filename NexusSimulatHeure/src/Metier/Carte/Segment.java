package Metier.Carte;

import Metier.Distribution;

public class Segment {
    private final Point pointDepart;
    private final Point pointArrivee;
    private Distribution distribution;
    
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
    
    public double obtenirMoyenneTempsTransit() {
        return distribution.obtenirMoyenne();
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Point))return false;
        Segment autreSegment = (Segment)o;
        return (pointDepart.equals(autreSegment.getPointDepart()) &&
                pointArrivee.equals(autreSegment.getPointArrivee()));
    }
}
