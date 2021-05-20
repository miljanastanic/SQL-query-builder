package gui;

import errorHandler.Error;
import gui.table.GUI;
import gui.table.MainFrame;
import gui.table.TableModel;
import observer.Notification;

public class GUIImpl implements GUI {

    private MainFrame instance;
    private TableModel tableModel;

    public GUIImpl(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
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
}
