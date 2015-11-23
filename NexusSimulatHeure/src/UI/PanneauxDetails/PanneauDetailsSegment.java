package UI.PanneauxDetails;

import java.util.Observable;
import javax.swing.JOptionPane;

public class PanneauDetailsSegment extends PanneauDetails implements java.util.Observer {

    private Metier.Carte.Segment segmentMetierLie;
    
    public PanneauDetailsSegment(Metier.Carte.Segment s)
    {
        super();
        initComponents();

        this.segmentMetierLie = s;
        this.segmentMetierLie.addObserver(this);
        
        rafraichir();
    }
    
    @Override
    public void rafraichir() {
        this.PanneauDistribution.setDistribution(this.segmentMetierLie.getDistribution());
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

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel2 = new javax.swing.JPanel();
        PanneauDistribution = new UI.PanneauxDetails.PanneauDistribution();
        jPanel1 = new javax.swing.JPanel();
        BoutonSauvegarder = new javax.swing.JButton();
        BoutonSupprimer = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());
        add(filler1, java.awt.BorderLayout.WEST);
        add(filler2, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        PanneauDistribution.setAlignmentX(1.0F);
        PanneauDistribution.setMinimumSize(new java.awt.Dimension(400, 153));
        jPanel2.add(PanneauDistribution);

        jPanel1.setMaximumSize(new java.awt.Dimension(2000000, 23));

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

        jPanel2.add(jPanel1);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        String validationDistribution = PanneauDistribution.validerValeurs();
        if(validationDistribution.isEmpty())
        {
            this.obtenirApplication().getSimulateur().modifierSegment(segmentMetierLie, PanneauDistribution.obtenirMin() , PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());    
        }
        else
        {
            JOptionPane.showMessageDialog(this.obtenirApplication(), "Les erreurs suivantes ont été détectées dans les paramètres de la distribution : \r\n" + validationDistribution, "Erreur dans les paramètres de la distribution", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer ce segment?", "Retirer ce segment?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerSegment(this.segmentMetierLie);
            this.obtenirApplication().viderPanneauDetails();
        }   
    }//GEN-LAST:event_BoutonSupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private UI.PanneauxDetails.PanneauDistribution PanneauDistribution;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
