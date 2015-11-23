package UI;

public abstract class ElementEspaceTravail extends javax.swing.JPanel{
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
    }
    
    public int calculerZoom(double d)
    {
        return (int)(d*zoom);
    }
    
    public void deplacer(java.awt.Point delta)
    {
        this.setLocation(this.getX() + delta.x, this.getY() + delta.y);
    }
    
    public EspaceTravail obtenirEspaceTravail()
    {
        return (EspaceTravail)this.getParent();
    }
}
