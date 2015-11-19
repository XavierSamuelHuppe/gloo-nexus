
package UI.PanneauxDetails;

import java.util.Observable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.text.DateFormat;

public class PanneauDetailsSource extends PanneauDetails implements java.util.Observer {

    private Metier.Source.Source sourceMetierLie;
    private UI.Point pointUILie;
    private Controleur.Simulateur simulateur;
    
    public PanneauDetailsSource() {
        initComponents();
    }
    public PanneauDetailsSource(Metier.Carte.Point p, UI.Point pUI, Metier.Source.Source s)
    {
        super();
        initComponents();
        
        this.simulateur = simulateur;

        this.sourceMetierLie = s;
        //this.sourceMetierLie.addObserver(this);
        
        this.pointUILie = pUI;
        
        rafraichir();
    }
                                        
    @Override
    public void rafraichir() {
        
        this.ChampCircuitNom.setText(sourceMetierLie.getCircuit().getNom());
        this.ChampFrequence.setText(String.valueOf(sourceMetierLie.getFrequence()));
        this.ChampConteneurPassager.setText(String.valueOf(sourceMetierLie.getcapacite().obtenirNombrePassagers()));
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

        jLabel1 = new javax.swing.JLabel();
        ChampFrequence = new javax.swing.JTextField();
        ChampCircuitNom = new javax.swing.JTextField();
        Frequence = new javax.swing.JLabel();
        ChampPoint = new javax.swing.JTextField();
        PointDepart = new javax.swing.JLabel();
        ChampHeureDepart = new javax.swing.JTextField();
        HeureDepart = new javax.swing.JLabel();
        ChampConteneurPassager = new javax.swing.JTextField();
        ConteneurPassager = new javax.swing.JLabel();
        ChampHeureFin = new javax.swing.JTextField();
        HeureFin = new javax.swing.JLabel();
        BoutonSauvegarder = new javax.swing.JButton();

        jLabel1.setText("Circuit");

        ChampFrequence.setText("ChampFrequence");

        ChampCircuitNom.setText("ChampCircuitNom");
        ChampCircuitNom.setToolTipText("");

        Frequence.setText("Frequence");

        ChampPoint.setText("ChampPoint");

        PointDepart.setText("Point de Départ");

        ChampHeureDepart.setText("ChampHeureDepart");

        HeureDepart.setText("Heure de départ");

        ChampConteneurPassager.setText("ChampConteneurPassager");

        ConteneurPassager.setText("Capacité des vehicules");

        ChampHeureFin.setText("ChampHeureFin");

        HeureFin.setText("fin de la source");

        BoutonSauvegarder.setText("Sauvegarder");
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(ChampFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChampCircuitNom, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Frequence)
                    .addComponent(ChampPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PointDepart)
                    .addComponent(ChampHeureDepart, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HeureDepart)
                    .addComponent(ChampConteneurPassager, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConteneurPassager)
                    .addComponent(ChampHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HeureFin))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(BoutonSauvegarder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampCircuitNom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(ConteneurPassager)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampConteneurPassager, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(HeureFin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChampHeureFin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BoutonSauvegarder)
                .addGap(0, 12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
/*
        this.obtenirApplication().getSimulateur().modifierPoint(this.pointMetierLie, new Metier.Carte.Position(Double.parseDouble(this.ChampLongitude.getText()), Double.parseDouble(this.ChampLatitude.getText())), this.ChampNom.getText());
        this.pointUILie.rafraichirApresModificationPointMetier();
        this.obtenirApplication().revalidate();
        
        */
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        //java.sql.Time heureFin = new java.sql.Time(formatter.parse(this.ChampHeureDepart.getText()).getTime());
        
        //java.sql.Time heureDebut = new java.sql.Time(formatter.parse(this.ChampHeureDepart.getText()).getTime());
        //Il me faut un point... on va devoir créer une méthode qui me donne un point selon son nom dans simulateur? 
        //Metier.Carte.Point unPoint = this.obtenirApplication().getSimulateur().....
        //pareil pour circuit...
        //this.obtenirApplication().getSimulateur().modifierSource(this.SourceMetierLie, heureFin ,  , heureDebut, WIDTH, null, Double.parseDouble(this.ChampConteneurPassager.getText()), null);
        
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JTextField ChampCircuitNom;
    private javax.swing.JTextField ChampConteneurPassager;
    private javax.swing.JTextField ChampFrequence;
    private javax.swing.JTextField ChampHeureDepart;
    private javax.swing.JTextField ChampHeureFin;
    private javax.swing.JTextField ChampPoint;
    private javax.swing.JLabel ConteneurPassager;
    private javax.swing.JLabel Frequence;
    private javax.swing.JLabel HeureDepart;
    private javax.swing.JLabel HeureFin;
    private javax.swing.JLabel PointDepart;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
