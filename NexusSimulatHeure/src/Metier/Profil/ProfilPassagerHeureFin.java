package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class ProfilPassagerHeureFin extends ProfilPassager {
    
    private LocalTime heureFin;
    
    public ProfilPassagerHeureFin(LocalTime heureFin, Point point, LocalTime heureDepart, Distribution distribution, Simulation simulation){
        super(point, heureDepart, distribution, simulation);
        this.heureFin = heureFin;
    }
    
    public LocalTime getHeureFin(){
        return heureFin;
    }
    public void setHeureFin(LocalTime Fin){
        heureFin = Fin;
    }
    @Override
    public String obtenirDescriptionProfil() {
        return heureDepart.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE) + " à " + heureFin.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }
}
