package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Segment;
import Metier.Carte.Point;
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
    
    public boolean utilise(Segment s)
    {
        return elementsTrajet.stream().anyMatch((t) -> t.getCircuit().utilise(s, t.getPointMontee(), t.getPointDescente()));
    }
    
    public Point obtenirPointDepart()
    {
        return elementsTrajet.getFirst().getPointMontee();
    }
    
    public Point obtenirPointArrivee()
    {
        return elementsTrajet.getLast().getPointDescente();
    }
}
