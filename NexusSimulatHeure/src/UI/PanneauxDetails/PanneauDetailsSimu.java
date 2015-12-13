
package UI.PanneauxDetails;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PanneauDetailsSimu extends javax.swing.JDialog {

    //private Controleur.Simulateur simulateur;
    private Metier.Simulation.ParametreSimulation paramSimulation;
    private Controleur.Simulateur simulateur;
    
    public PanneauDetailsSimu()
    {   
        initComponents();
    }
    
    public PanneauDetailsSimu(JFrame frameParent, Controleur.Simulateur s) {
        super(frameParent);
        initComponents();
        
        this.simulateur = s;
        this.paramSimulation = s.getParametresSimulation();
        
        this.rafraichir();
    }
    
    public void rafraichir() {
        this.ChampNbJours.setValue(paramSimulation.getNombreJourSimulation());
        this.ChampHeureDebut.setText(paramSimulation.getHeureDebut().toString());
        this.ChampHeureFin.setText(paramSimulation.getHeureFin().toString());
        
        if(this.RadioMinutes.isSelected())
        {
            this.ChampDistSegmentMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMin()/60));
            this.ChampDistSegmentMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMax()/60));
            this.ChampDistSegmentMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMode()/60));

            this.ChampDistVehiculeMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMin()/60));
            this.ChampDistVehiculeMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMax()/60));
            this.ChampDistVehiculeMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMode()/60));

            this.ChampDistPassagerMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMin()/60));
            this.ChampDistPassagerMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMax()/60));
            this.ChampDistPassagerMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMode()/60));
            
        }else{
            this.ChampDistSegmentMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMin()));
            this.ChampDistSegmentMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMax()));
            this.ChampDistSegmentMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsTransitSegmentDefaut().getMode()));
        
            this.ChampDistVehiculeMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMin()));
            this.ChampDistVehiculeMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMax()));
            this.ChampDistVehiculeMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMode()));
        
            this.ChampDistPassagerMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMin()));
            this.ChampDistPassagerMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMax()));
            this.ChampDistPassagerMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMode()));
        }
        
        
    }
    
    private void changerTemps(String mode){
        if(mode.equals("MINUTES"))
        {
            this.ChampDistPassagerMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMin.getText()) / 60));
            this.ChampDistPassagerMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMax.getText()) / 60));
            this.ChampDistPassagerMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMode.getText()) / 60));
            
            this.ChampDistVehiculeMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMin.getText()) / 60));
            this.ChampDistVehiculeMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMax.getText()) / 60));
            this.ChampDistVehiculeMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMode.getText()) / 60));
            
            this.ChampDistSegmentMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMin.getText()) / 60));
            this.ChampDistSegmentMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMax.getText()) / 60));
            this.ChampDistSegmentMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMode.getText()) / 60));
            
            
        }else if(mode.equals("SECONDES")){
            this.ChampDistPassagerMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMin.getText()) * 60));
            this.ChampDistPassagerMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMax.getText()) * 60));
            this.ChampDistPassagerMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistPassagerMode.getText()) * 60));
            
            this.ChampDistVehiculeMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMin.getText()) * 60));
            this.ChampDistVehiculeMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMax.getText()) * 60));
            this.ChampDistVehiculeMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistVehiculeMode.getText()) * 60));
            
            this.ChampDistSegmentMin.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMin.getText()) * 60));
            this.ChampDistSegmentMax.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMax.getText()) * 60));
            this.ChampDistSegmentMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampDistSegmentMode.getText()) * 60));
            
        }
        
        
        
    }
    private boolean valider()
    {
        String validations = "";
      
        if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE_SIMULATION, ChampHeureDebut.getText()) &&
           !java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureDebut.getText()))
        {
            validations += "L'heure de début de génération des passagers doit être inscrite dans un format 00:00 ou 00:00:00.\r\n";
        }
        
        if(!java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE_SIMULATION, ChampHeureFin.getText()) &&
           !java.util.regex.Pattern.matches(UI.Constantes.Validations.REGEX_FORMAT_HEURE, ChampHeureFin.getText()))
        {
            validations += "L'heure de fin de génération des passagers doit être inscrite dans un format 00:00 ou 00:00:00.\r\n";
        }
        
        try{
            int nMax = Integer.parseInt(this.ChampNbJours.getValue().toString());
            if(nMax < 0)
                throw new NumberFormatException();
        }
        catch(NumberFormatException ex){
            validations += "Le nombre maximal de passagers à générer doit être un nombre positif.\r\n";
        }
        
        validations += distributionValiderValeurs();
        
        if(!validations.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Les erreurs suivantes ont été détectées : \r\n" + validations, "Erreur à la sauvegarde du profil passager", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
        
    }
    
    public String distributionValiderValeurs() {       
        String retour = "";
        long min = 0;
        long max = 0;
        long mode = 0;
        long min2 = 0;
        long max2 = 0;
        long mode2 = 0;
        long min3 = 0;
        long max3 = 0;
        long mode3 = 0;
        boolean nombresValides = true;
        try{
            min = Long.parseLong(this.ChampDistSegmentMin.getText());  
            min2 = Long.parseLong(this.ChampDistVehiculeMin.getText()); 
            min3 = Long.parseLong(this.ChampDistPassagerMin.getText());
        }
        catch(NumberFormatException ex){
            nombresValides = false;
            retour += "La valeur minimale est obligatoire et doit être un nombre entier.\r\n";
        }
        
        try{
            max = Long.parseLong(this.ChampDistSegmentMax.getText());  
            max2 = Long.parseLong(this.ChampDistVehiculeMax.getText()); 
            max3 = Long.parseLong(this.ChampDistPassagerMax.getText());
        }
        catch(NumberFormatException ex){
            nombresValides = false;
            retour += "La valeur maximale est obligatoire et doit être un nombre entier.\r\n";
        }
        
        try{
            mode = Long.parseLong(this.ChampDistSegmentMode.getText()); 
            mode2 = Long.parseLong(this.ChampDistVehiculeMode.getText()); 
            mode3 = Long.parseLong(this.ChampDistPassagerMode.getText());
        }
        catch(NumberFormatException ex){
            nombresValides = false;
            retour += "Le mode est obligatoire et doit être un nombre entier.\r\n";
        }
        
        if(nombresValides)
        {
            if(min > mode || min > max)
            {
                retour += "La valeur minimale doit être plus petite que le mode et la valeur maximale.\r\n";
            }
            if(mode < min  || max < mode)
            {
                retour += "Le mode doit être en la valeur minimale et la valeur maximale.\r\n";
            }
            if(max < mode || max < min)
            {
                retour += "La valeur maximale doit être plus grande que la valeur minimale et le mode.\r\n";
            }
        }
        
        return retour;
    }
    
    private void sauvegarder(){
        if(!valider())
            return;
        simulateur.modifierNombreJourSimulation((int)this.ChampNbJours.getValue());
        simulateur.modifierHeureDebut(LocalTime.parse(this.ChampHeureDebut.getText(), DateTimeFormatter.ISO_LOCAL_TIME));
        simulateur.modifierHeureFin(LocalTime.parse(this.ChampHeureFin.getText(), DateTimeFormatter.ISO_LOCAL_TIME));
        if(this.RadioMinutes.isSelected()){
            simulateur.modifierDistributionTempsTransitSegment(
                                        Double.parseDouble(this.ChampDistSegmentMin.getText())*60,
                                        Double.parseDouble(this.ChampDistSegmentMode.getText())*60,
                                        Double.parseDouble(this.ChampDistSegmentMax.getText())*60);
            simulateur.modifierDistributionTempsGenerationVehicule(
                                        Double.parseDouble(this.ChampDistVehiculeMin.getText())*60,
                                        Double.parseDouble(this.ChampDistVehiculeMode.getText())*60,
                                        Double.parseDouble(this.ChampDistVehiculeMax.getText())*60);
            simulateur.modifierDistributionTempsGenerationPassager(
                                        Double.parseDouble(this.ChampDistPassagerMin.getText())*60,
                                        Double.parseDouble(this.ChampDistPassagerMode.getText())*60,
                                        Double.parseDouble(this.ChampDistPassagerMax.getText())*60);
        }else {
            simulateur.modifierDistributionTempsTransitSegment(
                                        Double.parseDouble(this.ChampDistSegmentMin.getText()),
                                        Double.parseDouble(this.ChampDistSegmentMode.getText()),
                                        Double.parseDouble(this.ChampDistSegmentMax.getText()));
            simulateur.modifierDistributionTempsGenerationVehicule(
                                        Double.parseDouble(this.ChampDistVehiculeMin.getText()),
                                        Double.parseDouble(this.ChampDistVehiculeMode.getText()),
                                        Double.parseDouble(this.ChampDistVehiculeMax.getText()));
            simulateur.modifierDistributionTempsGenerationPassager(
                                        Double.parseDouble(this.ChampDistPassagerMin.getText()),
                                        Double.parseDouble(this.ChampDistPassagerMode.getText()),
                                        Double.parseDouble(this.ChampDistPassagerMax.getText()));
        }
        
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ChampNbJours = new javax.swing.JSpinner();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ChampHeureDebut = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ChampHeureFin = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        RadioSeconde = new javax.swing.JRadioButton();
        RadioMinutes = new javax.swing.JRadioButton();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ChampDistSegmentMin = new javax.swing.JTextField();
        ChampDistSegmentMax = new javax.swing.JTextField();
        ChampDistSegmentMode = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ChampDistVehiculeMin = new javax.swing.JTextField();
        ChampDistVehiculeMax = new javax.swing.JTextField();
        ChampDistVehiculeMode = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        ChampDistPassagerMin = new javax.swing.JTextField();
        ChampDistPassagerMax = new javax.swing.JTextField();
        ChampDistPassagerMode = new javax.swing.JTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 15));
        jPanel5 = new javax.swing.JPanel();
        BoutonSauvegarder = new javax.swing.JButton();
        BoutonRetour = new javax.swing.JButton();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setMinimumSize(new java.awt.Dimension(410, 200));
        setName(""); // NOI18N
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Nb Jours");
        jPanel1.add(jLabel1);

        ChampNbJours.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        jPanel1.add(ChampNbJours);
        jPanel1.add(filler4);
        jPanel1.add(filler7);

        getContentPane().add(jPanel1);
        getContentPane().add(filler2);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setText("Heure Début");
        jPanel3.add(jLabel3);

        ChampHeureDebut.setText("ChampHeureDebut");
        jPanel3.add(ChampHeureDebut);

        jLabel4.setText("Heure Fin");
        jPanel3.add(jLabel4);

        ChampHeureFin.setText("ChampHeureFin");
        jPanel3.add(ChampHeureFin);

        getContentPane().add(jPanel3);

        jPanel4.setAutoscrolls(true);
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setText("Temps:");
        jPanel4.add(jLabel12);

        buttonGroup1.add(RadioSeconde);
        RadioSeconde.setSelected(true);
        RadioSeconde.setText("Secondes");
        RadioSeconde.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadioSecondeItemStateChanged(evt);
            }
        });
        jPanel4.add(RadioSeconde);

        buttonGroup1.add(RadioMinutes);
        RadioMinutes.setText("Minutes");
        RadioMinutes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadioMinutesItemStateChanged(evt);
            }
        });
        jPanel4.add(RadioMinutes);
        jPanel4.add(filler11);

        getContentPane().add(jPanel4);
        getContentPane().add(filler1);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Distributions par défaut");
        jLabel5.setToolTipText("");
        jPanel2.add(jLabel5);

        getContentPane().add(jPanel2);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel8.setText(" ");
        jLabel8.setToolTipText("");
        jPanel9.add(jLabel8);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Min");
        jPanel9.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Max");
        jPanel9.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Mode");
        jPanel9.add(jLabel11);

        getContentPane().add(jPanel9);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel2.setText("Transit Segment");
        jLabel2.setToolTipText("");
        jPanel6.add(jLabel2);

        ChampDistSegmentMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistSegmentMin.setText("5");
        ChampDistSegmentMin.setVerifyInputWhenFocusTarget(false);
        jPanel6.add(ChampDistSegmentMin);

        ChampDistSegmentMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistSegmentMax.setText("5");
        jPanel6.add(ChampDistSegmentMax);

        ChampDistSegmentMode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistSegmentMode.setText("5");
        jPanel6.add(ChampDistSegmentMode);

        getContentPane().add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel6.setText("Gen. Véhicules");
        jLabel6.setToolTipText("");
        jPanel7.add(jLabel6);

        ChampDistVehiculeMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistVehiculeMin.setText("5");
        ChampDistVehiculeMin.setVerifyInputWhenFocusTarget(false);
        jPanel7.add(ChampDistVehiculeMin);

        ChampDistVehiculeMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistVehiculeMax.setText("5");
        jPanel7.add(ChampDistVehiculeMax);

        ChampDistVehiculeMode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistVehiculeMode.setText("5");
        jPanel7.add(ChampDistVehiculeMode);

        getContentPane().add(jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        jLabel7.setText("Gen. Passagers");
        jLabel7.setToolTipText("");
        jPanel8.add(jLabel7);

        ChampDistPassagerMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistPassagerMin.setText("5");
        ChampDistPassagerMin.setVerifyInputWhenFocusTarget(false);
        jPanel8.add(ChampDistPassagerMin);

        ChampDistPassagerMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistPassagerMax.setText("5");
        jPanel8.add(ChampDistPassagerMax);

        ChampDistPassagerMode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChampDistPassagerMode.setText("5");
        jPanel8.add(ChampDistPassagerMode);

        getContentPane().add(jPanel8);
        getContentPane().add(filler3);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });
        jPanel5.add(BoutonSauvegarder);

        BoutonRetour.setText("Retour");
        BoutonRetour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonRetourActionPerformed(evt);
            }
        });
        jPanel5.add(BoutonRetour);

        getContentPane().add(jPanel5);
        getContentPane().add(filler10);
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        sauvegarder();
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonRetourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonRetourActionPerformed
        this.dispose();
        
    }//GEN-LAST:event_BoutonRetourActionPerformed

    private void RadioSecondeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadioSecondeItemStateChanged
        if(this.RadioSeconde.isSelected()){
            changerTemps("SECONDES");
        }
    }//GEN-LAST:event_RadioSecondeItemStateChanged

    private void RadioMinutesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadioMinutesItemStateChanged
        if(this.RadioMinutes.isSelected()){
            changerTemps("MINUTES");
        }
    }//GEN-LAST:event_RadioMinutesItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonRetour;
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JTextField ChampDistPassagerMax;
    private javax.swing.JTextField ChampDistPassagerMin;
    private javax.swing.JTextField ChampDistPassagerMode;
    private javax.swing.JTextField ChampDistSegmentMax;
    private javax.swing.JTextField ChampDistSegmentMin;
    private javax.swing.JTextField ChampDistSegmentMode;
    private javax.swing.JTextField ChampDistVehiculeMax;
    private javax.swing.JTextField ChampDistVehiculeMin;
    private javax.swing.JTextField ChampDistVehiculeMode;
    private javax.swing.JTextField ChampHeureDebut;
    private javax.swing.JTextField ChampHeureFin;
    private javax.swing.JSpinner ChampNbJours;
    private javax.swing.JRadioButton RadioMinutes;
    private javax.swing.JRadioButton RadioSeconde;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables

}
