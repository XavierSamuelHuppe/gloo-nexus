package Metier.Carte;

import Metier.Circuit.Circuit;
import java.io.Serializable;

public class ElementTrajet implements Serializable{
    private final Circuit circuit;
    private final Point pointMontee;
    private final Point pointDescente;
    
    public ElementTrajet(Circuit circuit, Point pointMontee, Point pointDescente){
        this.circuit = circuit;
        this.pointMontee = pointMontee;
        this.pointDescente = pointDescente;
    }
    
    public Circuit getCircuit(){ 
        return circuit;
    }
    
    public Point getPointMontee() {
        return pointDescente;
    }
    
    public Point getPointDescente() {
        return pointDescente;
    }
}
