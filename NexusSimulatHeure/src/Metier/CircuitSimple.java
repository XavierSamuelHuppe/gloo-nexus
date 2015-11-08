package Metier;

import Metier.Exceptions.*;
import java.util.List;

public class CircuitSimple extends Circuit{
    private String nom;
    private List<Segment> trajet;
    
    @Override
    public Segment obtenirProchainSegment(Segment dernierSegment) {
        int index = trajet.indexOf(dernierSegment);
        
        if(index == -1)
            throw new SegmentNonTrouveException();
        
        if(index == trajet.size() - 1)
            throw new FinDeCircuitException();
        
        return trajet.get(index+1);
    }
}
