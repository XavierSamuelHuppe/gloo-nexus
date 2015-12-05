package Metier.Carte;

import java.io.Serializable;

public class Position implements Serializable{
    
    private double X;
    private double Y;
    
    public Position(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Position))return false;
        Position autrePosition = (Position)o;
        return (X == autrePosition.getX() && Y == autrePosition.getY());
    }
}
