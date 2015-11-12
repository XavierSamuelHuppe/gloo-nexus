package Metier.Source;

import Metier.Circuit.Circuit;
import Metier.Carte.Point;
import Metier.Distribution;

public class Source {
    
    
    private int frequence;
    private int heureDebut;
    private Point pointDepart;
    private Circuit circuit;
    
    private Distribution distribution;
    public Source(Point pointDepart, int heureDebut, int frequence, Distribution distribution){
        this.frequence = frequence;
        this.heureDebut = heureDebut;
        this.pointDepart = pointDepart;
        this.distribution = distribution;
    }
    
    public Point getPointDepart(){
        return pointDepart;
    }
    public int getheureDebut(){
        return heureDebut;
    }
    public int getFrequence(){
        return frequence;
    }
    public Circuit getCircuit(){
        return circuit;
    }
    public void setPointDepart(Point point){
        pointDepart = point;
    }
    public void setheureDebut(int heureD){
        heureDebut = heureD;
    }
    public void setFrequence(int freq){
        frequence = freq;
    }
    
    public void pigerDonneesDepart()
    {
        //jajajajajaja
    }/*  décommenter quand on va travailler la dessus, je sais pas trop quel genre de Mouseevent il veut. 
    public Vehicule genererVehicule()
    {
        Vehicule unChar = new Vehicule();
        return unChar;
    }*/
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Source))return false;
        Source autreSource = (Source)o;
        return (memeFrequence(autreSource) &&
                memeCircuit(autreSource) &&
                memePoint(autreSource));
    }
    
    private boolean memeFrequence(Source autreSource){
        return frequence == autreSource.getFrequence();
    }
    private boolean memeCircuit(Source autreSource){
        return circuit.equals(autreSource.getCircuit());
    }
    private boolean memePoint(Source autreSource){
        return pointDepart.equals(autreSource.getPointDepart());
    }
}
