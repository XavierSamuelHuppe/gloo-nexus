package Metier.Simulation;

public class BoucleSimulation implements Runnable{

    private Simulation sim;
    private boolean executer = true;
    
    public BoucleSimulation(Simulation sim){
        this.sim = sim;
    }
    
    @Override
    public void run() {
        while(executer)
        {
            long tStart = System.currentTimeMillis();
            if(sim.getParametres().estEnAction()){
                int framerate = sim.getParametres().getFramerate();
                double ratioVitesse = sim.getParametres().obtenirRatioVitesse();
                double TempsEcouleParRatioEnSeconde = (1/(double)framerate) * ratioVitesse;
                long TempsEcouleParRatioEnNanos = ((Double)(TempsEcouleParRatioEnSeconde * 1000000000)).longValue();
                sim.faireAvancerSimulation(TempsEcouleParRatioEnNanos, TempsEcouleParRatioEnSeconde);
            }
            long tEnd = System.currentTimeMillis();
            int tDelta = ((Long)(tEnd - tStart)).intValue();
            
            try {
                int tempsAAttendreEnMili = (1000/sim.getParametres().getFramerate()) - tDelta;
                //???
                if(tempsAAttendreEnMili > 0)
                {
                    Thread.sleep(tempsAAttendreEnMili);    
                }
            } catch(InterruptedException ex) {
                System.out.println("Simulation stopée");
            }
        }
    }
    
    public void stop()
    {
        executer = false;
    }
}
