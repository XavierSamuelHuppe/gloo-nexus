
package Metier.Circuit;

import Metier.Circuit.Circuit;
import Metier.Carte.Position;
import Metier.Carte.Segment;

public class Vehicule {
   
    private ConteneurPassagers passagers;
    private Circuit circuitActuel;
    public double progres;
    public Segment segmentActuel;
    
    public Vehicule(Circuit circuit, Segment segment, ConteneurPassagers passagers){
        this.circuitActuel = circuit;
        this.segmentActuel = segment;
        this.progres = 0;
        this.passagers = passagers;
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
