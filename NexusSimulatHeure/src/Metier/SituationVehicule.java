package Metier;

public class SituationVehicule {

    private double x;
    private double y;
    private String circuit;
    
    public SituationVehicule(double x, double y, String circuit) {
        this.x = x;
        this.y = y;
        this.circuit = circuit;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getCircuit() {
        return circuit;
    }
}
