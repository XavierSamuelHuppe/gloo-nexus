package Metier.Carte;

import Metier.Distribution;
import java.io.Serializable;
import java.util.Observable;

public class Segment extends Observable implements Serializable{
    private final Point pointDepart;
    private final Point pointArrivee;
    private Distribution distribution;
    private double[] tempsTransit;
    private int journee;
    private boolean existe;
    
    public Segment(Point pointDepart, Point pointArrivee, Distribution distribution) {
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
        this.distribution = distribution;
        existe = true;
    }

    public void tuer(){
        existe = false;
        this.setChanged();
        notifyObservers();
    }
    public boolean mort(){
        return !existe;
    }
    
    public Point getPointDepart() {
        return pointDepart;
    }

    public Point getPointArrivee() {
        return pointArrivee;
    }
    
    public double getTempsTransit() {
        if(tempsTransit == null || tempsTransit.length == 0)
            return 0.0;
        return tempsTransit[journee];
    }
    
    public void setJournee(int journee)
    {
        this.journee = journee;
    }
    
    public int getJournee()
    {
        return this.journee;
    }
    
    public double obtenirMoyenneTempsTransit() {
        return distribution.obtenirMoyenne();
    }
    
    public void setDistribution(Distribution distribution){
        this.distribution = distribution;
    }
    
    public Distribution getDistribution(){
        return this.distribution;
    }
    
    public void recevoirNouveauTempsTransit(int nbJournees) {
        tempsTransit = new double[nbJournees];
        for(int i = 0; i < nbJournees; i++)
        {
            tempsTransit[i] = distribution.obtenirProchaineValeurAleatoire();
        }
        notifyObservers();
    }
    
    public void retirerTempsTransit() {
        tempsTransit = null;
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
