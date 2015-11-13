package Metier.Circuit;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Carte.Segment;
import Metier.Exceptions.*;
import java.util.List;

public class CircuitSimple extends Circuit{
    private String nom;
    private List<Segment> trajet;
    
    public CircuitSimple(String nom, List<Segment> trajet){
        super(nom, trajet);
    }
    
    @Override
    public Segment obtenirProchainSegment(Segment dernierSegment) {
        int index = trajet.indexOf(dernierSegment);
        
        if(index == -1)
            throw new SegmentNonTrouveException();
        
        if(index == trajet.size() - 1)
            throw new FinDeCircuitException();
        
        return trajet.get(index+1);
    }
    @Override
    public Segment obtenirProchainSegment(Point pointDepart){
        
        for(int i = trajet.size(); i >= 0; i--){
            if (trajet.get(i).getPointDepart() == pointDepart){
                return trajet.get(i);
            }
        }
        
        throw new SegmentNonTrouveException();

    }
    
}
