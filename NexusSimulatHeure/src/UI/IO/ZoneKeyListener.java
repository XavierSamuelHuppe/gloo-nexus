package UI.IO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ZoneKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent ke) {
        System.out.println("Zone keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("Zone keyPressed");
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println("Zone keyReleased");
    }
    
}
