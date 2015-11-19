package Metier;

public class ContexteEdition {

    public static enum ModeEdition {
        POINT,
        SEGMENT,
        CIRCUIT,
        SOURCE,
        PASSAGER
    }
    
    private ModeEdition mode;
    
    public ContexteEdition(){
        mode = ModeEdition.POINT;
    }
    
    public boolean estEnModePoint(){
        return mode == ModeEdition.POINT;
    }
    public boolean estEnModeSegment(){
        return mode == ModeEdition.SEGMENT;
    }
    public boolean estEnModeCircuit(){
        return mode == ModeEdition.CIRCUIT;
    }
    public boolean estEnModeSource(){
        return mode == ModeEdition.SOURCE;
    }
    public boolean estEnModePassager(){
        return mode == ModeEdition.PASSAGER;
    }
    
    public void passerEnModePoint(){
        mode = ModeEdition.POINT;
    }
    public void passerEnModeSegment(){
        mode = ModeEdition.SEGMENT;
    }
    public void passerEnModeCircuit(){
        mode = ModeEdition.CIRCUIT;
    }
    public void passerEnModeSource(){
        mode = ModeEdition.SOURCE;
    }
    public void passerEnModePassager(){
        mode = ModeEdition.PASSAGER;
    }
    
    
}
