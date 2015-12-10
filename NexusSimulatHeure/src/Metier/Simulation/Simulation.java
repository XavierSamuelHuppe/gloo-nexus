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
import java.io.Serializable;
import java.time.temporal.ChronoUnit;

public class Simulation extends Observable implements Serializable{
    private ParametreSimulation parametres;
    
    private transient Thread boucleThread;
    private transient BoucleSimulation boucle;
    
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
            s.retirerTempsGeneration();
        }
        for(ProfilPassager pp: profils){
            pp.retirerTempsGeneration();
        }
        
        vehicules.clear();
        
        //Fermer les statistiques
        //-Sources
        //-Segments ?
        //-ProfilPassagers
        
        initialiserHeureDebut();
        
        setChanged();
        notifyObservers();
    }
    private void initialiserDepartSimulation(){
        JourneeCourante = 1;
        initialiserDepartNouvelleJournee();
    }
    private void initialiserDepartNouvelleJournee(){
        initialiserHeureDebut();
        vehicules.clear();
        carte.initialiserDepartSimulation();
        for(Source s: sources){
            s.reInitialiserValeursDepartSimulation();
            s.pigerDonneesDepartNouvelleJournee();
        }
        for(ProfilPassager pp: profils){
            pp.pigerDonneesDepartNouvelleJournee();
        }
    }
    private void initialiserHeureDebut()
    {
        heureCourante = parametres.getHeureDebut();
    }
    
    public void faireAvancerSimulation(long tempsEcouleParRatioEnNanos, double tempsEcouleParRatioEnSeconde){
        if(!(parametres.estEnAction()))
            throw new SimulationEnMauvaisEtatException();
        
        heureCourante = heureCourante.plusNanos(tempsEcouleParRatioEnNanos);
        
        faireAvancerToutLesVehicules(tempsEcouleParRatioEnSeconde);
        faireAvancerGenerationPassagers(heureCourante, tempsEcouleParRatioEnSeconde);
        faireAvancerCreationVehicule(heureCourante, tempsEcouleParRatioEnSeconde);
        
        
        mettreAJourPassagersADesPoints(tempsEcouleParRatioEnSeconde);
        
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
    
    private void faireAvancerGenerationPassagers(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde){
        for(ProfilPassager pp: profils){
            pp.avancerGeneration(heureCourante, tempsEcouleParRatioEnSeconde);
        }
    }
    
    private void mettreAJourPassagersADesPoints(double tempsEcouleParRatioEnSeconde)
    {
        for(Point p : carte.getPoints())
        {
            /*if(p.obtenirNombrePassagersEnAttente() > 0)
            {
                for(Object px : p.obtenirPassagersEnAttente().toArray())
                {
                    Passager passager = (Passager)px;
                    if(passager.estArriveADestination(p))
                    {
                        passager.comptabiliserTempsAttenteDansProfilPassager();
                        p.retirerPasserDeConteneurPassager(passager);
                    }
                    else
                    {
                        passager.incrementerTempsAttente(tempsEcouleParRatioEnSeconde);
                    }
                }
            }*/
            for(Passager px : p.getPassagersArrives()){
                px.comptabiliserPassagerDansStatistiquesProfilPassager(heureCourante);
            }
            p.viderPassagersArrives();
//            for(Passager px : p.obtenirPassagersEnAttente()){
//                px.incrementerTempsVie(tempsEcouleParRatioEnSeconde);
//            }
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
    
    public void ajouterProfil(ProfilPassager profil){
        profil.getPointDepart().ajouterProfilPassaser(profil);
        profils.add(profil);
    }
    
    public void modifierProfil(ProfilPassager profil, LocalTime heureFin, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        ProfilPassagerHeureFin profilCible = (ProfilPassagerHeureFin) profil;
        profilCible.setHeureFin(heureFin);
        profilCible.setHeureDepart(heureDebut);
        Metier.Distribution dist = new Distribution(distributionMin, distributionMode, distributionMax);
        profilCible.setDistribution(dist);
    }
    
    public void modifierProfil(ProfilPassager profil, int nombreMax, LocalTime heureDebut, double distributionMin, double distributionMode, double distributionMax){
        ProfilPassagerFini profilCible = (ProfilPassagerFini) profil;
        profilCible.setHeureDepart(heureDebut);
        profilCible.setNombreMax(nombreMax);
        Metier.Distribution dist = new Distribution(distributionMin, distributionMode, distributionMax);
        profilCible.setDistribution(dist);
    }
    
    public void retirerProfil(ProfilPassager profil){
        profil.getPointDepart().retirerProfilPassager(profil);
        profils.remove(profil);
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
    
    public List<Trajet> trajetsPassantPar(Segment segment){
        List<Trajet> retour = new ArrayList();
        for(ProfilPassager pp: profils){
            if(pp.getTrajet().utilise(segment)){
                retour.add(pp.getTrajet());
            }
        }
        return retour;
    }

//    public List<Circuit> trajetsPassantPar(Point point){
//        List<Circuit> retour = new ArrayList();
//        for(Circuit c: circuits){
//            if(c.utilise(point)){
//                retour.add(c);
//            }
//        }
//        return retour;
//    }
    
    
    public void retirerPointAvecReferences(Point p){
        List<Source> sourcesAEnlever = p.getSources();
        List<ProfilPassager> profilsAEnlever = p.getProfilsPassagers();
        for(ProfilPassager pp: profilsAEnlever){
            if(pp.estSurPoint(p)){
                profilsAEnlever.add(pp);
            }
        }
        List<Segment> segmentsAEnlever = carte.obtenirSegmentsEntrantEtSortant(p);
        
        List<Circuit> circuitsAEnlever = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(p)){
                circuitsAEnlever.add(c);
                for(Source s: sources){
                    if(s.estSurCircuit(c)){
                        sourcesAEnlever.add(s);
                    }
                }
            }
        }
        sources.removeAll(sourcesAEnlever);
        profils.removeAll(profilsAEnlever);
        carte.retirerSegments(segmentsAEnlever);
        circuits.removeAll(circuitsAEnlever);
        
        carte.retirerPoint(p);
    }
    
        public void retirerSegmentAvecReferences(Segment segment){
        List<Source> sourcesAEnlever = new ArrayList();
        
        List<Circuit> circuitsAEnlever = new ArrayList();
        for(Circuit c: circuits){
            if(c.utilise(segment)){
                circuitsAEnlever.add(c);
                for(Source s: sources){
                    if(s.estSurCircuit(c)){
                        sourcesAEnlever.add(s);

                    }
                }
            }
        }
        sources.removeAll(sourcesAEnlever);
        circuits.removeAll(circuitsAEnlever);
        carte.retirerSegment(segment);
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
    
    public Carte getCarte(){
        return carte;
    }
    
    
    
    
    private Map<Point, Map<Circuit, SortedSet<LocalTime>>> tout;
    
    public void executerInstantanement()
    {
        JourneeCourante = 1;
        while(JourneeCourante <= this.parametres.getNombreJourSimulation())
        {
            initialiserDepartNouvelleJournee();
            
            JourneeCourante = 1;
            
            tout = genererTout();

            debugTout(tout);
                    
            Map<Passager, LocalTime> passagers = genererTousPassagers();
            debugPassagers(passagers);
            for(Passager passagerCourant : passagers.keySet())
            {
                LocalTime heureCourante = passagers.get(passagerCourant);
                
                int etape = 0;
                Trajet trajetCourant = passagerCourant.getTrajet();
                
                boolean passagerRenduADestination = true;
                while(etape < trajetCourant.obtenirNombreEtapes())
                {
                    ElementTrajet etapeCourante = trajetCourant.obtenirEtape(etape);
                    if(tout.containsKey(etapeCourante.getPointMontee()))
                    {
                        Map<Circuit, SortedSet<LocalTime>> circuitsParPoint = tout.get(etapeCourante.getPointMontee());
                        if(circuitsParPoint.containsKey(etapeCourante.getCircuit()))
                        {
                            LocalTime heurePassage = obtenirProchaineHeurePassage(heureCourante, circuitsParPoint.get(etapeCourante.getCircuit()));
                            if(heurePassage != null)
                            {
                                heureCourante = heurePassage;

                                heureCourante = heureCourante.plusSeconds((long)etapeCourante.getCircuit().obtenirTempsTransitTotalSousCircuitEnSecondes(etapeCourante.getPointMontee(), etapeCourante.getPointDescente()));

                                etape += 1;
                                continue;
                            }
                        }
                    }
                    passagerRenduADestination = false;
                    break;
                }
                
                if(passagerRenduADestination)
                {
                    passagerCourant.comptabiliserPassagerDansStatistiquesProfilPassager(heureCourante);
                }
            }
            
            JourneeCourante += 1;
        }
    }
    
    private LocalTime obtenirProchaineHeurePassage(LocalTime heureReference, SortedSet<LocalTime> heures)
    {
        //Derp.
        LinkedList listeHeures = new LinkedList(heures);
        int i = 0;
        while(i < listeHeures.size() - 1)
        {
            if(listeHeures.get(i) == heureReference)
                return (LocalTime)listeHeures.get(i);
            if(heureReference.isAfter((LocalTime)listeHeures.get(i)) && heureReference.isBefore((LocalTime)listeHeures.get(i+1)))
                return (LocalTime)listeHeures.get(i+1);
            i++;
        }
        
        if (((LocalTime)listeHeures.getLast()) == heureReference || ((LocalTime)listeHeures.getLast()).isAfter(heureReference))
            return (LocalTime)listeHeures.getLast();
        
        return null;
    }
    
    
    
    private Map<Vehicule, LocalTime> genererTousVehicules()
    {
        Map<Vehicule, LocalTime> vehicules = new HashMap<Vehicule, LocalTime>();
        for(Source s : sources)
        {
            vehicules.putAll(s.genererTousVehiculesAvecMoment());
        }
        return vehicules;
    }
    
    private Map<Passager, LocalTime> genererTousPassagers()
    {
        Map<Passager, LocalTime> passagers = new HashMap<Passager, LocalTime>();
        for(ProfilPassager pp : profils)
        {
            passagers.putAll(pp.genererTousPassagersAvecMoment());
        }
        return passagers;
    }
        
    private Map<Point, Map<Circuit, SortedSet<LocalTime>>> genererTout()
    {
        Map<Vehicule, LocalTime> vehicules = genererTousVehicules();
        //vehicules.forEach((v,t) -> System.out.println(v.toString() + " " + t.toString()));

        Map<Point, Map<Circuit, SortedSet<LocalTime>>> tout = new HashMap<Point, Map<Circuit, SortedSet<LocalTime>>>();

        for(Vehicule v : vehicules.keySet())
        {
            Map<Point, List<LocalTime>> passages = v.obtenirPointsEtHeuresDePassage(vehicules.get(v), this.parametres.getHeureDebut(), this.parametres.getHeureFin());
            for(Point p : passages.keySet())
            {
                if(!tout.containsKey(p))
                {
                    tout.put(p, new HashMap<Circuit, SortedSet<LocalTime>>());
                }

                if(!tout.get(p).containsKey(v.getCircuit()))
                {
                    tout.get(p).put(v.getCircuit(), new TreeSet<LocalTime>());
                }

                tout.get(p).get(v.getCircuit()).addAll(passages.get(p));
            }
        }
        
        return tout;
    }
    
    private void debugTout(Map<Point, Map<Circuit, SortedSet<LocalTime>>> tout)
    {
        LinkedList<Point> points = new LinkedList<Point>(tout.keySet());
        Collections.sort(points, new Comparator<Point>()
                 {
                     public int compare(Point f1, Point f2)
                     {
                         return f1.toString().compareTo(f2.toString());
                     }        
                 });
        for(Point p : points)
        {
            System.out.println(p.toString());
            LinkedList<Circuit> circuits = new LinkedList<Circuit>(tout.get(p).keySet());
            Collections.sort(circuits, new Comparator<Circuit>()
                     {
                         public int compare(Circuit f1, Circuit f2)
                         {
                             return f1.toString().compareTo(f2.toString());
                         }        
                     });
            for(Circuit c : circuits)
            {
                System.out.println("\t" + c.toString());
                //LinkedList<LocalTime> temps = new LinkedList<LocalTime>(tout.get(p).get(c));
                //Collections.sort(temps);
                
                int i = 0;
                for(LocalTime lt : tout.get(p).get(c))
                {
                    if(i == 0)
                    {
                        System.out.print("\t\t" + lt.toString());
                    }
                    else if (i == 10)
                    {
                        System.out.println(" " + lt.toString());
                        i = -1;
                    }
                    else
                    {
                        System.out.print(" " + lt.toString());
                    }
                    i++;
                }
                System.out.println("");
            }
        }
    }
    
    private void debugPassagers(Map<Passager, LocalTime> passagers)
    {
        LinkedList<Passager> passagersTri = new LinkedList<>(passagers.keySet());
        Collections.sort(passagersTri, new Comparator<Passager>()
                     {
                         public int compare(Passager f1, Passager f2)
                         {
                             return f1.toString().compareTo(f2.toString());
                         }        
                     });
        for(Passager passagerCourant : passagersTri)
        {
            System.out.println(passagers.get(passagerCourant).toString() + " : " + passagerCourant.toString());
        }
    }

    public boolean heureEstPassee(LocalTime heureActuelle, LocalTime heureVerifiee)
    {
        LocalTime heureDebutSimulation = this.getParametres().getHeureDebut();
        
        //Après minuit.
        if(heureVerifiee.isBefore(heureDebutSimulation)){
            //Après minuit.
            if(heureActuelle.isBefore(heureDebutSimulation))
            {
                return (heureActuelle.isAfter(heureVerifiee));
            }
            else
            {
                return false;
            }
            
        }
        else   
        {
            return (heureActuelle.isAfter(heureVerifiee));
        }
    }

}
