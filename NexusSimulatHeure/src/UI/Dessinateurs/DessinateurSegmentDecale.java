package UI.Dessinateurs;

import UI.Constantes.Couleurs;
import UI.Segment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class DessinateurSegmentDecale extends DessinateurSegment {
    public DessinateurSegmentDecale(UI.Segment s)
    {
        super(s);
    }
    
    public void dessiner(Graphics2D g2)
    {
        //@todo : Mettre zoom dans Segment?
        if(segment.getMode() == Segment.Mode.NORMAL)
        {
            dessinerFlecheUnTon(g2, Couleurs.SEGMENT);
        }
        else if (segment.getMode() == Segment.Mode.SELECTIONNE)
        {
            dessinerFlecheDeuxTons(g2, Couleurs.SEGMENT_SELECTIONNE_EXTERIEUR, Couleurs.SEGMENT_SELECTIONNE_INTERIEUR);
        }
        else if(segment.getMode() == Segment.Mode.CIRCUIT)
        {
            dessinerFlecheUnTon(g2, Couleurs.CIRCUIT);
        }
         else if(segment.getMode() == Segment.Mode.CIRCUIT_SELECTIONNE)
        {
            dessinerFlecheDeuxTons(g2, Couleurs.CIRCUIT_SELECTIONNE_EXTERIEUR, Couleurs.CIRCUIT_SELECTIONNE_INTERIEUR);
        }
        else if(segment.getMode() == Segment.Mode.TRAJET)
        {
            dessinerFlecheUnTon(g2, Couleurs.TRAJET);
        }

        //Temps segment.
        java.awt.Point centreSegment = calculerCentreSegment();
        g2.setColor(Couleurs.SEGMENT_TEXTE);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * segment.obtenirZoom())));
        g2.drawString(obtenirTempsSegmentAffiche(), centreSegment.x, centreSegment.y + obtenirAjustementPositionYTexte());
    }
    
    private void dessinerFlecheUnTon(Graphics2D g2, Color c1)
    {
        Path2D pointe = obtenirPointeFleche();
        g2.setColor(c1);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom())));
        g2.draw(pointe);
        
        java.awt.Point[] points = calculerPointsDepartEtArriveeDecales();
        
        g2.drawLine(points[0].x, points[0].y,
                    points[1].x, points[1].y); 
    }
    
    private java.awt.Point[] calculerPointsDepartEtArriveeDecales()
    {
        double angle = java.lang.Math.atan(segment.calculerDeltaY() / segment.calculerDeltaX());
        Segment.Orientation orientation = segment.obtenirOrientation();
        double facteurOrientation = (orientation == Segment.Orientation.SO || orientation == Segment.Orientation.NO) ? 1 : -1;
        
        //Calculer point depart prime.
        java.awt.Point pDepart = new Point((int)(segment.getDepart().calculerCentreX() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.cos(angle))), (int)(segment.getDepart().calculerCentreY() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.sin(angle))));
        AffineTransform.getRotateInstance(Math.toRadians(facteurOrientation * 90.0), segment.getDepart().calculerCentreX(), segment.getDepart().calculerCentreY()).transform(pDepart, pDepart);

        //Calculer point arrivée prime.
        java.awt.Point pArrivee = new Point((int)(segment.getArrivee().calculerCentreX() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.cos(angle))), (int)(segment.getArrivee().calculerCentreY() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom()* java.lang.Math.sin(angle))));
        AffineTransform.getRotateInstance(Math.toRadians(facteurOrientation * 90.0), segment.getArrivee().calculerCentreX(), segment.getArrivee().calculerCentreY()).transform(pArrivee, pArrivee);
        
        return new java.awt.Point[] {pDepart, pArrivee};
    }
    
    
    
    private void dessinerFlecheDeuxTons(Graphics2D g2, Color cExt, Color cInt)
    {
        Path2D pointe = obtenirPointeFleche();
        java.awt.Point[] points = calculerPointsDepartEtArriveeDecales();
        //Extérieur.
        g2.setColor(cExt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_EXTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(points[0].x,points[0].y,
                    points[1].x,points[1].y);
        
        //Intérieur.
        g2.setColor(cInt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_INTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(points[0].x,points[0].y,
                    points[1].x,points[1].y);
    }
    
    private Path2D obtenirPointeFleche()
    {
        java.awt.Point pFleche = calculerCentreSegment();
        Path2D pointe = new Path2D.Double();
        pointe.moveTo(0, 10);
        pointe.lineTo(10, 0);
        pointe.lineTo(0, -10);

        AffineTransform transformation = AffineTransform.getTranslateInstance((int)pFleche.x, (int)pFleche.y);
        transformation.concatenate(AffineTransform.getRotateInstance(segment.calculerDeltaX(), segment.calculerDeltaY()));
        transformation.concatenate(AffineTransform.getScaleInstance(segment.obtenirZoom(), segment.obtenirZoom()));
        pointe = (Path2D)pointe.createTransformedShape(transformation);
        return pointe;
    }
      
    private final static int DISTANCE_POINTE_FLECHE = 13;
        
    public java.awt.Point calculerCentreSegment()
    {
        double angle = java.lang.Math.atan(segment.calculerDeltaY() / segment.calculerDeltaX());
        Segment.Orientation orientation = segment.obtenirOrientation();
        double facteurOrientation = (orientation == Segment.Orientation.SO || orientation == Segment.Orientation.NO) ? 1 : -1;
        
        java.awt.Point centre = calculerPositionEntrePoints();
        java.awt.Point p = new Point((int)(centre.x + facteurOrientation * (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.cos(angle))), (int)(centre.y + facteurOrientation * (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.sin(angle))));
        
        AffineTransform.getRotateInstance(Math.toRadians(90.0), centre.x, centre.y).transform(p, p);
        return p;
    }
    
    private int obtenirAjustementPositionYTexte()
    {
        //Déterminer l'orientation du segment et dans quel quadrant il se trouve, si on suppose
        //le point de départ comme origine. Si orienté NE ou SO, ajouter à Y, sinon soustraire
        //à Y.
        if((segment.getArrivee().calculerCentreX() > segment.getDepart().calculerCentreX() 
                && segment.getArrivee().calculerCentreY() < segment.getDepart().calculerCentreY())
           ||(segment.getArrivee().calculerCentreX() < segment.getDepart().calculerCentreX() 
                && segment.getArrivee().calculerCentreY() > segment.getDepart().calculerCentreY()))
        {
            return UI.Constantes.Rendu.HAUTEUR_TEXTE + (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * segment.obtenirZoom());
        }
        else
        {
            return -UI.Constantes.Rendu.HAUTEUR_TEXTE;
        }
    }
}
