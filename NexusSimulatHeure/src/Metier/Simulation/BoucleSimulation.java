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
            int tDelta = 0;
            if(sim.getParametres().estEnAction()){
                int framerate = sim.getParametres().getFramerate();
                double ratioVitesse = sim.getParametres().obtenirRatioVitesse();
                double TempsEcouleParRatioEnSeconde = (1/framerate) * ratioVitesse;
                
                //On calcul le temps de computation pour locker le framerate
                long tStart = System.currentTimeMillis();
                sim.faireAvancerSimulation(TempsEcouleParRatioEnSeconde);
                long tEnd = System.currentTimeMillis();
                tDelta = ((Long)(tEnd - tStart)).intValue();
            }
            
            try {
                int tempsAAttendreEnMili = (1000/sim.getParametres().getFramerate()) - tDelta;
                Thread.sleep(tempsAAttendreEnMili);
            } catch(InterruptedException ex) {
                System.out.println("Simulation stopée");
            }
        }
    }
    
}
