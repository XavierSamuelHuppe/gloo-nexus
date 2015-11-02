/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.PanneauxDetails;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author The Vagrant Geek
 */
public abstract class PanneauDetails extends JPanel {
    public PanneauDetails()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
    
    public abstract void rafraichir();
}
