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
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Point))return false;
        Segment autreSegment = (Segment)o;
        return (pointDepart.equals(autreSegment.getPointDepart()) &&
                pointArrivee.equals(autreSegment.getPointArrivee()));
    }
}
