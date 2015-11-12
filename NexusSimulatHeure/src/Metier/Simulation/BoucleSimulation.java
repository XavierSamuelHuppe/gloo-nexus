package Metier.Simulation;

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
                int framerate = sim.getParametres().getFramerate();
                double ratioVitesse = sim.getParametres().obtenirRatioVitesse();
                double TempsEcouleParRatioEnSeconde = (1/framerate) * ratioVitesse;
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
