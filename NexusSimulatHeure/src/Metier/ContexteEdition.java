package Metier;

import Metier.Carte.*;
import Metier.Circuit.*;
import Metier.Exceptions.*;
import java.util.*;

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
    private Circuit circuitActif;
    private Segment segmentActif;
    
    private List<Segment> circuitEnCreation;
    private Point pointCreateur;
    
    
    private Carte carte;
    
    public ContexteEdition(Carte carte){
        mode = ModeEdition.POINT;
        this.carte = carte;
        circuitEnCreation = new ArrayList();
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
    
    public void setPointCreateur(Point p){
        pointCreateur = p;
    }
    public Point getPointCreateur(){
        if(pointCreateur == null)
            throw new AucunPointCreateurException();
        return pointCreateur;
    }
    public void viderPointCreateur(){
        pointCreateur = null;
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
    
    public Point creerSegmentAvecContinuation(Point p){
        if(!estEnModeSegment())
            throw new EditionEnMauvaisModeException();
        if(pointCreateur == null)
        {
            setPointCreateur(p);
            throw new AucunPointCreateurException();
        }
        
        Point retour = pointCreateur;
        return retour;
    }
    public Point creerSegmentSansContinuation(Point p){
        if(!estEnModeSegment())
            throw new EditionEnMauvaisModeException();
        if(pointCreateur == null)
        {
            setPointCreateur(p);
            throw new AucunPointCreateurException();
        }
        
        Point retour = pointCreateur;
        viderPointCreateur();
        return retour;
    }
    
    public void setSegmentActif(Segment segment){
        segmentActif = segment;
    }
    public Segment getSegmentActif(){
        if(segmentActif == null)
            throw new AucunSegmentActifException();
        return segmentActif;
    }
    public void viderSegmentActif(){
        segmentActif = null;
    }
    
    public void commencerContinuerCreationCircuit(Point p){
        if(!estEnModeCircuit())
            throw new EditionEnMauvaisModeException();
        
        try
        {
            Point monPointCreateur = getPointCreateur();
            setPointCreateur(p);
            Segment segmentAAjouter = carte.obtenirSegment(monPointCreateur, p);
            circuitEnCreation.add(segmentAAjouter);
            
        }catch(AucunPointActifException e){
            setPointCreateur(p);
        }
    }
    public Circuit obtenirNouveauCircuit(String nom){
        if(!estEnModeCircuit())
            throw new EditionEnMauvaisModeException();
        
        CircuitBuilder builder = new CircuitBuilder();
        Circuit nouveauCircuit = builder.ConstruireCircuit(nom, circuitEnCreation);
        circuitEnCreation.clear();
        viderPointCreateur();
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
