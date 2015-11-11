package Metier;
import java.time.*;
import Metier.Exceptions.*;

public class Simulation{
    public enum Etats {
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
    
    private static Distribution DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT = new DistributionTriangulaire(300, 300, 300);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT = new DistributionTriangulaire(900, 900, 900);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT = new DistributionTriangulaire(1, 1, 1);
    private static int NOMBRE_JOUR_SIMULATION_DEFAUT = 1;
    private static LocalTime HEURE_DEBUT_DEFAUT = LocalTime.of(8,0);
    private static LocalTime HEURE_FIN_DEFAUT = LocalTime.of(17,0);
    private static int VITESSE_DEFAUT = 100;
    
    private Thread boucle;
    
    public Simulation(){
        initialiserValeursParDefaut();
    }
    
    public void demarrer(){
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        
        etat = Etats.ACTION;

        if(etat == Etats.ARRET){
            BoucleSimulation vie = new BoucleSimulation(this);
            boucle = new Thread(vie, "boucle de la simulation");
        }
        boucle.start();
    }
    
    public void arreter(){
        if(etat == Etats.ARRET)
            throw new SimulationEnArretException();
        
        etat = Etats.ARRET;
        
        boucle.interrupt();
    }
    
    public void pauser(){
        if(etat == Etats.ARRET || etat == Etats.PAUSE)
            throw new SimulationEnMauvaisEtat();
        
        etat = Etats.PAUSE;
        
        boucle.interrupt();
    }
    
    public void modifierVitesse(int vitesse){
        if(etat == Etats.ARRET || etat == Etats.PAUSE)
            throw new SimulationEnMauvaisEtat();
        
        boucle.interrupt();
        this.vitesse = vitesse;
        boucle.start();
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
    }
    
    public void setDistributionTempsTransitSegment(Distribution distributionTempsTransitSegment) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.distributionTempsTransitSegment = distributionTempsTransitSegment;
    }

    public void setDistributionTempsGenerationVehicule(Distribution distributionTempsGenerationVehicule) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.distributionTempsGenerationVehicule = distributionTempsGenerationVehicule;
    }

    public void setDistributionTempsGenerationPassager(Distribution distributionTempsGenerationPassager) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.distributionTempsGenerationPassager = distributionTempsGenerationPassager;
    }

    public void setNombreJourSimulation(int nombreJourSimulation) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.nombreJourSimulation = nombreJourSimulation;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        if(etat == Etats.ACTION)
            throw new SimulationEnActionException();
        this.heureFin = heureFin;
    }
    
}
