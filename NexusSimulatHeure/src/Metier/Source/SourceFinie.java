package Metier.Source;

import Metier.Carte.Point;
import Metier.DistributionTriangulaire;

public class SourceFinie extends Source {
    private int nombreMax;
    
    public SourceFinie(int nombreMax, Point point, int heureDepart, int frequence, DistributionTriangulaire distribution){
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