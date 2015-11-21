package Metier.Circuit;

import Metier.Carte.*;
import Metier.Exceptions.FinDeCircuitException;
import Metier.Profil.Passager;
import java.util.*;

public class Vehicule extends Observable{
   
    private ConteneurPassagers passagers;
    private Circuit circuitActuel;
    private Segment segmentActuel;
    private double secondesSurSegment;
    
    public Vehicule(Circuit circuit, Segment segment, ConteneurPassagers passagers){
        this.circuitActuel = circuit;
        this.segmentActuel = segment;
        this.passagers = passagers;
    }
    public Circuit getCircuit(){
        return circuitActuel;
    }
    
    public double obtenirProgresSurSegmentEnPourcentage(){
        double progres = secondesSurSegment / segmentActuel.getTempsTransit();
        return progres;
    }
    public Segment getSegment(){
        return segmentActuel;
    }
    
    public void avancer(double tempsEcouleParRatioEnSeconde){
        System.out.println("Vehicule.avancer");
        double tempsTotal = secondesSurSegment+tempsEcouleParRatioEnSeconde;
        if(tempsTotal > segmentActuel.getTempsTransit()){
            //faire embarquer le monde
            Point pointTraverse = segmentActuel.getPointArrivee();
            List<Passager> passagersVoulantDescendre = passagers.debarquer(pointTraverse);
            pointTraverse.faireDescendreAuPoint(passagersVoulantDescendre);
            
            try{
                Segment prochainSegment = circuitActuel.obtenirProchainSegment(segmentActuel);

                List<Passager> passagersVoulantMonter = pointTraverse.faireMonterEnVehicule(circuitActuel);
                passagers.octroyer(passagersVoulantMonter);
                
                double tempsAAjouter = tempsTotal - segmentActuel.getTempsTransit();
                secondesSurSegment = tempsAAjouter;
                segmentActuel = prochainSegment;
            }catch(FinDeCircuitException e){
                throw e;
            }
        }else{
            secondesSurSegment = tempsTotal;
        }
        notifyObservers();
    }
    
    public Position obtenirPosition()
    {
        return calculerPosition(this.segmentActuel.getPointDepart().getPosition(), this.segmentActuel.getPointArrivee().getPosition());
    }

    private Position calculerPosition(Position posDepart, Position posArrivee)
    {
        double dx = calculerDeltaX(posDepart, posArrivee);
        double dy = calculerDeltaY(posDepart, posArrivee);
        
        dx = dx * obtenirProgresSurSegmentEnPourcentage() + posDepart.getX();
        dy = dy * obtenirProgresSurSegmentEnPourcentage() + posDepart.getY();
        
        return new Position(dx, dy);
    }
    
    private double calculerDeltaX(Position posDepart, Position posArrivee)
    {
        return posArrivee.getX() - posDepart.getX();
    }
    
    private double calculerDeltaY(Position posDepart, Position posArrivee)
    {
        return posArrivee.getY() - posDepart.getY();
    }
}
