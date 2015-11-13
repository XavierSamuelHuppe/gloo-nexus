package Metier.Carte;

import Metier.Circuit.ConteneurPassagers;
import Metier.Profil.ProfilPassager;
import Metier.Source.Source;
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
    
    public void ajouterProfilPassaser(ProfilPassager profil) {
        this.profilsPassagers.add(profil);
    }
    
    public void retirerProfilPassager(ProfilPassager profil) {
        this.profilsPassagers.remove(profil);
    }
    
    public void ajouterSource(Source source) {
        this.sources.add(source);
    }
    
    public void retirerProfilPassager(Source source) {
        this.sources.remove(source);
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Point))return false;
        Point autrePoint = (Point)o;
        return position.equals(autrePoint.getCoordonee());
    }
}
