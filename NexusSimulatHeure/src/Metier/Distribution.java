package Metier;

public class Distribution{
    
    private final double a;
    private final double b;
    private final double c;
    
    public Distribution(double min, double mode, double max){
        this.a = min;
        this.b = max;
        this.c = mode;
    }
    
    

    public double obtenirProchaineValeurAleatoire(){
        double retour;
        double x = Math.random();
        if (x == 0) {
            retour = a;
        } 
        else if (x < (c - a) / (b - a)) {
            retour = a + Math.sqrt(x * (b - a) * (c - a));
        } 
        else {
            retour = b - Math.sqrt((1 - x) * (b - a) * (b - c));
        }
        return retour;
        //Credit: https://en.wikipedia.org/wiki/Triangular_distribution
    }
    
    public double obtenirMoyenne(){
        return c;
    }

    public Distribution obtenirCopie() {
        return new Distribution(a, c, b);
    }
}
