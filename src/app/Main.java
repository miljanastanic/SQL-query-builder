package app;

import gui.MainFrame;

public class Main {

    public static void main(String[] args) {
        AppCore appCore = new AppCore();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(appCore);

        mainFrame.getAppCore().readDataFromTable("EMPLOYEES");
        mainFrame.getAppCore().loadResource();


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainFrame.getAppCore().readDataFromTable("JOBS");

    }
}
