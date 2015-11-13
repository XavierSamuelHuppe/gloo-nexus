
package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.DistributionTriangulaire;
import Metier.Circuit.Vehicule;
import Metier.Carte.Segment;
import Metier.Exceptions.CreationInvalideException;
import java.util.*;


public abstract class Source {
    
    
    private double frequence;
    private double heureDebut;
    private Point pointDepart;
    private Circuit circuitSource;
    private String typeSource;
    private int capaciteVehicule;
    private DistributionTriangulaire distributionAUtiliser;

    public Source(Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution, String typeSource, int capaciteVehicule, Circuit circuit){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
        this.typeSource = typeSource;
        this.capaciteVehicule = capaciteVehicule;
        this.circuitSource = circuit;
    }
    
    public Point getPointDepart(){
        return pointDepart;
    }
    public double getheureDebut(){
        return heureDebut;
    }
    public double getFrequence(){
        return frequence;
    }
    public String getTypeSource(){
        return typeSource;
    }
    public int getcapacite(){
        return capaciteVehicule;
    }
    public Circuit getCircuit(){
        return circuitSource;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setheureDebut(double heureD){
        heureDebut = heureD;
    }
    public void setFrequence(double freq){
        frequence = freq;
    }
    public void setTypeSource(String type){
        typeSource = type;
    }
    public void setCapacite(int capacite){
        capaciteVehicule = capacite;
    }
    public void setCircuit(Circuit circuit){
        circuitSource = circuit;
    }
    
    public void pigerDonneesDepart()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
    }
    public Vehicule genererVehicule()
    {
        Segment segment = circuitSource.obtenirProchainSegment(pointDepart);
        Vehicule vehicule = new Vehicule(circuitSource, segment, capaciteVehicule);
        return vehicule;
    }
}
