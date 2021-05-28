package app;

import compiler.Compiler;
import database.Database;
import database.settings.Settings;
import divider.Divider;
import errorHandler.ErrorHandler;
import gui.table.GUI;
import gui.table.MainFrame;
import observer.PublisherImpl;
import validator.Validator;


public abstract class AppFramework extends PublisherImpl {

    protected GUI gui;
    protected ErrorHandler errorHandler;
    protected Settings settings;
    protected Database database;
    protected Validator validator;
    protected Compiler compiler;

    public abstract void run();
    public void initialise(GUI gui, ErrorHandler errorHandler, Settings settings, Database database, Validator validator, Compiler compiler){
        this.gui = gui;
        this.errorHandler = errorHandler;
        this.settings = settings;
        this.database = database;
        this.validator = validator;
        this.compiler = compiler;
    }

/*
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainFrame.getAppCore().readDataFromTable("JOBS");


 */


}
