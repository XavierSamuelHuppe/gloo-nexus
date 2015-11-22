package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;

public class ProfilPassagerFini extends ProfilPassager{
    
    private int nombreMax;
    
    public ProfilPassagerFini(int nombreMax, Point point, double heureDepart, double frequence, Distribution distribution){
        super(point, heureDepart, frequence, distribution);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
}
