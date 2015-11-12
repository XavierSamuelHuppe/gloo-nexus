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
            if(sim.getParametres().estEnAction()){
                double TempsEcouleParRatioEnSeconde = 1/sim.getParametres().getFramerate() * sim.getParametres().obtenirRatioVitesse();
                sim.faireAvancerSimulation(TempsEcouleParRatioEnSeconde);
            }
            
            try {
                int tempsAAttendreEnMili = 1000/sim.getParametres().getFramerate();
                Thread.sleep(tempsAAttendreEnMili);
            } catch(InterruptedException ex) {
                System.out.println("Simulation stopée");
            }
        }
    }
    
}
