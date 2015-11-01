/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espacetravail.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author The Vagrant Geek
 */
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
