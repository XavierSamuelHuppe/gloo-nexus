package Metier;

public class Point {
    private String nom;
    private Position coordonee;
    //private List<ProfilsPassagers> profilsPassagers;
    //private List<Source> sources;
    private ConteneurPassagers passagers;
    
    public Point(String nom, ConteneurPassagers passagers) {
        this.nom = nom;
        this.passagers = passagers;
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
    
    public void changerPosition(Position coordonee) {
        this.coordonee = coordonee;
    }
    
}
