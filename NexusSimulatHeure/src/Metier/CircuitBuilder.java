package Metier;

import Metier.Exceptions.*;
import java.util.*;

public class CircuitBuilder {
    public Circuit ConstruireCircuit(String nom, List<Segment> segments){
        if(segments.isEmpty())
            throw new CreationInvalideException("Le circuit doit avoir au moins un segment");
        
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
