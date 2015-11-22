
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
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import java.time.format.DateTimeFormatter;


public class PanneauDetailsSource extends PanneauDetails implements java.util.Observer {

    private Metier.Source.Source sourceMetierLie;
    private Metier.Carte.Point pointMetierLie;
    private Controleur.Simulateur simulateur;
    private Metier.Circuit.Circuit circuitActuel;
    private boolean modeCreationBool;
        
    public PanneauDetailsSource(Simulateur sim, Metier.Carte.Point point) {
        super();
        initComponents();
        this.simulateur = sim;
        this.pointMetierLie = point;
        this.modeCreationBool = true;
        this.PanneauDistribution.setDistribution(this.simulateur.obtenirDistributionTempsGenerationVehiculeDefaut());
        this.modeCreation();
    }
    public PanneauDetailsSource(Metier.Source.Source s)
    {
        super();
        initComponents();
        this.simulateur = simulateur;
        this.pointMetierLie = s.getPointDepart();
        this.circuitActuel = s.getCircuit();
        this.sourceMetierLie = s;
        this.modeCreationBool = false;
        this.PanneauDistribution.setDistribution(s.getDistribution());
        
        rafraichir();
    }
             
    
    private void modeCreation(){
        try
        {
            this.ChampCircuit.removeAllItems();
            List<Circuit> circuits = this.simulateur.circuitsPassantPar(pointMetierLie);
            for (Circuit circuit: circuits){
                this.ChampCircuit.addItem(circuit);
            }
            
        }catch (Exception ex) {
               JOptionPane.showMessageDialog(this.obtenirApplication(),ex.toString());
        } 
        
        this.ChampCircuit.setEnabled(true);
        this.ChampHeureDepart.setText("00:00:00");
        this.ChampHeureFin.setText("00:00:00");
        this.ChampNombreMax.setText("0");
        this.BoutonSupprimer.setEnabled(false);
        
    }
    
    @Override
    public void rafraichir() {
        
        this.ChampCircuit.removeAllItems();
        List<Circuit> circuits = this.simulateur.circuitsPassantPar(pointMetierLie);
        for (Circuit circuit: circuits){
            this.ChampCircuit.addItem(circuit);
        }
        this.ChampCircuit.setSelectedItem(this.circuitActuel);
        this.ChampHeureDepart.setText(sourceMetierLie.getheureDebut().toString());
        if(this.sourceMetierLie.getClass() == SourceFinie.class){
            SourceFinie SourceCaster = (SourceFinie) sourceMetierLie;
            this.ChampNombreMax.setText(String.valueOf(SourceCaster.getNombreMax()));
            this.controlEnabler(false);
        }else if (this.sourceMetierLie.getClass() == SourceHeureFin.class){
            SourceHeureFin SourceCaster = (SourceHeureFin) sourceMetierLie;
            this.ChampHeureFin.setText(SourceCaster.getheureFin().toString());
            this.controlEnabler(true);
        }
        
    }
    @Override
    public void update(Observable o, Object o1) {
        this.rafraichir();
    }
    
    public void sauvegarderNouvelleSource(){
        if(!valider())
            return;
        
        this.circuitActuel =(Circuit) this.ChampCircuit.getSelectedItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(),formatter);

        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(),formatter);
            this.obtenirApplication().getSimulateur().ajouterSource(heureFin , this.pointMetierLie, heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().ajouterSource(Integer.parseInt(this.ChampNombreMax.getText()), this.pointMetierLie, heureDebut, this.circuitActuel, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        this.obtenirApplication().repaint();
        this.obtenirApplication().viderPanneauDetails();
    }
    
    public void sauvegarderSourceModifier(){
        if(!valider())
            return;
        
        this.circuitActuel =(Circuit) this.ChampCircuit.getSelectedItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(),formatter);
        
        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(),formatter);
            this.obtenirApplication().getSimulateur().modifierSource(sourceMetierLie, heureFin , this.pointMetierLie, heureDebut, this.circuitActuel);
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().modifierSource(sourceMetierLie, Integer.parseInt(this.ChampNombreMax.getText()), this.pointMetierLie, heureDebut, this.circuitActuel);
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
        
        if(!java.util.regex.Pattern.matches("^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9]):([0-5]?[0-9])$", ChampHeureDepart.getText()))
        {
            validations += "L'heure de début de génération des véhicules doit être inscrite dans un format 00:00:00.\r\n";
        }
        if(RadioHeureFin.isSelected())
        {
            if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureFin.getText()))
            {
                validations += "L'heure de fin de génération des véhicules doit être inscrite dans un format 00:00:00.\r\n";
            }
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ChampCircuit, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadioHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RadioNombreMax)
                            .addComponent(HeureFin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ChampHeureFin)
                            .addComponent(ChampNombreMax)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BoutonSauvegarder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BoutonSupprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ChampHeureDepart, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanneauDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(PanneauDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BoutonSupprimer)
                    .addComponent(BoutonSauvegarder)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        
        if(modeCreationBool == true){
            sauvegarderNouvelleSource();
        }else if(modeCreationBool == false)
        {
            sauvegarderSourceModifier();
        }
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void controlEnabler(Boolean bool){
        this.ChampHeureFin.setEnabled(bool);
        this.RadioHeureFin.setEnabled(bool);
        this.RadioHeureFin.setSelected(bool);
        this.ChampNombreMax.setEnabled(!bool);
        this.RadioNombreMax.setEnabled(!bool);
        this.RadioNombreMax.setSelected(!bool);
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
