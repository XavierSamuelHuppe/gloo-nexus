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
            long tStart = System.currentTimeMillis();
            if(sim.getParametres().estEnAction()){
                int framerate = sim.getParametres().getFramerate();
                double ratioVitesse = sim.getParametres().obtenirRatioVitesse();
                double TempsEcouleParRatioEnSeconde = (1/framerate) * ratioVitesse;
                sim.faireAvancerSimulation(TempsEcouleParRatioEnSeconde);
            }
            long tEnd = System.currentTimeMillis();
            int tDelta = ((Long)(tEnd - tStart)).intValue();
            
            try {
                int tempsAAttendreEnMili = (1000/sim.getParametres().getFramerate()) - tDelta;
                Thread.sleep(tempsAAttendreEnMili);
            } catch(InterruptedException ex) {
                System.out.println("Simulation stopée");
            }
        }
    }
    
}
