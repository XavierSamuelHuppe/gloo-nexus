package UI.Dessinateurs;

import java.awt.Graphics2D;

public abstract class DessinateurSegment {
    protected UI.Segment segment;
    
    

    public DessinateurSegment(UI.Segment s)
    {
        segment = s;
    }
    
    public abstract void dessiner(Graphics2D g2);
    public abstract java.awt.Point calculerCentreSegment();
           
    protected java.awt.Point calculerPositionEntrePoints()
    {
        return new java.awt.Point(segment.getDepart().calculerCentreX() + (int)((segment.getArrivee().calculerCentreX() - segment.getDepart().calculerCentreX()) / 2),
                                  segment.getDepart().calculerCentreY() + (int)((segment.getArrivee().calculerCentreY() - segment.getDepart().calculerCentreY()) / 2));
    }
    

    
    private String tempsAffiche = null;
    protected String obtenirTempsSegmentAffiche()
    {
        if(tempsAffiche == null)
        {
            //@todo Obtenir et formater le temps à partir de l'objet Métier.
            tempsAffiche = "temps"; 
        }
        return tempsAffiche;
    }
    

}
