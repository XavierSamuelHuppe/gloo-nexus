package Metier;

import java.util.List;

public class CircuitBuilder {
    public static Circuit ConstruireCircuit(String nom, List<Segment> segments){
        Circuit nouveauCircuit;
        
        int indexDebut = 0;
        int indexFin = segments.size()-1;
        if(segments.get(indexDebut).getPointDepart().equals(segments.get(indexFin).getPointArrivee())){
            nouveauCircuit = new CircuitBoucle(nom, segments);
        }else{
            nouveauCircuit = new CircuitSimple(nom, segments);
        }
        
        return nouveauCircuit;
    }
}
