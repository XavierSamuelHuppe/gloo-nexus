package Controleur;

import Metier.Circuit.*;
import Metier.Carte.*;
import Metier.Simulation.Simulation;
import Metier.*;
import Metier.Exceptions.*;
import Metier.Source.*;
import java.time.LocalTime;
import java.util.*;

public class Simulateur {
    
    private Simulation simulation;
    private Carte carte;
    private ContexteEdition contexte;
    
    public Simulateur(){
        carte = new Carte();
        simulation = new Simulation(carte);
        contexte = new ContexteEdition(carte);
    }
 
    public boolean estEnModePoint(){
        return contexte.estEnModePoint();
    }
    public boolean estEnModeSegment(){
        return contexte.estEnModeSegment();
    }
    public boolean estEnModeCircuit(){
        return contexte.estEnModeCircuit();
    }
    public boolean estEnModeSource(){
        return contexte.estEnModeSource();
    }
    public boolean estEnModePassager(){
        return contexte.estEnModePassager();
    }
    public void passerEnModePoint(){
        contexte.passerEnModePoint();
    }
    public void passerEnModeSegment(){
        contexte.passerEnModeSegment();
    }
    public void passerEnModeCircuit(){
        contexte.passerEnModeCircuit();
    }
    public void passerEnModeSource(){
        contexte.passerEnModeSource();
    }
    public void passerEnModePassager(){
        contexte.passerEnModePassager();
    }

    public void arreter(){
        simulation.arreter();
    }
    public void demarerRedemarer(){
        if(simulation.getParametres().estEnPause())
            simulation.redemarrer();
        else if (simulation.getParametres().estAvantDemarrage())
            simulation.demarrer();
    }
    public void pauser(){
        simulation.pauser();
    }
    public LocalTime obtenirHeureCourante(){
        return this.simulation.getHeureCourante();
    }
    public int obtenirJourneeCourante(){
        return this.simulation.getJourneeCourante();
    }
    public int obtenirNombreJourSimulation(){
        return this.simulation.getNombreJourSimulation();
    }
    public boolean simulationEstEnAction(){
        return this.getParametresSimulation().estEnAction();
    }
    
    public Point ajouterPoint(double x, double y){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint().enPosition(x,y).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(double x, double y, String nom){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint().avecUnNom(nom).enPosition(x,y).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(double x, double y, String nom, ConteneurPassagers passagers){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPointAvecCapacite(passagers).avecUnNom(nom).enPosition(x,y).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public void retirerPoint(Point point){
        simulation.retirerPointAvecReferences(point);
    }
    public void modifierPoint(Point pointCible, double x, double y, String nouveauNom){
        Position nouvellePosition = new Position(x, y);
        carte.modifierPoint(pointCible, nouvellePosition, nouveauNom);
    }
    
    private Segment ajouterSegment(Point depart, Point arrivee){
        Segment segment = new Segment(depart, arrivee, simulation.getParametres().getDistributionTempsTransitSegmentDefaut());
        carte.ajouterSegment(segment);
        return segment;
    }
    private Segment ajouterSegment(Point depart, Point arrivee, Distribution tempsTransit){
        Segment segment = new Segment(depart, arrivee, tempsTransit);
        carte.ajouterSegment(segment);
        return segment;
    }
    public void retirerSegment(Segment segment){
        carte.retirerSegment(segment);
        contexte.viderSegmentActif();
    }
    public void modifierSegment(Segment segmentCible, double min, double mode, double max){
        Distribution nouvelleDistribution = new Distribution(min, mode, max);
        carte.modifierSegment(segmentCible, nouvelleDistribution);
    }
    public void annulerCreationSegment(){
        contexte.viderPointCreateur();
    }
        
    public boolean verifierExistenceSegment(Point depart, Point arrivee) {
        return carte.verifierExistenceSegment(depart, arrivee);
    }
    public boolean verifierExistenceSegementEnSensInverse(Segment segment){
        return carte.segmentExisteEnSensInverse(segment);
    }
    public boolean estSegmentSortantDePoint(Point point, Segment segment){
        return this.carte.estSegmentSortantDePoint(point, segment);
    }
    
//    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distribution, passagers, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distribution, passagers, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, ConteneurPassagers passagers, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        Distribution distributionParDefaut = simulation.getParametres().getDistributionTempsGenerationVehiculeDefaut();
//        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distributionParDefaut, passagers, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, ConteneurPassagers passagers, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        Distribution distributionParDefaut = simulation.getParametres().getDistributionTempsGenerationVehiculeDefaut();
//        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distributionParDefaut, passagers, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
//        Distribution distributionParDefaut = simulation.getParametres().getDistributionTempsGenerationVehiculeDefaut();
//        
//        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distributionParDefaut, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
//        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distribution, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }
//    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        Distribution distributionParDefaut = simulation.getParametres().getDistributionTempsGenerationVehiculeDefaut();
//        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
//        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distributionParDefaut, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }   
//    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, Circuit circuit){
//        SourceBuilder builder = new SourceBuilder();
//        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
//        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distribution, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
//        simulation.ajouterSource(nouvelleSource);
//    }  
//    
    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        SourceBuilder builder = new SourceBuilder();
        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        
        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distributionAUtiliser, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
        simulation.ajouterSource(nouvelleSource);
    }
    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        SourceBuilder builder = new SourceBuilder();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distributionAUtiliser, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
        simulation.ajouterSource(nouvelleSource);
    }  
    
    public void retirerSource(Source source){
        simulation.retirerSource(source);
    }
    
//    public void modifierSource(Source source, LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(heureFin, pointDepart, heureDebut, distribution, passagers, circuit);
//    }
//    public void modifierSource(Source source, int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, ConteneurPassagers passagers, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(nombreMax, pointDepart, heureDebut, distribution, passagers, circuit);
//    }
//    public void modifierSource(Source source, LocalTime heureFin, Point pointDepart, LocalTime heureDebut, ConteneurPassagers passagers, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(heureFin, pointDepart, heureDebut, passagers, circuit);
//    }
//    public void modifierSource(Source source, int nombreMax, Point pointDepart, LocalTime heureDebut, ConteneurPassagers passagers, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(nombreMax, pointDepart, heureDebut, passagers, circuit);
//    }
//    public void modifierSource(Source source, int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(nombreMax, pointDepart, heureDebut, distribution, circuit);
//    }
//    public void modifierSource(Source source, int nombreMax, Point pointDepart, LocalTime heureDebut, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(nombreMax, pointDepart, heureDebut, circuit);
//    }
//    public void modifierSource(Source source, LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(heureFin, pointDepart, heureDebut, distribution, circuit);
//    }
//    public void modifierSource(Source source, LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Circuit circuit){
//        retirerSource(source);
//        this.ajouterSource(heureFin, pointDepart, heureDebut, circuit);
//    }

    public void modifierSource(Source source, LocalTime heureFin, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierSource(source, heureFin, heureDebut, circuit, distributionMin, distributionMode, distributionMax);
    }
    
    public void modifierSource(Source source, int nombreMax, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierSource(source, nombreMax, heureDebut, circuit, distributionMin, distributionMode, distributionMax);
    }  
    
    public List<Position> obtenirPositionVehicules(){
        return this.simulation.obtenirPositionsVehicules();
    }
    
    public void modfierVitesse(int pourcentage){
        simulation.getParametres().setVitesse(pourcentage);
    }
    public void modfierFramerate(int pourcentage){
        simulation.getParametres().setFramerate(pourcentage);
    }
    
    public Metier.Simulation.ParametreSimulation getParametresSimulation(){
        return this.simulation.getParametres();
    }
    
    public void modifierDistributionTempsTransitSegment(Distribution dist){
        simulation.getParametres().setDistributionTempsTransitSegment(dist);
    }
    public void modifierDistributionTempsGenerationVehicule(Distribution dist){
        simulation.getParametres().setDistributionTempsGenerationVehicule(dist);
    }
    public void modifierDistributionTempsGenerationPassager(Distribution dist){
        simulation.getParametres().setDistributionTempsGenerationPassager(dist);
    }
    public void modifierNombreJourSimulation(int nombreJours){
        simulation.getParametres().setNombreJourSimulation(nombreJours);
    }
    public void modifierHeureDebut(LocalTime heure){
        simulation.getParametres().setHeureDebut(heure);
    }
    public void modifierHeureFin(LocalTime heure){
        simulation.getParametres().setHeureFin(heure);
    }
        
    public void selectionnerPoint(Point p){
        contexte.setPointActif(p);
    }
    public boolean estPointActif(Point p){
        try{
        return contexte.getPointActif().equals(p);
        }catch(AucunPointActifException e){
            return false;
        }
    }
    public void viderPointActif(){
        contexte.viderPointActif();
    }
    
    public void selectionnerSegment(Segment segment){
        contexte.setSegmentActif(segment);
    }
    public boolean estSegmentActif(Segment segment){
        try{
        return contexte.getSegmentActif().equals(segment);
        }catch(AucunSegmentActifException e){
            return false;
        }
    }
    public void viderSegmentActif(){
        contexte.viderSegmentActif();
    }
    
    public Segment creerSegmentAvecContinuation(Point p){
        Point pointCreateur = contexte.creerSegmentAvecContinuation(p);
        return ajouterSegment(pointCreateur, p);
    }
    public Segment creerSegmentSansContinuation(Point p){
        Point pointCreateur = contexte.creerSegmentSansContinuation(p);
        return ajouterSegment(pointCreateur, p);
    }
    
    public void viderPointCreateur(){
        contexte.viderPointCreateur();
    }
    
    public List<Circuit> circuitsPassantPar(Segment segment){
        return simulation.circuitsPassantPar(segment);
    }
    public List<Circuit> circuitsPassantPar(Point point){
        return simulation.circuitsPassantPar(point);
    }
    public void activerCircuit(Circuit circuit){
        contexte.setCircuitActif(circuit);
    }
    public Circuit getCircuitActif(){
        return contexte.getCircuitActif();
    }
    public boolean estCircuitActif(Circuit circuit){
        return contexte.getCircuitActif().equals(circuit);
    }
    public void viderCircuitActif(){
        contexte.viderCircuitActif();
    }
    
    public void commencerContinuerCreationCircuit(Point p){
        contexte.commencerContinuerCreationCircuit(p);
    }
    public void sauvergarderCircuit(String nom){
        if(contexte.circuitEstEnCoursDeCreation()){
            Circuit nouveauCircuit = contexte.obtenirNouveauCircuit(nom);
            simulation.ajouterCircuit(nouveauCircuit);
        }
        else{
            simulation.modifierCircuit(contexte.getCircuitActif(), nom);
            contexte.viderCircuitActif();
        }
        
    }
    public void annulerCreationCircuit(){
        contexte.viderCircuitEnCreation();
    }
    
    public boolean estDansCircuitActif(Point point){
        try{
            return contexte.estDansCircuitActif(point);
        }catch(AucunCircuitActifException e){
            return false;
        }
    }
    public boolean estDansCircuitActif(Segment segment){
        try{
            return contexte.estDansCircuitActif(segment) || contexte.estDansCircuitEnCreation(segment);
        }catch(AucunCircuitActifException e){
            return false;
        }
    }
    public boolean estDansCircuitEnCreation(Point point){
        try{
            return contexte.estDansCircuitEnCreation(point) || contexte.getPointCreateur().equals(point);
        }catch(AucunPointCreateurException e){
            return false;
        }
    }
    public boolean estDansCircuitEnCreation(Segment segment){
        try{
            return contexte.estDansCircuitEnCreation(segment);
        }catch(AucunCircuitActifException e){
            return false;
        }
    }
    public boolean estDansAuMoinsUnCircuit(Point point){
        return simulation.circuitsPassantPar(point).size() > 0;
    }
    public boolean estDansAuMoinsUnCircuit(Segment segment){
        return simulation.circuitsPassantPar(segment).size() > 0;
    }
    
    public boolean estPointCreateur(Point point)
    {
        try{
            return this.contexte.getPointCreateur().equals(point);
        }
        catch (AucunPointCreateurException ex){
            return false;
        }
    }
    
    
    public void ajouterObserveurASimulation(Observer observeur)
    {
        simulation.addObserver(observeur);
    }
    
    public Metier.Distribution obtenirDistributionTempsGenerationVehiculeDefaut(){
        return this.simulation.getParametres().getDistributionTempsGenerationVehiculeDefaut();
    }
}
