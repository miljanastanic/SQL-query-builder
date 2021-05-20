package gui.controller;

public class ActionManager {

    private RunButton runButton;
    private ClearButton clearButton;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions(){
        runButton = new RunButton();
        clearButton = new ClearButton();
    }

    public RunButton getRunButton() {
        return runButton;
    }

    public ClearButton getClearButton() {
        return clearButton;
    }
}
