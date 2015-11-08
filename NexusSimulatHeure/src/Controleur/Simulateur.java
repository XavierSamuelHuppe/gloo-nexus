package Controleur;

import Metier.ConteneurPassagers;
import Metier.ConteneurPassagersIllimite;

public class Simulateur {
    public Metier.Point ajouterPoint(Metier.Position pos, String nom)
    {
        Metier.Point metierPoint = new Metier.Point(nom, new ConteneurPassagersIllimite());
        metierPoint.changerPosition(pos);
        return metierPoint;
    }
    
    public void modifierPoint(Metier.Point pointCible, Metier.Position pos, String nom)
    {
        pointCible.changerPosition(pos);
        pointCible.setNom(nom);
    }
}
