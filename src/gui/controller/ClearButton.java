package gui.controller;

import gui.table.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ClearButton extends AbstractActionManager{

    public ClearButton() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        //putValue(SMALL_ICON, loadIcon(""));
        putValue(NAME, "Clear");
        putValue(SHORT_DESCRIPTION, "Clear text");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clear();
    }
}
