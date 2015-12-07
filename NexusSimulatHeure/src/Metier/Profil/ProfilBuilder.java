
package Metier.Profil;

import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import Metier.Circuit.ConteneurPassagers;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import Metier.Profil.ProfilPassager;
import Metier.Profil.ProfilPassagerFini;
import Metier.Profil.ProfilPassagerHeureFin;
import java.time.LocalTime;


public class ProfilBuilder {
    public ProfilPassager ConstruireProfil(int nombreMax, Point pointDepart, LocalTime heureDebut, Distribution distribution, Trajet trajet, Simulation sim){
        ProfilPassager nouveauProfil = new ProfilPassagerFini(nombreMax , pointDepart, heureDebut, distribution, trajet, sim);
        return nouveauProfil;
    }
    
    public ProfilPassager ConstruireProfil(LocalTime heureFin, Point pointDepart, LocalTime heureDebut, Distribution distribution, Trajet trajet, Simulation sim){
        ProfilPassager nouveauProfil = new ProfilPassagerHeureFin(heureFin , pointDepart, heureDebut , distribution, trajet, sim);
        return nouveauProfil;
    }
}
