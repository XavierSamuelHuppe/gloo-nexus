package Metier.Profil;

import Metier.Carte.Point;
import Metier.Distribution;
import Metier.Simulation.Simulation;
import java.time.LocalTime;

public class ProfilPassagerFini extends ProfilPassager{
    
    private int nombreMax;
    private int nombreCree;
    
    private LocalTime prochaineGeneration;
    
    public ProfilPassagerFini(int nombreMax, Point point, LocalTime heureDepart, Distribution distribution, Trajet trajet, Simulation simulation){
        super(point, heureDepart, distribution, trajet, simulation);
        this.nombreMax = nombreMax;
    }
    
    public int getNombreMax(){
        return nombreMax;
    }
    public void setNombreMax(int max){
        nombreMax = max;
    }
    
    @Override
    public void avancerGeneration(LocalTime heureCourante, double tempsEcouleParRatioEnSeconde) {
        if(nombreCree == nombreMax)
            return;
        
        if(prochaineGeneration.isBefore(heureCourante)){
            genererPassager();
            nombreCree++;
            prochaineGeneration = heureDebut.plusSeconds((long)(getFrequence() * (double)nombreCree));
        }
    }
    
    @Override
    protected void reInitialiserValeursDepartSimulation() {
        nombreCree = 0;
        prochaineGeneration = heureDebut;
    }
    
    @Override
    public String obtenirDescriptionProfil() {
        return ((Integer)nombreMax).toString() + " p., à partir de " + heureDebut.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
    }
    
}
