package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Distribution;

public class SourceFinie extends Source {
    private int nombreMax;
    
    public SourceFinie(int nombreMax, Point pointDepart, double heureDebut, double frequence, Distribution distribution, int capaciteVehicule, Circuit circuit){
        super(pointDepart, heureDebut, frequence, distribution, capaciteVehicule, circuit);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
}