package controller;

import gui.MyTextPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.Constants.SQL_KEYWORDS;

public class SQLWordListener implements DocumentListener {
    private MyTextPane myTextPane;
    private StyledDocument styledDocument;
    private Style keywordStyle;
    public SQLWordListener(MyTextPane myTextPane) {
        this.myTextPane = myTextPane;
        this.styledDocument = myTextPane.getStyledDocument();
        this.keywordStyle = styledDocument.addStyle("KeywordStyle",null);
        StyleConstants.setForeground(keywordStyle,Color.RED);
    }

    private void highlightText() {
        SwingUtilities.invokeLater(() -> {
            String text = myTextPane.getText();
            styledDocument.setCharacterAttributes(0, text.length(), styledDocument.getStyle(StyleContext.DEFAULT_STYLE), true);

            for (String keyword : SQL_KEYWORDS) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(text);

                while (matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    styledDocument.setCharacterAttributes(start, end - start, keywordStyle, false);
                }
            }
        });
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        highlightText();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        highlightText();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
