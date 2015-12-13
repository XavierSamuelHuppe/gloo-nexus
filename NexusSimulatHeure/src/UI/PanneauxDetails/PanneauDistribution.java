package UI.PanneauxDetails;

import javax.swing.JOptionPane;

public class PanneauDistribution extends PanneauDetails {

    private Metier.Distribution distributionCourante;
    
    public PanneauDistribution() {
        initComponents();
        ajouterFocusListeners();
    }
    
    public PanneauDistribution(Metier.Distribution dt)
    {
        super();
        initComponents();
        ajouterFocusListeners();
        setDistribution(dt);
    }

    private void ajouterFocusListeners()
    {
        ChampMaximum.addFocusListener(new UI.Utils.FocusListenerSelectionTexte());
        ChampMinimum.addFocusListener(new UI.Utils.FocusListenerSelectionTexte());
        ChampMode.addFocusListener(new UI.Utils.FocusListenerSelectionTexte());
    }
    
    public void setDistribution(Metier.Distribution dt)
    {
        this.distributionCourante  = dt;
        rafraichir();
    }
    
    @Override
    public void rafraichir()
    {
        if(this.RadioMinutes.isSelected()){
            this.ChampMaximum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMax()/60));
            this.ChampMinimum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMin()/60));
            this.ChampMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMode()/60));
        }else{
            this.ChampMaximum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMax()));
            this.ChampMinimum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMin()));
            this.ChampMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(distributionCourante.getMode()));
        }
        this.revalidate();
    }
    
    public double obtenirMin(){
        
        if(this.RadioMinutes.isSelected()){
            return Double.parseDouble(this.ChampMinimum.getText()) * 60;
        }else{
            return Double.parseDouble(this.ChampMinimum.getText());
        }
    }
    
    public double obtenirMax(){
        
        if(this.RadioMinutes.isSelected()){
            return Double.parseDouble(this.ChampMaximum.getText()) * 60;
        }else{
            return Double.parseDouble(this.ChampMaximum.getText());
        }
        
    }
    
    public double obtenirMode(){
        if(this.RadioMinutes.isSelected()){
            return Double.parseDouble(this.ChampMode.getText()) * 60;
        }else{
            return Double.parseDouble(this.ChampMode.getText());
        }
        
    }
    
    public String validerValeurs() {       
        String retour = "";
        long min = 0;
        long max = 0;
        long mode = 0;
        boolean nombresValides = true;
        try{
            min = Long.parseLong(this.ChampMinimum.getText());   
        }
        catch(NumberFormatException ex){
            nombresValides = false;
            retour += "La valeur minimale est obligatoire et doit être un nombre entier.\r\n";
        }
        
        try{
            max = Long.parseLong(this.ChampMaximum.getText());   
        }
        catch(NumberFormatException ex){
            nombresValides = false;
            retour += "La valeur maximale est obligatoire et doit être un nombre entier.\r\n";
        }
        
        try{
            mode = Long.parseLong(this.ChampMode.getText());   
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
    private void Changertemps(String mode){
        if(mode.equals("MINUTES"))
        {
            this.ChampMaximum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMaximum.getText()) / 60));
            this.ChampMinimum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMinimum.getText()) / 60));
            this.ChampMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMode.getText()) / 60));
            
        }else if(mode.equals("SECONDES")){
            this.ChampMaximum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMaximum.getText()) * 60));
            this.ChampMinimum.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMinimum.getText()) * 60));
            this.ChampMode.setText(UI.Constantes.Formats.formatterDoubleSansDecimal(Double.parseDouble(this.ChampMode.getText()) * 60));
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

        TempsRadioGroup = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5));
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ChampMinimum = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        ChampMode = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        ChampMaximum = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        RadioSeconde = new javax.swing.JRadioButton();
        RadioMinutes = new javax.swing.JRadioButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 15));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Paramètres de la distribution");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel5.add(jLabel1, java.awt.BorderLayout.CENTER);

        add(jPanel5);
        add(filler2);
        add(filler1);

        jPanel2.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Minimum");
        jPanel2.add(jLabel2, java.awt.BorderLayout.NORTH);
        jPanel2.add(ChampMinimum, java.awt.BorderLayout.CENTER);

        add(jPanel2);

        jPanel4.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Mode");
        jLabel4.setToolTipText("");
        jPanel4.add(jLabel4, java.awt.BorderLayout.NORTH);
        jPanel4.add(ChampMode, java.awt.BorderLayout.CENTER);

        add(jPanel4);

        jPanel3.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Maximum");
        jLabel3.setToolTipText("");
        jPanel3.add(jLabel3, java.awt.BorderLayout.NORTH);
        jPanel3.add(ChampMaximum, java.awt.BorderLayout.CENTER);

        add(jPanel3);

        TempsRadioGroup.add(RadioSeconde);
        RadioSeconde.setSelected(true);
        RadioSeconde.setText("Secondes");
        RadioSeconde.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadioSecondeItemStateChanged(evt);
            }
        });
        jPanel1.add(RadioSeconde);

        TempsRadioGroup.add(RadioMinutes);
        RadioMinutes.setText("Minutes");
        RadioMinutes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RadioMinutesItemStateChanged(evt);
            }
        });
        jPanel1.add(RadioMinutes);

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents

    private void RadioSecondeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadioSecondeItemStateChanged
        if(this.RadioSeconde.isSelected()){
            Changertemps("SECONDES");
        }

    }//GEN-LAST:event_RadioSecondeItemStateChanged

    private void RadioMinutesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RadioMinutesItemStateChanged
        if(this.RadioMinutes.isSelected()){
            Changertemps("MINUTES");
        }
    }//GEN-LAST:event_RadioMinutesItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ChampMaximum;
    private javax.swing.JTextField ChampMinimum;
    private javax.swing.JTextField ChampMode;
    private javax.swing.JRadioButton RadioMinutes;
    private javax.swing.JRadioButton RadioSeconde;
    private javax.swing.ButtonGroup TempsRadioGroup;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
