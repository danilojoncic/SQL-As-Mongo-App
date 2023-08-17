package gui;

import controller.SQLWordListener;

import javax.swing.*;
import java.awt.*;

public class MyTextPane extends JTextPane {

    public MyTextPane() {
        this.getDocument().addDocumentListener(new SQLWordListener(this));
        this.setBackground(Color.lightGray);
        Font font = new Font("SQL",Font.BOLD,15);
        this.setFont(font);
    }
}
