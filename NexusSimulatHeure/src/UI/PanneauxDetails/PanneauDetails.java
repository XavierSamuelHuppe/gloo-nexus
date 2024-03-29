package UI.PanneauxDetails;

import UI.Application;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class PanneauDetails extends JPanel {
    
    protected Controleur.Simulateur simulateur;
    
    public PanneauDetails(Controleur.Simulateur sim)
    {
        this.simulateur = sim;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
 
    public Application obtenirApplication()
    {
        return (Application)SwingUtilities.getWindowAncestor(this);
    }
    
    public abstract void rafraichir();
}
