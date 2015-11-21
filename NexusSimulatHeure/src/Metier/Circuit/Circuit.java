package Metier.Circuit;

import Metier.Carte.*;
import java.util.*;

public abstract class Circuit {
    protected String nom;
    protected List<Segment> trajet;
    
    public Circuit(){
    }
    
    public Circuit(String nom, List<Segment> segments){
        this.nom = nom;
        appliquerCircuit(segments);
    }
    
    private void appliquerCircuit(List<Segment> segments){
        trajet = new ArrayList();
        for(Segment s : segments){
            trajet.add(s);
        }
    }
    
    public void setNom(String nom){
        this.nom = nom;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public int longueurTrajet(){
        return trajet.size();
    }
    
    public List<Segment> getTraget(){
        return trajet;
    }
    
    public void setTrajet(List<Segment> trajet){
        this.trajet = trajet;
    }
    
    public boolean utilise(Segment s){
        return trajet.contains(s);
    }
    
    // TODO rencontre: Explain witchcraft to team
    public boolean utilise(Point p){
        return trajet.stream().anyMatch((s) -> (s.getPointArrivee() == p || s.getPointDepart() == p));
    }
    
    public abstract Segment obtenirProchainSegment(Segment dernierSegment);
    
    public abstract Segment obtenirProchainSegment(Point pointDepart);
    
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
        if(longueurTrajet() != autreCircuit.longueurTrajet())
            return false;
        
        for(int i = trajet.size(); i >= 0; i--){
            if(trajet.get(i) != autreCircuit.getTraget().get(i))
                return false;
        }
        return true;
    }
}
