package Metier.Circuit;

import Metier.Carte.*;
import java.io.Serializable;
import java.util.*;

public abstract class Circuit implements Serializable {
    protected String nom;
    protected List<Segment> parcours;
    
    public Circuit(){
    }
    
    public Circuit(String nom, List<Segment> segments){
        this.nom = nom;
        appliquerCircuit(segments);
    }
    
    private void appliquerCircuit(List<Segment> segments){
        parcours = new ArrayList();
        for(Segment s : segments){
            parcours.add(s);
        }
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public int longueurParcours(){
        return parcours.size();
    }
    
    public List<Segment> getTraget(){
        return parcours;
    }
    
    public void setTrajet(List<Segment> trajet){
        this.parcours = trajet;
    }
    
    public boolean utilise(Segment s){
        return parcours.contains(s);
    }
    
    public boolean utilise(Point pointCible, Point entreDebut, Point entreFin)
    {
        return obtenirSousCircuit(entreDebut, entreFin).stream().anyMatch((s)-> (s.getPointDepart() == pointCible || s.getPointArrivee() == pointCible));
    }
    
    public boolean utilise(Segment segmentCible, Point entreDebut, Point entreFin)
    {
        return obtenirSousCircuit(entreDebut, entreFin).contains(segmentCible);
    }
    
    public List<Segment> obtenirSousCircuit(Point entreDebut, Point entreFin)
    {
        LinkedList<Segment> sousCircuit = new LinkedList<>();
        boolean ajouter = false;
        for(Segment s : parcours)
        {
            if(!ajouter)
            {
                if(s.getPointDepart() == entreDebut)
                {
                    sousCircuit.push(s);
                    ajouter = true;
                }
            }
            else
            {
                sousCircuit.push(s);
                if(s.getPointArrivee()==entreFin)
                {
                    ajouter = false;
                    break;
                }
            }
        }
        return sousCircuit;
    }
    
    
    // TODO rencontre: Explain witchcraft to team
    public boolean utilise(Point p){
        return parcours.stream().anyMatch((s) -> (s.getPointArrivee() == p || s.getPointDepart() == p));
    }
    
    public abstract Segment obtenirProchainSegment(Segment dernierSegment);
    
    public abstract Segment obtenirProchainSegment(Point pointDepart);
    
    public double obtenirTempsTransitTotalSousCircuitEnSecondes(Point d, Point a)
    {
        return this.obtenirSousCircuit(d, a).stream().mapToDouble(s -> s.getTempsTransit()).sum();
    }
    
    @Override
    public String toString(){
        return this.nom;
    }
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Circuit))return false;
        Circuit autreCircuit = (Circuit)o;
        if(longueurParcours() != autreCircuit.longueurParcours())
            return false;
        
        for(int i = parcours.size() - 1; i >= 0; i--){
            if(parcours.get(i) != autreCircuit.getTraget().get(i))
                return false;
        }
        return true;
    }
}
