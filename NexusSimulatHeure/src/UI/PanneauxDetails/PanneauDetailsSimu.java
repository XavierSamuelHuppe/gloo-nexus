
package UI.PanneauxDetails;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PanneauDetailsSimu extends PanneauDetails{

    //private Controleur.Simulateur simulateur;
    private Metier.Simulation.ParametreSimulation paramSimulation;
    private Controleur.Simulateur simulateur;
    
    public PanneauDetailsSimu()
    {
        initComponents();
    }
    
    public PanneauDetailsSimu(Controleur.Simulateur s) {
        super();
        initComponents();
        
        this.simulateur = s;
        this.paramSimulation = s.getParametresSimulation();
        
        this.rafraichir();
    }
    
    public void rafraichir() {
        this.ChampNbJours.setValue(paramSimulation.getNombreJourSimulation());
        this.ChampHeureDebut.setText(paramSimulation.getHeureDebut().toString());
        this.ChampHeureFin.setText(paramSimulation.getHeureFin().toString());
        
        this.ChampDistSegmentMin.setText(((Double)paramSimulation.getDistributionTempsTransitSegmentDefaut().getMin()).toString());
        this.ChampDistSegmentMax.setText(((Double)paramSimulation.getDistributionTempsTransitSegmentDefaut().getMax()).toString());
        this.ChampDistSegmentMode.setText(((Double)paramSimulation.getDistributionTempsTransitSegmentDefaut().getMode()).toString());
        
        this.ChampDistVehiculeMin.setText(((Double)paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMin()).toString());
        this.ChampDistVehiculeMax.setText(((Double)paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMax()).toString());
        this.ChampDistVehiculeMode.setText(((Double)paramSimulation.getDistributionTempsGenerationVehiculeDefaut().getMode()).toString());
        
        this.ChampDistPassagerMin.setText(((Double)paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMin()).toString());
        this.ChampDistPassagerMax.setText(((Double)paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMax()).toString());
        this.ChampDistPassagerMode.setText(((Double)paramSimulation.getDistributionTempsGenerationPassagerDefaut().getMode()).toString());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ChampNbJours = new javax.swing.JSpinner();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ChampHeureDebut = new javax.swing.JTextField();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ChampHeureFin = new javax.swing.JTextField();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
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

        setMinimumSize(new java.awt.Dimension(410, 200));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(410, 200));
        setRequestFocusEnabled(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setLayout(new java.awt.GridLayout());

        jLabel1.setText("Nb Jours");
        jPanel1.add(jLabel1);

        ChampNbJours.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        jPanel1.add(ChampNbJours);
        jPanel1.add(filler4);
        jPanel1.add(filler7);

        add(jPanel1);
        add(filler2);

        jPanel3.setLayout(new java.awt.GridLayout());

        jLabel3.setText("Heure Début");
        jPanel3.add(jLabel3);

        ChampHeureDebut.setText("ChampHeureDebut");
        jPanel3.add(ChampHeureDebut);
        jPanel3.add(filler5);
        jPanel3.add(filler8);

        add(jPanel3);

        jPanel4.setAutoscrolls(true);
        jPanel4.setLayout(new java.awt.GridLayout());

        jLabel4.setText("Heure Fin");
        jPanel4.add(jLabel4);

        ChampHeureFin.setText("ChampHeureFin");
        jPanel4.add(ChampHeureFin);
        jPanel4.add(filler6);
        jPanel4.add(filler9);

        add(jPanel4);
        add(filler1);

        jPanel2.setLayout(new java.awt.GridLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Distributions par défaut");
        jLabel5.setToolTipText("");
        jPanel2.add(jLabel5);

        add(jPanel2);

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

        add(jPanel9);

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

        add(jPanel6);

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

        add(jPanel7);

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

        add(jPanel8);
        add(filler3);

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

        add(jPanel5);
        add(filler10);
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        simulateur.modifierNombreJourSimulation((int)this.ChampNbJours.getValue());
        simulateur.modifierHeureDebut(LocalTime.parse(this.ChampHeureDebut.getText(), DateTimeFormatter.ISO_LOCAL_TIME));
        simulateur.modifierHeureFin(LocalTime.parse(this.ChampHeureFin.getText(), DateTimeFormatter.ISO_LOCAL_TIME));
        
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
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonRetourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonRetourActionPerformed
        //((javax.swing.JDialog)this.getParent()).setVisible(false);
    }//GEN-LAST:event_BoutonRetourActionPerformed


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
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
