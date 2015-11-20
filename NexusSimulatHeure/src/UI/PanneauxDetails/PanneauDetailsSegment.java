/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.PanneauxDetails;

import java.util.Observable;
import javax.swing.JOptionPane;

/**
 *
 * @author The Vagrant Geek
 */
public class PanneauDetailsSegment extends PanneauDetails implements java.util.Observer {

    private Metier.Carte.Segment segmentMetierLie;
    private UI.Segment segmentUILie;
    private Controleur.Simulateur simulateur;
    
    /**
     * Creates new form PanneauDetailsPoint2
     */
    public PanneauDetailsSegment() {
        initComponents();
    }
    
    public PanneauDetailsSegment(Metier.Carte.Segment s, UI.Segment sUI)
    {
        super();
        initComponents();

        this.segmentMetierLie = s;
        this.segmentMetierLie.addObserver(this);
        
        this.segmentUILie = sUI;
        
        rafraichir();
    }
    
    @Override
    public void rafraichir() {
//        this.ChampLatitude.setText(String.format("%1$f", pointMetierLie.getPosition().getY()));
//        this.ChampLongitude.setText(String.format("%1$f", pointMetierLie.getPosition().getX()));
//        this.ChampNom.setText(pointMetierLie.getNom());
    }

    @Override
    public void update(Observable o, Object o1) {
        this.rafraichir();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauDistribution = new UI.PanneauxDetails.PanneauDistribution();
        jPanel1 = new javax.swing.JPanel();
        BoutonSauvegarder = new javax.swing.JButton();
        BoutonSupprimer = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
        add(PanneauDistribution);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });
        jPanel1.add(BoutonSauvegarder);

        BoutonSupprimer.setText("Supprimer");
        BoutonSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSupprimerActionPerformed(evt);
            }
        });
        jPanel1.add(BoutonSupprimer);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer ce segment?", "Retirer ce segment?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerSegment(this.segmentMetierLie);
            this.obtenirApplication().viderPanneauDetails();
            this.obtenirApplication().repaint();
        }   
    }//GEN-LAST:event_BoutonSupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private UI.PanneauxDetails.PanneauDistribution PanneauDistribution;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
