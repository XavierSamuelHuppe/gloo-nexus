package UI.PanneauxDetails;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GenerateurChamps {
//    public static JPanel genererChampTexte(String libelle)
//    {
//        JPanel panneau = new JPanel();
//        panneau.setLayout(new BoxLayout(panneau, BoxLayout.PAGE_AXIS));
//    }
    
    public static JLabel genererLibelle(String contenu)
    {
        JLabel lbl = new JLabel();
        lbl.setText(contenu);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBackground(Color.yellow);
        lbl.setOpaque(true);
        //lbl.setPreferredSize(new Dimension(100, 20));
        //lbl.setMinimumSize(lbl.getPreferredSize());
        //lbl.setMaximumSize(new Dimension(500, 20));
        return lbl;
    }
    
    public static JTextField genererChampTexte()
    {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(100, 20));
        tf.setMinimumSize(tf.getPreferredSize());
        tf.setMaximumSize(new Dimension(500, 20));
        return tf;
    }
}
