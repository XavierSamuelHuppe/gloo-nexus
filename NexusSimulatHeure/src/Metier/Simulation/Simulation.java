package Metier.Simulation;
import Metier.Carte.*;
import Metier.Circuit.Circuit;
import java.time.*;
import java.util.*;
import Metier.*;
import Metier.Exceptions.*;
import Metier.Profil.*;
import Metier.Source.*;
import Metier.Circuit.Vehicule;
import Metier.Distribution;

public class Simulation extends Observable{
    private ParametreSimulation parametres;
    
    private Thread boucleThread;
    private BoucleSimulation boucle;
    private LocalTime heureCourante;
    private int JourneeCourante;
    
    private Carte carte;
    private List<Circuit> circuits;
    private List<Vehicule> vehicules;
    private List<Source> sources;
    private List<ProfilPassager> profils;
    
    public Simulation(Carte carte){
        parametres = new ParametreSimulation();
        this.carte = carte;
        
        this.circuits = new ArrayList<Circuit>();
        this.vehicules = new ArrayList<Vehicule>();
        this.sources = new ArrayList<Source>();
        this.profils = new ArrayList<ProfilPassager>();
    }
    
    public ParametreSimulation getParametres(){
        return parametres;
    }
    
    public void demarrer(){
        if(!(parametres.estEnArret() || parametres.estAvantDemarrage()))
            throw new SimulationEnMauvaisEtatException();
        
        initialiserDepartSimulation();
        parametres.mettreEnAction();
        boucle = new BoucleSimulation(this);
        boucleThread = new Thread(boucle, "boucle de la simulation");
        boucleThread.start();
        setChanged();
        notifyObservers();
    }
    
    public void arreter(){
        if(parametres.estEnArret())
            throw new SimulationEnMauvaisEtatException();
        
        terminerSimulation();
        parametres.mettreEnAvantDemarrage();
        boucle.stop();
        boucleThread.interrupt();
        setChanged();
        notifyObservers();
    }
    public void pauser(){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnPause();
        setChanged();
        notifyObservers();
    }
    public void redemarrer(){
        if(!(parametres.estEnPause()))
            throw new SimulationEnMauvaisEtatException();
        
        parametres.mettreEnAction();
        setChanged();
        notifyObservers();
    }
            
    private void terminerSimulation(){
        carte.terminerSimulation();
        
        for(Source s: sources){
            s.rafraichirDonneesDepart();
        }
        
        vehicules.clear();
        
        //+ dist profils
        
        //Fermer les statistiques
        setChanged();
        notifyObservers();
    }
    private void initialiserDepartSimulation(){
        JourneeCourante = 1;
        initialiserDepartNouvelleJournee();
    }
    private void initialiserDepartNouvelleJournee(){
        heureCourante = parametres.getHeureDebut();
        carte.initialiserDepartSimulation();
        for(Source s: sources){
            s.pigerDonneesDepart();
        }
        //+ dist profils
    }
    
    
    public void faireAvancerSimulation(long tempsEcouleParRatioEnNanos, double tempsEcouleParRatioEnSeconde){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        heureCourante = heureCourante.plusNanos(tempsEcouleParRatioEnNanos);
        
        faireAvancerToutLesVehicules(tempsEcouleParRatioEnSeconde);
        faireAvancerCreationVehicule(heureCourante, tempsEcouleParRatioEnSeconde);
        //faire spawner les gens
        
        if(!doitContinuerJournee()){
            if(derniereJournee())
                arreter();
            else{
                JourneeCourante += 1;
                initialiserDepartNouvelleJournee();
            }
        }
        setChanged();
        notifyObservers();
    }
    private boolean doitContinuerJournee(){
        LocalTime heureFin = parametres.getHeureFin();
        LocalTime heureDebutNouvelleJournee = ParametreSimulation.HEURE_DEBUT_NOUVELLE_JOURNEE;
        LocalTime minuit = LocalTime.MIDNIGHT;
        LocalTime justeAvantMinuit = LocalTime.MAX;
        if(heureFin.isBefore(justeAvantMinuit) && heureFin.isAfter(heureDebutNouvelleJournee)){
            return heureCourante.isBefore(heureFin);
        }else if(heureFin.isAfter(minuit) && heureFin.isBefore(heureDebutNouvelleJournee)){
            if(heureCourante.isBefore(justeAvantMinuit) && heureCourante.isAfter(heureDebutNouvelleJournee))
                return true;
            else
                return heureCourante.isBefore(heureFin);
        }
        throw new DateTimeException("Les paramètres de la simulation ne sont pas bien réglés.");
    }
    
    private boolean derniereJournee(){
        return JourneeCourante == parametres.getNombreJourSimulation();
    }
    
    private void faireAvancerToutLesVehicules(double tempsEcouleParRatioEnSeconde){
        List<Vehicule> vehiculesAEnlever = new ArrayList();
        for(Vehicule v: vehicules){
            try{
                v.avancer(tempsEcouleParRatioEnSeconde);
            }catch(FinDeCircuitException e){
                vehiculesAEnlever.add(v);
            }
        }
        vehicules.removeAll(vehiculesAEnlever);
    }
    
    private void faireAvancerCreationVehicule(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde){
        for(Source s: sources){
            s.avancerCreation(heureCourante, tempsEcouleParRatioEnSeconde);
        }
    }
    
    
    public void ajouterCircuit(Circuit circuit){
        circuits.add(circuit);
    }
    
    public void modifierCircuit(Circuit circuit, String nom)
    {
        circuit.setNom(nom);
    }
    
    public void retirerCircuit(Circuit circuit){
        circuits.remove(circuit);
    }
    
    public void ajouterSource(Source source){
        source.getPointDepart().ajouterSource(source);
        sources.add(source);
    }
    
    public void modifierSource(Source source, LocalTime heureFin, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax)
    {
        SourceHeureFin sourceCible = (SourceHeureFin)source;
        sourceCible.setheureFin(heureFin);
        sourceCible.setheureDebut(heureDebut);
        sourceCible.setCircuit(circuit);
        Metier.Distribution dist = new Distribution(distributionMin, distributionMode, distributionMax);
        sourceCible.setDistribution(dist);
    }
        
    public void modifierSource(Source source, int nombreMax, LocalTime heureDebut, Circuit circuit, double distributionMin, double distributionMode, double distributionMax)
    {
        SourceFinie sourceCible = (SourceFinie)source;
        sourceCible.setNombreMax(nombreMax);
        sourceCible.setheureDebut(heureDebut);
        sourceCible.setCircuit(circuit);
        Metier.Distribution dist = new Distribution(distributionMin, distributionMode, distributionMax);
        sourceCible.setDistribution(dist);
    }
    
    public void retirerSource(Source source){
        source.getPointDepart().retirerSource(source);
        sources.remove(source);
    }
    
    public List<Circuit> circuitsPassantPar(Segment segment){
        List<Circuit> retour = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(segment)){
                retour.add(c);
            }
        }
        return retour;
    }

    public List<Circuit> circuitsPassantPar(Point point){
        List<Circuit> retour = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(point)){
                retour.add(c);
            }
        }
        return retour;
    }
    
    public void retirerPointAvecReferences(Point p){
        List<Source> sourcesAEnlever = p.getSources();
        List<ProfilPassager> profilsAEnlever = p.getProfilsPassagers();
        List<Segment> segmentsAEnlever = carte.obtenirSegmentsEntrantEtSortant(p);
        
        List<Circuit> circuitsAEnlever = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(p)){
                circuitsAEnlever.add(c);
            }
        }
        sources.removeAll(sourcesAEnlever);
        profils.removeAll(profilsAEnlever);
        carte.retirerSegments(segmentsAEnlever);
        circuits.removeAll(circuitsAEnlever);
        
        carte.retirerPoint(p);
    }
    
    public void retirerSegmentAvecReferences(Segment s){
        List<Source> sourcesAEnlever = new ArrayList();
        
        List<Circuit> circuitsAEnlever = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(s)){
                circuitsAEnlever.add(c);
            }
        }
        sources.removeAll(sourcesAEnlever);
        circuits.removeAll(circuitsAEnlever);
        
        carte.retirerSegment(s);
    }
    
    public List<SituationVehicule> obtenirSituationsVehicules()
    {
        ArrayList<SituationVehicule> situations = new ArrayList<SituationVehicule>();
        for(Vehicule v : vehicules)
        {
            situations.add(v.obtenirSituation());
        }
        return situations;
    }
    
    public LocalTime getHeureCourante()
    {
        return this.heureCourante;
    }
    
    public int getJourneeCourante()
    {
        return this.JourneeCourante;
    }
    
    public int getNombreJourSimulation()
    {
        return parametres.getNombreJourSimulation();
    }
    
    public void ajouterVehicule(Vehicule v)
    {
        vehicules.add(v);
    }
    
}
