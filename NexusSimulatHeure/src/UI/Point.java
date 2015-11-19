package UI;

import UI.Constantes.Couleurs;
import UI.PanneauxDetails.PanneauDetails;
import UI.PanneauxDetails.PanneauDetailsPoint;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Observable;
import javax.swing.BorderFactory;
import java.util.Observer;

public class Point extends ElementEspaceTravail implements MouseListener, MouseMotionListener, IDetailsAffichables, Observer {

    @Override
    public void update(Observable o, Object o1) {
        rafraichir();
    }
    
    public void rafraichir()
    {
        this.determinerMode();
        this.setSize((int)(DIAMETRE * zoom), (int)(DIAMETRE * zoom));
        this.setLocation(this.obtenirEspaceTravail().transformerPositionEspaceTravailEnPositionViewport(this.obtenirEspaceTravail().transformerPostionGeorgraphiqueEnPositionEspaceTravail(this.pointMetier.getPosition())));        
    }

    enum Mode {NORMAL, SELECTIONNE, CIRCUIT};
        
    private java.awt.Point pointPoigneeDrag;
    private Mode modeActuel = Mode.NORMAL;
    
    private Metier.Carte.Point pointMetier;
    
    public static final int DIAMETRE = 32;
    private static final int POSITION_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/4.0);
    private static final int LARGEUR_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/2.0);
    
    private static final int AJUSTEMENT_POSITION_NOM_X = DIAMETRE + 10;
    private static final int AJUSTEMENT_POSITION_NOM_Y = (int)(UI.Constantes.Rendu.HAUTEUR_TEXTE / 2);
    
    public Point(int x, int y, double zoom, Metier.Carte.Point p)
    {
        this.pointMetier = p;
        this.zoom = zoom;
        this.setLayout(new FlowLayout());
        this.setSize(calculerZoom(DIAMETRE),calculerZoom(DIAMETRE));
        this.setLocation(x,y);
        this.setOpaque(false);
        
        this.pointMetier.addObserver(this);
    }

    public Metier.Carte.Point getPointMetier()
    {
        return this.pointMetier;
    }
        
    @Override
    public void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        super.zoom(facteurZoom, positionCurseur);
        rafraichir();
        this.obtenirEspaceTravail().repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        dessinerFond(g2);
        dessinerCentre(g2);
    }
    
    public void dessiner(Graphics2D g2)
    {
        determinerMode();
        this.repaint();
        dessinerNomSiRequis(g2);
        dessinerDetailsSourcesSiRequis(g2);
    }
    
    private void dessinerFond(Graphics2D g2)
    {
        if(modeActuel == Mode.NORMAL)
        {
            g2.setColor(Couleurs.POINT);    
        }
        else if(modeActuel == Mode.SELECTIONNE)
        {
            g2.setColor(Couleurs.POINT_SELECTIONNE);    
        }
        else if(modeActuel == Mode.CIRCUIT)
        {
            g2.setColor(Couleurs.POINT_CIRCUIT);
        }
        g2.fillOval(0, 0, calculerZoom(DIAMETRE), calculerZoom(DIAMETRE));
    }
    
    private void dessinerCentre(Graphics2D g2)
    {
        if(modeActuel == Mode.NORMAL)
        {
            g2.setColor(Couleurs.POINT_FOND);    
        }
        else if(modeActuel == Mode.SELECTIONNE)
        {
            g2.setColor(Couleurs.POINT_FOND_SELECTIONNE);    
        }
        else if(modeActuel == Mode.CIRCUIT)
        {
            g2.setColor(Couleurs.POINT_FOND_CIRCUIT);    
        }
        g2.fillOval(calculerZoom(POSITION_CERCLE_INTERNE), calculerZoom(POSITION_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE));
    }
    
    private void dessinerNomSiRequis(Graphics2D g2)
    {
        //@todo Déplacer au niveau de EspaceTravai, pour renderer hors du JPanel.
        if(this.getPointMetier().getNom() != null && !this.getPointMetier().getNom().isEmpty())
        {
            g2.setColor(Couleurs.POINT_NOM);
            g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
            g2.drawString(this.getPointMetier().getNom(), this.getX() + (int)(this.zoom * 42), this.getY() + (int)(this.zoom * 16));
            
            //(int)(this.zoom * (this.getX())) + (int)this.zoom * 50, (int)(this.zoom * (this.getY())) + (int)this.zoom * 50
        }
    }
    
    private void dessinerDetailsSourcesSiRequis(Graphics2D g2)
    {
        if(!this.pointMetier.getSources().isEmpty())
        {
            LinkedList<DetailsSource> liste = new LinkedList<DetailsSource>();
            for(Metier.Source.Source s : this.pointMetier.getSources())
            {
                liste.add(new DetailsSource(s));
            }

            g2.setColor(Couleurs.POINT_SOURCE_DETAILS);
            g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
            int offsetY = 0;
            for(DetailsSource ds : liste)
            {
                g2.drawString(this.getPointMetier().getNom(), (int)(this.zoom * 50), offsetY);
                offsetY += (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom);
            }
        }
    }
    
       
    public int calculerCentreX()
    {
        return this.getX() + (calculerZoom(DIAMETRE) / 2);
    }
       
    public int calculerCentreY()
    {
        return this.getY() + (calculerZoom(DIAMETRE) / 2);
    }
    
    private EspaceTravail obtenirZone()
    {
        return (EspaceTravail)this.getParent();
    }
    
    public void determinerMode()
    {
        Controleur.Simulateur sim = this.obtenirEspaceTravail().getSimulateur();
        if(sim.estEnModePoint() && sim.estPointActif(this.pointMetier))
        {
            this.modeActuel = Mode.SELECTIONNE;
        }
        else if (sim.estEnModeCircuit() && true)
        {
            this.modeActuel = Mode.CIRCUIT;
        }
        else
        {
            this.modeActuel = Mode.NORMAL;
        }        
    }        

    boolean dragged = false;
    
    //<editor-fold desc="Implémentations MouseMotionListener, MouseListener">
    //Implémentations MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent me) {
        dragged = true;
        this.setLocation(this.getX() + me.getX() - (int)this.pointPoigneeDrag.getX(), this.getY() + me.getY() - (int)this.pointPoigneeDrag.getY());
        obtenirZone().repaint();
        System.out.println("Point mouseDragged");
    }
 
    @Override
    public void mouseMoved(MouseEvent me) {}

    //Implémentations MouseListener.
    @Override
    public void mousePressed(MouseEvent me) {
        this.pointPoigneeDrag = me.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        obtenirEspaceTravail().pointClique(this);
    }
    
    @Override
    public void mouseReleased(MouseEvent me) 
    {
        if(dragged)
        {
            this.obtenirEspaceTravail().deplacerPoint(this);    
        }
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    //</editor-fold>

    @Override
    public PanneauDetails obtenirPanneauDetails() {
        return new PanneauDetailsPoint(this.getPointMetier(), this);
    }
}
