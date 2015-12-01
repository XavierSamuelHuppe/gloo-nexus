package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class ProfilPassagerFini extends ProfilPassager{
    
    private int nombreMax;
    
    public ProfilPassagerFini(int nombreMax, Point point, LocalTime heureDepart, Distribution distribution, Simulation simulation){
        super(point, heureDepart, distribution, simulation);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
    
}
