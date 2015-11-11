package Metier;

public class BoucleSimulation implements Runnable{

    private Simulation sim;
    
    public BoucleSimulation(Simulation sim){
        this.sim = sim;
    }
    
    @Override
    public void run() {
        while(true)
        {
            if(sim.getEtat() == Simulation.Etats.ACTION){
                double TempsEcouleParRatioEnSeconde = 1/sim.getFramerate() * sim.obtenirRatio();
                sim.faireAvancerSimulation(TempsEcouleParRatioEnSeconde);
            }
            
            try {
                int tempsAAttendre = 1000/sim.getFramerate();
                Thread.sleep(tempsAAttendre);
            } catch(InterruptedException ex) {
                System.out.println("Simulation stopée");
            }
        }
    }
    
}
