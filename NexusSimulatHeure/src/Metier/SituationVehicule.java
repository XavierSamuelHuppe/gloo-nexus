/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

/**
 *
 * @author The Vagrant Geek
 */
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

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }
    
}
