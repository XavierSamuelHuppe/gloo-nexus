package Controleur;

import Metier.*;

public class Simulateur {
    
    private Simulation simulation;
    
    public Simulateur(){
    }
    
    public Simulateur(Simulation simulation){
        this.simulation = simulation;
    }
    
    public Point ajouterPoint(Metier.Position pos, String nom){
        Point metierPoint = new Metier.Point(nom, new ConteneurPassagersIllimite());
        metierPoint.setPosition(pos);
        metierPoint.setNom("LaLiLuLeLo");
        return metierPoint;
    }
    
    public void modifierPoint(Metier.Point pointCible, Metier.Position pos, String nom){
        pointCible.setPosition(pos);
        pointCible.setNom(nom);
    }
    
    public void arreter(){
        simulation.arreter();
    }
    
    public void demarerRedemarer(){
        if(simulation.estEnPause())
            simulation.redemarrer();
        else
            simulation.demarrer();
    }
    
    public void pauser(){
        simulation.pauser();
    }
    
    public void modfierVitesse(int pourcentage){
        simulation.modifierVitesse(pourcentage);
    }
    
    public void modfierFramerate(int pourcentage){
        simulation.modifierFramerate(pourcentage);
    }
}
