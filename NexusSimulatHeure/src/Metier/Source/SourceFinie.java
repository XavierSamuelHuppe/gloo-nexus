package Metier.Source;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.DistributionTriangulaire;

public class SourceFinie extends Source {
    private double nombreMax;
    
    public SourceFinie(double nombreMax, Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution, String typeSource, int capaciteVehicule, Circuit circuit){
        super(pointDepart, heureDebut, frequence, distribution, typeSource, capaciteVehicule, circuit);
        this.nombreMax = nombreMax;
    }
    
    public double getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
}