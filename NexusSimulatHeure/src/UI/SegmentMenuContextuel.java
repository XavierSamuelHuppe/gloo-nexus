package UI;

import Controleur.Simulateur;
import java.util.*;
import javax.swing.*;
import Metier.Circuit.*;
import Metier.Carte.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SegmentMenuContextuel extends JPopupMenu implements ActionListener {
    private Simulateur simulateur;
    private Application application;
    
    public SegmentMenuContextuel(Simulateur simulateur, Application app, Metier.Carte.Segment segment){
        this.simulateur = simulateur;
        this.application = app;
        List<Circuit> circuitsAAfficher = simulateur.circuitsPassantPar(segment);
        for(Circuit c: circuitsAAfficher){
            CircuitMenuItem item = new CircuitMenuItem(c, c.getNom());
            item.addActionListener(this);
            add(item);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof CircuitMenuItem){
            CircuitMenuItem item = (CircuitMenuItem)(e.getSource());
            simulateur.activerCircuit(item.getCircuit());
            application.afficherPanneauDetailsCircuitExistant(simulateur.getCircuitActif());
        }
    }
}
