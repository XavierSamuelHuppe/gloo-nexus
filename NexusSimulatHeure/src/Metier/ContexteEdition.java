package Metier;

import Metier.Carte.Carte;
import Metier.Carte.Point;
import Metier.Carte.Segment;
import Metier.Exceptions.AucunPointActifException;
import Metier.Exceptions.EditionEnMauvaisModeException;
import Metier.Simulation.Simulation;
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
    private List<Segment> circuitEnCours;
    
    private Simulation simulation;
    private Carte carte;
    
    public ContexteEdition(Simulation simulation, Carte carte){
        mode = ModeEdition.POINT;
        this.carte = carte;
        this.simulation = simulation;
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
    
    public void commencerContinuerCreationCircuit(Point p){
        if(!estEnModeCircuit())
            throw new EditionEnMauvaisModeException();
        
        setPointActif(p);
        Segment segmentAAjouter = carte.obtenirSegment(pointActif, p);
        circuitEnCours.add(segmentAAjouter);
    }
    
    
}
