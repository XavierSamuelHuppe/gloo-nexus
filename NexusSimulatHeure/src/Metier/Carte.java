package Metier;

import java.util.*;

public class Carte {
    private List<Segment> segments;
    private List<Point> points;
    
    public Carte() {
        segments = new ArrayList();
        points = new ArrayList();
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    
}
