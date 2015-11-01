/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author The Vagrant Geek
 */
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
