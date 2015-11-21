package Metier.Circuit;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Carte.Segment;
import Metier.Exceptions.*;
import java.util.List;

public class CircuitBoucle extends Circuit {

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
    @Override
    public Segment obtenirProchainSegment(Point pointDepart){
        
        for(int i = trajet.size() - 1; i >= 0; i--){
            if (trajet.get(i).getPointDepart() == pointDepart){
                return trajet.get(i);
            }
        }
        
        throw new SegmentNonTrouveException();

        
    }
    
    
}
