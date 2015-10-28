package Metier;

import java.util.*;

public class Carte {
    private List<Segment> segments;
    private List<Point> points;
    
    public Carte() {
        segments = new ArrayList();
        points = new ArrayList();
    }
    
    public void ajouterPoint(Position position, String nom){
        Point nouveauPoint = new Point(nom);
        nouveauPoint.setCoordonee(position);
        points.add(nouveauPoint);
    }
    
    public void modifierPoint(Point pointCible, String nom, Position position){
        int index = points.indexOf(pointCible);
        Point nouveauPoint = new Point(nom);
        nouveauPoint.setCoordonee(position);
        points.remove(pointCible);
        points.add(index, nouveauPoint);
    }
}
