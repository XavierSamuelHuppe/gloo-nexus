/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Metier.ConteneurPassagers;
import Metier.ConteneurPassagersIllimite;

/**
 *
 * @author The Vagrant Geek
 */
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
