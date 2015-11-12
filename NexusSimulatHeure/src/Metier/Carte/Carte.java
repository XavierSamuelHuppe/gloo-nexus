package Metier.Carte;
import Metier.Circuit.ConteneurPassagers;
import Metier.Exceptions.*;

import java.util.*;

public class Carte {
    private List<Segment> segments;
    private List<Point> points;
    
    public Carte() {
        segments = new ArrayList();
        points = new ArrayList();
    }
    
    public Point ajouterPoint(Position position, String nom, ConteneurPassagers passagers){
        Point nouveauPoint = new Point(nom, passagers);
        nouveauPoint.setPosition(position);
        points.add(nouveauPoint);
        return nouveauPoint;
    }
    
    public void modifierPoint(Point pointCible, String nom, Position position){
        int index = points.indexOf(pointCible);
        Point pointAChanger = points.get(index);
        pointAChanger.setNom(nom);
        pointAChanger.setPosition(position);
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
    
    public boolean verifierExistenceSegment(Point depart, Point arrivee){
        try{
            obtenirSegment(depart, arrivee);
            return true;
        }catch(SegmentNonTrouveException e){
            return false;
        }
    }
}
