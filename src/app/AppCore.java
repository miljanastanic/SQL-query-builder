package app;

import database.Database;
import database.settings.Settings;
import database.settings.SettingsImpl;
import gui.table.GUI;
import gui.table.TableModel;
import utils.Constants;

public class AppCore {

    private Database database;
    private Settings settings;
    private GUI gui;

    public AppCore() {
        this.settings = initialiseSettings();
       // this.database = new dbimpl
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
}
