package Metier.Circuit;

import Metier.Circuit.Circuit;
import Metier.Carte.Segment;
import Metier.Exceptions.*;
import java.util.*;

public class CircuitBuilder {
    public Circuit ConstruireCircuit(String nom, List<Segment> segments){
        if(segments.isEmpty())
            throw new CreationInvalideException("Le circuit doit avoir au moins un segment.");
        
        if(nom.isEmpty())
            throw new CreationInvalideException("Le nom du circuit est obligatoire.");
        
        if(!segments.get(segments.size() - 1).getPointArrivee().estArret())
            throw new CreationInvalideException("Le point d'arrivée du circuit doit être un arrêt.");
                
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
