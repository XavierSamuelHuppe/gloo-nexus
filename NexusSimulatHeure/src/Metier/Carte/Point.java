package Metier.Carte;

import Metier.Circuit.*;
import Metier.Passager;
import Metier.Profil.ProfilPassager;
import Metier.Source.Source;
import java.util.*;

public class Point extends Observable{
    private String nom;
    private Position position;
    private List<ProfilPassager> profilsPassagers;
    private List<Source> sources;
    private ConteneurPassagers passagers;
    
    public Point(ConteneurPassagers passagers){
        this.passagers = passagers;
        profilsPassagers = new ArrayList();
        sources = new ArrayList();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        notifyObservers();
    }

    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        notifyObservers();
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
    
    public void faireDescendreAuPoint(List<Passager> passagers){
        this.passagers.octroyer(passagers);
    }
    
    public List<Passager> faireMonterEnVehicule(Circuit circuit){
        return passagers.embarquer(circuit);
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Point))return false;
        Point autrePoint = (Point)o;
        return position.equals(autrePoint.getPosition());
    }
}
