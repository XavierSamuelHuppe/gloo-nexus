
package Metier;

/**
 *
 * @author Charles-André
 */
public class ElementTrajet {
    private Circuit circuit;
    private Point point;
    
    public ElementTrajet(Circuit circuit, Point point){
        this.circuit = circuit;
        this.point = point;
    }
    
    public Circuit getCircuit(){ 
        return circuit;
    }
    
    public Point getPoint() {
        return point;
    }
}
