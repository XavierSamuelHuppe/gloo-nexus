package UI.IO;

public abstract class SegmentDetectionClic {
    protected UI.Segment segment;
    protected static final double TOLERANCE_CLIC = 5;
    
    public SegmentDetectionClic(UI.Segment s)
    {
        segment = s;
    }
    public abstract boolean estSegmentClique(java.awt.Point p);
}
