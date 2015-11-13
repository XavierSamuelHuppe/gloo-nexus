package Metier.Carte;

import Metier.Circuit.Circuit;

public class ElementTrajet {
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
