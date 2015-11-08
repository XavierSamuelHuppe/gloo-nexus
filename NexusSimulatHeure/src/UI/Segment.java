package UI;

import UI.Constantes.Couleurs;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Segment {
    public enum Mode {NORMAL, SELECTIONNE};
               
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
    
    public void dessiner(Graphics2D g2)
    {
        //@todo : Mettre zoom dans Segment?
        if(modeActuel == Mode.NORMAL)
        {
            g2.setColor(Couleurs.SEGMENT);
            g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom())));
            g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                        this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
        }
        else if (modeActuel == Mode.SELECTIONNE)
        {
            //Extérieur.
            g2.setColor(Couleurs.SEGMENT_SELECTIONNE_EXTERIEUR);
            g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_EXTERIEUR)));
            g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                        this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
            //Intérieur.
            g2.setColor(Couleurs.SEGMENT_SELECTIONNE_INTERIEUR);
            g2.setStroke(new BasicStroke((float)(UI.Constantes.Rendu.TAILLE_TRAIT * obtenirZoom() * UI.Constantes.Rendu.FACTEUR_TRAIT_DEUX_TONS_INTERIEUR)));
            g2.drawLine(this.getDepart().calculerCentreX(),this.getDepart().calculerCentreY(),
                        this.getArrivee().calculerCentreX(),this.getArrivee().calculerCentreY());
        }
        //Temps segment.
        java.awt.Point centreSegment = calculerCentreSegment();
        g2.setColor(Couleurs.SEGMENT_TEXTE);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * obtenirZoom())));
        g2.drawString(obtenirTempsSegmentAffiche(), centreSegment.x, centreSegment.y + obtenirAjustementPositionYTexte());
        
//        g2.setColor(Color.ORANGE);
//        g2.fillOval(centreSegment.x, centreSegment.y, 1, 1);
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
    
    
}
