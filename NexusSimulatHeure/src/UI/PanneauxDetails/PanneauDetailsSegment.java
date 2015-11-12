package UI.PanneauxDetails;

import UI.PanneauxDetails.PanneauDetails;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Observable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class PanneauDetailsSegment extends PanneauDetails implements java.util.Observer {

    private Metier.Carte.Segment segmentLie;
        
    public PanneauDetailsSegment(Metier.Carte.Segment s)
    {
        super();
        
        this.segmentLie = s;
        
        initialiserComponents();
        
        rafraichir();
    }
    
    private void initialiserComponents()
    {
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.add(GenerateurChamps.genererLibelle("SEGMENT!"));

        //Ajouter "Sauvegarder", "Annuler".
        
        this.revalidate();
    }
    
    @Override
    public void rafraichir() {

    }

    @Override
    public void update(Observable o, Object o1) {
        this.rafraichir();
    }
    
}
