package gui.controller;

import javax.swing.*;
import java.net.URL;

public abstract class AbstractActionManager extends AbstractAction {
    public Icon loadIcon(String iconFile){

        URL imageURL = getClass().getResource(iconFile);
        Icon icon = null;

        if (imageURL != null) {
            icon = new ImageIcon(imageURL);
        }
        else {
            System.err.println("Resource not found: " + iconFile);
        }
        return icon;
    }
}
