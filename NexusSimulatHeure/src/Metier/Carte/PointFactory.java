package Metier.Carte;

import Metier.Circuit.ConteneurPassagers;
import Metier.Circuit.ConteneurPassagersIllimite;
import Metier.Exceptions.CreationInvalideException;

public class PointFactory {
    private Point pointEnCreation;
    
    public PointFactory nouveauPoint(){
        ConteneurPassagers passagers = new ConteneurPassagersIllimite();
        pointEnCreation = new Point(passagers);
        return this;
    }
    
    public PointFactory nouveauPointAvecCapacite(ConteneurPassagers passagers){
        pointEnCreation = new Point(passagers);
        return this;
    }
    
    public PointFactory avecUnNom(String nom){
        pointEnCreation.setNom(nom);
        return this;
    }
    
    public PointFactory enPosition(double x, double y){
        Position nouvellePosition = new Position(x, y);
        pointEnCreation.setPosition(nouvellePosition);
        return this;
    }
    
    public Point construire(){
        if(pointEnCreation.getPosition() == null)
            throw new CreationInvalideException("Un point doit avoir une position.");
        
        if(pointEnCreation.getNom() == null)
            pointEnCreation.setNom("");
            
        return pointEnCreation;
    }

}
