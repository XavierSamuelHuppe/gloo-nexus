package UI.IO;

import static UI.IO.SegmentDetectionClic.TOLERANCE_CLIC;
import UI.Segment;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class SegmentDecaleDetectionClic extends SegmentDetectionClic {

    public SegmentDecaleDetectionClic(UI.Segment s)
    {
        super(s);
    }
        
    private final static int DISTANCE_POINTE_FLECHE = 13;
    
    public boolean estSegmentClique(Point p) {
        double angle = java.lang.Math.atan(segment.calculerDeltaY() / segment.calculerDeltaX());
        Segment.Orientation orientation = segment.obtenirOrientation();
        double facteurOrientation = (orientation == Segment.Orientation.SO || orientation == Segment.Orientation.NO) ? 1 : -1;
        
        //Calculer point depart prime.
        java.awt.Point pDepart = new Point((int)(segment.getDepart().calculerCentreX() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.cos(angle))), (int)(segment.getDepart().calculerCentreY() + (DISTANCE_POINTE_FLECHE * java.lang.Math.sin(angle))));
        AffineTransform.getRotateInstance(Math.toRadians(facteurOrientation * 90.0), segment.getDepart().calculerCentreX(), segment.getDepart().calculerCentreY()).transform(pDepart, pDepart);

        //Calculer point arrivée prime.
        java.awt.Point pArrivee = new Point((int)(segment.getArrivee().calculerCentreX() + (DISTANCE_POINTE_FLECHE * segment.obtenirZoom() * java.lang.Math.cos(angle))), (int)(segment.getArrivee().calculerCentreY() + (DISTANCE_POINTE_FLECHE * java.lang.Math.sin(angle))));
        AffineTransform.getRotateInstance(Math.toRadians(facteurOrientation * 90.0), segment.getArrivee().calculerCentreX(), segment.getArrivee().calculerCentreY()).transform(pArrivee, pArrivee);
        
        return Line2D.ptSegDist((double)pDepart.x, pDepart.y,
                                (double)pArrivee.x, pArrivee.y,
                                (double)p.x, (double)p.y) <= TOLERANCE_CLIC;
    }
    
}
