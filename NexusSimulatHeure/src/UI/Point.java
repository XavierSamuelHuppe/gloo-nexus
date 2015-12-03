package UI;

import UI.Constantes.Couleurs;
import UI.PanneauxDetails.PanneauDetails;
import UI.PanneauxDetails.PanneauDetailsPoint;
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

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
        if(pointMetier.mort()){
            this.obtenirEspaceTravail().retirerPoint(this);
        }
    }

    enum Mode {NORMAL, SELECTIONNE, CIRCUIT, CREATION_SEGMENT};
        
    private java.awt.Point pointPoigneeDrag;
    private Mode modeActuel = Mode.NORMAL;
    
    private Metier.Carte.Point pointMetier;
    
    public static final int DIAMETRE = 32;
    private static final int POSITION_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/4.0);
    private static final int LARGEUR_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/2.0);
    
    private static final int AJUSTEMENT_POSITION_NOM_X = DIAMETRE + 4;
    private static final int AJUSTEMENT_POSITION_NOM_Y = (int)(DIAMETRE / 2.0) + (int)(UI.Constantes.Rendu.HAUTEUR_TEXTE / 2.0) ;
    
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
        
//        dessinerFond(g2);
//        dessinerCentre(g2);
    }
    
    public void dessiner(Graphics2D g2)
    {
        determinerMode();
        this.repaint();
        dessinerFond(g2);
        dessinerCentre(g2);
        dessinerNomSiRequis(g2);
        dessinerDetailsSourcesSiRequis(g2);
        if(this.obtenirEspaceTravail().getSimulateur().simulationEstEnAction())
        {
            dessinerDetailsPassagers(g2);
        }
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
        else if(modeActuel == Mode.CREATION_SEGMENT)
        {
            g2.setColor(Couleurs.POINT_CREATION_SEGMENT);
        }
        g2.fillOval(this.getX(), this.getY(), calculerZoom(DIAMETRE), calculerZoom(DIAMETRE));
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
        else if(modeActuel == Mode.CREATION_SEGMENT)
        {
            g2.setColor(Couleurs.POINT_FOND_CREATION_SEGMENT);
        }
        g2.fillOval(this.getX() + calculerZoom(POSITION_CERCLE_INTERNE), this.getY() + calculerZoom(POSITION_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE));
    }
    
    private void dessinerNomSiRequis(Graphics2D g2)
    {
        //@todo Déplacer au niveau de EspaceTravai, pour renderer hors du JPanel.
        if(this.getPointMetier().getNom() != null && !this.getPointMetier().getNom().isEmpty())
        {
            g2.setColor(Couleurs.POINT_NOM);
            g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
            g2.drawString(this.getPointMetier().getNom(), this.getX() + (int)(this.zoom * AJUSTEMENT_POSITION_NOM_X), this.getY() + (int)(this.zoom * AJUSTEMENT_POSITION_NOM_Y));
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
//            for(DetailsSource ds : liste)
//            {
//                g2.drawString(this.getPointMetier().getNom(), (int)(this.zoom * 50), offsetY);
//                offsetY += (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom);
//            }
        }
    }
    
    private void dessinerDetailsPassagers(Graphics2D g2)
    {
        g2.setColor(Couleurs.POINT_PASSAGERS_DETAILS);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
        Integer nbPassagers = this.getPointMetier().obtenirNombrePassagersEnAttente();
//        String str = "Aucun passager en attente";
//        if(nbPassagers > 0)
//        {
//            str = nbPassagers.toString() + "  passager" + (nbPassagers == 1 ? "" : "s") + " en attente";
//        }
        g2.drawString(nbPassagers.toString(), this.getX() + calculerZoom(ICONE_PASSAGER_LARGEUR + 4), this.getY() + calculerZoom(DIAMETRE + UI.Constantes.Rendu.HAUTEUR_TEXTE + 6));
        dessinerIconePassagers(g2);
    }
    
    private void dessinerIconePassagers(Graphics2D g2)
    {
        dessinerTetePassager(g2);
        dessinerCorpsPassager(g2);
    }
    
    private static final int ICONE_PASSAGER_LARGEUR = 8;
    
    private void dessinerTetePassager(Graphics2D g2)
    {
        g2.setColor(Couleurs.POINT_PASSAGERS_DETAILS);        
        g2.fillOval(this.getX(), this.getY() + calculerZoom(DIAMETRE), calculerZoom(ICONE_PASSAGER_LARGEUR), calculerZoom(ICONE_PASSAGER_LARGEUR));
    }
    
    private void dessinerCorpsPassager(Graphics2D g2)
    {
        Path2D p = new Path2D.Double();
        p.moveTo(0, 0);
        p.lineTo(8,0);
        p.lineTo(4,16);
        p.lineTo(0,0);
        
        AffineTransform at = AffineTransform.getTranslateInstance(this.getX(), this.getY() + calculerZoom(DIAMETRE + ICONE_PASSAGER_LARGEUR + 1));
        at.concatenate(AffineTransform.getScaleInstance(zoom,zoom));
        
        g2.setColor(Couleurs.POINT_PASSAGERS_DETAILS);        
        g2.fill(p.createTransformedShape(at));
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
        else if (sim.estEnModeCircuit() && (sim.estDansCircuitActif(pointMetier) || sim.estDansCircuitEnCreation(pointMetier) || sim.estDansAuMoinsUnCircuit(pointMetier)))
        {
            this.modeActuel = Mode.CIRCUIT;
        }
        else if (sim.estEnModeSegment() && (sim.estPointCreateur(pointMetier)))
        {
            this.modeActuel = Mode.CREATION_SEGMENT;
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
        if(this.obtenirEspaceTravail().permettreDeplacementPoint())
        {
            dragged = true;
            this.setLocation(this.getX() + me.getX() - (int)this.pointPoigneeDrag.getX(), this.getY() + me.getY() - (int)this.pointPoigneeDrag.getY());
            obtenirZone().repaint();
        }
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
