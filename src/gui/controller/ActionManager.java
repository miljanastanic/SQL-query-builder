package gui.controller;

public class ActionManager {

    private RunButton runButton;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions(){
        runButton = new RunButton();
    }

    public RunButton getRunButton() {
        return runButton;
    }
}
