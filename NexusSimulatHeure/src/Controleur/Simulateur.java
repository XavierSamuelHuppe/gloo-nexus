package Controleur;

import Metier.Circuit.*;
import Metier.Carte.*;
import Metier.Simulation.Simulation;
import Metier.*;
import Metier.Exceptions.*;
import Metier.Source.*;
import Metier.Profil.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.util.*;

public class Simulateur {
    
    private Simulation simulation;
    private Carte carte;
    private ContexteEdition contexte;
    
    public Simulateur(){
        carte = new Carte();
        simulation = new Simulation(carte);
        contexte = new ContexteEdition(carte, simulation);
    }
    
    public boolean enregistrer(){
        try{
           FileOutputStream fileOut = new FileOutputStream("sauvegarde.ser");
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(simulation);
           out.close();
           fileOut.close();
           System.out.println("Simulation enregistré sous sauvegarde.ser");
           return true;
        }catch(IOException i){
            i.printStackTrace();
            System.out.println("ERREUR - la serialisation a explosé!");
            return false;
        }
    }
    public boolean charger(/*String path*/){
        try{
           FileInputStream fileIn = new FileInputStream("sauvegarde.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           simulation = (Simulation) in.readObject();
           carte = simulation.getCarte();
           contexte.setCarte(carte);
           contexte.setSimulation(simulation);
           in.close();
           fileIn.close();
           System.out.printf("Simulation chargé avec succes");
           return true;
        }catch(IOException i){
           i.printStackTrace();
           System.out.printf("ERREUR - le chargement a detruit l'espace!");
           return false;
        }catch(ClassNotFoundException c){
         System.out.println("Classe pas trouvée");
         c.printStackTrace();
         return false;
      }
    }
    
    public List<Point> obtenirToutLesPoints(){
        return carte.getPoints();
    }
    public List<Segment> obtenirToutLesSegments(){
        return carte.getSegments();
    }
 
    public boolean estEnModeAucun(){
        return contexte.estEnModeAucun();
    }
    public boolean estEnModeArret(){
        return contexte.estEnModeArret();
    }
    public boolean estEnModeIntersection(){
        return contexte.estEnModeIntersection();
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
    public void passerEnModeArret(){
        contexte.passerEnModeArret();
    }
    public void passerEnModeIntersection(){
        contexte.passerEnModeIntersection();
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
        this.contexte.passerEnModeAucun();
        if(simulation.getParametres().estEnPause())
            simulation.redemarrer();
        else if (simulation.getParametres().estAvantDemarrage())
            simulation.demarrer();
    }
    
    public void recommencer()
    {
        this.contexte.passerEnModeAucun();
        simulation.recommencer();
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
    
    public Point ajouterPoint(double x, double y, boolean estArret){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint(estArret).enPosition(x,y).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(double x, double y, boolean estArret, String nom){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPoint(estArret).avecUnNom(nom).enPosition(x,y).construire();
        carte.ajouterPoint(nouveauPoint);
        return nouveauPoint;
    }
    public Point ajouterPoint(double x, double y, boolean estArret, String nom, ConteneurPassagers passagers){
        PointFactory factory = new PointFactory();
        Point nouveauPoint = factory.nouveauPointAvecCapacite(passagers, estArret).avecUnNom(nom).enPosition(x,y).construire();
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
        simulation.retirerSegmentAvecReferences(segment);
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

    public void ajouterSource(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        SourceBuilder builder = new SourceBuilder();
        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        
        Source nouvelleSource = builder.ConstruireSource(heureFin, pointDepart, heureDebut, distributionAUtiliser, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
        simulation.ajouterSource(nouvelleSource);
        contexte.viderPointActif();
        contexte.viderCircuitActif();
    }
    
    public void ajouterSource(int nombreMax, Point pointDepart, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        SourceBuilder builder = new SourceBuilder();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        ConteneurPassagersIllimite conteneurPassagersIllimiteParDefaut = simulation.getParametres().getConteneurPassagersIllimiteSource();
        
        Source nouvelleSource = builder.ConstruireSource(nombreMax, pointDepart, heureDebut, distributionAUtiliser, conteneurPassagersIllimiteParDefaut, circuit, this.simulation);
        simulation.ajouterSource(nouvelleSource);
        contexte.viderPointActif();
        contexte.viderCircuitActif();
    }  
    
    public void ajouterProfil(LocalTime heureFin, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        ProfilBuilder builder = new ProfilBuilder();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        Trajet trajet = this.sauvegarderTrajetPourProfilPassager();
        ProfilPassager nouveauPassager = builder.ConstruireProfil(heureFin, trajet.obtenirPointDepart(), heureDebut, distributionAUtiliser, trajet, simulation);
        simulation.ajouterProfil(nouveauPassager);
    }
    
    public void ajouterProfil(int nombreMax, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        ProfilBuilder builder = new ProfilBuilder();
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        Trajet trajet = this.sauvegarderTrajetPourProfilPassager();
        ProfilPassager nouveauPassager = builder.ConstruireProfil(nombreMax, trajet.obtenirPointDepart(), heureDebut, distributionAUtiliser, trajet, simulation);
        simulation.ajouterProfil(nouveauPassager);
    }
    
    public void retirerSource(Source source){
        simulation.retirerSource(source);
    }
    
    public void retirerProfil(ProfilPassager profil){
        simulation.retirerProfil(profil);
    }

    public void modifierSource(Source source, LocalTime heureFin, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierSource(source, heureFin, heureDebut, circuit, distributionMin, distributionMode, distributionMax);
    }
    
    public void modifierSource(Source source, int nombreMax, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierSource(source, nombreMax, heureDebut, circuit, distributionMin, distributionMode, distributionMax);
    }  
    
    public void modifierProfil(ProfilPassager profil, LocalTime heureFin, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierProfil(profil, heureFin, heureDebut, distributionMin, distributionMode, distributionMax);
    }
    public void modifierProfil(ProfilPassager profil, int nombreMax, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        simulation.modifierProfil(profil, nombreMax, heureDebut, distributionMin, distributionMode, distributionMax);
    }
    
    public List<SituationVehicule> obtenirSituationsVehicules(){
        return this.simulation.obtenirSituationsVehicules();
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
    
    public void modifierDistributionTempsTransitSegment(double distributionMin, double distributionMode, double distributionMax){
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        simulation.getParametres().setDistributionTempsTransitSegment(distributionAUtiliser);
    }
    public void modifierDistributionTempsGenerationVehicule(double distributionMin, double distributionMode, double distributionMax){
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        simulation.getParametres().setDistributionTempsGenerationVehicule(distributionAUtiliser);
    }
    public void modifierDistributionTempsGenerationPassager(double distributionMin, double distributionMode, double distributionMax){
        Distribution distributionAUtiliser = new Distribution(distributionMin, distributionMode, distributionMax);
        simulation.getParametres().setDistributionTempsGenerationPassager(distributionAUtiliser);
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
    public boolean circuitActifEstConnu()
    {
        try{
           contexte.getCircuitActif();
        }
        catch(AucunCircuitActifException ex){
            return false;
        }
        return true;
    }
    
    public void commencerContinuerCreationCircuit(Point p){
        contexte.commencerContinuerCreationCircuit(p);
    }
    public void sauvergarderCircuit(String nom){
        if(contexte.circuitEstEnCoursDeCreation()){
            Circuit nouveauCircuit = contexte.obtenirNouveauCircuit(nom);
            simulation.ajouterCircuit(nouveauCircuit);
            contexte.viderCircuitEnCreation();
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
    
    public LocalTime obtenirHeureDebutSimulation(){
        return this.getParametresSimulation().getHeureDebut();
    }
    
    public LocalTime obtenirHeureFinSimulation(){
        return this.getParametresSimulation().getHeureFin();
    }
    
    
    
    public void commencerContinuerCreationTrajet(Point p){
        contexte.commencerContinuerCreationTrajet(p);
    }
    
    public void choisirCircuitActifPourCreationTrajet(Circuit c){
        if(!contexte.isPremiereEtapeConstruite())
        {
            contexte.setCircuitActif(c);
            contexte.setPremiereEtapeConstruite(true);
        }
        else
        {
            contexte.changerCircuitActif(c);
        }
    }
    
    private Trajet sauvegarderTrajetPourProfilPassager(){
        Trajet trajetAEmprunter = contexte.terminerConstructionTrajet();
        return trajetAEmprunter;
    }
    
    public void annulerCreationTrajet(){
        contexte.annulerCreationTrajet();
    }
    public boolean possedeUnTrajetEnCoursDeCreation(){
        return contexte.possedeUnTrajetEnCoursDeCreation();
    }
    
    public boolean segmentEstDansCircuitActifPourCreationTrajet(Segment segment){
        
        return estDansCircuitActif(segment);
    }
    public boolean pointEstDansCircuitActifPourCreationTrajet(Point point){
        return estDansCircuitActif(point);
    }
    
    public boolean estDansTrajetActif(Point point){
        try{
            return contexte.estDansTrajetActif(point);
        }catch(AucunTrajetActifException e){
            return false;
        }
    }
    public boolean estDansTrajetActif(Segment segment){
        try{
            return contexte.estDansTrajetActif(segment) || contexte.estDansTrajetEnCreation(segment);
        }catch(AucunTrajetActifException e){
            return false;
        }
    }
    public boolean estDansTrajetEnCreation(Point point){
        try{
            return contexte.estDansTrajetEnCreation(point) || contexte.getPointCreateur().equals(point);
        }catch(AucunPointCreateurException e){
            return false;
        }
    }
    public boolean estDansTrajetEnCreation(Segment segment){
        try{
            return contexte.estDansTrajetEnCreation(segment);
        }catch(AucunTrajetActifException e){
            return false;
        }
    }
    
    public void executerSimulationInstantanement()
    {
        this.simulation.executerInstantanement(false);
    }
    
    
    
    public String obtenirStatistiques()
    {
        return simulation.obtenirStatistiques();
    }
}
