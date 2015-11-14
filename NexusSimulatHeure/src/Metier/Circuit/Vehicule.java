package Metier.Circuit;

import Metier.Carte.*;
import Metier.Exceptions.FinDeCircuitException;
import Metier.Passager;
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
        double progres = secondesSurSegment * 100 / segmentActuel.getTempsTransit();
        return progres;
    }
    public Segment getSegment(){
        return segmentActuel;
    }
    
    public void avancer(double tempsEcouleParRatioEnSeconde){
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
        //Position xy = this.calculerPosition(segmentActuel.getPointDepart(), segmentActuel.getPointDepart()); a voir plus tard
        Position xy = new Position(0,0);
        return xy;
    }
    
    
    private Position calculerPosition(Position posDepart, Position posArrivee)
    {
        double x,y,arrX,arrY,depX,depY, a,b,c;
        Position nouvellePosition = new Position(0,0);
        depX = posDepart.getX();
        depY = posDepart.getY();
        arrX = posArrivee.getX();
        arrY = posArrivee.getY();
        x = arrX - depX;
        y = arrY - depY;
        
        a = y / x;//...
        
        nouvellePosition.setX(x);
        nouvellePosition.setY(y);
        return nouvellePosition;
    }
}
