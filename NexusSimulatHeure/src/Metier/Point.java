package Metier;

public class Point {
    private String nom;
    private Position coordonee;
    
    public Point(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Position getCoordonee() {
        return coordonee;
    }
    
    public void setCoordonee(Position coordonee) {
        this.coordonee = coordonee;
    }
    
}
