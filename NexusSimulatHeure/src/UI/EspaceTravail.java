package UI;

import Controleur.Simulateur;
import Metier.Exceptions.AucunPointCreateurException;
import UI.Constantes.Couleurs;
import UI.Exceptions.SegmentNonTrouveException;
import UI.Utils.PaireDoubles;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import java.util.List;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EspaceTravail extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    
    private Controleur.Simulateur simulateur;

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
    
    public Simulateur getSimulateur()
    {
        return this.simulateur;
    }
    
    private Application obtenirApplication()
    {
        return (Application)SwingUtilities.getWindowAncestor(this);
    }
    
    public void deplacerPoint(UI.Point p)
    {
        PaireDoubles coords = transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(p.getLocation()));
        this.simulateur.modifierPoint(p.getPointMetier(), coords.getPremier(), coords.getSecond(), p.getPointMetier().getNom());
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
            this.repaint();
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
    
    public double getZoom()
    {
        return this.zoom;
    }
    
    private void ajouterPoint(MouseEvent me)
    {
        PaireDoubles pd = transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(me.getPoint()));
        Metier.Carte.Point mp = this.simulateur.ajouterPoint(pd.getPremier(), pd.getSecond(), "");
        
        Point p = new Point(me.getX(),me.getY(), this.zoom, mp);
        
        points.add(p);
        
        p.addMouseListener(p);
        p.addMouseMotionListener(p);
        
        this.add(p);
        this.repaint();
    }

    public void retirerPoint(Point p){
        points.remove(p);
        this.remove(p);
        this.repaint();
    }

    public void retirerSegment(Segment s){
        segments.remove(s);
        this.repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        dessinerPoints(g2);
        dessinerSegments(g2);
    }
    
    private void dessinerPoints(Graphics2D g2)
    {
        for(Point p : points)
        {
            p.dessiner(g2);
        }
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
            this.repaint();
        }
        pointDrag = me.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        java.awt.Point pET = transformerPositionViewportEnPositionEspaceTravail(me.getPoint());
        PaireDoubles pd = transformerPositionEspaceTravailEnPostionGeorgraphique(pET);
        obtenirApplication().mettreAJourCoordonnesGeographiques(pd.getSecond(), pd.getPremier());
    }

    public Segment obtenirSegmentParPoints(Point pD, Point pA)
    {
        for(Segment s : segments)
        {
            if(s.getDepart() == pD && s.getArrivee() == pA)
            {
                return s;
            }
        }
        throw new SegmentNonTrouveException();
    }
    
    public UI.Point obtenirPointUIParPointMetier(Metier.Carte.Point point)
    {
        return points.stream().filter((p) -> p.getPointMetier().equals(point)).findFirst().get();
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
        if(simulateur.estEnModePoint()){
            ajouterPoint(me);
        }
        else if(simulateur.estEnModeSegment() || simulateur.estEnModeCircuit()){
            for(Segment s : segments)
            {
                if(s.estSegmentClique(me.getPoint()))
                {
                    if(SwingUtilities.isLeftMouseButton(me)){
                        segmentClique(s);
                        break;
                    }
                    else if(SwingUtilities.isRightMouseButton(me)){
                        SegmentMenuContextuel m = new SegmentMenuContextuel(simulateur, s.getSegmentMetier());
                        m.show(me.getComponent(), me.getX(), me.getY());
                    }
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
        
    public java.awt.Point transformerPostionGeorgraphiqueEnPositionEspaceTravail(PaireDoubles posGeo)
    {
        return new java.awt.Point((int)(posGeo.getPremier()/ ratioPixelDegreLontitude), 
                                  (int)(posGeo.getSecond() / -ratioPixelDegreLatitude));
    }
    
        public PaireDoubles transformerPositionEspaceTravailEnPostionGeorgraphique(java.awt.Point posET)
    {
        return new PaireDoubles(posET.x * ratioPixelDegreLontitude, 
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
        if(simulateur.estEnModeSegment())
        {
            try
            {
                Metier.Carte.Segment sp = null;
//                System.err.println(fanionClavier1);
                if(fanionClavier1)
                {
                    System.out.println("creerSegmentAvecContinuation");
                    sp = this.simulateur.creerSegmentAvecContinuation(p.getPointMetier());
                }
                else
                {
                    System.out.println("creerSegmentSansContinuation");
                    sp = this.simulateur.creerSegmentSansContinuation(p.getPointMetier());
                }

                Segment s = new Segment(obtenirPointUIParPointMetier(sp.getPointDepart()), obtenirPointUIParPointMetier(sp.getPointArrivee()), this, sp);
                segments.add(s);        
                this.repaint();
            }
            catch(Metier.Exceptions.AucunPointCreateurException ex)
            {
                System.out.println("AucunPointCreateurException");
            }
            
        }
        else if (simulateur.estEnModePoint())
        {
            this.simulateur.selectionnerPoint(p.getPointMetier());
            this.obtenirApplication().afficherPanneauDetails(p);
        }
        else if (simulateur.estEnModeCircuit())
        {
            try
            {
                this.simulateur.commencerContinuerCreationCircuit(p.getPointMetier());
            }
            catch(AucunPointCreateurException ex)
            {
                System.err.println("AucunPointCreateurException");
            }
        }
        this.repaint();
    }
    
    public void segmentClique(Segment s)
    {
        if(simulateur.estEnModeSegment())
        {
            simulateur.selectionnerSegment(s.getSegmentMetier());
            afficherDetails(s);
            this.repaint();
        }
        else if(simulateur.estEnModeCircuit())
        {
            simulateur.commencerContinuerCreationCircuit(s.getArrivee().getPointMetier());
        }
    }
    
    private void afficherDetails(IDetailsAffichables elementCible)
    {
        this.obtenirApplication().afficherPanneauDetails(elementCible);
    }
}
