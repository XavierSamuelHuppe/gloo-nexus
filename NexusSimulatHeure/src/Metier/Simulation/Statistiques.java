package Metier.Simulation;

import java.util.LinkedList;
import java.util.List;

public class Statistiques {
    private List<JourneeStatistiques> journees = new LinkedList<>();
    private JourneeStatistiques journeeCourante;
    
    private int nbElements = 0;
    private double tempsTotal = 0;
    
    public void ajouterDonnee(double temps) {
        System.out.println("Nouvelle stat : " + ((Double)temps).toString());
        nbElements += 1;
        tempsTotal += temps;
        
        if(temps < journeeCourante.tempsMinimal)
            journeeCourante.tempsMinimal = temps;
        
        if(temps > journeeCourante.tempsMaximal)
            journeeCourante.tempsMaximal = temps;
        
        journeeCourante.tempsMoyen = (double)(tempsTotal / nbElements);
        
        journeeCourante.aStats = true;
    }
    
    public void creerNouvelleJournee()
    {
        journeeCourante = new JourneeStatistiques();
        journees.add(journeeCourante);
        nbElements = 0;
        tempsTotal = 0;
    }
    
    public void reinitialiser()
    {
        journeeCourante = null;
        journees.clear();
        nbElements = 0;
        tempsTotal = 0;
    }
    
    @Override
    public String toString()
    {    
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(JourneeStatistiques journee : journees)
        {
            sb.append("Journée ");
            sb.append(i);
            sb.append(" : ");
            sb.append(journee.toString());
            sb.append("\r\n");
            i++;
        }
        return sb.toString();
    }
    
    private class JourneeStatistiques
    {
        public boolean aStats = false;
        public double tempsMoyen = 0;
        public double tempsMinimal = Double.MAX_VALUE;
        public double tempsMaximal = 0;
        
        @Override
        public String toString()
        {
            if(aStats)
            {
                return String.format("Min : %1$.2f\tMax : %2$.2f\tMoyen : %3$.2f", tempsMinimal, tempsMaximal, tempsMoyen);    
            }
            else
            {
                return "Aucune statistique pour cette journée.";
            }
            
        }
    }
}
