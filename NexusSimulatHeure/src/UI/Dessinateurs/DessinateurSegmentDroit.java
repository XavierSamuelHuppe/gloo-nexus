package UI.Dessinateurs;

import UI.Constantes.Couleurs;
import java.awt.Graphics2D;
import UI.Segment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class DessinateurSegmentDroit extends DessinateurSegment {
    
    public DessinateurSegmentDroit(UI.Segment s)
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
        g2.drawLine(segment.getDepart().calculerCentreX(),segment.getDepart().calculerCentreY(),
                    segment.getArrivee().calculerCentreX(),segment.getArrivee().calculerCentreY());
    }
    
    private void dessinerFlecheUnTonCourbe(Graphics2D g2, Color c1)
    {
        Path2D pointe = obtenirPointeFleche();
        g2.setColor(c1);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom())));
        g2.draw(pointe);
//        g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
//                    this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
        
        java.awt.Point p = calculerPositionPointeFleche();
        java.awt.Point centre = calculerCentreSegment();
        AffineTransform.getRotateInstance(Math.toRadians(90.0), centre.x, centre.y).transform(p, p);

        Path2D courbe = new Path2D.Double();
        courbe.moveTo(segment.getDepart().calculerCentreX(),segment.getDepart().calculerCentreY());
        courbe.curveTo(segment.getDepart().calculerCentreX(),segment.getDepart().calculerCentreY(),
                       p.x, p.y,
                       segment.getArrivee().calculerCentreX(),segment.getArrivee().calculerCentreY());
        g2.draw(courbe);
    }
    
    private void dessinerFlecheDeuxTons(Graphics2D g2, Color cExt, Color cInt)
    {
        Path2D pointe = obtenirPointeFleche();
        //Extérieur.
        g2.setColor(cExt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_EXTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(segment.getDepart().calculerCentreX(),segment.getDepart().calculerCentreY(),
                    segment.getArrivee().calculerCentreX(),segment.getArrivee().calculerCentreY());
        //Intérieur.
        g2.setColor(cInt);
        g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * segment.obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_INTERIEUR)));
        g2.draw(pointe);
        g2.drawLine(segment.getDepart().calculerCentreX(),segment.getDepart().calculerCentreY(),
                    segment.getArrivee().calculerCentreX(),segment.getArrivee().calculerCentreY());
    }
    
    private Path2D obtenirPointeFleche()
    {
        java.awt.Point pFleche = calculerPositionPointeFleche();
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
       
        
    private java.awt.Point calculerPositionPointeFleche()
    {
        double angle = java.lang.Math.atan(segment.calculerDeltaY() / segment.calculerDeltaX());
        java.awt.Point centre = calculerCentreSegment();
        return new java.awt.Point((int)(centre.x + (0 * java.lang.Math.cos(angle))), (int)(centre.y + (0 * java.lang.Math.sin(angle))));
    }
    
    public java.awt.Point calculerCentreSegment()
    {
        return new java.awt.Point(segment.getDepart().calculerCentreX() + (int)((segment.getArrivee().calculerCentreX() - segment.getDepart().calculerCentreX()) / 2),
                                  segment.getDepart().calculerCentreY() + (int)((segment.getArrivee().calculerCentreY() - segment.getDepart().calculerCentreY()) / 2));
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
