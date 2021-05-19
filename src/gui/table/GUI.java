package gui.table;

import observer.Subscriber;

import javax.swing.table.DefaultTableModel;

public interface GUI extends Subscriber {

    void start();

    TableModel getTableModel();
    //void updateModel();
    //TableModel getTableModel();
}
