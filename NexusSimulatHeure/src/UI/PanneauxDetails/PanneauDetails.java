package UI.PanneauxDetails;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class PanneauDetails extends JPanel {
    public PanneauDetails()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    public abstract void rafraichir();
}
