package UI;

import UI.Constantes.Couleurs;
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

public class Segment implements IDetailsAffichables {
    
    private static final double TOLERANCE_CLIC = 5;
    public enum Mode {NORMAL, SELECTIONNE, CIRCUIT, CIRCUIT_SELECTIONNE};
               
    private Point pointDepart;
    private Point pointArrivee;
    
    private Metier.Segment segmentMetier;
    private Mode modeActuel = Mode.NORMAL;
    
    public Segment(Point pD, Point pA)
    {
        this.pointDepart = pD;
        this.pointArrivee = pA;
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
        return Line2D.ptSegDist((double)this.pointDepart.calculerCentreX(), (double)this.pointDepart.calculerCentreY(),
                                (double)this.pointArrivee.calculerCentreX(), (double)this.pointArrivee.calculerCentreY(),
                                (double)p.x, (double)p.y) <= TOLERANCE_CLIC;
    }
    
    public void dessiner(Graphics2D g2)
    {
        //@todo : Mettre zoom dans Segment?
        if(modeActuel == Mode.NORMAL)
        {
            dessinerFlecheUnTon(g2, Couleurs.SEGMENT);
        }
        else if (modeActuel == Mode.SELECTIONNE)
        {
            dessinerFlecheDeuxTons(g2, Couleurs.SEGMENT_SELECTIONNE_EXTERIEUR, Couleurs.SEGMENT_SELECTIONNE_INTERIEUR);
        }
        else if(modeActuel == Mode.CIRCUIT)
        {
            dessinerFlecheUnTon(g2, Couleurs.CIRCUIT);
        }

        //Temps segment.
        java.awt.Point centreSegment = calculerCentreSegment();
        g2.setColor(Couleurs.SEGMENT_TEXTE);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * obtenirZoom())));
        g2.drawString(obtenirTempsSegmentAffiche(), centreSegment.x, centreSegment.y + obtenirAjustementPositionYTexte());
    }
    
    private void dessinerFlecheUnTon(Graphics2D g2, Color c1)
    {
        Path2D pointe = obtenirPointeFleche();
        g2.setColor(c1);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom())));
        g2.draw(pointe);
        g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                    this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
    }
    
    private void dessinerFlecheDeuxTons(Graphics2D g2, Color cExt, Color cInt)
    {
        Path2D pointe = obtenirPointeFleche();
        //Extérieur.
        g2.setColor(cExt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_EXTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                    this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
        //Intérieur.
        g2.setColor(cInt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_INTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                    this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
    }
    
    private Path2D obtenirPointeFleche()
    {
        java.awt.Point pFleche = calculerPositionPointeFleche();
        Path2D pointe = new Path2D.Double();
        pointe.moveTo(0, 10);
        pointe.lineTo(10, 0);
        pointe.lineTo(0, -10);

        AffineTransform transformation = AffineTransform.getTranslateInstance((int)pFleche.x, (int)pFleche.y);
        transformation.concatenate(AffineTransform.getRotateInstance(calculerDeltaX(), calculerDeltaY()));
        transformation.concatenate(AffineTransform.getScaleInstance(this.obtenirZoom(), this.obtenirZoom()));
        pointe = (Path2D)pointe.createTransformedShape(transformation);
        return pointe;
    }
        
    private java.awt.Point calculerPositionPointeFleche()
    {
        double angle = java.lang.Math.atan(this.calculerDeltaY() / this.calculerDeltaX());
        java.awt.Point centre = calculerCentreSegment();
        return new java.awt.Point((int)(centre.x + (0 * java.lang.Math.cos(angle))), (int)(centre.y + (0 * java.lang.Math.sin(angle))));
    }
    
    private String tempsAffiche = null;
    private String obtenirTempsSegmentAffiche()
    {
        if(tempsAffiche == null)
        {
            //@todo Obtenir et formater le temps à partir de l'objet Métier.
            tempsAffiche = "temps"; 
        }
        return tempsAffiche;
    }
    
    private double obtenirZoom()
    {
        return this.getDepart().getZoom();
    }
    
    private java.awt.Point calculerCentreSegment()
    {
        return new java.awt.Point(this.getDepart().calculerCentreX() + (int)((this.getArrivee().calculerCentreX() - this.getDepart().calculerCentreX()) / 2),
                                  this.getDepart().calculerCentreY() + (int)((this.getArrivee().calculerCentreY() - this.getDepart().calculerCentreY()) / 2));
    }
    
    private int obtenirAjustementPositionYTexte()
    {
        //Déterminer l'orientation du segment et dans quel quadrant il se trouve, si on suppose
        //le point de départ comme origine. Si orienté NE ou SO, ajouter à Y, sinon soustraire
        //à Y.
        if((this.pointArrivee.calculerCentreX() > this.pointDepart.calculerCentreX() 
                && this.pointArrivee.calculerCentreY() < this.pointDepart.calculerCentreY())
           ||(this.pointArrivee.calculerCentreX() < this.pointDepart.calculerCentreX() 
                && this.pointArrivee.calculerCentreY() > this.pointDepart.calculerCentreY()))
        {
            return UI.Constantes.Rendu.HAUTEUR_TEXTE + (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * obtenirZoom());
        }
        else
        {
            return -UI.Constantes.Rendu.HAUTEUR_TEXTE;
        }
    }
    
    public Metier.Segment getSegmentMetier()
    {
        return this.segmentMetier;
    }
    
    private double calculerDeltaX()
    {
        return this.pointArrivee.calculerCentreX() - this.pointDepart.calculerCentreX();
    }
    
    private double calculerDeltaY()
    {
        return this.pointArrivee.calculerCentreY() - this.pointDepart.calculerCentreY();
    }
    
    @Override
    public PanneauDetails obtenirPanneauDetails() {
        return new PanneauDetailsSegment(this.getSegmentMetier());
    }
    
}
