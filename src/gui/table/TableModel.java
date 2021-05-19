package gui.table;

import resource.data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel{

    private List<Row> rows;

    public void updateModel() {

        int columnCount = rows.get(1).getFields().keySet().size();

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(1).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
    }

    public TableModel getTableModel() {
        return this;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }


}
