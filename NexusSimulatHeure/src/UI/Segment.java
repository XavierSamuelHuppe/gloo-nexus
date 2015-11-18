package UI;

import UI.Constantes.Couleurs;
import UI.Dessinateurs.DessinateurSegmentDecale;
import UI.Dessinateurs.DessinateurSegmentDroit;
import UI.IO.SegmentDecaleDetectionClic;
import UI.IO.SegmentDroitDetectionClic;
import UI.PanneauxDetails.PanneauDetails;
import UI.PanneauxDetails.PanneauDetailsPoint;
import UI.PanneauxDetails.PanneauDetailsSegment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import sun.awt.geom.Curve;

public class Segment implements IDetailsAffichables {
    
    public enum Mode {NORMAL, SELECTIONNE, CIRCUIT, CIRCUIT_SELECTIONNE};
               
    private Point pointDepart;
    private Point pointArrivee;
    
    private Metier.Carte.Segment segmentMetier;
    private Mode modeActuel = Mode.NORMAL;
    
    private UI.Dessinateurs.DessinateurSegment dessinateur;
    private UI.IO.SegmentDetectionClic detectionClic;
        
    public enum Orientation {NO, NE, SE, SO};
   
    
    public Segment(Point pD, Point pA)
    {
        this.pointDepart = pD;
        this.pointArrivee = pA;
        passerModeDroit();
    }
    
    public void passerModeDroit()
    {
        dessinateur = new DessinateurSegmentDroit(this);
        detectionClic = new SegmentDroitDetectionClic(this);
    }
    
    public void passerModeDecale()
    {
        dessinateur = new DessinateurSegmentDecale(this);
        detectionClic = new SegmentDecaleDetectionClic(this);
    }
    
    public Point getDepart()
    {
        return this.pointDepart;
    }
    
    public Point getArrivee()
    {
        return this.pointArrivee;
    }
    
    public Mode getMode()
    {
        return this.modeActuel;
    }
    
    public void setMode(Mode m)
    {
        this.modeActuel = m;
    }
    
    public boolean estSegmentClique(java.awt.Point p)
    {
        return detectionClic.estSegmentClique(p);
    }

    public void dessiner(Graphics2D g2)
    {
        dessinateur.dessiner(g2);
    }
    
    public Metier.Carte.Segment getSegmentMetier()
    {
        return this.segmentMetier;
    }
        
    @Override
    public PanneauDetails obtenirPanneauDetails() {
        return new PanneauDetailsSegment(this.getSegmentMetier());
    }
    
    public Orientation obtenirOrientation()
    {
        double dX = calculerDeltaX();
        double dY = calculerDeltaY();
        
        if(dX >= 0 && dY >= 0)
        {
            System.out.println("SE");
            return Orientation.SE;
        }
        else if(dX >= 0 && dY < 0)
        {
            System.out.println("NE");
            return Orientation.NE;
        }
        else if(dX < 0 && dY >= 0)
        {
            System.out.println("SO");
            return Orientation.SO;
        }
        else
        {
            System.out.println("NO");
            return Orientation.NO;
        }
    }
    
    public double calculerDeltaX()
    {
        return this.pointArrivee.calculerCentreX() - this.pointDepart.calculerCentreX();
    }
    
    public double calculerDeltaY()
    {
        return this.pointArrivee.calculerCentreY() - this.pointDepart.calculerCentreY();
    }
        
    public double obtenirZoom()
    {
        return this.pointDepart.getZoom();
    }
        
}
