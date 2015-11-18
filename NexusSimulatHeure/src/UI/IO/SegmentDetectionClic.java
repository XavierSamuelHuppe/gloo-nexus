/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.IO;

/**
 *
 * @author The Vagrant Geek
 */
public abstract class SegmentDetectionClic {
    protected UI.Segment segment;
    protected static final double TOLERANCE_CLIC = 5;
    
    public SegmentDetectionClic(UI.Segment s)
    {
        segment = s;
    }
    public abstract boolean estSegmentClique(java.awt.Point p);
}
