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
import java.util.Observable;
import java.util.Observer;

public class Segment implements IDetailsAffichables, Observer {

    @Override
    public void update(Observable o, Object arg) {
        if(segmentMetier.mort()){
            this.espaceTravail.retirerSegment(this);
        }
    }
    
    public enum Mode {NORMAL, SELECTIONNE, CIRCUIT, CIRCUIT_SELECTIONNE};
               
    private Point pointDepart;
    private Point pointArrivee;
    
    private Metier.Carte.Segment segmentMetier;
    private Mode modeActuel = Mode.NORMAL;
    
    private UI.EspaceTravail espaceTravail;
    
    private UI.Dessinateurs.DessinateurSegment dessinateur;
    private UI.IO.SegmentDetectionClic detectionClic;
        
    public enum Orientation {NO, NE, SE, SO};
   
    public Segment(Point pD, Point pA, EspaceTravail et, Metier.Carte.Segment segmentMetier)
    {
        this.pointDepart = pD;
        this.pointArrivee = pA;
        this.segmentMetier = segmentMetier;
        this.espaceTravail = et;
        segmentMetier.addObserver(this);
    }
    
        public void determinerMode()
        {
            Controleur.Simulateur sim = this.espaceTravail.getSimulateur();
            
            if(sim.verifierExistenceSegementEnSensInverse(this.segmentMetier))
            {
                passerModeDecale();
            }
            else
            {
                passerModeDroit();
            }
            
            if(sim.estEnModeSegment()&& sim.estSegmentActif(this.segmentMetier))
            {
                this.modeActuel = Segment.Mode.SELECTIONNE;
            }
            else if (sim.estEnModeCircuit())
            {
                if((sim.estDansCircuitActif(this.segmentMetier) || sim.estDansCircuitEnCreation(this.segmentMetier)))
                {
                    this.modeActuel = Segment.Mode.CIRCUIT_SELECTIONNE;
                }
                else if (sim.estDansAuMoinsUnCircuit(this.segmentMetier))
                {
                    this.modeActuel = Segment.Mode.CIRCUIT;
                }
            }
            else
            {
                this.modeActuel = Segment.Mode.NORMAL;
            }        
        }  
        
    private void passerModeDroit()
    {
        dessinateur = new DessinateurSegmentDroit(this);
        detectionClic = new SegmentDroitDetectionClic(this);
    }
    
    private void passerModeDecale()
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
    
    public boolean estSegmentClique(java.awt.Point p)
    {
        return detectionClic.estSegmentClique(p);
    }

    public void dessiner(Graphics2D g2)
    {
        determinerMode();
        dessinateur.dessiner(g2);
    }
    
    public Metier.Carte.Segment getSegmentMetier()
    {
        return this.segmentMetier;
    }
        
    @Override
    public PanneauDetails obtenirPanneauDetails() {
        return new PanneauDetailsSegment(this.getSegmentMetier(), this);
    }
    
    public Orientation obtenirOrientation()
    {
        double dX = calculerDeltaX();
        double dY = calculerDeltaY();
        
        if(dX >= 0 && dY >= 0)
        {
//            System.out.println("SE");
            return Orientation.SE;
        }
        else if(dX >= 0 && dY < 0)
        {
//            System.out.println("NE");
            return Orientation.NE;
        }
        else if(dX < 0 && dY >= 0)
        {
//            System.out.println("SO");
            return Orientation.SO;
        }
        else
        {
//            System.out.println("NO");
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
