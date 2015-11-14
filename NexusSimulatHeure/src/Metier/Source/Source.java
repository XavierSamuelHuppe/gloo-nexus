
package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Circuit.Vehicule;
import Metier.Carte.Segment;
import Metier.Circuit.ConteneurPassagers;
import Metier.Exceptions.CreationInvalideException;
import java.util.*;


public abstract class Source {
    
    private double frequence;
    private double heureDebut;
    private Point pointDepart;
    private Circuit circuitSource;
    private ConteneurPassagers passagers;
    private Distribution distributionAUtiliser;

    public Source(Point pointDepart, double heureDebut, double frequence, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distributionAUtiliser = distribution;
        this.passagers = passagers;
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
    public ConteneurPassagers getcapacite(){
        return passagers;
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
    public void setCapacite(ConteneurPassagers conteneurPassagers){
        passagers = conteneurPassagers;
    }
    public void setCircuit(Circuit circuit){
        circuitSource = circuit;
    }
    
    public void pigerDonneesDepart()
    {
        frequence = distributionAUtiliser.obtenirProchaineValeurAleatoire();
    }
    
    public void retirerDonneesDepart(){
        frequence = 0;
    }
    public Vehicule genererVehicule()
    {
        Segment segment = circuitSource.obtenirProchainSegment(pointDepart);
        Vehicule vehicule = new Vehicule(circuitSource, segment, passagers);
        return vehicule;
    }
}
