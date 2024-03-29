package Metier;

import Metier.Profil.ElementTrajet;
import Metier.Carte.*;
import Metier.Circuit.*;
import Metier.Exceptions.*;
import Metier.Profil.Trajet;
import Metier.Simulation.Simulation;
import java.util.*;

public class ContexteEdition {

    public static enum ModeEdition {
        AUCUN,
        INTERSECTION,
        ARRET,
        SEGMENT,
        CIRCUIT,
        SOURCE,
        PASSAGER
    }
    
    private ModeEdition mode;
    private Point pointActif;
    private Circuit circuitActif;
    private Segment segmentActif;
    private Trajet trajecActif;
    
    private List<Segment> circuitEnCreation;
    private Point pointCreateur;
    
    private Trajet trajetEnCreation;
    private Point dernierPointDeMonte;
    private List<Segment> trajetEnCreationEnSegments;
    
    private Carte carte;
    private Simulation simulation;
    
    public ContexteEdition(Carte carte, Simulation simulation){
        mode = ModeEdition.ARRET;
        this.carte = carte;
        this.simulation = simulation;
        circuitEnCreation = new ArrayList();
        trajetEnCreationEnSegments = new ArrayList();
    }
    
    public void setCarte(Carte carte) {
        this.carte = carte;
    }
    
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
    
    public boolean estEnModeAucun(){
        return mode == ModeEdition.AUCUN;
    }
    public boolean estEnModeArret(){
        return mode == ModeEdition.ARRET;
    }
    public boolean estEnModeIntersection(){
        return mode == ModeEdition.INTERSECTION;
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
    public void passerEnModeAucun(){
        appliquerChangementMode();
        mode = ModeEdition.AUCUN;
    }
    public void passerEnModeArret(){
        appliquerChangementMode();
        mode = ModeEdition.ARRET;
    }
    public void passerEnModeIntersection(){
        appliquerChangementMode();
        mode = ModeEdition.INTERSECTION;
    }
    public void passerEnModeSegment(){
        appliquerChangementMode();
        mode = ModeEdition.SEGMENT;
    }
    public void passerEnModeCircuit(){
        appliquerChangementMode();
        mode = ModeEdition.CIRCUIT;
    }
    public void passerEnModeSource(){
        appliquerChangementMode();
        mode = ModeEdition.SOURCE;
    }
    public void passerEnModePassager(){
        appliquerChangementMode();
        mode = ModeEdition.PASSAGER;
    }
    private void appliquerChangementMode(){
        viderPointActif();
        viderCircuitActif();
        viderSegmentActif();
        viderPointCreateur();
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
        setPointCreateur(p);
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
        if(!p.estArret() && !possedePointCreateur())
            throw new MauvaisPointDeDepartException();
            
        Point monPointCreateur = null;
        try
        {
            monPointCreateur = getPointCreateur();
            setPointCreateur(p);
            if(carte.verifierExistenceSegment(monPointCreateur, p)){
                Segment segmentAAjouter = carte.obtenirSegment(monPointCreateur, p);
                circuitEnCreation.add(segmentAAjouter);
            }else{
                List<Segment> segmentsAAjouter = carte.plusCourtCheminEnTempsMoyen(monPointCreateur, p);
                circuitEnCreation.addAll(segmentsAAjouter);
            }
            
        }catch(AucunPointCreateurException e){
            setPointCreateur(p);
        }catch(AucunCheminPossibleException e){
            setPointCreateur(monPointCreateur);
            throw e;
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
        pointCreateur = null;
        circuitEnCreation.clear();
    }
    
    public boolean circuitEstEnCoursDeCreation(){
        return circuitActif == null;
    }
    
    public boolean estDansCircuitActif(Point point){
        return getCircuitActif().utilise(point);
    }
    public boolean estDansCircuitActif(Segment segment){
        return getCircuitActif().utilise(segment);
    }
    
    public boolean estDansCircuitEnCreation(Point point){
        return circuitEnCreation.stream().anyMatch((s) -> s.getPointDepart().equals(point) || s.getPointArrivee().equals(point));
    }
    public boolean estDansCircuitEnCreation(Segment segment){
        return circuitEnCreation.stream().anyMatch((s) -> s.equals(segment));
    }
    
    private boolean possedePointCreateur(){
        try{
            getPointCreateur();
        }catch(AucunPointCreateurException e){
            return false;
        }
        return true;
    }
    private boolean possedeCircuitActif(){
        try{
            getCircuitActif();
        }catch(AucunCircuitActifException e){
            return false;
        }
        return true;
    }
    
    
    public boolean possedeUnTrajetEnCoursDeCreation(){
        return (trajetEnCreation != null) || pointCreateur != null;
    }
    
    public boolean trajetEnCoursDeCreationContientAuMoinsUneEtape()
    {
        return possedeUnTrajetEnCoursDeCreation() && trajetEnCreation.contientAuMoinsUneEtape();
    }
       
    public void commencerContinuerCreationTrajet(Point p){
        if(!estEnModePassager())
            throw new EditionEnMauvaisModeException();
        if(simulation.circuitsPassantPar(p).size() == 0 || (!possedePointCreateur() && p.estIntersection()))
            throw new MauvaisPointDeDepartException();
        if(!possedePointCreateur()){
            setPointCreateur(p);
            dernierPointDeMonte = p;
            trajetEnCreation = new Trajet();
            setPremiereEtapeConstruite(false);
            return;
        }
        if(!possedeCircuitActif())
            throw new AucunCircuitActifException();
        if(!circuitActif.utilise(p))
            throw new PointPasSurCircuitActifException();
        
        Point monPointCreateur = null;
        monPointCreateur = getPointCreateur();
        setPointCreateur(p);
        try
        {
            if(carte.verifierExistenceSegment(monPointCreateur, p)){
                Segment segmentAAjouter = carte.obtenirSegment(monPointCreateur, p);
                trajetEnCreationEnSegments.add(segmentAAjouter);
            }else{
                List<Segment> segmentsAAjouter = circuitActif.obtenirSousCircuit(monPointCreateur, p);
                trajetEnCreationEnSegments.addAll(segmentsAAjouter);
            }
        }catch(AucunCheminPossibleException e){
            setPointCreateur(monPointCreateur);
            throw e;
        }
        
    }
    
    private boolean premiereEtapeConstruite;
    public void setPremiereEtapeConstruite(boolean premiereEtapeConstruite) {
        this.premiereEtapeConstruite = premiereEtapeConstruite;
    }
    public boolean isPremiereEtapeConstruite() {
        return premiereEtapeConstruite;
    }
    
    public void changerCircuitActif(Circuit c){
        if(!pointCreateur.estArret())
            throw new ChangementDeCircuitInvalideException();
        
        if(dernierPointDeMonte != pointCreateur)
        {
            ElementTrajet nouvelElement = new ElementTrajet(circuitActif, dernierPointDeMonte, pointCreateur);
            trajetEnCreation.ajouterElementTrajet(nouvelElement);
            dernierPointDeMonte = pointCreateur;
            circuitActif = c;
        }
        else
        {
            circuitActif = c;
        }
    }
    
    public void annulerCreationTrajet(){
        setPointCreateur(null);
        trajetEnCreation = null;
        trajetEnCreationEnSegments.clear();
        dernierPointDeMonte = null;
    }
    public Trajet terminerConstructionTrajet(){
        if(!pointCreateur.estArret())
            throw new MauvaisPointArriveException();
        if(trajetEnCreation.obtenirNombreEtapes() == 0 && dernierPointDeMonte == pointCreateur)
            throw new TrajetVideException();
        
        ElementTrajet nouvelElement = new ElementTrajet(circuitActif, dernierPointDeMonte, pointCreateur);
        trajetEnCreation.ajouterElementTrajet(nouvelElement);
        setPointCreateur(null);
        trajetEnCreationEnSegments.clear();
        dernierPointDeMonte = null;
        return trajetEnCreation;
    }
    
    
    
    public void setTrajetActif(Trajet t){
        trajecActif = t;
    }
    public Trajet getTrajetActif(){
        if(trajecActif == null)
            throw new AucunTrajetActifException();
        return trajecActif;
    }
    public void viderTrajetActif(){
        circuitActif = null;
    }
    
    public boolean estDansTrajetActif(Point point){
        return getTrajetActif().utilise(point);
    }
    public boolean estDansTrajetActif(Segment segment){
        return getTrajetActif().utilise(segment);
    }
    
    public boolean estDansTrajetEnCreation(Point point){
        return trajetEnCreationEnSegments.stream().anyMatch((s) -> s.getPointDepart().equals(point) || s.getPointArrivee().equals(point));
    }
    public boolean estDansTrajetEnCreation(Segment segment){
        return trajetEnCreationEnSegments.stream().anyMatch((s) -> s.equals(segment));
    }
    
    public void viderElementsActifs()
    {
        viderCircuitActif();
        viderPointActif();
        viderSegmentActif();
        viderTrajetActif();
    }
}
