package UI;

import Metier.Circuit.*;
import javax.swing.*;


public class CircuitMenuItem extends JMenuItem{
    private Circuit circuit;
    
    public CircuitMenuItem(Circuit circuit, String nom){
        super(nom);
        this.circuit = circuit;
    }
    public Circuit getCircuit(){
        return circuit;
    }
}
