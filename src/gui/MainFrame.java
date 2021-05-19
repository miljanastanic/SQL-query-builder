package gui;

import app.AppCore;
import observer.Notification;
import observer.NotificationCode;
import observer.Subscriber;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
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

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = 600;
        int screenWidth = 700;
        setSize(screenWidth, screenHeight);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DataBase project");
        setLocationRelativeTo(null);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.WHITE);

        jScrollPane = new JScrollPane();
        jScrollPane.setSize(new Dimension(700,550));
        jScrollPane.setBackground(Color.WHITE);

        jTable = new JTable();
        jTable.setBackground(Color.WHITE);
        jTable.setPreferredScrollableViewportSize(new Dimension(700,550));
        jTable.setFillsViewportHeight(true);
        jTable.add(jScrollPane);

        jTextArea = new JTextArea();
        jTextArea.setBackground(Color.WHITE);
        jTextArea.setSelectedTextColor(Color.BLUE);


        JScrollPane scroll = new JScrollPane (jTextArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        jPanel.add(scroll);

        jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jPanel,jTable);
        jSplitPane.setBackground(Color.WHITE);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setDividerLocation(50);

        this.add(jSplitPane, BorderLayout.CENTER);

        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setAppCore(AppCore appCore){
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        jTable.setModel(appCore.getGui().getTableModel());

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
