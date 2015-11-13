package Metier.Simulation;
import Metier.Carte.*;
import Metier.Circuit.Circuit;
import java.time.*;
import java.util.*;
import Metier.Exceptions.*;
import Metier.Profil.*;
import Metier.Source.*;
import Metier.Circuit.Vehicule;
import Metier.DistributionTriangulaire;

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
        //ré-init les données de la simulation
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
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        //faire avancer les données de la simulation (heureCourant, journée courante
        //faire avancer les vehicules
        //faire spawner les vehicules
        //faire spawner les gens
    }
    
    public void ajouterCircuit(Circuit circuit){
        circuits.add(circuit);
    }
    
    public void retirerCircuit(Circuit circuit){
        circuits.remove(circuit);
    }
    
    public void modifierSourceHeureFin(Metier.Source.SourceHeureFin sourceHeureFin, double heureFin ,Point pointDepart, double heureDebut, double frequence){
        /*
        int index = profils.indexOf(sourceHeureFin);
        SourceHeureFin sourceAChanger = sources.get(index);
        
        sourceAChanger.setFrequence(frequence);
        sourceAChanger.setheureDebut(heureDebut);
        sourceAChanger.setPointDepart(pointDepart);
        sourceAChanger.setheureFin(heureFin);
        */
    }
    public void modifierSourceFinie(Metier.Source.SourceFinie sourceFinie, int nombreMax ,Point pointDepart, double heureDebut, double frequence){
        /*
        int index = profils.indexOf(sourceFinie);
        SourceFinie sourceAChanger = sources.get(index);
        
        sourceAChanger.setFrequence(frequence);
        sourceAChanger.setheureDebut(heureDebut);
        sourceAChanger.setPointDepart(pointDepart);
        sourceAChanger.setNombreMax(nombreMax);*/
    }
    
    public void modifierProfilPassagerFini(Metier.Profil.ProfilPassagerFini profilPassagerFini, int nombreMax, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        /*
        int index = profils.indexOf(profilPassagerFini);
        ProfilPassagerFini profilAChanger = profils.get(index);
        
        profilAChanger.setFrequence(frequence);
        profilAChanger.setHeureDepart(heureDepart);
        profilAChanger.setPointDepart(point);
        profilAChanger.setNombreMax(nombreMax);
        */
    }
    
    public void modifierProfilPassagerHeureFin(Metier.Profil.ProfilPassagerHeureFin profilPassagerHeureFin, double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        /*
        int index = profils.indexOf(profilPassagerHeureFin);
        ProfilPassagerHeureFin profilAChanger = profils.get(index);
        
        profilAChanger.setFrequence(frequence);
        profilAChanger.setHeureDepart(heureDepart);
        profilAChanger.setPointDepart(point);
        profilAChanger.setHeureFin(heureFin);*/
        
    }
    
    public void retirerSourceHeureFin(Metier.Source.SourceHeureFin sourceHeureFin){
        sources.remove(sourceHeureFin);
    }
    
    public void retirerSourceFinie(Metier.Source.SourceFinie sourceFinie){
        sources.remove(sourceFinie);
    }
    
    public void retirerProfilPassagerFini(Metier.Profil.ProfilPassagerFini profilPassagerFini){
        profils.remove(profilPassagerFini);
    }
    
    public void retirerProfilPassagerHeureFin(Metier.Profil.ProfilPassagerHeureFin profilPassagerHeureFin){
        profils.remove(profilPassagerHeureFin);
    }
    
    public void ajouterSourceHeureFin(double heureFin ,Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution){
        SourceHeureFin nouveauSourceHeureFin = new SourceHeureFin(heureFin , pointDepart, heureDebut, frequence, distribution);
        sources.add(nouveauSourceHeureFin);
    }
    
    public void ajouterSourceFinie(int nombreMax ,Point pointDepart, double heureDebut, double frequence, DistributionTriangulaire distribution){
        SourceFinie nouveauSourceFinie = new SourceFinie(nombreMax , pointDepart, heureDebut, frequence, distribution);
        sources.add(nouveauSourceFinie);
    }
    
    public void ajouterProfilPassagerFini(int nombreMax, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        ProfilPassagerFini nouveauProfilPassagerFini = new ProfilPassagerFini(nombreMax, point, heureDepart, frequence, distribution);
        profils.add(nouveauProfilPassagerFini);
    }
    
    public void ajouterProfilPassagerHeureFin(double heureFin, Point point, double heureDepart, double frequence, DistributionTriangulaire distribution){
        ProfilPassagerHeureFin nouveauProfilPassagerHeureFin = new ProfilPassagerHeureFin(heureFin, point, heureDepart, frequence, distribution);
        profils.add(nouveauProfilPassagerHeureFin);
    }
    
    
    
    
    
    
}
