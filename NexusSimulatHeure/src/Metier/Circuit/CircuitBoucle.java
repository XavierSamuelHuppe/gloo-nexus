package Metier.Circuit;

import Metier.Circuit.Circuit;
import Metier.Carte.Segment;
import Metier.Exceptions.*;
import java.util.List;

/**
 *
 * @author Charles-André
 */
public class CircuitBoucle extends Circuit {
    private String nom;
    private List<Segment> trajet;
    
    public CircuitBoucle(String nom, List<Segment> trajet){
        super(nom, trajet);
    }
    
    @Override
    public Segment obtenirProchainSegment(Segment dernierSegment){
        int index = trajet.indexOf(dernierSegment);
        
        if(index == -1) 
            throw new SegmentNonTrouveException();
        
        if(index == trajet.size() - 1) {
            return trajet.get(0);
        }
        else {
            return trajet.get(index+1);
        }
    }
}
