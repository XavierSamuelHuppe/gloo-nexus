package Metier;

public class Distribution{
    
    private final double min;
    private final double max;
    private final double mode;
    
    public Distribution(double min, double mode, double max){
        this.min = min;
        this.max = max;
        this.mode = mode;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMode() {
        return mode;
    }
    
    

    public double obtenirProchaineValeurAleatoire(){
        double retour;
        double x = Math.random();
        if (x == 0) {
            retour = min;
        } 
        else if (x < (mode - min) / (max - min)) {
            retour = min + Math.sqrt(x * (max - min) * (mode - min));
        } 
        else {
            retour = max - Math.sqrt((1 - x) * (max - min) * (max - mode));
        }
        return retour;
        //Credit: https://en.wikipedia.org/wiki/Triangular_distribution
    }
    
    public double obtenirMoyenne(){
        return mode;
    }

    public Distribution obtenirCopie() {
        return new Distribution(min, mode, max);
    }
}
