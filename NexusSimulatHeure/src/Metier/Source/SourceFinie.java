package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;
import Metier.Circuit.ConteneurPassagers;

public class SourceFinie extends Source {
    private int nombreMax;
    
    public SourceFinie(int nombreMax, Point pointDepart, double heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        super(pointDepart, heureDebut, frequence, distribution, passagers, circuit);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
}