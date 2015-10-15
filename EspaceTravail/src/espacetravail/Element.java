/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espacetravail;

/**
 *
 * @author The Vagrant Geek
 */
public abstract class Element extends javax.swing.JPanel{
    protected double zoom = 1;
    
    public void setZoom(double d)
    {
        this.zoom = d;
    }
    
    public double getZoom()
    {
        return this.zoom;
    }
    
    public void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        this.zoom += facteurZoom;
        
        double zoomEffectif = facteurZoom > 0 ? 1 + facteurZoom : 1 / (1 + Math.abs(facteurZoom));
        this.setLocation((int)(this.getX() * zoomEffectif), (int)(this.getY() * zoomEffectif));
    }
    
    public int calculerZoom(double d)
    {
        return (int)(d*zoom);
    }
    
    public void deplacer(java.awt.Point delta)
    {
        this.setLocation(this.getX() + delta.x, this.getY() + delta.y);
    }
}
