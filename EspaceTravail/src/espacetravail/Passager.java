/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espacetravail;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 *
 * @author The Vagrant Geek
 */
public class Passager extends Element {
    private final int LARGEUR = 15;
    private final int HAUTEUR = 30;
    
    public Passager(MouseEvent me)
    {
        this.setLayout(new FlowLayout());
        this.setSize(calculerZoom(LARGEUR), calculerZoom(HAUTEUR));
        this.setLocation(me.getX(), me.getY());
        this.setOpaque(false);
    }
        
    @Override
    public void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        super.zoom(facteurZoom, positionCurseur);
        this.setSize(calculerZoom(LARGEUR), calculerZoom(HAUTEUR));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        dessinerTete(g2);
        dessinerCorps(g2);
    }
    
    private void dessinerTete(Graphics2D g2)
    {
        g2.setColor(Color.BLACK);        
        g2.fillOval(calculerZoom(3), calculerZoom(0), calculerZoom(9), calculerZoom(9));
    }
    
    private void dessinerCorps(Graphics2D g2)
    {
        Path2D p = new Path2D.Double();
        p.moveTo(3, 10);
        p.lineTo(12,10);
        p.lineTo(15,20);
        p.lineTo(12,20);
        p.lineTo(10,15);
        p.lineTo(10,20);
        p.lineTo(12,30);
        p.lineTo(9,30);
        p.lineTo(7.5,25);
        p.lineTo(6,30);
        p.lineTo(3,30);
        p.lineTo(5,20);
        p.lineTo(5,15);
        p.lineTo(3,20);
        p.lineTo(0,20);
        p.lineTo(3,10);
        
        g2.setColor(Color.BLACK);        
        g2.fill(p.createTransformedShape(AffineTransform.getScaleInstance(zoom, zoom)));
    }
}
