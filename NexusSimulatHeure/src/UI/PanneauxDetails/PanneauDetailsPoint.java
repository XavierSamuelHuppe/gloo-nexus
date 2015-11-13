package UI.PanneauxDetails;

import UI.PanneauxDetails.PanneauDetails;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Observable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class PanneauDetailsPoint extends PanneauDetails implements java.util.Observer {

    private Metier.Carte.Point pointLie;
    
    private javax.swing.JTextField ChampTexteNom;
    private javax.swing.JTextField ChampTexteLatitude;
    private javax.swing.JTextField ChampTexteLongitude;
    
    public PanneauDetailsPoint(Metier.Carte.Point p)
    {
        super();
        
        this.pointLie = p;
        
        initialiserComponents();
        
        rafraichir();
    }
    
    private void initialiserComponents()
    {
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.add(GenerateurChamps.genererLibelle("Nom"));
        
        this.ChampTexteNom = GenerateurChamps.genererChampTexte();
        this.add(ChampTexteNom);
        
        this.add(Box.createRigidArea(new Dimension(500,10)));

        this.add(GenerateurChamps.genererLibelle("Latitude"));
        
        this.ChampTexteLatitude = GenerateurChamps.genererChampTexte();
        this.ChampTexteLatitude.setText(String.format("%1$f", pointLie.getPosition().getY()));
        this.add(ChampTexteLatitude);
        
        this.add(Box.createRigidArea(new Dimension(500,10)));
        
        this.add(GenerateurChamps.genererLibelle("Longitude"));
        
        this.ChampTexteLongitude = GenerateurChamps.genererChampTexte();
        this.ChampTexteLongitude.setText(String.format("%1$f", pointLie.getPosition().getX()));
        this.add(ChampTexteLongitude);

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
