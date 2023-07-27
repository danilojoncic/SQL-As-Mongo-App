package database;

import data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class MyTableModel extends DefaultTableModel {
    private List<Row> rows;
    private void updateModel() {
        if (rows == null || rows.isEmpty()) {
            setDataVector(new Vector(), new Vector());
            return;
        }
        Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector dataVector = new Vector();
        for (Row row : rows) {
            if (row.getFields().size() != columnVector.size()) {
                continue;
            }
            Vector rowData = DefaultTableModel.convertToVector(row.getFields().values().toArray());
            dataVector.add(rowData);
        }
        setDataVector(dataVector, columnVector);
    }
    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }
}
