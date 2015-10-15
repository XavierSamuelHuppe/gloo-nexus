/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espacetravail;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author The Vagrant Geek
 */
public class Zone extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    public enum Mode {POINT, SEGMENT, VEHICULE, PASSAGER};

    private Mode mode = Mode.POINT;
    private List<Point> points = new LinkedList<Point>();
    private List<Segment> segments = new LinkedList<Segment>();
    private List<Vehicule> vehicules = new LinkedList<Vehicule>();
    private List<Passager> passagers = new LinkedList<Passager>();
    private double zoom = 1;
    private boolean fanionClavier1 = false;
    private java.awt.Point pointDrag = null;
    private Point tempSegmentPointDepart = null;
    
    private final double PAS_ZOOM = 0.1;
    private final float TAILLE_TRAIT_SEGMENT = 5;
    
    public Zone()
    {
        this.setBackground(Color.WHITE);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }
    
    private void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        this.zoom += facteurZoom;
        for(Point p : points)
        {
            p.zoom(facteurZoom, positionCurseur);
        }
        for(Vehicule v : vehicules)
        {
            v.zoom(facteurZoom, positionCurseur);
        }
        for(Passager p : passagers)
        {
            p.zoom(facteurZoom, positionCurseur);
        }
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
        Point p = new Point(me.getX() - (Point.DIAMETRE / 2),me.getY() - (Point.DIAMETRE / 2), this.zoom);
        
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
    
    private void ajouterPassager(MouseEvent me)
    {
        Passager p = new Passager(me);
        
        passagers.add(p);
        
        this.add(p);
        p.repaint();
    }
    
    private void ajouterSegment(Point pDepart, Point pArrivee)
    {
        Segment s = new Segment(pDepart, pArrivee);
        segments.add(s);
        
        this.repaint();
    }
    
    
    public void paintComponent(Graphics g)
    {
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
            g2.setColor(Color.decode("#EFE1FC"));
            g2.setStroke(new BasicStroke(TAILLE_TRAIT_SEGMENT * (float)this.zoom));
            g2.drawLine(s.getDepart().calculerCentreX(),s.getDepart().calculerCentreY(),
                        s.getArrivee().calculerCentreX(),s.getArrivee().calculerCentreY());
        }
    }
    
    public void pointSelectionne(Point p)
    {
        System.out.println("pointSelectionne");
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
            for(Passager p : passagers)
            {
                p.deplacer(delta);
            }
        }
        pointDrag = me.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {}

    //Implémentations MouseWheelListener.
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mwe.getPreciseWheelRotation() > 0)
        {
            zoom(PAS_ZOOM, mwe.getPoint());
        }
        else
        {
            zoom(-PAS_ZOOM, mwe.getPoint());
        }
    }

    //Implémentations MouseListener.
    @Override
    public void mouseClicked(MouseEvent me) {
        if(mode == Mode.POINT)
        {
            ajouterPoint(me);
        }
        else if(mode == Mode.SEGMENT)
        {
            //Détecter segment le plus proche.
            //Comparer le point du clic avec les segments en utilisant
            //Line2D.ptLineDist ou Line2D.intersects.
        }
        else if(mode == Mode.VEHICULE)
        {
            ajouterVehicule(me);
        }
        else if(mode == Mode.PASSAGER)
        {
            ajouterPassager(me);
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
}
