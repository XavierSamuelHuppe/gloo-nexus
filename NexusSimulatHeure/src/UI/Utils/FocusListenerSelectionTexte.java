package UI.Utils;

import java.awt.event.FocusEvent;

public class FocusListenerSelectionTexte extends java.awt.event.FocusAdapter {
    public void focusGained(FocusEvent fe) {
        ((javax.swing.JTextField)fe.getComponent()).selectAll();
    }
}
