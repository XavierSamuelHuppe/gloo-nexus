package UI;

import Controleur.Simulateur;
import Metier.SituationVehicule;
import Metier.Exceptions.AucunCheminPossibleException;
import Metier.Exceptions.AucunCircuitActifException;
import Metier.Exceptions.AucunPointCreateurException;
import Metier.Exceptions.EditionEnMauvaisModeException;
import Metier.Exceptions.MauvaisPointDeDepartException;
import Metier.Exceptions.PointPasSurCircuitActifException;
import UI.Constantes.Couleurs;
import UI.Dessinateurs.DessinateurVehicule;
import UI.Utils.PaireDoubles;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import java.util.List;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class EspaceTravail extends javax.swing.JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private static final double ZOOM_DEFAULT = 1;
    
    private Controleur.Simulateur simulateur;

    private List<Point> points = new LinkedList<>();
    private List<Segment> segments = new LinkedList<>();
    private double zoom = ZOOM_DEFAULT;
    private boolean fanionClavier1 = false;
    private java.awt.Point pointDrag = null;
    
    private Image imageFond = null;
    
    private final double PAS_ZOOM = 0.1;
    
    public EspaceTravail()
    {
        //Requis pour éviter des problèmes visuels.
        this.setLayout(null);
        
        this.setBackground(Couleurs.FOND_ESPACE_TRAVAIL);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }
    
    public void reinitialiser()
    {
        this.removeAll();
        viderListes();
        this.revalidate();
        zoom = ZOOM_DEFAULT;
        fanionClavier1 = false;
        pointDrag = null;
    }
    
    private void viderListes()
    {
        if(points != null)
        {
            points.clear();
        }
        else
        {
            points = new LinkedList<>();
        }
        
        if(segments != null)
        {
            segments.clear();
        }
        else
        {
            segments = new LinkedList<>();
        }
    }
    
    public void setSimulateur(Controleur.Simulateur s)
    {
        this.simulateur = s;
    }
    
    public Simulateur getSimulateur()
    {
        return this.simulateur;
    }
    
    public Application obtenirApplication()
    {
        return (Application)SwingUtilities.getWindowAncestor(this);
    }
    
    public void deplacerPoint(UI.Point p)
    {
        PaireDoubles coords = transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(p.getLocation()));
        this.simulateur.modifierPoint(p.getPointMetier(), coords.getPremier(), coords.getSecond(), p.getPointMetier().getNom());
    }
    
    public boolean permettreDeplacementPoint(UI.Point p)
    {
        return ((this.simulateur.estEnModeArret() && p.getPointMetier().estArret())
                    || (this.simulateur.estEnModeIntersection() && p.getPointMetier().estIntersection())
                && !this.simulateur.simulationEstEnAction());
    }
    
    private final double ZOOM_BORNE_INFERIEURE = 0.1;
    private final double ZOOM_BORNE_SUPERIEURE = 2;
    
    private void zoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        if((this.zoom + facteurZoom >= ZOOM_BORNE_INFERIEURE) && (this.zoom + facteurZoom <= ZOOM_BORNE_SUPERIEURE))
        {
            mettreAJourPositionReferenceApresZoom(facteurZoom, positionCurseur);
            this.zoom += facteurZoom;
            for(Point p : points)
            {
                p.zoom(facteurZoom, positionCurseur);
            }

            obtenirApplication().mettreAJourZoom(this.zoom);
            this.repaint();
        }

    }
    
    private void mettreAJourPositionReferenceApresZoom(double facteurZoom, java.awt.Point positionCurseur)
    {
        this.posReferenceX += positionCurseur.x * facteurZoom;
        this.posReferenceY += positionCurseur.y * facteurZoom;
    }
    
    private void mettreAJourPositionReferenceApresDrag(java.awt.Point delta)
    {
        this.posReferenceX -= delta.x;
        this.posReferenceY -= delta.y;
    }
    
    private void afficherPosReference()
    {
        System.out.println("ref : " + posReferenceX + " " + posReferenceY);
    }
    
    public double getZoom()
    {
        return this.zoom;
    }
    
    private void ajouterArret(MouseEvent me)
    {
        _ajouterPoint(me, true);
    }
    
    private void ajouterIntersection(MouseEvent me)
    {
        _ajouterPoint(me, false);
    }
    
    private void _ajouterPoint(MouseEvent me, boolean estArret)
    {
        java.awt.Point posAjustee = new java.awt.Point(me.getX() - calculerZoom(Point.DIAMETRE / 2), me.getY() - calculerZoom(Point.DIAMETRE / 2));
        
        PaireDoubles pd = transformerPositionEspaceTravailEnPostionGeorgraphique(transformerPositionViewportEnPositionEspaceTravail(posAjustee));
        Metier.Carte.Point mp = this.simulateur.ajouterPoint(pd.getPremier(), pd.getSecond(), estArret, "");
        
        Point p = new Point(posAjustee.x, posAjustee.y, this.zoom, mp);
        
        points.add(p);

        this.add(p);
        this.repaint();
    }

    private int calculerZoom(double d)
    {
        return (int)(d*zoom);
    }
        
    public void retirerPoint(Point p){
        points.remove(p);
        this.remove(p);
        this.repaint();
    }

    public void retirerSegment(Segment s){
        segments.remove(s);
        this.repaint();
    }

    public void paintComponent(Graphics g)
    {
//        System.out.println("EspaceTravail.paintComponent");
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dessiner(g2);
    }
    private void dessiner(Graphics2D g2)
    {
//        System.out.println("EspaceTravail.dessiner");
        if(this.imageFond != null)
        {
            AffineTransform at = AffineTransform.getTranslateInstance(-this.posReferenceX, -this.posReferenceY);
            at.concatenate(AffineTransform.getScaleInstance(zoom,zoom));
            g2.drawImage(this.imageFond, at, this);
        }
        dessinerSegments(g2);
        dessinerPoints(g2);
        dessinerVehicules(g2);
    }
    
    private void dessinerPoints(Graphics2D g2)
    {
        for(Point p : points)
        {
            p.dessiner(g2);
        }
    }
    
    private void dessinerSegments(Graphics2D g2)
    {
        for(Segment s : segments)
        {
            s.dessiner(g2);
        }
    }
    
    private void dessinerVehicules(Graphics2D g2)
    {
        UI.Dessinateurs.DessinateurVehicule dv = new DessinateurVehicule();
        if(this.simulateur!=null)
        {
            for(SituationVehicule sv : this.simulateur.obtenirSituationsVehicules())
            {
                PaireDoubles pd = new PaireDoubles(sv.getX(), sv.getY());
                java.awt.Point point = transformerPositionEspaceTravailEnPositionViewport(transformerPostionGeorgraphiqueEnPositionEspaceTravail(pd));
                dv.dessiner(g2, point.x, point.y, sv.getNombrePassagers(),sv.getCircuit(), this.zoom);
            }
        }
    }

    public void setFanionClavier1(boolean b)
    {
        this.fanionClavier1 = b;
    }
    
    //Implémentations MouseMotionListener.
    @Override
    public void mouseDragged(MouseEvent me) {
        if(pointDrag != null)
        {
            java.awt.Point delta = new java.awt.Point(me.getX() - pointDrag.x, 
                                                  me.getY() - pointDrag.y);
            for(Point p : points)
            {
                p.deplacer(delta);
            }
            
            mettreAJourPositionReferenceApresDrag(delta);
            this.repaint();
        }
        pointDrag = me.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        java.awt.Point pET = transformerPositionViewportEnPositionEspaceTravail(me.getPoint());
        PaireDoubles pd = transformerPositionEspaceTravailEnPostionGeorgraphique(pET);
        obtenirApplication().mettreAJourCoordonnesGeographiques(pd.getSecond(), pd.getPremier());
    }
    
    public UI.Point obtenirPointUIParPointMetier(Metier.Carte.Point point)
    {
        return points.stream().filter((p) -> p.getPointMetier().equals(point)).findFirst().get();
    }
    
    
    
    
    
    
    
    
    
    //Implémentations MouseWheelListener.
    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mwe.getPreciseWheelRotation() > 0)
        {
            zoom(-PAS_ZOOM, mwe.getPoint());
        }
        else
        {
            zoom(PAS_ZOOM, mwe.getPoint());
        }
    }

    //Implémentations MouseListener.
    @Override
    public void mouseClicked(MouseEvent me) {
        if(simulateur.estEnModeArret()){
            ajouterArret(me);
        }
        else if(simulateur.estEnModeIntersection()){
            ajouterIntersection(me);
        }
        else if(simulateur.estEnModeSegment() || simulateur.estEnModeCircuit()){
            for(Segment s : segments)
            {
                if(s.estSegmentClique(me.getPoint()))
                {
                    if(SwingUtilities.isLeftMouseButton(me)){
                        segmentClique(s);
                        break;
                    }
                    else if(SwingUtilities.isRightMouseButton(me)){
                        SegmentMenuContextuel m = new SegmentMenuContextuel(simulateur, this.obtenirApplication(), s.getSegmentMetier());
                        m.show(this, me.getX(), me.getY());
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        pointDrag = null;
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
    
    private int posReferenceX = 0;
    private int posReferenceY = 0;
    
    private double ratioPixelDegreLatitude = (double)(0.005 / 500.0);
    private double ratioPixelDegreLontitude = (double)(0.005 / 500.0);

    public java.awt.Point transformerPostionGeorgraphiqueEnPositionEspaceTravail(Metier.Carte.Position posGeo)
    {
        return new java.awt.Point((int)(posGeo.getX() / ratioPixelDegreLontitude), 
                                  (int)(posGeo.getY() / -ratioPixelDegreLatitude));
    }
        
    public java.awt.Point transformerPostionGeorgraphiqueEnPositionEspaceTravail(PaireDoubles posGeo)
    {
        return new java.awt.Point((int)(posGeo.getPremier()/ ratioPixelDegreLontitude), 
                                  (int)(posGeo.getSecond() / -ratioPixelDegreLatitude));
    }
    
    public PaireDoubles transformerPositionEspaceTravailEnPostionGeorgraphique(java.awt.Point posET)
    {
        return new PaireDoubles(posET.x * ratioPixelDegreLontitude, 
                                posET.y * -ratioPixelDegreLatitude);
    }
    
    public java.awt.Point transformerPositionEspaceTravailEnPositionViewport(java.awt.Point posET)
    {
        return new java.awt.Point((int)(posET.x * zoom) - posReferenceX, (int)(posET.y * zoom) - posReferenceY);
    }
    
    public java.awt.Point transformerPositionViewportEnPositionEspaceTravail(java.awt.Point posVP)
    {
        return new java.awt.Point((int)((posVP.x + posReferenceX) / zoom), (int)((posVP.y + posReferenceY) / zoom));
    }

    public void pointClique(Point p)
    {
        if(simulateur.estEnModeSegment())
        {
            try
            {
                Metier.Carte.Segment sp = null;
                if(fanionClavier1)
                {
                    sp = this.simulateur.creerSegmentAvecContinuation(p.getPointMetier());
                }
                else
                {
                    sp = this.simulateur.creerSegmentSansContinuation(p.getPointMetier());
                }

                Segment s = new Segment(obtenirPointUIParPointMetier(sp.getPointDepart()), obtenirPointUIParPointMetier(sp.getPointArrivee()), this, sp);
                segments.add(s);        
                this.repaint();
            }
            catch(Metier.Exceptions.AucunPointCreateurException ex)
            {
                System.out.println("AucunPointCreateurException");
            }
            catch(Metier.Exceptions.CreationInvalideException ex)
            {
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Un tel segment ne peut-être créé : ", "Création invalide d'un segment.", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if ((simulateur.estEnModeArret() && p.getPointMetier().estArret())
                    || (simulateur.estEnModeIntersection() && p.getPointMetier().estIntersection()))
        {
            this.simulateur.selectionnerPoint(p.getPointMetier());
            this.obtenirApplication().afficherPanneauDetails(p);
        } 
        else if (simulateur.estEnModeCircuit())
        {
            try
            {
                this.simulateur.commencerContinuerCreationCircuit(p.getPointMetier());
            }
            catch(AucunPointCreateurException ex)
            {
                System.err.println("AucunPointCreateurException");
            }
            catch(AucunCheminPossibleException ex)
            {
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Il n'existe pas de segments permettant de relier les deux points sélectionnés.", "Création invalide d'un circuit.", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (simulateur.estEnModeSource() && p.getPointMetier().estArret())
        {
            try
            {
                this.obtenirApplication().afficherPanneauDetailsSourceNouvelleSource(p.getPointMetier());
            }
            catch(AucunPointCreateurException ex)
            {
                System.err.println("AucunPointCreateurException");
            }
        }
        else if (simulateur.estEnModePassager())
        {
            try
            {
                this.simulateur.commencerContinuerCreationTrajet(p.getPointMetier());
                this.obtenirApplication().remplirListeCircuitTrajet(p.getPointMetier());

                if(!this.obtenirApplication().panneauDetailsEstPresentementAffiche())
                {
                    this.obtenirApplication().afficherPanneauDetailsProfilPassagerNouveauProfil();
                }
            }
            catch(AucunPointCreateurException ex){
                System.err.println("AucunPointCreateurException");
            }
            catch(EditionEnMauvaisModeException ex){
                System.err.println("EditionEnMauvaisModeException");
            }
            catch(AucunCircuitActifException ex){
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Vous devez choisir un circuit avant de pouvoir tracer un trajet.", "Aucun circuit actif", JOptionPane.ERROR_MESSAGE);
            }
            catch(PointPasSurCircuitActifException ex){
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Le point sélectionné n'est pas un arrêt du circuit actif.", "Arrêt doit être sur circuit actif", JOptionPane.ERROR_MESSAGE);
            }
            catch(MauvaisPointDeDepartException ex){
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Le point de départ du trajet doit être un arrêt par lequel passe au moins un circuit.", "Point de départ de trajet invalide", JOptionPane.ERROR_MESSAGE);
            }
            catch(AucunCheminPossibleException ex){
                JOptionPane.showMessageDialog(this.obtenirApplication(), "Il n'existe pas de segments permettant de relier les deux arrêts sélectionnés sur le circuit actif.", "Création invalide d'un trajet pour un profil de passager", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.repaint();
    }
    
    public void segmentClique(Segment s)
    {
        if(simulateur.estEnModeSegment())
        {
            simulateur.selectionnerSegment(s.getSegmentMetier());
            afficherDetails(s);
            this.repaint();
        }
        else if(simulateur.estEnModeCircuit())
        {
            simulateur.commencerContinuerCreationCircuit(s.getArrivee().getPointMetier());
        }
    }
    
    private void afficherDetails(IDetailsAffichables elementCible)
    {
        this.obtenirApplication().afficherPanneauDetails(elementCible);
    }
    
    public void setImageFond(Image i)
    {
        this.imageFond = i;
    }
    
    
    public void rechargerObjetsUI()
    {
        this.removeAll();
        viderListes();
        
        for(Metier.Carte.Point p : this.simulateur.obtenirToutLesPoints())
        {
            java.awt.Point pViewport = transformerPositionEspaceTravailEnPositionViewport(transformerPostionGeorgraphiqueEnPositionEspaceTravail(p.getPosition()));
            UI.Point uiP = new Point(pViewport.x, pViewport.y, zoom, p);
            this.points.add(uiP);
            this.add(uiP);
        }
        
        for(Metier.Carte.Segment s : this.simulateur.obtenirToutLesSegments())
        {
            Segment uiS = new Segment(obtenirPointUIParPointMetier(s.getPointDepart()), obtenirPointUIParPointMetier(s.getPointArrivee()), this, s);
            segments.add(uiS);   
        }
        
        this.revalidate();
    }
}
