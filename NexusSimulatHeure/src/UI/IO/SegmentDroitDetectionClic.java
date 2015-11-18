/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.IO;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 *
 * @author The Vagrant Geek
 */
public class SegmentDroitDetectionClic extends SegmentDetectionClic {

    public SegmentDroitDetectionClic(UI.Segment s)
    {
        super(s);
    }
        
    @Override
    public boolean estSegmentClique(Point p) {
        return Line2D.ptSegDist((double)segment.getDepart().calculerCentreX(), (double)segment.getDepart().calculerCentreY(),
                                (double)segment.getArrivee().calculerCentreX(), (double)segment.getArrivee().calculerCentreY(),
                                (double)p.x, (double)p.y) <= TOLERANCE_CLIC;
    }
    
}
