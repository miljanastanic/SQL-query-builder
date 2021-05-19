package gui;

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

    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
