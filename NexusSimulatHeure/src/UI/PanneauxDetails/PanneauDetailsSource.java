
package UI.PanneauxDetails;

import java.util.*;
import Metier.Circuit.Circuit;
import java.util.Observable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.text.DateFormat;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;


public class PanneauDetailsSource extends PanneauDetails implements java.util.Observer {

    private Metier.Source.Source sourceMetierLie;
    private Controleur.Simulateur simulateur;
    
    public PanneauDetailsSource() {
        initComponents();
    }
    public PanneauDetailsSource(Metier.Source.Source s)
    {
        super();
        initComponents();
        this.simulateur = simulateur;

        this.sourceMetierLie = s;
        
        rafraichir();
    }
                                        
    @Override
    public void rafraichir() {
        List<Circuit> circuits = this.obtenirApplication().getSimulateur().circuitsPassantPar(this.sourceMetierLie.getPointDepart());
        this.ChampCircuit.setModel(new DefaultComboBoxModel(circuits.toArray()));
        this.ChampCircuit.setSelectedItem(this.sourceMetierLie.getCircuit());
        this.ChampFrequence.setText(String.valueOf(sourceMetierLie.getFrequence()));
        this.ChampHeureDepart.setText(sourceMetierLie.getheureDebut().toString());
        this.ChampPoint.setText(sourceMetierLie.getPointDepart().getNom());
        
    }
    @Override
    public void update(Observable o, Object o1) {
        this.rafraichir();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonGroupFinSource = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        ChampFrequence = new javax.swing.JTextField();
        Frequence = new javax.swing.JLabel();
        ChampPoint = new javax.swing.JTextField();
        PointDepart = new javax.swing.JLabel();
        ChampHeureDepart = new javax.swing.JTextField();
        HeureDepart = new javax.swing.JLabel();
        ChampHeureFin = new javax.swing.JTextField();
        HeureFin = new javax.swing.JLabel();
        BoutonSauvegarder = new javax.swing.JButton();
        RadioHeureFin = new javax.swing.JRadioButton();
        RadioNombreMax = new javax.swing.JRadioButton();
        ChampNombreMax = new javax.swing.JTextField();
        ChampCircuit = new javax.swing.JComboBox();
        BoutonSupprimer = new javax.swing.JButton();

        jLabel1.setText("Circuit");

        ChampFrequence.setText("ChampFrequence");

        Frequence.setText("Frequence");

        ChampPoint.setText("ChampPoint");
        ChampPoint.setEnabled(false);

        PointDepart.setText("Point de Départ");

        ChampHeureDepart.setText("ChampHeureDepart");

        HeureDepart.setText("Heure de départ");

        ChampHeureFin.setText("ChampHeureFin");

        HeureFin.setText("fin de la source");

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });

        ButtonGroupFinSource.add(RadioHeureFin);
        RadioHeureFin.setSelected(true);
        RadioHeureFin.setText("heure de fin");
        RadioHeureFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RadioHeureFinMouseClicked(evt);
            }
        });

        ButtonGroupFinSource.add(RadioNombreMax);
        RadioNombreMax.setText("Nombre max");
        RadioNombreMax.setEnabled(false);
        RadioNombreMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RadioNombreMaxMouseClicked(evt);
            }
        });

        ChampNombreMax.setText("ChampNombreMax");
        ChampNombreMax.setEnabled(false);

        ChampCircuit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        BoutonSupprimer.setText("Supprimer");
        BoutonSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSupprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ChampCircuit, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChampFrequence, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Frequence, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChampPoint, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PointDepart, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChampHeureDepart, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeureDepart, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadioHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RadioNombreMax)
                            .addComponent(HeureFin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ChampNombreMax, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(ChampHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BoutonSauvegarder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BoutonSupprimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampCircuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(Frequence)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PointDepart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HeureDepart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampHeureDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeureFin)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ChampHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RadioHeureFin))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioNombreMax)
                    .addComponent(ChampNombreMax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BoutonSauvegarder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BoutonSupprimer)
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        
             
        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText());
        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText());
            this.obtenirApplication().getSimulateur().modifierSource(this.sourceMetierLie, heureFin , this.sourceMetierLie.getPointDepart(), heureDebut, Double.parseDouble(this.ChampFrequence.getText()), this.sourceMetierLie.getCircuit());
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().modifierSource(this.sourceMetierLie, Integer.parseInt(this.ChampNombreMax.getText()) , this.sourceMetierLie.getPointDepart(), heureDebut, Double.parseDouble(this.ChampFrequence.getText()), this.sourceMetierLie.getCircuit());
        }
        
        this.obtenirApplication().repaint();
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void controlEnabler(Boolean bool){
        this.ChampHeureFin.setEnabled(bool);
        this.RadioHeureFin.setEnabled(bool);
        this.ChampNombreMax.setEnabled(!bool);
        this.RadioNombreMax.setEnabled(!bool);
    }
    
    private void RadioHeureFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioHeureFinMouseClicked
        this.controlEnabler(true);
    }//GEN-LAST:event_RadioHeureFinMouseClicked

    private void RadioNombreMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioNombreMaxMouseClicked
        this.controlEnabler(false);
    }//GEN-LAST:event_RadioNombreMaxMouseClicked

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer cette source?", "Retirer cette source?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerSource(sourceMetierLie);
            this.obtenirApplication().repaint();
        }  
    }//GEN-LAST:event_BoutonSupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private javax.swing.ButtonGroup ButtonGroupFinSource;
    private javax.swing.JComboBox ChampCircuit;
    private javax.swing.JTextField ChampFrequence;
    private javax.swing.JTextField ChampHeureDepart;
    private javax.swing.JTextField ChampHeureFin;
    private javax.swing.JTextField ChampNombreMax;
    private javax.swing.JTextField ChampPoint;
    private javax.swing.JLabel Frequence;
    private javax.swing.JLabel HeureDepart;
    private javax.swing.JLabel HeureFin;
    private javax.swing.JLabel PointDepart;
    private javax.swing.JRadioButton RadioHeureFin;
    private javax.swing.JRadioButton RadioNombreMax;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
