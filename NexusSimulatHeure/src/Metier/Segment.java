package Metier;

public class Segment {
    private final Point pointDepart;
    private final Point pointArrivee;
    
    public Segment(Point pointDepart, Point pointArrivee) {
        this.pointDepart = pointDepart;
        this.pointArrivee = pointArrivee;
    }

    public Point getPointDepart() {
        return pointDepart;
    }

    public Point getPointArrivee() {
        return pointArrivee;
    }
    
}
