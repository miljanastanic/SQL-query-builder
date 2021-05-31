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

    private String bulitQuery;
    private String s;
    private String m;
    private boolean check;

    public RunButton() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("pic/run.png"));
        putValue(NAME, "Run");
        putValue(SHORT_DESCRIPTION, "Run query");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        s = MainFrame.getInstance().getText();
       // check = AppCore.getInstance().getValidator().valid(s);
        if(s!=null) {
            if (AppCore.getInstance().getValidator().valid(s)) {
                m = AppCore.getInstance().getCompiler().makeSQLQuery(s);
                //s = "";
                AppCore.getInstance().uzmiQuery(m);
                m = "";
                //AppCore.getInstance().setKveri(AppCore.getInstance().getCompiler().makeSQLQuery(s));

                //AppCore.getInstance().readDataFromTable(AppCore.getInstance().getKveri());
                //AppCore.getInstance().loadResource();
                //AppCore.getInstance().getCompiler().makeSQLQuery(s);
                s=null;
            } else {
                AppCore.getInstance().getErrorHandler().generateError(Type.CANNOT_COMPILE);
              //  s = "";
                //check = true;

            }

        }else{
            //s = "";
            AppCore.getInstance().getErrorHandler().generateError(Type.CANNOT_COMPILE);
        }
    }
}
