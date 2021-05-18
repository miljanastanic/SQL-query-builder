package gui;

import app.AppCore;

import javax.swing.*;

public class MainFrame {

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
        //update
}
