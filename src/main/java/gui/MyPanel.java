package gui;

import database.MyTableModel;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel{
    private MyTextPane textPane;
    private JTable table;

    public MyPanel() {
        init();
    }

    public void init(){
        this.setLayout(new BorderLayout());
        textPane = new MyTextPane();
        JScrollPane textScrollPane = new JScrollPane(textPane);
        table = new JTable();
        //novo dodato
        table.setModel(new MyTableModel());
        JScrollPane tableScrollPane = new JScrollPane(table);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,textScrollPane,tableScrollPane);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true);
        add(splitPane,BorderLayout.CENTER);
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
