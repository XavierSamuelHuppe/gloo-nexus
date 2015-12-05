package Metier.Circuit;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Carte.Segment;
import Metier.Exceptions.*;
import java.util.List;

public class CircuitSimple extends Circuit{
    public CircuitSimple(String nom, List<Segment> trajet){
        super(nom, trajet);
    }
    
    @Override
    public Segment obtenirProchainSegment(Segment dernierSegment) {
        int index = parcours.indexOf(dernierSegment);
        
        if(index == -1)
            throw new SegmentNonTrouveException();
        
        if(index == parcours.size() - 1)
            throw new FinDeCircuitException();
        
        return parcours.get(index+1);
    }
    @Override
    public Segment obtenirProchainSegment(Point pointDepart){
        
        for(int i = parcours.size() - 1; i >= 0; i--){
            if (parcours.get(i).getPointDepart() == pointDepart){
                return parcours.get(i);
            }
        }
        
        throw new SegmentNonTrouveException();

    }
    
}
