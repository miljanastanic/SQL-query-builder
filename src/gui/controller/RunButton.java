package gui.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RunButton extends AbstractActionManager{

    public RunButton() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("pic/run.png"));
        putValue(NAME, "Run");
        putValue(SHORT_DESCRIPTION, "Run query");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
