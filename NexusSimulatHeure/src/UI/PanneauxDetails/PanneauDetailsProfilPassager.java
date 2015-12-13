package UI.PanneauxDetails;

import Controleur.Simulateur;
import java.text.ParseException;
import java.util.*;
import Metier.Circuit.Circuit;
import Metier.Profil.ProfilPassagerFini;
import Metier.Profil.ProfilPassagerHeureFin;
import java.util.Observable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.text.DateFormat;
import Metier.Carte.Point;
import Metier.Exceptions.TrajetVideException;
import Metier.Exceptions.MauvaisPointArriveException;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

public class PanneauDetailsProfilPassager extends PanneauDetails implements java.util.Observer {

    private Metier.Profil.ProfilPassager ProfilPassagerMetierLie;
    private boolean modeCreationBool;
    
    public PanneauDetailsProfilPassager(Simulateur sim) {
        super(sim);
        initComponents();
        
        this.modeCreationBool = true;
        this.PanneauDistribution.setDistribution(this.simulateur.obtenirDistributionTempsGenerationPassagersDefaut());
        this.modeCreation(sim);
        
    }
    
    public PanneauDetailsProfilPassager(Metier.Profil.ProfilPassager p, Simulateur sim) {
        super(sim);
        initComponents();
        this.ProfilPassagerMetierLie = p;
        this.modeCreationBool = false;
        this.PanneauDistribution.setDistribution(p.getDistribution());
        
        rafraichir();
    }
    
    private void modeCreation(Simulateur sim){
        
        this.ChampHeureDepart.setText(sim.obtenirHeureDebutSimulation().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
        this.ChampHeureFin.setText(sim.obtenirHeureFinSimulation().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
        this.ChampNombreMax.setText("1");
        this.BoutonSupprimer.setEnabled(false);
    }
    
    @Override
    public void rafraichir() {
        
        this.ChampHeureDepart.setText(ProfilPassagerMetierLie.getHeureDepart().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
        if(this.ProfilPassagerMetierLie.getClass() == ProfilPassagerFini.class){
            ProfilPassagerFini ProfilCaster = (ProfilPassagerFini) ProfilPassagerMetierLie;
            this.ChampNombreMax.setText(String.valueOf(ProfilCaster.getNombreMax()));
            this.activerDesactiverControles(false);
        }else if (this.ProfilPassagerMetierLie.getClass() == ProfilPassagerHeureFin.class){
            ProfilPassagerHeureFin ProfilCaster = (ProfilPassagerHeureFin) ProfilPassagerMetierLie;
            this.ChampHeureFin.setText(ProfilCaster.getHeureFin().format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE));
            this.activerDesactiverControles(true);
        }
    }
    
    @Override
    public void update(Observable o, Object o1) {
        //this.rafraichir();
    }
    
    private void activerDesactiverControles(Boolean activer){
        this.ChampHeureFin.setEnabled(activer);
        this.RadioHeureFin.setEnabled(activer);
        this.RadioHeureFin.setSelected(activer);
        this.ChampNombreMax.setEnabled(!activer);
        this.RadioNombreMax.setEnabled(!activer);
        this.RadioNombreMax.setSelected(!activer);
    }
    
    private void sauvegarderNouveauProfil(){
        if(!valider())
            return;
        
        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);

        try
        {
            if(this.RadioHeureFin.isSelected()){
                LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
                this.obtenirApplication().getSimulateur().ajouterProfil(heureFin, heureDebut, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
            }
            if(this.RadioNombreMax.isSelected()){
                this.obtenirApplication().getSimulateur().ajouterProfil(Integer.parseInt(this.ChampNombreMax.getText()), heureDebut, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
            }
            this.obtenirApplication().repaint();
            this.obtenirApplication().viderPanneauDetails();
        }
        catch(TrajetVideException ex){
            JOptionPane.showMessageDialog(this.obtenirApplication(), "Le trajet ne peut pas être vide.", "Trajet vide", JOptionPane.ERROR_MESSAGE);
        }
        catch(MauvaisPointArriveException ex)
        {
            JOptionPane.showMessageDialog(this.obtenirApplication(), "Le trajet doit se terminer sur un arrêt.", "Mauvais point d'arrivée.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void sauvegarderProfilModifier(){
        if(!valider())
            return;
        
        LocalTime heureDebut = LocalTime.parse(this.ChampHeureDepart.getText(), UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
        
        if(this.RadioHeureFin.isSelected()){
            LocalTime heureFin = LocalTime.parse(this.ChampHeureFin.getText(),UI.Constantes.Formats.FORMAT_HEURE_COURANTE);
            this.obtenirApplication().getSimulateur().modifierProfil(ProfilPassagerMetierLie, heureFin, heureDebut, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        if(this.RadioNombreMax.isSelected()){
            this.obtenirApplication().getSimulateur().modifierProfil(ProfilPassagerMetierLie, Integer.parseInt(this.ChampNombreMax.getText()), heureDebut, PanneauDistribution.obtenirMin(), PanneauDistribution.obtenirMode(), PanneauDistribution.obtenirMax());
        }
        
        this.obtenirApplication().repaint();
        this.obtenirApplication().viderPanneauDetails();
    }
    
    private boolean valider()
    {
        String validations = "";
      
        if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureDepart.getText().trim()))
        {
            validations += "L'heure de début de génération des passagers doit être inscrite dans un format 00:00:00.\r\n";
        }
        if(RadioHeureFin.isSelected())
        {
            if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureFin.getText().trim()))
            {
                validations += "L'heure de fin de génération des passagers doit être inscrite dans un format 00:00:00.\r\n";
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
                validations += "Le nombre maximal de passagers à générer doit être un nombre positif.\r\n";
            }
        }
        
        validations += PanneauDistribution.validerValeurs();
        
        if(!validations.equals(""))
        {
            JOptionPane.showMessageDialog(this.obtenirApplication(), "Les erreurs suivantes ont été détectées : \r\n" + validations, "Erreur à la sauvegarde du profil passager", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ChampHeureDepart = new javax.swing.JTextField();
        HeureDepart = new javax.swing.JLabel();
        ChampHeureFin = new javax.swing.JTextField();
        HeureFin = new javax.swing.JLabel();
        BoutonSauvegarder = new javax.swing.JButton();
        RadioHeureFin = new javax.swing.JRadioButton();
        RadioNombreMax = new javax.swing.JRadioButton();
        ChampNombreMax = new javax.swing.JTextField();
        BoutonSupprimer = new javax.swing.JButton();
        PanneauDistribution = new UI.PanneauxDetails.PanneauDistribution();

        ChampHeureDepart.setText("ChampHeureDepart");

        HeureDepart.setText("Heure de départ");

        ChampHeureFin.setText("ChampHeureFin");

        HeureFin.setText("Fin du profil passager");

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });

        RadioHeureFin.setSelected(true);
        RadioHeureFin.setText("Heure de fin");
        RadioHeureFin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RadioHeureFinMouseClicked(evt);
            }
        });

        RadioNombreMax.setText("Nombre max");
        RadioNombreMax.setEnabled(false);
        RadioNombreMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RadioNombreMaxMouseClicked(evt);
            }
        });

        ChampNombreMax.setText("ChampNombreMax");
        ChampNombreMax.setEnabled(false);

        BoutonSupprimer.setText("Supprimer");
        BoutonSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSupprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(HeureDepart)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RadioNombreMax)
                            .addComponent(HeureFin)
                            .addComponent(RadioHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ChampHeureFin)
                            .addComponent(ChampNombreMax)))
                    .addComponent(ChampHeureDepart)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BoutonSauvegarder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BoutonSupprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 23, Short.MAX_VALUE))
                            .addComponent(PanneauDistribution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(HeureDepart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampHeureDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HeureFin)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ChampHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RadioHeureFin))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioNombreMax)
                    .addComponent(ChampNombreMax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanneauDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BoutonSupprimer)
                    .addComponent(BoutonSauvegarder)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 239, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 409, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSupprimerActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this.obtenirApplication(), "Désirez-vous vraiment retirer ce profil de passager?", "Retirer ce profil de passager?", JOptionPane.YES_NO_OPTION))
        {
            this.obtenirApplication().getSimulateur().retirerProfil(ProfilPassagerMetierLie);
            this.obtenirApplication().viderPanneauDetails();
            this.obtenirApplication().repaint();
        }
    }//GEN-LAST:event_BoutonSupprimerActionPerformed

    private void RadioNombreMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioNombreMaxMouseClicked
        this.activerDesactiverControles(false);
    }//GEN-LAST:event_RadioNombreMaxMouseClicked

    private void RadioHeureFinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RadioHeureFinMouseClicked
        this.activerDesactiverControles(true);
    }//GEN-LAST:event_RadioHeureFinMouseClicked

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        if(modeCreationBool == true){
            sauvegarderNouveauProfil();
        }
        else {
            sauvegarderProfilModifier();
        }
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSupprimer;
    private javax.swing.JTextField ChampHeureDepart;
    private javax.swing.JTextField ChampHeureFin;
    private javax.swing.JTextField ChampNombreMax;
    private javax.swing.JLabel HeureDepart;
    private javax.swing.JLabel HeureFin;
    private UI.PanneauxDetails.PanneauDistribution PanneauDistribution;
    private javax.swing.JRadioButton RadioHeureFin;
    private javax.swing.JRadioButton RadioNombreMax;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
