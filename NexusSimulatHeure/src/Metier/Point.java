package Metier;

import java.util.*;

public class Point {
    private String nom;
    private Position position;
    private List<ProfilPassager> profilsPassagers;
    private List<Source> sources;
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
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
}
