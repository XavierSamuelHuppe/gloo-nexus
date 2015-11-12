package Metier;

import java.util.*;

public abstract class Circuit {
    private String nom;
    private List<Segment> trajet;
    
    public Circuit(){
    }
    
    public Circuit(String nom, List<Segment> segments){
        this.nom = nom;
        this.trajet = segments;
    }
    
    public void SetNom(String nom){
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
    
    public abstract Segment obtenirProchainSegment(Segment dernierSegment);
    
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
