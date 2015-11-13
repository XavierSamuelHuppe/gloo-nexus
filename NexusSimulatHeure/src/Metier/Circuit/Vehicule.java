
package Metier.Circuit;

import Metier.Circuit.Circuit;
import Metier.Carte.Position;
import Metier.Carte.Segment;

public class Vehicule {
   
    private ConteneurPassagers passagers;
    private Circuit circuitActuel;
    public double progres;
    public Segment segmentActuel;
    
    public Vehicule(Circuit circuit, Segment segment){
        this.circuitActuel = circuit;
        this.segmentActuel = segment;
        this.progres = 0;
        // passager?????????????????????????????????????????????
    }
    public Circuit getCircuit(){
        return circuitActuel;
    }
    
    public double getProgres(){
        return progres;
    }
    public Segment getSegment(){
        return segmentActuel;
    }
    
    //Passager????
    
    
    // A faire
    public Position obtenirPosition()
    {
        Position xy = new Position(0,0);
        return xy;
    }
    
    //i guess que progres va jouer la dedans
    private Position calculerPosition(Position posDepart, Position posArrivee)
    {
        double x,y,arrX,arrY,depX,depY;
        Position nouvellePosition = new Position(0,0);
        depX = posDepart.getX();
        depY = posDepart.getY();
        arrX = posArrivee.getX();
        arrY = posArrivee.getY();
        x = arrX - depX;
        y = arrY - depY;
        nouvellePosition.setX(x);
        nouvellePosition.setY(y);
        return nouvellePosition;
        //nouvellePosition.setX();
    }
}
