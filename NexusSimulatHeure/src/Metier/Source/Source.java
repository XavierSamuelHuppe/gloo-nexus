
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
        //
    }
    public Vehicule genererVehicule()
    {
        Segment segment = null;
        List<Segment> trajet = circuitSource.getTraget();
        for(int i = trajet.size(); i >= 0; i--){
            if (trajet.get(i).getPointDepart() == pointDepart){
                segment = trajet.get(i);
                break;
            }
        }
        if (segment == null){
            throw new CreationInvalideException("Le segment doit exister pour créer un vehicule dessus");
        }
        Vehicule vehicule = new Vehicule(circuitSource, segment);
        return vehicule;
    }
}
