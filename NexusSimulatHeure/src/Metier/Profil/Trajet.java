package Metier.Profil;

import Metier.Carte.Segment;
import Metier.Carte.Point;
import Metier.Circuit.Circuit;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.text.Element;

public class Trajet implements Serializable {
    private LinkedList<ElementTrajet> elementsTrajet;
    
    public Trajet()
    {
        elementsTrajet = new LinkedList<>();
    }
    
    public void ajouterElementTrajet(ElementTrajet et)
    {
        elementsTrajet.add(et);
    }
    
    public boolean contientAuMoinsUneEtape()
    {
        return elementsTrajet.size() > 0;
    }
    
    public ElementTrajet obtenirEtape(int etape)
    {
        return elementsTrajet.get(etape);
    }
    
    public boolean utilise(Point p)
    {
        return elementsTrajet.stream().anyMatch((t) -> t.getCircuit().utilise(p, t.getPointMontee(), t.getPointDescente()));
    }
        
    public boolean utilise(Segment s)
    {
        return elementsTrajet.stream().anyMatch((t) -> t.getCircuit().utilise(s, t.getPointMontee(), t.getPointDescente()));
    }
    
    public boolean utilise(Circuit c)
    {
        return elementsTrajet.stream().anyMatch((t) -> t.getCircuit().equals(c));
    }
    
    public Point obtenirPointDepart()
    {
        return elementsTrajet.getFirst().getPointMontee();
    }
    
    public Point obtenirPointArrivee()
    {
        return elementsTrajet.getLast().getPointDescente();
    }
    
    public int obtenirNombreEtapes()
    {
        return elementsTrajet.size();
    }
    
    @Override
    public String toString()
    {
        String retour = this.obtenirPointDepart().toString();
        for(ElementTrajet et : elementsTrajet)
        {
            retour += " -> (" + et.getCircuit().toString() + " : " + et.getPointDescente().toString() + ")";
        }
        return retour;
    }
}
