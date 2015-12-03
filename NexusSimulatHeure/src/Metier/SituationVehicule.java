package Metier;

public class SituationVehicule {

    private double x;
    private double y;
    private int nbPassagers;
    private String circuit;
    
    public SituationVehicule(double x, double y, int nbPassagers, String circuit) {
        this.x = x;
        this.y = y;
        this.nbPassagers = nbPassagers;
        this.circuit = circuit;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getNombrePassagers()
    {
        return nbPassagers;
    }
    public String getCircuit() {
        return circuit;
    }
}
