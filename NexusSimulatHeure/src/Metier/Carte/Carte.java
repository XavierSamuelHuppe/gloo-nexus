package Metier.Carte;
import Metier.Distribution;
import Metier.Exceptions.*;
import java.io.Serializable;

import java.util.*;

public class Carte implements Serializable{
    private List<Segment> segments;
    private List<Point> points;
    
    public Carte() {
        segments = new ArrayList();
        points = new ArrayList();
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public List<Point> getPoints() {
        return points;
    }
    
    public void initialiserDepartSimulation(int nbJournees){
        for(Segment s: segments){
            s.recevoirNouveauTempsTransit(nbJournees);
        }
        for(Point p: points){
            p.viderConteneurPassager();
        }
    }
    
    public void initialiserNouvelleJourneeSimulation(int journee){
        for(Segment s: segments){
            s.setJournee(journee);
        }
        for(Point p: points){
            p.viderConteneurPassager();
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
        point.tuer();
        points.remove(point);
    }
    
    public void modifierPoint(Point pointCible, Position pos, String nom)
    {
        pointCible.setNom(nom);
        pointCible.setPosition(pos);
    }
    public void modifierSegment(Segment segmentCible, Distribution distribution){
        segmentCible.setDistribution(distribution);
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
    public List<Segment> obtenirSegmentsEntrant(Point point){
        List<Segment> retour = new ArrayList();
        for(Segment s : segments){
            if(s.getPointArrivee().equals(point)){
                retour.add(s);
            }
        }
        return retour;
    }
    public List<Segment> obtenirSegmentsEntrantEtSortant(Point point){
        List<Segment> retour = new ArrayList();
        for(Segment s : obtenirSegmentsEntrant(point)){
            retour.add(s);
        }
        for(Segment s : obtenirSegmentsSortant(point)){
            retour.add(s);
        }
        return retour;
    }
    
    public boolean estSegmentSortantDePoint(Point point, Segment segment)
    {
        List<Segment> l = obtenirSegmentsSortant(point);
        boolean r = l.stream().anyMatch((s) -> s.equals(segment));
        return r;
    }
    
    public void ajouterSegment(Segment segment){
        if(segments.contains(segment))
            throw new CreationInvalideException("Ce segment existe déjà.");
        else if (segment.getPointDepart().equals(segment.getPointArrivee()))
            throw new CreationInvalideException("Un segment ne peut pas avoir le même point comme départ et comme arrivée.");
        else
            segments.add(segment);
    }
    
    public void retirerSegment(Segment segment){
        segment.tuer();
        segments.remove(segment);
    }
    public void retirerSegments(List<Segment> segments){
        for(Segment s: segments){
            retirerSegment(s);
        }
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
    public boolean segmentExisteEnSensInverse(Segment segment){
        return verifierExistenceSegment(segment.getPointArrivee(), segment.getPointDepart());
    }
    
    public List<Segment> plusCourtCheminEnTempsMoyen(Point depart, Point arrive){
        Map<Point, Integer> temps = new HashMap<Point, Integer>();
        Map<Point, Point> meilleurDernierPoint = new HashMap<Point, Point>();
        List<Point> PointNonVisites = new ArrayList();
        
        //On initialise les poids
        for(Point p : points){
            temps.put(p,0);
            meilleurDernierPoint.put(p,null);
            PointNonVisites.add(p);
        }
        temps.put(depart,1);
        while(!PointNonVisites.isEmpty()){
            //On pogne le plus petit poid
            Integer lowestTime = 0;
            Point lowestPoint = null;
            
            /*for(Map.Entry<Point, Integer> pair: temps.entrySet()){
                System.out.println("3");
                if(lowestTime == 0 || (pair.getValue() < lowestTime)){
                    lowestTime = pair.getValue();
                    lowestPoint = pair.getKey();
                }
            }*/
            for(Point p : PointNonVisites){
                if(lowestTime == 0 || (temps.get(p) != 0 && (temps.get(p) < lowestTime))){
                    lowestTime = temps.get(p);
                    lowestPoint = p;
                }
            }
            PointNonVisites.remove(lowestPoint);
            
            //On trouve ses adjacents
            //si n'as pas de poid encore ou
            //si nouveau poid plus petit qu'ancien, on modifie
            boolean shortcut = false;
            for(Point p : obtenirPointsAdjacents(lowestPoint)){
                int tempsSegment = ((Double)(obtenirSegment(lowestPoint, p).obtenirMoyenneTempsTransit())).intValue();
                Integer PoidAlternatif = temps.get(lowestPoint) + tempsSegment;
                if(temps.get(p) == 0 || (PoidAlternatif <= temps.get(p) )){
                    meilleurDernierPoint.put(p,lowestPoint);
                    temps.put(p, PoidAlternatif);
                }
                if(p.equals(arrive)){
                    shortcut = true;
                }
            }
            if(shortcut){
                break;
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
            if(avantFin == null){
                throw new AucunCheminPossibleException();
            }
            Segment SegmentAAjouter = obtenirSegment(avantFin, fin);
            retour.add(SegmentAAjouter);
            if(avantFin.equals(depart)){
                pasDebut = false;
            }
            fin = avantFin;
        }
        Collections.reverse(retour);
        return retour;
    }
    
    
}
