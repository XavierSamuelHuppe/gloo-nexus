package Metier.Source;

import Metier.Carte.Point;
import Metier.Distribution;

public class SourceHeureFin extends Source {
    private int heureFin;
    
    public SourceHeureFin(Point point, int heureDepart, int heureFin, int frequence, Distribution distribution){
        super(point, heureDepart, frequence, distribution);
        this.heureFin = heureFin;
    }
    
    public int getHeureFin(){
        return heureFin;
    }
    public void setHeureFin(int Fin){
        heureFin = Fin;
    }
}
