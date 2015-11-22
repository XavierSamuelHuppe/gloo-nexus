package Metier.Simulation;

import Metier.Distribution;
import Metier.Exceptions.SimulationEnMauvaisEtatException;
import java.time.LocalTime;
import Metier.Circuit.ConteneurPassagersIllimite;

public class ParametreSimulation {
    public static enum Etats {
        AVANT_DEMARRAGE,
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
    private ConteneurPassagersIllimite conteneurPassagersIllimiteSource;
    private ConteneurPassagersIllimite conteneurPassagersIllimiteArret;
    
    
    private static Distribution DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT = new Distribution(300, 300, 300);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT = new Distribution(900, 900, 900);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT = new Distribution(1, 1, 1);
    private static int NOMBRE_JOUR_SIMULATION_DEFAUT = 1;
    private static LocalTime HEURE_DEBUT_DEFAUT = LocalTime.of(5,0);
    private static LocalTime HEURE_FIN_DEFAUT = LocalTime.of(1,0);
    public static LocalTime HEURE_DEBUT_NOUVELLE_JOURNEE = LocalTime.of(3,0);
    private static int VITESSE_DEFAUT = 100;
    private static int FRAMERATE_DEFAUT = 30;
    private static ConteneurPassagersIllimite CONTENEUR_PASSAGERS_ILLIMITE_SOURCE_DEFAUT = new ConteneurPassagersIllimite();
    private static ConteneurPassagersIllimite CONTENEUR_PASSAGERS_ILLIMITE_ARRET_DEFAUT = new ConteneurPassagersIllimite();
    
    public ParametreSimulation(){
        initialiserValeursParDefaut();
    }    
    
    private void initialiserValeursParDefaut(){
        etat = Etats.AVANT_DEMARRAGE;
        distributionTempsTransitSegment = DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT;
        distributionTempsGenerationVehicule = DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT;
        distributionTempsGenerationPassager = DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT;
        nombreJourSimulation = NOMBRE_JOUR_SIMULATION_DEFAUT;
        heureDebut = HEURE_DEBUT_DEFAUT;
        heureFin = HEURE_FIN_DEFAUT;
        vitesse = VITESSE_DEFAUT;
        framerate = FRAMERATE_DEFAUT;
        conteneurPassagersIllimiteSource = CONTENEUR_PASSAGERS_ILLIMITE_SOURCE_DEFAUT;
        conteneurPassagersIllimiteArret = CONTENEUR_PASSAGERS_ILLIMITE_ARRET_DEFAUT;
    }
    
    public Etats getEtat() {
        return etat;
    }
    
    public boolean estAvantDemarrage() {
        return etat == Etats.AVANT_DEMARRAGE;
    }
        
    public void mettreEnAvantDemarrage() {
        etat = Etats.AVANT_DEMARRAGE;
    }
        
    public boolean estEnPause() {
        return etat == Etats.PAUSE;
    }

    public void mettreEnPause() {
        etat = Etats.PAUSE;
    }
    
    public boolean estEnArret() {
        return etat == Etats.ARRET;
    }
    
    public void mettreEnArret() {
        etat = Etats.ARRET;
    }
    
    public boolean estEnAction() {
        return etat == Etats.ACTION;
    }
    
    public void mettreEnAction() {
        etat = Etats.ACTION;
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
    
    public ConteneurPassagersIllimite getConteneurPassagersIllimiteSource(){
        return conteneurPassagersIllimiteSource;
    }
    public ConteneurPassagersIllimite getConteneurPassagersIllimiteArret(){
        return conteneurPassagersIllimiteArret;
    }
    
    public double obtenirRatioVitesse() {
        double vitesseEnDouble = (new Integer(vitesse)).doubleValue();
        return vitesseEnDouble / 100;
    }

    public Distribution getDistributionTempsTransitSegmentDefaut() {
        return distributionTempsTransitSegment.obtenirCopie();
    }

    public Distribution getDistributionTempsGenerationVehiculeDefaut() {
        return distributionTempsGenerationVehicule.obtenirCopie();
    }

    public Distribution getDistributionTempsGenerationPassagerDefaut() {
        return distributionTempsGenerationPassager.obtenirCopie();
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
    
    public void setVitesse(int vitesse){
        this.vitesse = vitesse;
    }
    public void setFramerate(int framerate){
        this.framerate = framerate;
    }
    
    public void setConteneurPassagersIllimiteSource(ConteneurPassagersIllimite conteneurPassagersIllimiteSource) {
        this.conteneurPassagersIllimiteSource = conteneurPassagersIllimiteSource;
    }
    
    public void setConteneurPassagersIllimiteArret(ConteneurPassagersIllimite conteneurPassagersIllimiteArret) {
        this.conteneurPassagersIllimiteArret = conteneurPassagersIllimiteArret;
    }
}
