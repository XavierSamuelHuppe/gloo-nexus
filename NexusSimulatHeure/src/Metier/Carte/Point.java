package Metier.Carte;

import Metier.Circuit.*;
import Metier.Profil.Passager;
import Metier.Profil.ProfilPassager;
import Metier.Source.Source;
import java.util.*;

public class Point extends Observable{
    private String nom;
    private Position position;
    private List<ProfilPassager> profilsPassagers;
    private List<Source> sources;
    private ConteneurPassagers passagers;
    private boolean existe;
    
    public Point(ConteneurPassagers passagers){
        this.passagers = passagers;
        profilsPassagers = new ArrayList();
        sources = new ArrayList();
        existe = true;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        this.setChanged();
        notifyObservers();
    }

    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        this.setChanged();
        notifyObservers();
    }
    
    public void tuer(){
        existe = false;
        this.setChanged();
        notifyObservers();
    }
    public boolean mort(){
        return !existe;
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
    
    public List<Source> getSources()
    {
        return this.sources;
    }
    public List<ProfilPassager> getProfilsPassagers(){
        return this.profilsPassagers;
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
