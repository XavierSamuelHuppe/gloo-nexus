package Metier.Carte;

import Metier.Circuit.Circuit;
import java.io.Serializable;

public class ElementTrajet implements Serializable{
    private final Circuit circuit;
    private final Point point;
    
    public ElementTrajet(Circuit circuit, Point point){
        this.circuit = circuit;
        this.point = point;
    }
    
    public Circuit getCircuit(){ 
        return circuit;
    }
    
    public Point getPoint() {
        return point;
    }
}
