package Metier.Profil;

import Metier.Carte.ElementTrajet;
import Metier.Carte.Segment;
import java.util.LinkedList;
import javax.swing.text.Element;

public class Trajet {
    private LinkedList<ElementTrajet> elementsTrajet;
    
    public Trajet()
    {
        elementsTrajet = new LinkedList<>();
    }
    
    public void ajouterElementTrajet(ElementTrajet et)
    {
        elementsTrajet.add(et);
    }
    
    public ElementTrajet obtenirEtape(int etape)
    {
        return elementsTrajet.get(etape);
    }
    
    public boolean utilise(Segment s)
    {
        return elementsTrajet.stream().anyMatch((t) -> t.getCircuit().utilise(s, t.getPointMontee(), t.getPointDescente()));
    }
}
