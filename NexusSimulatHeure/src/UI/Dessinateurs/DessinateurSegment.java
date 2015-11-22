package UI.Dessinateurs;

import java.awt.Graphics2D;

public abstract class DessinateurSegment {
    protected UI.Segment segment;
    private String tempsAffiche = null;
    
    public DessinateurSegment(UI.Segment s) {
        segment = s;
    }
    
    public abstract void dessiner(Graphics2D g2);
    public abstract java.awt.Point calculerCentreSegment();
           
    protected java.awt.Point calculerPositionEntrePoints() {
        return new java.awt.Point(segment.getDepart().calculerCentreX() + (int)((segment.getArrivee().calculerCentreX() - segment.getDepart().calculerCentreX()) / 2),
                                  segment.getDepart().calculerCentreY() + (int)((segment.getArrivee().calculerCentreY() - segment.getDepart().calculerCentreY()) / 2));
    }
    
    protected String obtenirTempsSegmentAffiche() {
        if(this.segment.getSegmentMetier().getTempsTransit() > 0.0)
        {
            return ((Double)this.segment.getSegmentMetier().getTempsTransit()).toString();
        }
        return "";
    }
}
