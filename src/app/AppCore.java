package app;

import database.Database;
import database.DatabaseImpl;
import database.repositories.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImpl;
import gui.table.GUI;
import gui.table.TableModel;
import observer.Notification;
import observer.NotificationCode;
import observer.PublisherImpl;
import resource.implementation.InformationResource;
import utils.Constants;

public class AppCore extends PublisherImpl {

    private Database database;
    private Settings settings;
    private GUI gui;

    public AppCore() {
        this.settings = initialiseSettings();
        this.database = new DatabaseImpl(new MSSQLrepository(this.settings));
        this.gui = new TableModel();
    }

    private Settings initialiseSettings(){
        Settings settingsImpl = new SettingsImpl();
        settingsImpl.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImpl.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImpl.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImpl.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImpl;

    }

    public void loadResource(){
        InformationResource informationResource = (InformationResource)this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,informationResource));
    }
    public void readDataFromTable(String fromTable){
        gui.getTableModel().setRows(this.database.readDataFromTable(fromTable));
    }
    public TableModel getTableModel() {
        return gui.getTableModel();
    } //PITAJANU

   // public void setGui(GUI gui) {
       // this.gui = gui;
   // }
}
