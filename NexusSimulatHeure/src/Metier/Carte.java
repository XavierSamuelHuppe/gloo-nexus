package Metier;
import Metier.Exceptions.*;

import java.util.*;

public class Carte {
    private List<Segment> segments;
    private List<Point> points;
    
    public Carte() {
        segments = new ArrayList();
        points = new ArrayList();
    }
    
    public Point ajouterPoint(Position position, String nom){
        Point nouveauPoint = new Point(nom);
        nouveauPoint.setCoordonee(position);
        points.add(nouveauPoint);
        return nouveauPoint;
    }
    
    public void modifierPoint(Point pointCible, String nom, Position position){
        int index = points.indexOf(pointCible);
        Point nouveauPoint = new Point(nom);
        nouveauPoint.setCoordonee(position);
        points.remove(pointCible);
        points.add(index, nouveauPoint);
    }
    
    public void retirerPoint(Point point){
        points.remove(point);
    }
    
    public List<Point> obtenirPointsAdjacents(Point point){
        List<Point> retour = new ArrayList();
        for(Segment s : obtenirSegmentsSortant(point)){
                retour.add(s.getPointArrivee());
        }
        return retour;
    }
    
    public List<Segment> obtenirSegmentsSortant(Point point){
        List<Segment> retour = new ArrayList();
        for(Segment s : segments){
            if(s.getPointDepart().equals(point)){
                retour.add(s);
            }
        }
        return retour;
    }
    
    public Segment ajouterSegment(Point depart, Point arrivee){
        Segment nouveauSegment = new Segment(depart, arrivee);
        segments.add(nouveauSegment);
        return nouveauSegment;
    }
    
    public void retirerSegment(Segment segment){
        segments.remove(segment);
    }
    
    public Segment obtenirSegment(Point depart, Point arrivee){
        for(Segment s : segments){
            if(s.getPointDepart().equals(depart) &&
               s.getPointArrivee().equals(arrivee)){
                return s;
            }
        }
        throw new SegmentNonTrouveException();
    }
}
