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
    
    public abstract Segment obtenirProchainSegment(Segment dernierSegment);
}
