package UI.PanneauxDetails;

import Controleur.Simulateur;
import java.util.Observable;
import javax.swing.JOptionPane;

public class PanneauDetailsPoint extends PanneauDetails implements java.util.Observer {

    private Metier.Carte.Point pointMetierLie;
    
    public PanneauDetailsPoint(Metier.Carte.Point p, Simulateur sim) {
        super(sim);
        initComponents();
        
        this.pointMetierLie = p;
        this.pointMetierLie.addObserver(this);
        
        rafraichir();
    }
    
    @Override
    public void rafraichir() {
        this.ChampLatitude.setText(String.format("%1$.6f", pointMetierLie.getPosition().getY()));
        this.ChampLongitude.setText(String.format("%1$.6f", pointMetierLie.getPosition().getX()));
        this.ChampNom.setText(pointMetierLie.getNom());
        
        if(this.pointMetierLie.estArret())
        {
            this.PanneauSources.setVisible(true);
            this.PanneauProfilsPassagers.setVisible(true);
            this.ListeSources.removeAllItems();
            this.ListeSources.addItem("");
            for(Metier.Source.Source s : this.pointMetierLie.getSources())
            {
                this.ListeSources.addItem(s);
            }
            this.ListeProfilsPassager.removeAllItems();
            this.ListeProfilsPassager.addItem("");
            for(Metier.Profil.ProfilPassager p : this.pointMetierLie.getProfilsPassagers())
            {
                this.ListeProfilsPassager.addItem(p);
            }
        }
        else
        {
            this.PanneauSources.setVisible(false);
            this.PanneauProfilsPassagers.setVisible(false);
        }
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

        jPanel2 = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ChampLatitude = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ChampLongitude = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ChampNom = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
        jPanel1 = new javax.swing.JPanel();
        BoutonSauvegarder = new javax.swing.JButton();
        BoutonSupprimer = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        PanneauSources = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel6 = new javax.swing.JPanel();
        LibelleSources = new javax.swing.JLabel();
        ListeSources = new javax.swing.JComboBox();
        PanneauProfilsPassagers = new javax.swing.JPanel();
        filler17 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler18 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jPanel11 = new javax.swing.JPanel();
        LibelleProfilPassager = new javax.swing.JLabel();
        ListeProfilsPassager = new javax.swing.JComboBox();
        filler16 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));

        setMaximumSize(new java.awt.Dimension(250, 32822));
        setMinimumSize(new java.awt.Dimension(250, 200));
        setPreferredSize(new java.awt.Dimension(250, 200));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(filler3, java.awt.BorderLayout.WEST);
        jPanel2.add(filler4, java.awt.BorderLayout.EAST);

        jPanel8.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setText("Latitude");
        jPanel8.add(jLabel2);

        ChampLatitude.setText("ChampLatitude");
        jPanel8.add(ChampLatitude);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        add(jPanel2);

        jPanel3.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(filler6, java.awt.BorderLayout.WEST);
        jPanel3.add(filler7, java.awt.BorderLayout.EAST);

        jPanel9.setLayout(new java.awt.GridLayout(2, 0));

        jLabel3.setText("Longitude");
        jPanel9.add(jLabel3);

        ChampLongitude.setText("ChampLongitude");
        jPanel9.add(ChampLongitude);

        jPanel3.add(jPanel9, java.awt.BorderLayout.CENTER);

        add(jPanel3);

        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(filler8, java.awt.BorderLayout.WEST);
        jPanel4.add(filler9, java.awt.BorderLayout.EAST);

        jPanel10.setLayout(new java.awt.GridLayout(2, 0));

        jLabel4.setText("Nom");
        jPanel10.add(jLabel4);

        ChampNom.setText("ChampNom");
        jPanel10.add(ChampNom);

        jPanel4.add(jPanel10, java.awt.BorderLayout.CENTER);

        add(jPanel4);
        add(filler1);
        add(filler12);

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
        add(filler2);

        PanneauSources.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        PanneauSources.setPreferredSize(new java.awt.Dimension(100, 50));
        PanneauSources.setLayout(new java.awt.BorderLayout());
        PanneauSources.add(filler5, java.awt.BorderLayout.WEST);
        PanneauSources.add(filler10, java.awt.BorderLayout.EAST);

        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        LibelleSources.setText("Sources");
        jPanel6.add(LibelleSources);

        ListeSources.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ListeSources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListeSourcesActionPerformed(evt);
            }
        });
        jPanel6.add(ListeSources);

        PanneauSources.add(jPanel6, java.awt.BorderLayout.CENTER);

        add(PanneauSources);

        PanneauProfilsPassagers.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        PanneauProfilsPassagers.setMinimumSize(new java.awt.Dimension(76, 40));
        PanneauProfilsPassagers.setPreferredSize(new java.awt.Dimension(100, 50));
        PanneauProfilsPassagers.setLayout(new java.awt.BorderLayout());
        PanneauProfilsPassagers.add(filler17, java.awt.BorderLayout.WEST);
        PanneauProfilsPassagers.add(filler18, java.awt.BorderLayout.EAST);

        jPanel11.setLayout(new java.awt.GridLayout(2, 0));

        LibelleProfilPassager.setText("Profils de passagers");
        jPanel11.add(LibelleProfilPassager);

        ListeProfilsPassager.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ListeProfilsPassager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListeProfilsPassagerActionPerformed(evt);
            }
        });
        jPanel11.add(ListeProfilsPassager);

        PanneauProfilsPassagers.add(jPanel11, java.awt.BorderLayout.CENTER);
        PanneauProfilsPassagers.add(filler16, java.awt.BorderLayout.LINE_END);

        add(PanneauProfilsPassagers);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        this.obtenirApplication().getSimulateur()
                .modifierPoint(this.pointMetierLie, 
                        Double.parseDouble(this.ChampLongitude.getText().replace(',', '.')), 
                        Double.parseDouble(this.ChampLatitude.getText().replace(',', '.')), this.ChampNom.getText());
        this.obtenirApplication().repaint();
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer ce point?", "Retirer ce point?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerPoint(this.pointMetierLie);
            this.obtenirApplication().repaint();
            this.obtenirApplication().viderPanneauDetails();
        }         
    }//GEN-LAST:event_BoutonSupprimerActionPerformed

    private void ListeSourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListeSourcesActionPerformed
        if(ListeSources.getSelectedItem() != null && !ListeSources.getSelectedItem().equals(""))
        {
            this.obtenirApplication().afficherPanneauDetailsSource((Metier.Source.Source)ListeSources.getSelectedItem());
        }
    }//GEN-LAST:event_ListeSourcesActionPerformed

    private void ListeProfilsPassagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListeProfilsPassagerActionPerformed
        if(ListeProfilsPassager.getSelectedItem() != null && !ListeProfilsPassager.getSelectedItem().equals(""))
        {
            this.obtenirApplication().afficherPanneauDetailsProfilPassager((Metier.Profil.ProfilPassager)ListeProfilsPassager.getSelectedItem());
        }
    }//GEN-LAST:event_ListeProfilsPassagerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private javax.swing.JTextField ChampLatitude;
    private javax.swing.JTextField ChampLongitude;
    private javax.swing.JTextField ChampNom;
    private javax.swing.JLabel LibelleProfilPassager;
    private javax.swing.JLabel LibelleSources;
    private javax.swing.JComboBox ListeProfilsPassager;
    private javax.swing.JComboBox ListeSources;
    private javax.swing.JPanel PanneauProfilsPassagers;
    private javax.swing.JPanel PanneauSources;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler16;
    private javax.swing.Box.Filler filler17;
    private javax.swing.Box.Filler filler18;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
