/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Dessinateurs;

import UI.Constantes.Couleurs;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class DessinateurVehicule {
    private final int LARGEUR = 100;
    private final int HAUTEUR = 37;

    public DessinateurVehicule()
    {
        if(!init)
            initialiserFormes();
    }

    private double zoom;
    private int x;
    private int y;
    private Integer nbPassagers;
    private String circuit;
    
    public void dessiner(Graphics2D g2, int x, int y, int nbPassagers, String circuit, double zoom) {
        this.x = x - (LARGEUR / 2);
        this.y = y - (HAUTEUR / 2);
        this.circuit = circuit;
        this.nbPassagers = nbPassagers;
        this.zoom = zoom;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        dessinerBase(g2);
        dessinerZoneGrise(g2);
        dessinerBandesBlanches(g2);
        dessinerPneus(g2);
        dessinerFondFenetre(g2);
        dessinerFenetre(10,9,g2);
        dessinerFenetre(22,9,g2);
        dessinerFenetre(34,9,g2);
        dessinerFenetre(46,9,g2);
        dessinerFenetre(58,9,g2);
        dessinerFenetre(70,9,g2);
        dessinerFenetre(82,9,g2);
        dessinerNomCircuit(g2);
        dessinerNombrePassagers(g2);
    }
    
    private static boolean init = false;
    private static Path2D base = null;
    private static Path2D zoneGrise = null;
    private static Path2D bandesBlanches = null;
    private static Path2D fondFenetres = null;

    private void initialiserFormes()
    {
        base = new Path2D.Double();
        base.moveTo(2,0);
        base.lineTo(12,0);
        base.lineTo(15,3);
        base.lineTo(92,3);
        base.curveTo(92,3,99,3,100,23);
        base.curveTo(100,23,95,24,80,25);
        base.lineTo(0,25);
        base.lineTo(2,20);
        base.lineTo(2,0);
        
        zoneGrise = new Path2D.Double();
        zoneGrise.moveTo(30, 3);
        zoneGrise.lineTo(70, 3);
        zoneGrise.curveTo(70, 3, 60, 20, 45, 26);
        zoneGrise.lineTo(5,26);
        zoneGrise.curveTo(5,26, 20, 15, 30, 3);
        
        bandesBlanches = new Path2D.Double();
        bandesBlanches.moveTo(70, 3);
        bandesBlanches.curveTo(70, 3, 60, 20, 45, 25);
        bandesBlanches.moveTo(5,25);
        bandesBlanches.curveTo(5,25, 20, 15, 30, 3);
        
        fondFenetres = new Path2D.Double();
        fondFenetres.moveTo(8, 8);
        fondFenetres.lineTo(92, 8);
        fondFenetres.lineTo(92, 17);
        fondFenetres.lineTo(48, 17);
        fondFenetres.curveTo(48, 17, 35, 17, 8, 14);
        fondFenetres.lineTo(8,8);
    }
    
    private AffineTransform obtenirTransformation()
    {
        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        at.concatenate(AffineTransform.getScaleInstance(zoom,zoom));
        return at;
    }
            
    private void dessinerBase(Graphics2D g2)
    {
        Shape s = base.createTransformedShape(obtenirTransformation());
                
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.decode("#004494"));
        
        g2.draw(s);
        g2.fill(s);
    }
    
    private void dessinerZoneGrise(Graphics2D g2)
    {
        Shape s = zoneGrise.createTransformedShape(obtenirTransformation());
        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(s);
    }
    
    private void dessinerBandesBlanches(Graphics2D g2)
    {
        Shape s = bandesBlanches.createTransformedShape(obtenirTransformation());
        g2.setStroke(new BasicStroke(calculerZoom(2)));
        g2.setColor(Color.WHITE);
        g2.draw(s);
    }
    
    private void dessinerPneus(Graphics2D g2)
    {
        g2.setColor(Color.BLACK);
        g2.fillOval(this.x + calculerZoom(20), this.y + calculerZoom(20), calculerZoom(10), calculerZoom(10));
        g2.fillOval(this.x + calculerZoom(70), this.y + calculerZoom(20), calculerZoom(10), calculerZoom(10));
        g2.setColor(Color.GRAY);
        g2.fillOval(this.x + calculerZoom(22), this.y + calculerZoom(22), calculerZoom(6), calculerZoom(6));
        g2.fillOval(this.x + calculerZoom(72), this.y + calculerZoom(22), calculerZoom(6), calculerZoom(6));
    }
    
    private void dessinerFondFenetre(Graphics2D g2)
    {
        Shape s = fondFenetres.createTransformedShape(obtenirTransformation());
        g2.setColor(Color.decode("#1e1e1e"));
        g2.fill(s);
    }
    
    private void dessinerFenetre(int xF, int yF, Graphics2D g2)
    {
        Path2D p = new Path2D.Double();
        p.moveTo(this.x + calculerZoom(xF), this.y + calculerZoom(yF));
        p.lineTo(this.x + calculerZoom(xF + 10), this.y + calculerZoom(yF));
        p.lineTo(this.x + calculerZoom(xF + 10), this.y + calculerZoom(yF + 7));
        p.lineTo(this.x + calculerZoom(xF), this.y + calculerZoom(yF + 7));
        p.lineTo(this.x + calculerZoom(xF), this.y + calculerZoom(yF));
        g2.setColor(Color.decode("#bbe1ee"));
        g2.fill(p);
    }
    
    private void dessinerNomCircuit(Graphics2D g2)
    {
        g2.setColor(Couleurs.VEHICULE_NOM_CIRCUIT);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
        g2.drawString(this.circuit, this.x, this.y);
    }
    
    private void dessinerNombrePassagers(Graphics2D g2)
    {
        g2.setColor(Couleurs.VEHICULE_NOMBRE_PASSAGERS);
        g2.setFont(new Font(null, Font.PLAIN, (int)(UI.Constantes.Rendu.TAILLE_POLICE_POINTS * this.zoom)));
        g2.drawString("Passagers : " + this.nbPassagers.toString(), this.x, this.y + calculerZoom(HAUTEUR + UI.Constantes.Rendu.HAUTEUR_TEXTE));
    }
            
    private int calculerZoom(int i)
    {
        return (int)(zoom * i);
    }
    
}
