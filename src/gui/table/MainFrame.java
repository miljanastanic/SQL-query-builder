package gui.table;

import app.AppCore;
import errorHandler.Error;
import gui.controller.ActionManager;
import gui.controller.RunButton;
import javafx.scene.control.ToolBar;
import observer.Notification;
import observer.NotificationCode;
import observer.Subscriber;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private ActionManager actionManager;
    private TableModel tableModel;
    private JTable jTable;
    private JPanel jPanel;
    private JScrollPane jScrollPane;
    private JSplitPane jSplitPane;
    private JPanel panel;
    private JTextArea jTextArea;

    private MainFrame(){
    }

    public static MainFrame getInstance(){
        if(instance==null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;

    }
    private void initialise (){

        actionManager = new ActionManager();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = 600;
        int screenWidth = 1100;
        setSize(screenWidth, screenHeight);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DataBase project");
        setLocationRelativeTo(null);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.WHITE);

        jTable = new JTable();
        jTable.setBackground(Color.WHITE);
        jTable.setPreferredScrollableViewportSize(new Dimension(1100,500));
        jTable.setFillsViewportHeight(true);



        jScrollPane = new JScrollPane(jTable);

        jTextArea = new JTextArea();
        jTextArea.setBackground(Color.WHITE);
        jTextArea.setSelectedTextColor(Color.BLUE);

        JScrollPane scroll = new JScrollPane (jTextArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        RunToolBar runToolBar = new RunToolBar();
        jPanel.add(runToolBar,BorderLayout.EAST);

        jPanel.add(scroll);

        jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jPanel,jScrollPane);
        jSplitPane.setBackground(Color.WHITE);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerLocation(100);

        this.add(jSplitPane, BorderLayout.CENTER);

        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

//   public void setAppCore(AppCore appCore){
//        this.appCore = appCore;
//        this.appCore.addSubscriber(this);
//        jTable.setModel(appCore.getTableModel()); //PITAJ ANNU

    //}

    @Override
    public void update(Notification notification) {
        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }else{
            jTable.setModel((TableModel)notification.getData());
        }
    }

//    public void setCore(AppCore appCore){
//
//
//    }

    public ActionManager getActionManager() {
        return actionManager;
    }
    public String getText(){
        //jTextArea.getText();
       return jTextArea.getSelectedText();
    }
    public void clear(){
        jTextArea.setText(null);
    }

    public JTable getjTable() {
        return jTable;
    }

    public void showError(Error notification){
        System.out.println("aaa");
        JOptionPane.showMessageDialog(this, notification.getMessage(), notification.getTitle(), JOptionPane.WARNING_MESSAGE);
    }
}
