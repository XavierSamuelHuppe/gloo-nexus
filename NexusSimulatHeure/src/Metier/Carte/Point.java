package Metier.Carte;

import Metier.Circuit.*;
import Metier.Profil.Passager;
import Metier.Profil.ProfilPassager;
import Metier.Source.Source;
import java.io.Serializable;
import java.util.*;

public class Point extends Observable implements Serializable{
    private String nom;
    private Position position;
    private List<ProfilPassager> profilsPassagers;
    private List<Source> sources;
    private ConteneurPassagers passagers;
    private List<Passager> passagersArrives;
    private boolean existe;
    private boolean estArret;
    
    public Point(ConteneurPassagers passagers, boolean estArret){
        this.passagers = passagers;
        this.estArret = estArret;
        profilsPassagers = new ArrayList();
        sources = new ArrayList();
        passagersArrives = new ArrayList();
        existe = true;
    }

    public List<Passager> getPassagersArrives() {
        return passagersArrives;
    }
    public void viderPassagersArrives(){
        passagersArrives.clear();
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
    
    public void retirerSource(Source source) {
        this.sources.remove(source);
    }
    
    public void retirerProfilPassager(Source source) {
        this.sources.remove(source);
    }
    
    public void faireDescendreAuPoint(List<Passager> passagers){
        for(Passager p : passagers){
            if(p.estArriveADestination(this))
                passagersArrives.add(p);
            else
                this.passagers.octroyer(p);
        }
    }
    public void faireArriverNouveauPassager(Passager passager){
        this.passagers.octroyer(passager);
    }
    
    public int obtenirNombrePassagersEnAttente()
    {
        return this.passagers.obtenirNombrePassagers();
    }
    
    public List<Passager> faireMonterEnVehicule(Circuit circuit){
        return passagers.embarquer(circuit);
    }
    
    public List<Passager> obtenirPassagersEnAttente()
    {
        return passagers.obtenirPassagers();
    }
    
    public List<Source> getSources(){
        return this.sources;
    }
    
    public List<ProfilPassager> getProfilsPassagers(){
        return this.profilsPassagers;
    }
    
    public void setEstArret(boolean estArret){
        this.estArret = estArret;
    }
    
    public boolean estIntersection(){
        return !this.estArret;
    }
    
    public boolean estArret(){
        return this.estArret;
    }
    
    public void viderConteneurPassager(){
        passagers.vider();
    }
    
    public void retirerPasserDeConteneurPassager(Passager passager){
        passagers.retirer(passager);
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
