package Metier;

import Metier.Carte.*;
import Metier.Circuit.*;
import Metier.Exceptions.*;
import java.util.List;

public class ContexteEdition {

    public static enum ModeEdition {
        POINT,
        SEGMENT,
        CIRCUIT,
        SOURCE,
        PASSAGER
    }
    
    private ModeEdition mode;
    private Point pointActif;
    private List<Segment> circuitEnCreation;
    
    private Circuit circuitActif;
    
    private Carte carte;
    
    public ContexteEdition(Carte carte){
        mode = ModeEdition.POINT;
        this.carte = carte;
    }
    
    public boolean estEnModePoint(){
        return mode == ModeEdition.POINT;
    }
    public boolean estEnModeSegment(){
        return mode == ModeEdition.SEGMENT;
    }
    public boolean estEnModeCircuit(){
        return mode == ModeEdition.CIRCUIT;
    }
    public boolean estEnModeSource(){
        return mode == ModeEdition.SOURCE;
    }
    public boolean estEnModePassager(){
        return mode == ModeEdition.PASSAGER;
    }
    
    public void passerEnModePoint(){
        mode = ModeEdition.POINT;
    }
    public void passerEnModeSegment(){
        mode = ModeEdition.SEGMENT;
    }
    public void passerEnModeCircuit(){
        mode = ModeEdition.CIRCUIT;
    }
    public void passerEnModeSource(){
        mode = ModeEdition.SOURCE;
    }
    public void passerEnModePassager(){
        mode = ModeEdition.PASSAGER;
    }
    
    public void setPointActif(Point p){
        pointActif = p;
    }
    public Point getPointActif(){
        if(pointActif == null)
            throw new AucunPointActifException();
        return pointActif;
    }
    public void viderPointActif(){
        pointActif = null;
    }
    
    public void setCircuitActif(Circuit p){
        circuitActif = p;
    }
    public Circuit getCircuitActif(){
        if(circuitActif == null)
            throw new AucunCircuitActifException();
        return circuitActif;
    }
    public void viderCircuitActif(){
        circuitActif = null;
    }
    
    public void commencerContinuerCreationCircuit(Point p){
        if(!estEnModeCircuit())
            throw new EditionEnMauvaisModeException();
        
        try
        {
            Point monPointActif = getPointActif();
            setPointActif(p);
            Segment segmentAAjouter = carte.obtenirSegment(monPointActif, p);
            circuitEnCreation.add(segmentAAjouter);
            
        }catch(AucunPointActifException e){
            setPointActif(p);
        }
    }
    public Circuit obtenirNouveauCircuit(String nom){
        if(!estEnModeCircuit())
            throw new EditionEnMauvaisModeException();
        
        CircuitBuilder builder = new CircuitBuilder();
        Circuit nouveauCircuit = builder.ConstruireCircuit(nom, circuitEnCreation);
        circuitEnCreation.clear();
        return nouveauCircuit;
    }
    public void viderCircuitEnCreation(){
        circuitEnCreation.clear();
    }
    
    public boolean estDansCircuitActif(Point point){
        return getCircuitActif().utilise(point);
    }
    public boolean estDansCircuitActif(Segment segment){
        return getCircuitActif().utilise(segment);
    }
    
    
}
