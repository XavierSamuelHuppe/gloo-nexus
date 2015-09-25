package Metier;

public class Segment {
    private Point pointDepart;
    private Point pointArrivee;
    
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
