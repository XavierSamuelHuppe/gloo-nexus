package UI;

import Controleur.Simulateur;
import UI.Constantes.Couleurs;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalTime;
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
        
        this.simulateur = new Controleur.Simulateur();
        
        this.ZoneEspaceTravail.setSimulateur(simulateur);
        
        this.BoutonParametres.setActionCommand(UI.Constantes.Commandes.PARAMETRES_SIMULATION);
        this.BoutonParametres.addActionListener(this);
        
        initialiserBoutonsModes();
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
        
        this.simulateur.ajouterObserveurASimulation(this);
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
//        System.out.println("S'passe de quoi.");
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
        UI.PanneauxDetails.PanneauDetailsSimu testPanel;
        testPanel = new UI.PanneauxDetails.PanneauDetailsSimu(this.simulateur);
        final javax.swing.JDialog frame = new javax.swing.JDialog(this, "Paramètres Simulation", true);
        frame.getContentPane().add(testPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
    
    public void passerEnModeArret()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModeArret();
        viderPanneauDetails();
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeIntersection()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModeIntersection();
        viderPanneauDetails();
        BoutonModeIntersection.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeSegment()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModeSegment();
        viderPanneauDetails();
        BoutonModeSegment.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeCircuit()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModeCircuit();
        afficherPanneauDetailsCircuitNouveauCircuit();
        BoutonModeCircuit.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
        this.repaint();
    }
    
    public void passerEnModeSource()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModeSource();
        //afficherPanneauDetailsSourceNouvelleSource();
        viderPanneauDetails();
        BoutonModeSource.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    public void passerEnModeProfilPassager()
    {
        reinitialiserCouleurBoutonsModes();
        simulateur.passerEnModePassager();
        viderPanneauDetails();
        BoutonModeProfilPassager.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND_ACTIF);
    }
    
    private void reinitialiserCouleurBoutonsModes()
    {
        BoutonModeArret.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeIntersection.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeSegment.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeCircuit.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeSource.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
        BoutonModeProfilPassager.setBackground(Couleurs.UI_BARRE_BOUTONS_COULEUR_FOND);
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
//            System.err.println("fanion false");
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
        PanneauEtat = new javax.swing.JPanel();
        PanneauEtatGauche = new javax.swing.JPanel();
        PanneauControleSimulation = new javax.swing.JPanel();
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
        PanneauBarreOutils.add(BoutonNouveau);

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
        PanneauBarreOutils.add(BoutonSauvegarder);

        BoutonCharger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/data-transfer-upload-2x.png"))); // NOI18N
        BoutonCharger.setToolTipText("Charger");
        BoutonCharger.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonCharger.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonCharger.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonCharger.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonCharger.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonCharger.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonCharger);

        BoutonAnnuler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/action-undo-2x.png"))); // NOI18N
        BoutonAnnuler.setToolTipText("Undo");
        BoutonAnnuler.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonAnnuler.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonAnnuler.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonAnnuler.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonAnnuler.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonAnnuler.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonAnnuler);

        BoutonRepeter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/action-redo-2x.png"))); // NOI18N
        BoutonRepeter.setToolTipText("Redo");
        BoutonRepeter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonRepeter.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonRepeter.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonRepeter.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonRepeter.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonRepeter.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonRepeter);

        BoutonParametres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/wrench-2x.png"))); // NOI18N
        BoutonParametres.setToolTipText("Paramètres de la Simulation");
        BoutonParametres.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonParametres.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonParametres.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonParametres.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonParametres.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonParametres.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonParametres);

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
        PanneauBarreOutils.add(BoutonImageFond);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(10, 32767));
        jSeparator1.setPreferredSize(new java.awt.Dimension(10, 10));
        PanneauBarreOutils.add(jSeparator1);

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
        PanneauBarreOutils.add(BoutonModeArret);

        BoutonModeIntersection.setText("+");
        BoutonModeIntersection.setToolTipText("Édition d'intersections");
        BoutonModeIntersection.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeIntersection.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeIntersection.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeIntersection.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeIntersection.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeIntersection.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonModeIntersection);

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
        PanneauBarreOutils.add(BoutonModeSegment);

        BoutonModeCircuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/fork-2x.png"))); // NOI18N
        BoutonModeCircuit.setToolTipText("Édition de circuits");
        BoutonModeCircuit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeCircuit.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeCircuit.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeCircuit.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeCircuit.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeCircuit.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonModeCircuit);

        BoutonModeSource.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/dashboard-2x.png"))); // NOI18N
        BoutonModeSource.setToolTipText("Édition de sources");
        BoutonModeSource.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeSource.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeSource.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeSource.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeSource.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeSource.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonModeSource);

        BoutonModeProfilPassager.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/people-2x.png"))); // NOI18N
        BoutonModeProfilPassager.setToolTipText("Édition de profils passagers");
        BoutonModeProfilPassager.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BoutonModeProfilPassager.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        BoutonModeProfilPassager.setMargin(new java.awt.Insets(2, 2, 2, 2));
        BoutonModeProfilPassager.setMaximumSize(new java.awt.Dimension(24, 24));
        BoutonModeProfilPassager.setMinimumSize(new java.awt.Dimension(24, 24));
        BoutonModeProfilPassager.setPreferredSize(new java.awt.Dimension(24, 24));
        PanneauBarreOutils.add(BoutonModeProfilPassager);

        getContentPane().add(PanneauBarreOutils, java.awt.BorderLayout.PAGE_START);

        PanneauEtat.setLayout(new java.awt.BorderLayout());

        PanneauEtatGauche.setLayout(new javax.swing.BoxLayout(PanneauEtatGauche, javax.swing.BoxLayout.LINE_AXIS));

        PanneauControleSimulation.setLayout(new javax.swing.BoxLayout(PanneauControleSimulation, javax.swing.BoxLayout.LINE_AXIS));

        BoutonDemarrerPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/Icones/media-play-2x.png"))); // NOI18N
        BoutonDemarrerPause.setToolTipText("Lancer simulation");
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
    private javax.swing.JLabel LibelleCoordonneesGeographiques;
    private javax.swing.JLabel LibelleHeureCourante;
    private javax.swing.JLabel LibelleJoursSimulation;
    private javax.swing.JLabel LibelleVitesse;
    private javax.swing.JLabel LibelleZoom;
    private javax.swing.JPanel PanneauBarreOutils;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
