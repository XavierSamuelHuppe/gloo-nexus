package UI;

import UI.Constantes.Couleurs;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import java.util.List;
import java.util.LinkedList;
import javax.swing.SwingUtilities;

public class EspaceTravail extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    
    private Controleur.Simulateur simulateur;
    
    public enum Mode {POINT, SEGMENT, CIRCUIT, SOURCE, PROFIL_PASSAGER};

    private Mode mode = Mode.POINT;
    private List<Point> points = new LinkedList<Point>();
    private List<Segment> segments = new LinkedList<Segment>();
    private List<Vehicule> vehicules = new LinkedList<Vehicule>();
    private double zoom = 1;
    private boolean fanionClavier1 = false;
    private java.awt.Point pointDrag = null;
    private Point tempSegmentPointDepart = null;
    
    private final double PAS_ZOOM = 0.1;
    
    public EspaceTravail()
    {
        //Requis pour éviter des problèmes visuels.
        this.setLayout(null);
        
        this.setBackground(Couleurs.FOND_ESPACE_TRAVAIL);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        
        this.addKeyListener(new UI.IO.ZoneKeyListener());
    }
    
    public void setSimulateur(Controleur.Simulateur s)
    {
        this.simulateur = s;
    }
    
    private Application obtenirApplication()
    {
        return (Application)SwingUtilities.getWindowAncestor(this);
    }
    
    public void deplacerPoint(UI.Point p)
    {
        this.simulateur.modifierPoint(p.getPointMetier(), transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(p.getLocation())), p.getPointMetier().getNom());
    }
    
    private final double ZOOM_BORNE_INFERIEURE = 0.1;
    private final double ZOOM_BORNE_SUPERIEURE = 2;
    
    private void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        if((this.zoom + facteurZoom >= ZOOM_BORNE_INFERIEURE) && (this.zoom + facteurZoom <= ZOOM_BORNE_SUPERIEURE))
        {
            mettreAJourPositionReferenceApresZoom(facteurZoom, positionCurseur);
            this.zoom += facteurZoom;
            for(Point p : points)
            {
                p.zoom(facteurZoom, positionCurseur);
            }
    //        for(Vehicule v : vehicules)
    //        {
    //            v.zoom(facteurZoom, positionCurseur);
    //        }

            obtenirApplication().mettreAJourZoom(this.zoom);
        }

    }
    
    private void mettreAJourPositionReferenceApresZoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        this.posReferenceX += positionCurseur.x * facteurZoom;
        this.posReferenceY += positionCurseur.y * facteurZoom;
    }
    
    private void mettreAJourPositionReferenceApresDrag(java.awt.Point delta)
    {
        this.posReferenceX -= delta.x;
        this.posReferenceY -= delta.y;
    }
    
    private void afficherPosReference()
    {
        System.out.println("ref : " + posReferenceX + " " + posReferenceY);
    }
    
    public void setMode(Mode m)
    {
        System.out.println("Mode : " + m.toString());
        this.mode = m;
        
        //Nettoyage.
        tempSegmentPointDepart = null;
    }
    
    public Mode getMode()
    {
        return this.mode;
    }
    
    public double getZoom()
    {
        return this.zoom;
    }
    
    private void ajouterPoint(MouseEvent me)
    {
        //Metier.Point mp = this.simulateur.ajouterPoint(transformerPositionEspaceTravailEnPostionGeorgraphique(me.getPoint()), "A");
        Metier.Carte.Point mp = this.simulateur.ajouterPoint(transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(me.getPoint())), "A");
        
        Point p = new Point(me.getX(),me.getY(), this.zoom, mp);
        
        points.add(p);
        
        p.addMouseListener(p);
        p.addMouseMotionListener(p);
        
        this.add(p);
        p.repaint();
    }
    
    private void ajouterVehicule(MouseEvent me)
    {
        Vehicule v = new Vehicule(me);
        
        vehicules.add(v);
        
        this.add(v);
        v.repaint();
    }
    
//    private void ajouterPassager(MouseEvent me)
//    {
//        Passager p = new Passager(me);
//        
//        passagers.add(p);
//        
//        this.add(p);
//        p.repaint();
//    }
    
    private void ajouterSegment(Point pDepart, Point pArrivee)
    {
        Segment s = new Segment(pDepart, pArrivee);
        segments.add(s);
        
        this.repaint();
    }
    
    
    public void paintComponent(Graphics g)
    {
        //System.out.println("EspaceTravail paintComponent");
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Les point se dessinent par eux-mêmes.
        
        //Comme les segments ne sont pas des contrôles, on doit les dessiner
        //manuellement.
        dessinerSegments(g2);
    }
    
    private void dessinerSegments(Graphics2D g2)
    {
        for(Segment s : segments)
        {
            s.dessiner(g2);
        }
    }
    

    public void setFanionClavier1(boolean b)
    {
        this.fanionClavier1 = b;
    }
    
    //Implémentations MouseMotionListener.
    @Override
    public void mouseDragged(MouseEvent me) {
        if(pointDrag != null)
        {
            java.awt.Point delta = new java.awt.Point(me.getX() - pointDrag.x, 
                                                  me.getY() - pointDrag.y);
            for(Point p : points)
            {
                p.deplacer(delta);
            }
            
            for(Vehicule v : vehicules)
            {
                v.deplacer(delta);
            }
            
            mettreAJourPositionReferenceApresDrag(delta);
        }
        pointDrag = me.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //afficherPosReference();
        //afficherPoint("vp", me.getPoint());
        java.awt.Point pET = transformerPositionViewportEnPositionEspaceTravail(me.getPoint());
        //afficherPoint("et", pET);
        Metier.Carte.Position p = transformerPositionEspaceTravailEnPostionGeorgraphique(pET);
        //afficherPosition("geo", p);
        obtenirApplication().mettreAJourCoordonnesGeographiques(p.getY(), p.getX());
    }

    private void afficherPoint(String s, java.awt.Point p)
    {
        System.out.println(s + " : " + p.x + " " + p.y);
    }
    
    private void afficherPosition(String s, Metier.Carte.Position p)
    {
        System.out.println(s + " : " + p.getX() + " " + p.getY());
    }
    
    //Implémentations MouseWheelListener.
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mwe.getPreciseWheelRotation() > 0)
        {
            zoom(-PAS_ZOOM, mwe.getPoint());
        }
        else
        {
            zoom(PAS_ZOOM, mwe.getPoint());
        }
    }

    //Implémentations MouseListener.
    @Override
    public void mouseClicked(MouseEvent me) {
        if(mode == Mode.POINT)
        {
            ajouterPoint(me);
        }
        else if(mode == Mode.SEGMENT || mode == Mode.CIRCUIT)
        {
            for(Segment s : segments)
            {
                if(s.estSegmentClique(me.getPoint()))
                {
                    segmentClique(s);
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        pointDrag = null;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    private int posReferenceX = 0;
    private int posReferenceY = 0;
    
    private double ratioPixelDegreLatitude = (double)(0.005 / 500.0);
    private double ratioPixelDegreLontitude = (double)(0.005 / 500.0);

    public java.awt.Point transformerPostionGeorgraphiqueEnPositionEspaceTravail(Metier.Carte.Position posGeo)
    {
        return new java.awt.Point((int)(posGeo.getX() / ratioPixelDegreLontitude), 
                                  (int)(posGeo.getY() / -ratioPixelDegreLatitude));
    }
    
        public Metier.Carte.Position transformerPositionEspaceTravailEnPostionGeorgraphique(java.awt.Point posET)
    {
        return new Metier.Carte.Position(posET.x * ratioPixelDegreLontitude, 
                                   posET.y * -ratioPixelDegreLatitude);
    }
    
    public java.awt.Point transformerPositionEspaceTravailEnPositionViewport(java.awt.Point posET)
    {
        return new java.awt.Point((int)(posET.x * zoom) - posReferenceX, (int)(posET.y * zoom) - posReferenceY);
    }
    
    public java.awt.Point transformerPositionViewportEnPositionEspaceTravail(java.awt.Point posVP)
    {
        return new java.awt.Point((int)((posVP.x + posReferenceX) / zoom), (int)((posVP.y + posReferenceY) / zoom));
    }
    
    
    
    public void pointClique(Point p)
    {
        if(mode == Mode.SEGMENT)
        {
            if(tempSegmentPointDepart == null)
            {
                tempSegmentPointDepart = p;
            }
            else
            {
                ajouterSegment(tempSegmentPointDepart, p);
                tempSegmentPointDepart = fanionClavier1 ? p : null;
            }
        }
        else if (mode == Mode.POINT)
        {
            deselectionnerTout();
            p.setModeActuel(Point.Mode.SELECTIONNE);
            afficherDetails(p);
            this.repaint();
        }
    }
        
    public void segmentClique(Segment s)
    {
        if(mode == Mode.SEGMENT)
        {
            deselectionnerTout();
            s.setMode(Segment.Mode.SELECTIONNE);
            afficherDetails(s);
            this.repaint();
        }
        else if(mode == Mode.CIRCUIT)
        {
            deselectionnerTout();
            s.setMode(Segment.Mode.CIRCUIT);
            this.repaint();
        }
    }
    
    
    private void afficherDetails(IDetailsAffichables elementCible)
    {
        this.obtenirApplication().afficherPanneauDetails(elementCible);
    }
    
    private void deselectionnerTout()
    {
        deselectionnerTousPoints();
        deselectionnerTousSegments();
    }
       
    private void deselectionnerTousPoints()
    {
        for(UI.Point p : points)
        {
            if(p.getModeActuel() == Point.Mode.SELECTIONNE)
            {
                p.setModeActuel(Point.Mode.NORMAL);    
                p.repaint();
            }
        }
    }
    
    private void deselectionnerTousSegments()
    {
        for(UI.Segment s : segments)
        {
            if(s.getMode() == Segment.Mode.SELECTIONNE)
            {
                s.setMode(Segment.Mode.NORMAL);    
            }
        }
    }
}
