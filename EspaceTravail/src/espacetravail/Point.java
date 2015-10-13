/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espacetravail;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;

/**
 *
 * @author The Vagrant Geek
 */
public class Point extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    enum Mode {NORMAL, SELECTIONNE};
        
    private double zoom = 1;
    private java.awt.Point pointPoigneeDrag;
    private Mode modeActuel = Mode.NORMAL;
    
    public static final int DIAMETRE = 32;
    private static final int POSITION_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/4.0);
    private static final int LARGEUR_CERCLE_INTERNE = (int)Math.ceil(DIAMETRE/2.0);
    
    public Point(int x, int y, double zoom)
    {
        System.out.println("new Point");
        this.zoom = zoom;
        this.setLayout(new FlowLayout());
        this.setSize(DIAMETRE,DIAMETRE);
        this.setLocation(x,y);
        this.setOpaque(false);
    }

    public void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        this.zoom += facteurZoom;
        
        this.setSize((int)(DIAMETRE * zoom), (int)(DIAMETRE * zoom));
        
        double zoomEffectif = facteurZoom > 0 ? 1 + facteurZoom : 1 / (1 + Math.abs(facteurZoom));
        this.setLocation((int)(this.getX() * zoomEffectif), (int)(this.getY() * zoomEffectif));
    }
        
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(modeActuel == Mode.NORMAL)
        {
            g2.setColor(Color.green);    
        }
        else if(modeActuel == Mode.SELECTIONNE)
        {
            g2.setColor(Color.blue);    
        }
        g2.fillOval(0, 0, calculerZoom(DIAMETRE), calculerZoom(DIAMETRE));
        
        g2.setColor(Color.white);
        g2.fillOval(calculerZoom(POSITION_CERCLE_INTERNE), calculerZoom(POSITION_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE), calculerZoom(LARGEUR_CERCLE_INTERNE));
    }
    
    public int calculerZoom(double d)
    {
        return (int)(d*zoom);
    }
    
    public void deplacer(java.awt.Point delta)
    {
        this.setLocation(this.getX() + delta.x, this.getY() + delta.y);
    }
        
    public int calculerCentreX()
    {
        return this.getX() + (calculerZoom(DIAMETRE) / 2);
    }
       
    public int calculerCentreY()
    {
        return this.getY() + (calculerZoom(DIAMETRE) / 2);
    }
    
    private Zone.Mode obtenirModeZone()
    {
        return obtenirZone().getMode();
    }
    
    private Zone obtenirZone()
    {
        return (Zone)this.getParent();
    }
    
    //Implémentations MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent me) {
        this.setLocation(this.getX() + me.getX() - (int)this.pointPoigneeDrag.getX(), this.getY() + me.getY() - (int)this.pointPoigneeDrag.getY());
        obtenirZone().repaint();
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
        obtenirZone().pointSelectionne(this);
    }
    
    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
