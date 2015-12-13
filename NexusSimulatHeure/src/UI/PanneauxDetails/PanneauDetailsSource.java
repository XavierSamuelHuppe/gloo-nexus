
package UI.PanneauxDetails;

import java.text.ParseException;
import Controleur.Simulateur;
import java.util.*;
import Metier.Circuit.Circuit;
import Metier.Source.SourceFinie;
import Metier.Source.SourceHeureFin;
import java.util.Observable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.text.DateFormat;
import Metier.Carte.Point;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;


public class PanneauDetailsSource extends PanneauDetails implements java.util.Observer {

    private Metier.Source.Source sourceMetierLie;
    private Metier.Carte.Point pointMetierLie;
    private Metier.Circuit.Circuit circuitActuel;
    private Simulateur simulateur;
    private boolean modeCreationBool;
        
    public PanneauDetailsSource(Metier.Carte.Point point, Simulateur sim) {
        super();
        initComponents();
        
        this.pointMetierLie = point;
        this.modeCreationBool = true;
        this.simulateur = sim;
        this.PanneauDistribution.setDistribution(this.simulateur.obtenirDistributionTempsGenerationVehiculeDefaut());
        this.modeCreation(sim);
    }
    
    public PanneauDetailsSource(Metier.Source.Source s, Simulateur sim) {
        super();
        initComponents();
        this.simulateur = sim;
        this.pointMetierLie = s.getPointDepart();
        this.circuitActuel = s.getCircuit();
        this.sourceMetierLie = s;
        this.modeCreationBool = false;
        this.PanneauDistribution.setDistribution(s.getDistribution());
        
        rafraichir();
    }
    
    private void modeCreation(Simulateur sim){
 
        this.ChampCircuit.removeAllItems();
        List<Circuit> circuits = sim.circuitsPassantPar(pointMetierLie);
        if(circuits.size() > 0)
        {
            for (Circuit circuit: circuits){
                this.ChampCircuit.addItem(circuit);
            }
            this.simulateur.activerCircuit((Metier.Circuit.Circuit)this.ChampCircuit.getSelectedItem());
            ChampCircuit.addActionListener(actionListenerChampCircuit);

            this.simulateur.selectionnerPoint(this.pointMetierLie);


            this.ChampCircuit.setEnabled(true);
            this.ChampHeureDepart.setText(sim.obtenirHeureDebutSimulation().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
            this.ChampHeureFin.setText(sim.obtenirHeureFinSimulation().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
            this.ChampNombreMax.setText("1");
            this.BoutonSupprimer.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(this.obtenirApplication(), "L!!!", "Point de départ de trajet invalide", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private final ActionListener actionListenerChampCircuit = new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
             ChampCircuitActionPerformed(evt);
        }
    };
    
    private void ChampCircuitActionPerformed(java.awt.event.ActionEvent evt)
    {
        this.simulateur.activerCircuit((Metier.Circuit.Circuit)ChampCircuit.getSelectedItem());
    }
    
    
    @Override
    public void rafraichir() {
        this.ChampCircuit.removeAllItems();
        List<Circuit> circuits = this.simulateur.circuitsPassantPar(pointMetierLie);
        ChampCircuit.removeActionListener(actionListenerChampCircuit);
        for (Circuit circuit: circuits){
            this.ChampCircuit.addItem(circuit);
        }
        ChampCircuit.addActionListener(actionListenerChampCircuit);
        this.ChampCircuit.setSelectedItem(this.circuitActuel);

        this.simulateur.activerCircuit((Metier.Circuit.Circuit)this.ChampCircuit.getSelectedItem());
        this.simulateur.selectionnerPoint(this.pointMetierLie);
            
        this.ChampHeureDepart.setText(sourceMetierLie.getheureDebut().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
        if(this.sourceMetierLie.getClass() == SourceFinie.class){
            SourceFinie SourceCaster = (SourceFinie) sourceMetierLie;
            this.ChampNombreMax.setText(String.valueOf(SourceCaster.getNombreMax()));
            this.activerDesactiverControles(false);
        }else if (this.sourceMetierLie.getClass() == SourceHeureFin.class){
            SourceHeureFin SourceCaster = (SourceHeureFin) sourceMetierLie;
            this.ChampHeureFin.setText(SourceCaster.getheureFin().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
            this.activerDesactiverControles(true);
        }
    }
    @Override
    public void update(Observable o, Object o1) {
        this.rafraichir();
    }
    
    private void sauvegarderNouvelleSource(){
        if(!valider())
            return;
        
        this.circuitActuel =(Circuit) this.ChampCircuit.getSelectedItem();

        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);

        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
            this.obtenirApplication().getSimulateur().ajouterSource(heureFin, this.pointMetierLie, heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().ajouterSource(Integer.parseInt(this.ChampNombreMax.getText()), this.pointMetierLie, heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        this.obtenirApplication().repaint();
        this.obtenirApplication().viderPanneauDetails();
    }
    
    private void sauvegarderSourceModifier(){
        if(!valider())
            return;
        
        this.circuitActuel =(Circuit) this.ChampCircuit.getSelectedItem();

        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
        
        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(),UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
            this.obtenirApplication().getSimulateur().modifierSource(sourceMetierLie, heureFin, heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().modifierSource(sourceMetierLie, Integer.parseInt(this.ChampNombreMax.getText()), heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        
        this.obtenirApplication().repaint();
        this.obtenirApplication().viderPanneauDetails();
    }
    
    private boolean valider()
    {
        String validations = "";
        if(ChampCircuit.getSelectedItem() == null)
        {
            validations += "Une source de véhicule doit absolument être lié à un circuit.\r\n";
        }
        
        if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureDepart.getText()))
        {
            validations += "L'heure de début de génération des véhicules doit être inscrite dans un format 00:00:00.\r\n";
        }
        if(RadioHeureFin.isSelected())
        {
            if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureFin.getText()))
            {
                validations += "L'heure de fin de génération des véhicules doit être inscrite dans un format 00:00:00.\r\n";
            }
//            else
//            {
//                if(LocalTime.parse(this.ChampHeureDepart.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE).isAfter(LocalTime.parse(this.ChampHeureFin.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE)))
//                {
//                    validations += "L'heure de début de génération des véhicules doit précédé l'heure de fin de génération des véhicules.\r\n";
//                }
//            }
        }
        if(this.RadioNombreMax.isSelected()){
            try{
                int nMax = Integer.parseInt(this.ChampNombreMax.getText());
                if(nMax < 0)
                    throw new NumberFormatException();
            }
            catch(NumberFormatException ex){
                validations += "Le nombre maximal de véhicules à générer doit être un nombre positif.\r\n";
            }
        }
        
        validations += PanneauDistribution.validerValeurs();
        
        if(!validations.equals(""))
        {
            JOptionPane.showMessageDialog(this.obtenirApplication(), "Les erreurs suivantes ont été détectées : \r\n" + validations, "Erreur à la sauvegarde de la source", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonGroupFinSource = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
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
        PanneauDistribution = new UI.PanneauxDetails.PanneauDistribution();

        jLabel1.setText("Circuit");

        ChampHeureDepart.setText("ChampHeureDepart");

        HeureDepart.setText("Heure de départ");

        ChampHeureFin.setText("ChampHeureFin");

        HeureFin.setText("Fin de la source");

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });

        ButtonGroupFinSource.add(RadioHeureFin);
        RadioHeureFin.setSelected(true);
        RadioHeureFin.setText("Heure de fin");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeureDepart, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChampCircuit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadioNombreMax)
                            .addComponent(HeureFin)
                            .addComponent(RadioHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ChampHeureFin)
                            .addComponent(ChampNombreMax)))
                    .addComponent(ChampHeureDepart)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BoutonSauvegarder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BoutonSupprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(PanneauDistribution, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampCircuit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanneauDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BoutonSupprimer)
                    .addComponent(BoutonSauvegarder)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        
        if(modeCreationBool == true){
            sauvegarderNouvelleSource();
        }
        else {
            sauvegarderSourceModifier();
        }
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void activerDesactiverControles(Boolean activer){
        this.ChampHeureFin.setEnabled(activer);
        this.RadioHeureFin.setEnabled(activer);
        this.RadioHeureFin.setSelected(activer);
        this.ChampNombreMax.setEnabled(!activer);
        this.RadioNombreMax.setEnabled(!activer);
        this.RadioNombreMax.setSelected(!activer);
    }
    
    private void RadioHeureFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioHeureFinMouseClicked
        this.activerDesactiverControles(true);
    }//GEN-LAST:event_RadioHeureFinMouseClicked

    private void RadioNombreMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioNombreMaxMouseClicked
        this.activerDesactiverControles(false);
    }//GEN-LAST:event_RadioNombreMaxMouseClicked

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer cette source?", "Retirer cette source?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerSource(sourceMetierLie);
            this.obtenirApplication().viderPanneauDetails();
            this.obtenirApplication().repaint();
        }  
    }//GEN-LAST:event_BoutonSupprimerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private javax.swing.ButtonGroup ButtonGroupFinSource;
    private javax.swing.JComboBox ChampCircuit;
    private javax.swing.JTextField ChampHeureDepart;
    private javax.swing.JTextField ChampHeureFin;
    private javax.swing.JTextField ChampNombreMax;
    private javax.swing.JLabel HeureDepart;
    private javax.swing.JLabel HeureFin;
    private UI.PanneauxDetails.PanneauDistribution PanneauDistribution;
    private javax.swing.JRadioButton RadioHeureFin;
    private javax.swing.JRadioButton RadioNombreMax;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
