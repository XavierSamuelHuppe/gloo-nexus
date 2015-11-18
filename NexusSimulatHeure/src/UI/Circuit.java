package UI;

import UI.PanneauxDetails.PanneauDetails;
import UI.PanneauxDetails.PanneauDetailsCircuit;
import java.util.*;

public class Circuit implements IDetailsAffichables {

    @Override
    public PanneauDetails obtenirPanneauDetails() {
        return new PanneauDetailsCircuit(this.circuitMetierLie, this);
    }
    private Point depart;
    private List<UI.Segment> segments;
    private Metier.Circuit.Circuit circuitMetierLie;
    
    public Circuit(Point pDepart)
    {
        this.depart = pDepart;
        this.segments = new ArrayList<UI.Segment>();
        this.circuitMetierLie = null;
    }
    
    public Circuit(Metier.Circuit.Circuit circuitMetier)
    {
        this.circuitMetierLie = circuitMetier;
    }
    
    public void ajouterSegment(UI.Segment s)
    {
        segments.add(s);
        s.setMode(Segment.Mode.CIRCUIT_SELECTIONNE);
    }
    
    public List<Metier.Carte.Segment> obtenirListeSegmentsMetier()
    {
        ArrayList<Metier.Carte.Segment> l = new ArrayList<Metier.Carte.Segment>();
        for(UI.Segment s : segments)
        {
            l.add(s.getSegmentMetier());
        }
        return l;
    }
    
    public void deselectionner()
    {
        for(UI.Segment s : segments)
        {
            s.setMode(Segment.Mode.CIRCUIT);
        }
    }
        
    public UI.Segment obtenirDernierSegment()
    {
        return segments.get(segments.size() - 1);
    }
            
    public boolean contientSegments()
    {
        return segments.size() > 0;
    }
    
    public Point getDepart()
    {
        return this.depart;
    }
    
}
