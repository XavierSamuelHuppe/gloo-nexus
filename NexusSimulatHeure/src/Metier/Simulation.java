package Metier;
import java.time.*;
import java.util.*;
import Metier.Exceptions.*;

public class Simulation{
    private ParametreSimulation parametres;
    
    private Thread boucleThread;
    private BoucleSimulation boucle;
    
    private Carte carte;
    private List<Circuit> circuits;
    private List<Vehicule> vehicules;
    private List<Source> sources;
    private List<ProfilPassager> profils;
    
    public Simulation(){
        //TODO: devrait éventuelement être injecté quand on se sera penché sur la question
        parametres = new ParametreSimulation();
    }
    
    public ParametreSimulation getParametres(){
        return parametres;
    }
    
    public void demarrer(){
        if(!(parametres.estEnArret()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnAction();
        boucle = new BoucleSimulation(this);
        boucleThread = new Thread(boucle, "boucle de la simulation");
        boucleThread.start();
    }
    
    public void arreter(){
        if(parametres.estEnArret())
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnArret();
        boucleThread.interrupt();
    }
    
    public void pauser(){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnPause();
    }
    
    public void redemarrer(){
        if(!(parametres.estEnPause()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnAction();
    }
            
    public void faireAvancerSimulation(double TempsEcouleParRatioEnSeconde){
        
    }
    
}
