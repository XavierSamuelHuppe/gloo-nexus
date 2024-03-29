package UI;

import Controleur.Simulateur;
import javax.swing.JFrame;

public class DialogueStatistiques extends javax.swing.JDialog {

    private Simulateur simulateur;
    
    public DialogueStatistiques(JFrame frameParent, Simulateur sim) {
        super(frameParent, "Statistiques");
        this.simulateur = sim;
        initComponents();
        
        afficherStatistiques();
    }
    
    private void afficherStatistiques()
    {
        this.ZoneTexteStatistiques.setText(this.simulateur.obtenirStatistiques());
    }
           

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        LIbelleEntete = new javax.swing.JLabel();
        PanneauBoutons = new javax.swing.JPanel();
        BoutonFermer = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ZoneTexteStatistiques = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(800, 400));

        LIbelleEntete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LIbelleEntete.setText("Statistiques de l'exécution de la simulation");
        getContentPane().add(LIbelleEntete, java.awt.BorderLayout.PAGE_START);
        LIbelleEntete.getAccessibleContext().setAccessibleName("LibelleEntete");

        PanneauBoutons.setMinimumSize(new java.awt.Dimension(100, 20));
        PanneauBoutons.setPreferredSize(new java.awt.Dimension(400, 32));
        PanneauBoutons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        BoutonFermer.setLabel("Fermer");
        BoutonFermer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonFermerActionPerformed(evt);
            }
        });
        PanneauBoutons.add(BoutonFermer);

        getContentPane().add(PanneauBoutons, java.awt.BorderLayout.PAGE_END);

        ZoneTexteStatistiques.setColumns(20);
        ZoneTexteStatistiques.setRows(5);
        jScrollPane2.setViewportView(ZoneTexteStatistiques);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonFermerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonFermerActionPerformed
        this.dispose();
    }//GEN-LAST:event_BoutonFermerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonFermer;
    private javax.swing.JLabel LIbelleEntete;
    private javax.swing.JPanel PanneauBoutons;
    private javax.swing.JTextArea ZoneTexteStatistiques;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
