package controller;

import data.Row;
import database.MyTableModel;
import third_perspective.AdapterMDB;
import utils.Parser;
import third_perspective.Query;
import utils.Validator;
import gui.MainFrame;
import javax.swing.*;
import java.util.List;

public class UpdateController {
    private MainFrame mainFrame;

    public UpdateController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        attachListeners();
    }

    private void attachListeners() {
        mainFrame.getMyToolbar().setInputListener(new InputListener() {
            @Override
            public void listenInput(String input) {
                Query query = Parser.getInstance().createFromString(mainFrame.getMyPanel().getTextArea().getText());
                if (!Validator.getInstance().validate(query)) {
                    JOptionPane.showMessageDialog(null, "Query is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    mainFrame.getMyPanel().getTextArea().setText("");
                    return;
                }
                List<Row> rows = AdapterMDB.getInstance().returnRows(query);
                tableBiz(rows);
            }
        });
    }

    private void tableBiz(List<Row> rows) {
        populateTable(rows);
    }
    private void populateTable(List<Row> rows) {
        MyTableModel model = (MyTableModel) mainFrame.getMyPanel().getTable().getModel();
        model.setRows(rows);
    }
}
