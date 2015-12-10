package UI;

import Controleur.Simulateur;
import Metier.Circuit.Circuit;
import UI.Constantes.Couleurs;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.Observable;
import javax.swing.JOptionPane;
import java.util.Observer;
import javax.swing.JFileChooser;


public class Application extends javax.swing.JFrame implements KeyListener, ActionListener, Observer {
    private Controleur.Simulateur simulateur;
    
    
    /**
     * Creates new form MainFrame
     */
    public Application() {
        initComponents();
        this.addKeyListener(this);

        this.setTitle("SimulatHeure");
        this.BoutonNouveau.addKeyListener(this);
        this.BoutonModeCircuit.addKeyListener(this);
        this.BoutonModeArret.addKeyListener(this);
        this.BoutonModeIntersection.addKeyListener(this);
        this.BoutonModeProfilPassager.addKeyListener(this);
        this.BoutonModeSegment.addKeyListener(this);
        this.BoutonModeSource.addKeyListener(this);
        
        this.PanneauBarreOutilsChoixCircuitTrajet.setVisible(false);
        
        this.simulateur = new Controleur.Simulateur();
        
        this.ZoneEspaceTravail.setSimulateur(simulateur);
        this.simulateur.ajouterObserveurASimulation(this);
        
        this.BoutonParametres.setActionCommand(UI.Constantes.Commandes.PARAMETRES_SIMULATION);
        this.BoutonParametres.addActionListener(this);
        
        initialiserBoutonsModes();
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
        
        
    }
    
    private void initialiserBoutonsModes()
    {
        this.BoutonModeArret.setActionCommand(UI.Constantes.Commandes.MODE_ARRET);
        this.BoutonModeArret.addActionListener(this);
        
        this.BoutonModeIntersection.setActionCommand(UI.Constantes.Commandes.MODE_INTERSECTION);
        this.BoutonModeIntersection.addActionListener(this);
        
        this.BoutonModeSegment.setActionCommand(UI.Constantes.Commandes.MODE_SEGMENT);
        this.BoutonModeSegment.addActionListener(this);
        
        this.BoutonModeCircuit.setActionCommand(UI.Constantes.Commandes.MODE_CIRCUIT);
        this.BoutonModeCircuit.addActionListener(this);
        
        this.BoutonModeSource.setActionCommand(UI.Constantes.Commandes.MODE_SOURCE);
        this.BoutonModeSource.addActionListener(this);
        
        this.BoutonModeProfilPassager.setActionCommand(UI.Constantes.Commandes.MODE_PROFIL_PASSAGER);
        this.BoutonModeProfilPassager.addActionListener(this);        
    }

    
    @Override
    public void update(Observable o, Object o1) {
        //System.out.println("S'passe de quoi.");
        rafraichir();
    }

    private void rafraichir()
    {
        this.ZoneEspaceTravail.repaint();
        afficherHeureCourante();
        afficherJoursSimulations();
        afficherVitesseExecution();
        this.revalidate();
    }
        
    private void afficherHeureCourante()
    {
        LocalTime heureCourante = this.simulateur.obtenirHeureCourante();
        LibelleHeureCourante.setText(String.format("Temps : " + heureCourante.format(UI.Constantes.Formats.FORMAT_HEURE_COURANTE)));
    }
    
    private void afficherJoursSimulations()
    {
        LibelleJoursSimulation.setText(String.format("Journée : " + this.simulateur.obtenirJourneeCourante() + " de " + this.simulateur.obtenirNombreJourSimulation()));
    }

    private void afficherVitesseExecution()
    {
        LibelleVitesse.setText("Vitesse d'exécution " + SliderVitesse.getValue() + "x");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand())
        {
            case UI.Constantes.Commandes.MODE_ARRET:
                passerEnModeArret();
                break;
            case UI.Constantes.Commandes.MODE_INTERSECTION:
                passerEnModeIntersection();
                break;
            case UI.Constantes.Commandes.MODE_SEGMENT:
                passerEnModeSegment();
                break;
            case UI.Constantes.Commandes.MODE_CIRCUIT:
                passerEnModeCircuit();
                break;
            case UI.Constantes.Commandes.MODE_SOURCE:
                passerEnModeSource();
                break;
            case UI.Constantes.Commandes.MODE_PROFIL_PASSAGER:
                passerEnModeProfilPassager();
                break;
            case UI.Constantes.Commandes.PARAMETRES_SIMULATION:
                afficherParametreSimulation();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println("keyTyped " + ke.getKeyChar());
        if(ke.getKeyChar()=='1')
        {
            passerEnModeArret();
        }
        else if (ke.getKeyChar()=='2')
        {
            passerEnModeSegment();
        }
        else if (ke.getKeyChar()=='3')
        {
            passerEnModeSource();
        }
        else if (ke.getKeyChar()=='4')
        {
            passerEnModeProfilPassager();
        }
    }
    
    public void afficherParametreSimulation()
    {
        /*UI.PanneauxDetails.PanneauDetailsSimu testPanel;
        testPanel = new UI.PanneauxDetails.PanneauDetailsSimu(this.simulateur);
        final javax.swing.JDialog frame = new javax.swing.JDialog(this, "Paramètres Simulation", true);
        frame.getContentPane().add(testPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);*/
        UI.PanneauxDetails.PanneauDetailsSimu frame = new UI.PanneauxDetails.PanneauDetailsSimu(this.simulateur);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
    
    public void passerEnModeArret()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModeArret();
        viderPanneauDetails();
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeIntersection()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModeIntersection();
        viderPanneauDetails();
        BoutonModeIntersection.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeSegment()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModeSegment();
        viderPanneauDetails();
        BoutonModeSegment.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeCircuit()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModeCircuit();
        afficherPanneauDetailsCircuitNouveauCircuit();
        BoutonModeCircuit.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
        this.repaint();
    }
    
    public void passerEnModeSource()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModeSource();
        //afficherPanneauDetailsSourceNouvelleSource();
        viderPanneauDetails();
        BoutonModeSource.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeProfilPassager()
    {
        reinitialiserAffichageAvantChangementMode();
        simulateur.passerEnModePassager();
        viderPanneauDetails();
        BoutonModeProfilPassager.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    private void reinitialiserAffichageAvantChangementMode()
    {
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeIntersection.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeSegment.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeCircuit.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeSource.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeProfilPassager.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        
        cacherListeCircuitTrajet();
    }

    private boolean fanionClavier1 = false;
    
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_CONTROL)
        {
//            System.err.println("fanion true");
            fanionClavier1 = true;
            this.ZoneEspaceTravail.setFanionClavier1(this.fanionClavier1);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            fanionClavier1 = false;
            this.ZoneEspaceTravail.setFanionClavier1(this.fanionClavier1);
            
            if(this.simulateur.estEnModeSegment())
            {
                this.simulateur.annulerCreationSegment();
            }
        }
        else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if(this.simulateur.estEnModeSegment())
            {
                this.simulateur.annulerCreationSegment();
            }
        }
    }

    public void mettreAJourCoordonnesGeographiques(double latitude, double longitude)
    {
        this.LibelleCoordonneesGeographiques.setText(String.format("%1$.4f°, %2$.4f°", latitude, longitude));
    }

    public void mettreAJourZoom(double zoom)
    {
        this.LibelleZoom.setText(String.format("Zoom : %1$.0f", (zoom * 100)) + "%");
    }

    public void viderPanneauDetails()
    {
        this.simulateur.annulerCreationCircuit();
        this.simulateur.annulerCreationTrajet();
        cacherListeCircuitTrajet();
        this.PanneauDetails.removeAll();
        this.repaint();
        this.revalidate();
    }
    
    public void afficherPanneauDetails(IDetailsAffichables element)
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(element.obtenirPanneauDetails(), BorderLayout.CENTER);

        this.PanneauDetails.repaint();
        
        this.revalidate();
    }
    
    public void afficherPanneauDetailsCircuitNouveauCircuit()
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsCircuit());
        this.PanneauDetails.repaint();
        this.revalidate();
    }
    
    public void afficherPanneauDetailsCircuitExistant(Metier.Circuit.Circuit c)
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsCircuit(c));
        this.PanneauDetails.repaint();
        this.revalidate();
    }
    
    public void afficherPanneauDetailsSourceNouvelleSource(Metier.Carte.Point point)
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsSource(point, this.simulateur));
        this.PanneauDetails.repaint();
        
        this.revalidate();
    }
    
    public void afficherPanneauDetailsSource(Metier.Source.Source source) {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsSource(source, this.simulateur));
        this.PanneauDetails.repaint();
        
        this.revalidate();
    }
    
    public void afficherPanneauDetailsProfilPassagerNouveauProfil(Metier.Carte.Point point)
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsProfilPassager(point, this.simulateur));
        this.PanneauDetails.repaint();
        
        this.revalidate();
    }
    
    public void afficherPanneauDetailsProfilPassager(Metier.Profil.ProfilPassager profil)
    {
        this.PanneauDetails.removeAll();
        this.PanneauDetails.add(new UI.PanneauxDetails.PanneauDetailsProfilPassager(profil, this.simulateur));
        this.PanneauDetails.repaint();
        
        this.revalidate();
    }
    
    
    
    public EspaceTravail getEspaceTravail() {
        return this.ZoneEspaceTravail;
    }
    
    public Controleur.Simulateur getSimulateur()
    {
        return this.simulateur;
    }
    
    private void rafraichirIconeBoutonDemarrerPause()
    {
        if(this.simulateur.simulationEstEnAction()){
            BoutonDemarrerPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-pause-2x.png")));
        }
        else{
            BoutonDemarrerPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-play-2x.png")));
        }
        BoutonDemarrerPause.revalidate();
    }

    public void repeindreEspaceTravail()
    {
        this.ZoneEspaceTravail.revalidate();
        this.ZoneEspaceTravail.repaint();
    }
    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauBarreOutils = new javax.swing.JPanel();
        PanneauBarreOutilsBoutons = new javax.swing.JPanel();
        BoutonNouveau = new javax.swing.JButton();
        BoutonSauvegarder = new javax.swing.JButton();
        BoutonCharger = new javax.swing.JButton();
        BoutonAnnuler = new javax.swing.JButton();
        BoutonRepeter = new javax.swing.JButton();
        BoutonParametres = new javax.swing.JButton();
        BoutonImageFond = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        BoutonModeArret = new javax.swing.JButton();
        BoutonModeIntersection = new javax.swing.JButton();
        BoutonModeSegment = new javax.swing.JButton();
        BoutonModeCircuit = new javax.swing.JButton();
        BoutonModeSource = new javax.swing.JButton();
        BoutonModeProfilPassager = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0));
        PanneauBarreOutilsChoixCircuitTrajet = new javax.swing.JPanel();
        LibelleCircuit = new javax.swing.JLabel();
        ListeCircuitTrajet = new javax.swing.JComboBox();
        PanneauEtat = new javax.swing.JPanel();
        PanneauEtatGauche = new javax.swing.JPanel();
        PanneauControleSimulation = new javax.swing.JPanel();
        BoutonSimulationInfinie = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        BoutonDemarrerPause = new javax.swing.JButton();
        BoutonArreter = new javax.swing.JButton();
        BoutonRedemarrer = new javax.swing.JButton();
        LibelleHeureCourante = new javax.swing.JLabel();
        LibelleVitesse = new javax.swing.JLabel();
        SliderVitesse = new javax.swing.JSlider();
        jSeparator2 = new javax.swing.JSeparator();
        LibelleJoursSimulation = new javax.swing.JLabel();
        PanneauEtatDroite = new javax.swing.JPanel();
        LibelleZoom = new javax.swing.JLabel();
        LibelleCoordonneesGeographiques = new javax.swing.JLabel();
        PanneauPrincipal = new javax.swing.JPanel();
        PanneauCentre = new javax.swing.JSplitPane();
        PanneauGauche = new javax.swing.JSplitPane();
        PanneauDetails = new javax.swing.JPanel();
        ZoneEspaceTravail = new UI.EspaceTravail();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanneauBarreOutils.setLayout(new javax.swing.BoxLayout(PanneauBarreOutils, javax.swing.BoxLayout.LINE_AXIS));

        PanneauBarreOutilsBoutons.setLayout(new javax.swing.BoxLayout(PanneauBarreOutilsBoutons, javax.swing.BoxLayout.LINE_AXIS));

        BoutonNouveau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/file-2x.png"))); // NOI18N
        BoutonNouveau.setToolTipText("Nouveau");
        BoutonNouveau.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonNouveau.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonNouveau.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonNouveau.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonNouveau.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonNouveau.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonNouveauActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonNouveau);

        BoutonSauvegarder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/data-transfer-download-2x.png"))); // NOI18N
        BoutonSauvegarder.setToolTipText("Sauvegarder");
        BoutonSauvegarder.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonSauvegarder.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonSauvegarder.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonSauvegarder.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonSauvegarder.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonSauvegarder.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSauvegarderActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonSauvegarder);

        BoutonCharger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/data-transfer-upload-2x.png"))); // NOI18N
        BoutonCharger.setToolTipText("Charger");
        BoutonCharger.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonCharger.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonCharger.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonCharger.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonCharger.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonCharger.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonCharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonChargerActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonCharger);

        BoutonAnnuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/action-undo-2x.png"))); // NOI18N
        BoutonAnnuler.setToolTipText("Undo");
        BoutonAnnuler.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonAnnuler.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonAnnuler.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonAnnuler.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonAnnuler.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonAnnuler.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonAnnuler);

        BoutonRepeter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/action-redo-2x.png"))); // NOI18N
        BoutonRepeter.setToolTipText("Redo");
        BoutonRepeter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonRepeter.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonRepeter.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonRepeter.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonRepeter.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonRepeter.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonRepeter);

        BoutonParametres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/wrench-2x.png"))); // NOI18N
        BoutonParametres.setToolTipText("Paramètres de la Simulation");
        BoutonParametres.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonParametres.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonParametres.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonParametres.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonParametres.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonParametres.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonParametres);

        BoutonImageFond.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/image-2x.png"))); // NOI18N
        BoutonImageFond.setToolTipText("Afficher une image de fond");
        BoutonImageFond.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonImageFond.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonImageFond.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonImageFond.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonImageFond.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonImageFond.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonImageFond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonImageFondActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonImageFond);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(10, 32767));
        jSeparator1.setPreferredSize(new java.awt.Dimension(10, 10));
        PanneauBarreOutilsBoutons.add(jSeparator1);

        BoutonModeArret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/target-2x.png"))); // NOI18N
        BoutonModeArret.setToolTipText("Édition d'arrêts");
        BoutonModeArret.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeArret.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeArret.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeArret.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeArret.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeArret.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonModeArret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonModeArretActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonModeArret);

        BoutonModeIntersection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/cross-2x.png"))); // NOI18N
        BoutonModeIntersection.setToolTipText("Édition d'intersections");
        BoutonModeIntersection.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeIntersection.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeIntersection.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeIntersection.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeIntersection.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeIntersection.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonModeIntersection);

        BoutonModeSegment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/transfer-2x.png"))); // NOI18N
        BoutonModeSegment.setToolTipText("Édition de segments");
        BoutonModeSegment.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeSegment.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeSegment.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeSegment.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeSegment.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeSegment.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonModeSegment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonModeSegmentActionPerformed(evt);
            }
        });
        PanneauBarreOutilsBoutons.add(BoutonModeSegment);

        BoutonModeCircuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/fork-2x.png"))); // NOI18N
        BoutonModeCircuit.setToolTipText("Édition de circuits");
        BoutonModeCircuit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeCircuit.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeCircuit.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeCircuit.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeCircuit.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeCircuit.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonModeCircuit);

        BoutonModeSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/dashboard-2x.png"))); // NOI18N
        BoutonModeSource.setToolTipText("Édition de sources");
        BoutonModeSource.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeSource.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeSource.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeSource.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeSource.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeSource.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonModeSource);

        BoutonModeProfilPassager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/people-2x.png"))); // NOI18N
        BoutonModeProfilPassager.setToolTipText("Édition de profils passagers");
        BoutonModeProfilPassager.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeProfilPassager.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeProfilPassager.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeProfilPassager.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeProfilPassager.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeProfilPassager.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutilsBoutons.add(BoutonModeProfilPassager);

        PanneauBarreOutils.add(PanneauBarreOutilsBoutons);
        PanneauBarreOutils.add(filler1);

        PanneauBarreOutilsChoixCircuitTrajet.setLayout(new javax.swing.BoxLayout(PanneauBarreOutilsChoixCircuitTrajet, javax.swing.BoxLayout.LINE_AXIS));

        LibelleCircuit.setText("Circuit du trajet :");
        PanneauBarreOutilsChoixCircuitTrajet.add(LibelleCircuit);

        ListeCircuitTrajet.setMaximumSize(new java.awt.Dimension(32767, 22));
        ListeCircuitTrajet.setPreferredSize(new java.awt.Dimension(28, 22));
        PanneauBarreOutilsChoixCircuitTrajet.add(ListeCircuitTrajet);

        PanneauBarreOutils.add(PanneauBarreOutilsChoixCircuitTrajet);

        getContentPane().add(PanneauBarreOutils, java.awt.BorderLayout.PAGE_START);

        PanneauEtat.setLayout(new java.awt.BorderLayout());

        PanneauEtatGauche.setLayout(new javax.swing.BoxLayout(PanneauEtatGauche, javax.swing.BoxLayout.LINE_AXIS));

        PanneauControleSimulation.setLayout(new javax.swing.BoxLayout(PanneauControleSimulation, javax.swing.BoxLayout.LINE_AXIS));

        BoutonSimulationInfinie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/infinity-2x.png"))); // NOI18N
        BoutonSimulationInfinie.setToolTipText("Exécuter la simulation instantanément");
        BoutonSimulationInfinie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonSimulationInfinie.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonSimulationInfinie.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonSimulationInfinie.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonSimulationInfinie.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonSimulationInfinie.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonSimulationInfinie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonSimulationInfinieActionPerformed(evt);
            }
        });
        PanneauControleSimulation.add(BoutonSimulationInfinie);
        PanneauControleSimulation.add(filler2);

        BoutonDemarrerPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-play-2x.png"))); // NOI18N
        BoutonDemarrerPause.setToolTipText("Lancer la simulation");
        BoutonDemarrerPause.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonDemarrerPause.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonDemarrerPause.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonDemarrerPause.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonDemarrerPause.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonDemarrerPause.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonDemarrerPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonDemarrerPauseActionPerformed(evt);
            }
        });
        PanneauControleSimulation.add(BoutonDemarrerPause);

        BoutonArreter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-stop-2x.png"))); // NOI18N
        BoutonArreter.setToolTipText("Arrêter simulation");
        BoutonArreter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonArreter.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonArreter.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonArreter.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonArreter.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonArreter.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonArreter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonArreterActionPerformed(evt);
            }
        });
        PanneauControleSimulation.add(BoutonArreter);

        BoutonRedemarrer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-step-backward-2x.png"))); // NOI18N
        BoutonRedemarrer.setToolTipText("Redémarrer la simulation");
        BoutonRedemarrer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonRedemarrer.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonRedemarrer.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonRedemarrer.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonRedemarrer.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonRedemarrer.setPreferredSize(new java.awt.Dimension(24, 24));
        BoutonRedemarrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutonRedemarrerActionPerformed(evt);
            }
        });
        PanneauControleSimulation.add(BoutonRedemarrer);

        PanneauEtatGauche.add(PanneauControleSimulation);

        LibelleHeureCourante.setText("Temps : 00:00:00 ");
        LibelleHeureCourante.setPreferredSize(new java.awt.Dimension(150, 14));
        PanneauEtatGauche.add(LibelleHeureCourante);

        LibelleVitesse.setText("Vitesse d'exécution");
        LibelleVitesse.setToolTipText("");
        LibelleVitesse.setPreferredSize(new java.awt.Dimension(150, 14));
        PanneauEtatGauche.add(LibelleVitesse);
        LibelleVitesse.getAccessibleContext().setAccessibleName("");

        SliderVitesse.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        SliderVitesse.setMajorTickSpacing(100);
        SliderVitesse.setMaximum(1000);
        SliderVitesse.setMinimum(1);
        SliderVitesse.setMinorTickSpacing(50);
        SliderVitesse.setPaintTicks(true);
        SliderVitesse.setSnapToTicks(true);
        SliderVitesse.setToolTipText("Changer la vitesse d'exécution de la simulation.");
        SliderVitesse.setValue(1);
        SliderVitesse.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SliderVitesseStateChanged(evt);
            }
        });
        PanneauEtatGauche.add(SliderVitesse);
        PanneauEtatGauche.add(jSeparator2);

        LibelleJoursSimulation.setText("Journée : 1 de X");
        LibelleJoursSimulation.setPreferredSize(new java.awt.Dimension(150, 14));
        PanneauEtatGauche.add(LibelleJoursSimulation);

        PanneauEtat.add(PanneauEtatGauche, java.awt.BorderLayout.WEST);

        LibelleZoom.setText("Zoom : 100%");
        LibelleZoom.setMaximumSize(new java.awt.Dimension(100, 14));
        LibelleZoom.setMinimumSize(new java.awt.Dimension(100, 14));
        LibelleZoom.setPreferredSize(new java.awt.Dimension(100, 14));
        LibelleZoom.setRequestFocusEnabled(false);
        PanneauEtatDroite.add(LibelleZoom);

        LibelleCoordonneesGeographiques.setText("Position : 46.780373, -71.277205");
        PanneauEtatDroite.add(LibelleCoordonneesGeographiques);

        PanneauEtat.add(PanneauEtatDroite, java.awt.BorderLayout.EAST);

        getContentPane().add(PanneauEtat, java.awt.BorderLayout.PAGE_END);

        PanneauPrincipal.setLayout(new java.awt.BorderLayout());

        PanneauCentre.setDividerLocation(250);
        PanneauCentre.setDividerSize(2);

        PanneauGauche.setDividerLocation(150);
        PanneauGauche.setDividerSize(2);
        PanneauGauche.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        PanneauCentre.setLeftComponent(PanneauGauche);

        PanneauDetails.setLayout(new java.awt.BorderLayout());
        PanneauCentre.setTopComponent(PanneauDetails);
        PanneauCentre.setRightComponent(ZoneEspaceTravail);

        PanneauPrincipal.add(PanneauCentre, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanneauPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BoutonNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonNouveauActionPerformed
        if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Désirez-vous vraiment créer une nouvelle simulation? Toutes les modifications non-enregistrées seront perdues.", "Créer une nouvelle simulation?", JOptionPane.YES_NO_OPTION))
        {
            this.viderPanneauDetails();
            
            this.simulateur = new Simulateur();
            this.simulateur.ajouterObserveurASimulation(this);
            
            this.getEspaceTravail().reinitialiser();
            this.getEspaceTravail().setSimulateur(this.simulateur);
            this.passerEnModeArret();
            this.repaint();
            this.revalidate();
        }
    }//GEN-LAST:event_BoutonNouveauActionPerformed

    private void BoutonDemarrerPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonDemarrerPauseActionPerformed
        if(this.simulateur.simulationEstEnAction()) {
            this.simulateur.pauser();
        }
        else {
            this.simulateur.demarerRedemarer();
        }
        rafraichirIconeBoutonDemarrerPause();
    }//GEN-LAST:event_BoutonDemarrerPauseActionPerformed

    private void BoutonArreterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonArreterActionPerformed
        this.simulateur.arreter();
        rafraichirIconeBoutonDemarrerPause();
        afficherHeureCourante();
    }//GEN-LAST:event_BoutonArreterActionPerformed

    private void BoutonRedemarrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonRedemarrerActionPerformed
        if(this.simulateur.simulationEstEnAction()) {
            this.simulateur.arreter();
        }
        this.simulateur.demarerRedemarer();
        rafraichirIconeBoutonDemarrerPause();
    }//GEN-LAST:event_BoutonRedemarrerActionPerformed

    private void SliderVitesseStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SliderVitesseStateChanged
        this.simulateur.modfierVitesse(100 * SliderVitesse.getValue());
        afficherVitesseExecution();
        this.revalidate();
    }//GEN-LAST:event_SliderVitesseStateChanged

    private void BoutonImageFondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonImageFondActionPerformed
        javax.swing.JFileChooser choixFichier = new JFileChooser();
        
         if (choixFichier.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
            File fichier = choixFichier.getSelectedFile();
            java.awt.Image i;
            try
            {
                i = javax.imageio.ImageIO.read(fichier);
                this.ZoneEspaceTravail.setImageFond(i);
                this.ZoneEspaceTravail.repaint();
            }
            catch (Exception x){}
        }
    }//GEN-LAST:event_BoutonImageFondActionPerformed

    private void BoutonModeArretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonModeArretActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoutonModeArretActionPerformed

    private void BoutonModeSegmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonModeSegmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoutonModeSegmentActionPerformed

    private void BoutonSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSauvegarderActionPerformed
        if(simulateur.enregistrer())
            JOptionPane.showMessageDialog(this, "La sauvegarde a réussie.");
        else
            JOptionPane.showMessageDialog(this, "La sauvegarde a explosé!");
    }//GEN-LAST:event_BoutonSauvegarderActionPerformed

    private void BoutonChargerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonChargerActionPerformed
        if(simulateur.charger())
        {
            JOptionPane.showMessageDialog(this, "Le chargement a réussie.");
            this.simulateur.ajouterObserveurASimulation(this);
            this.ZoneEspaceTravail.rechargerObjetsUI();
            this.revalidate();
            this.repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Le chargement a explosé!");
        }
    }//GEN-LAST:event_BoutonChargerActionPerformed

    private void BoutonSimulationInfinieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutonSimulationInfinieActionPerformed
        this.simulateur.executerSimulationInstantanement();
    }//GEN-LAST:event_BoutonSimulationInfinieActionPerformed
    
    public void remplirListeCircuitTrajet(Metier.Carte.Point p)
    {
        List<Circuit> circuits = this.simulateur.circuitsPassantPar(p);

        ListeCircuitTrajet.removeActionListener(actionListenerListeCircuitTrajet);
        
        Metier.Circuit.Circuit circuitCourant = (Metier.Circuit.Circuit)ListeCircuitTrajet.getSelectedItem();

        ListeCircuitTrajet.removeAllItems();
        for(Metier.Circuit.Circuit c : this.simulateur.circuitsPassantPar(p))
        {
            ListeCircuitTrajet.addItem(c);
        }
        
        ListeCircuitTrajet.setSelectedItem(circuitCourant);
        
        ListeCircuitTrajet.addActionListener(actionListenerListeCircuitTrajet);
        
        if(!PanneauBarreOutilsChoixCircuitTrajet.isVisible())
        {
            PanneauBarreOutilsChoixCircuitTrajet.setVisible(true);
            this.revalidate();
        }
    }
    
    public Metier.Circuit.Circuit obtenirCircuitDansListeChoixCircuit()
    {
        return (Metier.Circuit.Circuit)ListeCircuitTrajet.getSelectedItem();
    }
    
    private final ActionListener actionListenerListeCircuitTrajet = new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            ListeCircuitTrajetActionPerformed(evt);
        }
    };
    
    private void ListeCircuitTrajetActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        this.simulateur.choisirCircuitActifPourCreationTrajet((Metier.Circuit.Circuit)ListeCircuitTrajet.getSelectedItem());
    }        

    private void cacherListeCircuitTrajet()
    {
        System.out.println("remove");
        ListeCircuitTrajet.removeActionListener(actionListenerListeCircuitTrajet);
        ListeCircuitTrajet.removeAllItems();
        PanneauBarreOutilsChoixCircuitTrajet.setVisible(false);
        this.revalidate();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoutonAnnuler;
    private javax.swing.JButton BoutonArreter;
    private javax.swing.JButton BoutonCharger;
    private javax.swing.JButton BoutonDemarrerPause;
    private javax.swing.JButton BoutonImageFond;
    private javax.swing.JButton BoutonModeArret;
    private javax.swing.JButton BoutonModeCircuit;
    private javax.swing.JButton BoutonModeIntersection;
    private javax.swing.JButton BoutonModeProfilPassager;
    private javax.swing.JButton BoutonModeSegment;
    private javax.swing.JButton BoutonModeSource;
    private javax.swing.JButton BoutonNouveau;
    private javax.swing.JButton BoutonParametres;
    private javax.swing.JButton BoutonRedemarrer;
    private javax.swing.JButton BoutonRepeter;
    private javax.swing.JButton BoutonSauvegarder;
    private javax.swing.JButton BoutonSimulationInfinie;
    private javax.swing.JLabel LibelleCircuit;
    private javax.swing.JLabel LibelleCoordonneesGeographiques;
    private javax.swing.JLabel LibelleHeureCourante;
    private javax.swing.JLabel LibelleJoursSimulation;
    private javax.swing.JLabel LibelleVitesse;
    private javax.swing.JLabel LibelleZoom;
    private javax.swing.JComboBox ListeCircuitTrajet;
    private javax.swing.JPanel PanneauBarreOutils;
    private javax.swing.JPanel PanneauBarreOutilsBoutons;
    private javax.swing.JPanel PanneauBarreOutilsChoixCircuitTrajet;
    private javax.swing.JSplitPane PanneauCentre;
    private javax.swing.JPanel PanneauControleSimulation;
    private javax.swing.JPanel PanneauDetails;
    private javax.swing.JPanel PanneauEtat;
    private javax.swing.JPanel PanneauEtatDroite;
    private javax.swing.JPanel PanneauEtatGauche;
    private javax.swing.JSplitPane PanneauGauche;
    private javax.swing.JPanel PanneauPrincipal;
    private javax.swing.JSlider SliderVitesse;
    private UI.EspaceTravail ZoneEspaceTravail;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
