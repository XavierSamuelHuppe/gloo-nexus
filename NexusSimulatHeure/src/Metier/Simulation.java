package Metier;
import java.time.*;
import Metier.Exceptions.*;

public class Simulation {
    private boolean simulationActive;
    private Distribution distributionTempsTransitSegment;
    private Distribution distributionTempsGenerationVehicule;
    private Distribution distributionTempsGenerationPassager;
    private int nombreJourSimulation;
    private int jouEnCours;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    
    private static Distribution DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT = new DistributionTriangulaire(300, 300, 300);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT = new DistributionTriangulaire(900, 900, 900);
    private static Distribution DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT = new DistributionTriangulaire(1, 1, 1);
    private static int NOMBRE_JOUR_SIMULATION_DEFAUT = 1;
    private static LocalTime HEURE_DEBUT_DEFAUT = LocalTime.of(8,0);
    private static LocalTime HEURE_FIN_DEFAUT = LocalTime.of(17,0);
    
    public Simulation(){
        initialiserValeursParDefaut();
    }
    
    public void initialiserValeursParDefaut(){
        simulationActive = false;
        distributionTempsTransitSegment = DISTRIBUTION_TEMPS_TRANSIT_SEGMENT_DEFAUT;
        distributionTempsGenerationVehicule = DISTRIBUTION_TEMPS_GENERATION_VEHICULE_DEFAUT;
        distributionTempsGenerationPassager = DISTRIBUTION_TEMPS_GENERATION_PASSAGER_DEFAUT;
        nombreJourSimulation = NOMBRE_JOUR_SIMULATION_DEFAUT;
        heureDebut = HEURE_DEBUT_DEFAUT;
        heureFin = HEURE_FIN_DEFAUT;
    }

    public void setDistributionTempsTransitSegment(Distribution distributionTempsTransitSegment) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.distributionTempsTransitSegment = distributionTempsTransitSegment;
    }

    public void setDistributionTempsGenerationVehicule(Distribution distributionTempsGenerationVehicule) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.distributionTempsGenerationVehicule = distributionTempsGenerationVehicule;
    }

    public void setDistributionTempsGenerationPassager(Distribution distributionTempsGenerationPassager) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.distributionTempsGenerationPassager = distributionTempsGenerationPassager;
    }

    public void setNombreJourSimulation(int nombreJourSimulation) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.nombreJourSimulation = nombreJourSimulation;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        if(simulationActive)
            throw new SimulationEnActionException();
        this.heureFin = heureFin;
    }
    
    
}
