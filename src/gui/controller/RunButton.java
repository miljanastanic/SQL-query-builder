package gui.controller;

import app.AppCore;
import errorHandler.Type;
import gui.table.MainFrame;
import observer.Notification;
import observer.NotificationCode;
import validator.Validator;

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
        String s = MainFrame.getInstance().getText();

        if(s!=null) {
            if (MainFrame.getInstance().getAppCore().getValidator().valid(s)) {
                MainFrame.getInstance().getAppCore().getCompiler().compile(s);
            } else {
                MainFrame.getInstance().getAppCore().getErrorHandler().generateError(Type.CANNOT_COMPILE);

            }
            s = null;
        }else{
            System.out.println("aaa");
            MainFrame.getInstance().getAppCore().getErrorHandler().generateError(Type.CANNOT_COMPILE);
        }
    }
}
