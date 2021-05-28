package gui;

import app.AppCore;
import database.Database;
import database.DatabaseImpl;
import database.repositories.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImpl;
import errorHandler.Error;
import gui.table.GUI;
import gui.table.MainFrame;
import gui.table.TableModel;
import observer.Notification;
import resource.implementation.InformationResource;

public class GUIImpl implements GUI {

    private MainFrame instance;
    private TableModel tableModel;
    //private Settings settings = AppCore.getInstance().getSettings();
    private Database baza = new DatabaseImpl(new MSSQLrepository(AppCore.getInstance().getSettings()));

    public GUIImpl(TableModel tableModel) {
        this.tableModel = tableModel;
        //baza.readDataFromTable("EMPLOYEES");

    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        instance.getjTable().setModel(tableModel);
        instance.setVisible(true);
    }

    @Override
    public void update(Notification notification) {
        System.out.println("update");
        Object error = notification.getData();
        Error error1 = (Error) error;
        if (error1 instanceof Error) {
            MainFrame.getInstance().showError(error1);
        }
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
//    public void loadResource(){
//        InformationResource informationResource = (InformationResource)this.baza.loadResource();
//        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,informationResource));
//    }
//    public void readDataFromTable(String fromTable){
//        this.getTableModel().setRows(this.baza.readDataFromTable(fromTable));
//    }
}
