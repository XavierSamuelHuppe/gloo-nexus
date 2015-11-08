package UI;

public class Segment {
    private Point pointDepart;
    private Point pointArrivee;
    
    public Segment(Point pD, Point pA)
    {
        this.pointDepart = pD;
        this.pointArrivee = pA;
    }
    
    public Point getDepart()
    {
        return this.pointDepart;
    }
    
    public Point getArrivee()
    {
        return this.pointArrivee;
    }
}
