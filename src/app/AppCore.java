package app;

import compiler.Compiler;
import compiler.CompilerImpl;
import database.Database;
import database.DatabaseImpl;
import database.repositories.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImpl;
import errorHandler.ErrorHandler;
import errorHandler.ErrorHandlerImpl;
import gui.GUIImpl;
import gui.table.GUI;
import gui.table.TableModel;
import observer.Notification;
import observer.NotificationCode;
import utils.Constants;
import validator.Validator;
import validator.ValidatorImpl;

public class AppCore extends AppFramework {

    private static AppCore instance;
    //private String kveri = "SELECT * FROM EMPLOYEES";

    private AppCore() {

    }

    public static AppCore getInstance() {
        if(instance == null){
            instance = new AppCore();
        }
        return instance;
    }

    public static void main(String[] args) {
        AppFramework app = AppCore.getInstance();
        Settings settings = initialiseSettings();
        Database database = new DatabaseImpl(new MSSQLrepository(settings));
        GUI gui = new GUIImpl(new TableModel());
        Validator validator = new ValidatorImpl();
        Compiler compiler = new CompilerImpl();
        ErrorHandler errorHandler = new ErrorHandlerImpl();
        errorHandler.addSubsriber(gui);
        app.addSubscriber(gui);
        app.initialise(gui,errorHandler,settings,database,validator,compiler);
        app.run();
        //System.out.println(AppCore.getInstance().getKveri() + "main");
       // AppCore.getInstance().readDataFromTable(AppCore.getInstance().getKveri());
        //AppCore.getInstance().loadResource();
    }

    public static Settings initialiseSettings(){
        Settings settingsImpl = new SettingsImpl();
        settingsImpl.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImpl.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImpl.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImpl.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImpl;

    }

//    public void loadResource(){
//        InformationResource informationResource = (InformationResource)this.database.loadResource();
//        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,informationResource));
//    }
//    public void readDataFromTable(String fromTable){
//        gui.getTableModel().setRows(this.database.readDataFromTable(fromTable));
//    }

//    public TableModel getTableModel() {
//        return gui.getTableModel();
//    }

    public Validator getValidator() {
        return validator;
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void uzmiQuery(String s){
        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATER,s));
    }


    public Settings getSettings(){
        return settings;
    }

    @Override
    public void run() {
        this.gui.start();

    }

//    public String getKveri() {
//        return kveri;
//    }
//
//    public void setKveri(String kveri) {
//        this.kveri = kveri;
//    }
}
