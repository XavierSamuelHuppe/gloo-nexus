package Controleur;

import Metier.ConteneurPassagersIllimite;

public class Simulateur {
    public Metier.Point ajouterPoint(Metier.Position pos, String nom)
    {
        Metier.Point metierPoint = new Metier.Point(nom, new ConteneurPassagersIllimite());
        metierPoint.setPosition(pos);
        metierPoint.setNom("LaLiLuLeLo");
        return metierPoint;
    }
    
    public void modifierPoint(Metier.Point pointCible, Metier.Position pos, String nom)
    {
        pointCible.setPosition(pos);
        pointCible.setNom(nom);
    }
}
