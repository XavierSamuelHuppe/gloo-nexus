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
    
    public void initialiserDepartSimulation(){
        for(Segment s: segments){
            s.recevoirNouveauTempsTransit();
        }
    }
    
    public void terminerSimulation(){
        for(Segment s: segments){
            s.retirerTempsTransit();
        }
    }
    
    public void ajouterPoint(Point point){
        points.add(point);
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
    
    public void ajouterSegment(Segment segment){
        segments.add(segment);
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
    
    public List<Segment> plusCourtCheminEnTempsMoyen(Point depart, Point arrive){
        Map<Point, Integer> temps = new HashMap<Point, Integer>();
        Map<Point, Point> meilleurDernierPoint = new HashMap<Point, Point>();
        
        //On initialise les poids
        for(Point p : points){
            temps.put(p,0);
            meilleurDernierPoint.put(p,null);
        }
        temps.put(depart,1);
        
        while(temps.size() != 0){
            
            //On pogne le plus petit poid
            Integer lowestTime = 0;
            Point lowestPoint = null;
            
            for(Map.Entry<Point, Integer> pair: temps.entrySet()){
                if(lowestTime == 0 || (pair.getValue() < lowestTime)){
                    lowestTime = pair.getValue();
                    lowestPoint = pair.getKey();
                }
            }
            temps.remove(lowestPoint);
            
            //On trouve ses adjacents
            //si n'as pas de poid encore ou
            //si nouveau poid plus petit qu'ancien, on modifie
            for(Point p : obtenirPointsAdjacents(lowestPoint)){
                Integer PoidAlternatif = temps.get(lowestPoint) + ((Double)(obtenirSegment(lowestPoint, p).obtenirMoyenneTempsTransit())).intValue();
                if(temps.get(p) == 0 || (PoidAlternatif < temps.get(p) )){
                    meilleurDernierPoint.put(p,lowestPoint);
                    temps.put(p, PoidAlternatif);
                }
            }
        }
        
        try{
            meilleurDernierPoint.get(arrive);
        }catch(Exception e){
            throw new AucunCheminPossibleException();
        }
        
        List<Segment> retour = new ArrayList<Segment>();
        boolean pasDebut = true;
        Point fin = arrive;
        while(pasDebut){
            Point avantFin = meilleurDernierPoint.get(fin);
            Segment SegmentAAjouter = obtenirSegment(avantFin, fin);
            retour.add(SegmentAAjouter);
            if(avantFin.equals(depart)){
                pasDebut = false;
            }
        }
        Collections.reverse(retour);
        return retour;
    }
    
    
}
