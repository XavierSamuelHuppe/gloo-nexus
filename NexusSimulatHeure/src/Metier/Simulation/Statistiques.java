package Metier.Simulation;

import Metier.Profil.Passager;

public class Statistiques {
    private double tempsMoyen = 0;
    private double tempsMinimal = 0;
    private double tempsMaximal = 0;
    
    private int nbElements = 0;
    private double tempsTotal = 0;
    
    public void ajouterDonnee(double temps) {
        System.out.println("Nouvelle stat : " + ((Double)temps).toString());
        nbElements += 1;
        tempsTotal += temps;
        
        if(temps < tempsMinimal)
            tempsMinimal = temps;
        
        if(temps > tempsMaximal)
            tempsMaximal = temps;
        
        tempsMoyen = (double)(tempsTotal / nbElements);
    }
    
    public double getTempsMoyen()
    {
        return tempsMoyen;
    }
    
    public double tempsMinimal()
    {
        return tempsMinimal;
    }
    
    public double tempsMaximal()
    {
        return tempsMaximal;
    }
}
