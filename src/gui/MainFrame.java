package gui;

import app.AppCore;
import observer.Notification;
import observer.NotificationCode;
import observer.Subscriber;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.table.TableModel;

public class MainFrame implements Subscriber {

        private static MainFrame instance = null;

        private AppCore appCore;
        private JTable jTable;
        private JScrollPane jScrollPane;
        private JPanel panel;
        private JTextArea jTextArea;

        private MainFrame(){

        }

        public static MainFrame getInstance(){
            if(instance==null){
                instance = new MainFrame();
                instance.intialise();
            }
            return instance;

        }
        private void intialise (){}
        public void setAppCore(AppCore appCore){
            this.appCore = appCore;
            //this.appCore.
            //this.jTable = new JTable()

        }

    @Override
    public void update(Notification notification) {
        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }else{
            jTable.setModel((TableModel)notification.getData());
        }
    }
}
