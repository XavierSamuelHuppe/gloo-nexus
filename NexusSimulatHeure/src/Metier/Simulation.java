package Metier;
import java.time.*;
import Metier.Exceptions.*;

public class Simulation{
    public static enum Etats {
        ACTION,
        ARRET,
        PAUSE
    }
    
    private Etats etat;
    private Distribution distributionTempsTransitSegment;
    private Distribution distributionTempsGenerationVehicule;
    private Distribution distributionTempsGenerationPassager;
    private int nombreJourSimulation;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private int vitesse;
    private int framerate;
    
    private static Distribution DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT = new DistributionTriangulaire(300, 300, 300);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT = new DistributionTriangulaire(900, 900, 900);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT = new DistributionTriangulaire(1, 1, 1);
    private static int NOMBRE_JOUR_SIMULATION_DEFAUT = 1;
    private static LocalTime HEURE_DEBUT_DEFAUT = LocalTime.of(8,0);
    private static LocalTime HEURE_FIN_DEFAUT = LocalTime.of(17,0);
    private static int VITESSE_DEFAUT = 100;
    private static int FRAMERATE_DEFAUT = 30;
    
    private Thread boucleThread;
    private BoucleSimulation boucle;
    
    public Simulation(){
        initialiserValeursParDefaut();
    }
    
    public void demarrer(){
        if(!(etat == Etats.ARRET))
            throw new SimulationEnMauvaisEtatException();
        
        etat = Etats.ACTION;
        boucle = new BoucleSimulation(this);
        boucleThread = new Thread(boucle, "boucle de la simulation");
        boucleThread.start();
    }
    
    public void arreter(){
        if(etat == Etats.ARRET)
            throw new SimulationEnMauvaisEtatException();
        
        etat = Etats.ARRET;
        boucleThread.interrupt();
    }
    
    public void pauser(){
        if(!(etat == Etats.ACTION))
            throw new SimulationEnMauvaisEtatException();
        
        etat = Etats.PAUSE;
    }
    
    public void redemarrer(){
        if(!(etat == Etats.PAUSE))
            throw new SimulationEnMauvaisEtatException();
        
        etat = Etats.ACTION;
    }
    
    public void initialiserValeursParDefaut(){
        etat = Etats.ARRET;
        distributionTempsTransitSegment = DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT;
        distributionTempsGenerationVehicule = DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT;
        distributionTempsGenerationPassager = DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT;
        nombreJourSimulation = NOMBRE_JOUR_SIMULATION_DEFAUT;
        heureDebut = HEURE_DEBUT_DEFAUT;
        heureFin = HEURE_FIN_DEFAUT;
        vitesse = VITESSE_DEFAUT;
        framerate = FRAMERATE_DEFAUT;
    }

    public Etats getEtat() {
        return etat;
    }
    public boolean estEnPause() {
        return etat == Etats.PAUSE;
    }

    public int getNombreJourSimulation() {
        return nombreJourSimulation;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public int getVitesse() {
        return vitesse;
    }
    
    public int getFramerate(){
        return framerate;
    }
    
    public double obtenirRatio() {
        double vitesseEnDouble = (new Integer(vitesse)).doubleValue();
        return vitesseEnDouble / 100;
    }
    
    public void setDistributionTempsTransitSegment(Distribution distributionTempsTransitSegment) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.distributionTempsTransitSegment = distributionTempsTransitSegment;
    }

    public void setDistributionTempsGenerationVehicule(Distribution distributionTempsGenerationVehicule) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.distributionTempsGenerationVehicule = distributionTempsGenerationVehicule;
    }

    public void setDistributionTempsGenerationPassager(Distribution distributionTempsGenerationPassager) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.distributionTempsGenerationPassager = distributionTempsGenerationPassager;
    }

    public void setNombreJourSimulation(int nombreJourSimulation) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.nombreJourSimulation = nombreJourSimulation;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        if(etat == Etats.ACTION)
            throw new SimulationEnMauvaisEtatException();
        this.heureFin = heureFin;
    }
    
    public void modifierVitesse(int vitesse){
        this.vitesse = vitesse;
    }
    public void modifierFramerate(int framerate){
        this.framerate = framerate;
    }
            
    public void faireAvancerSimulation(double TempsEcouleParRatioEnSeconde){
        
    }
    
}
