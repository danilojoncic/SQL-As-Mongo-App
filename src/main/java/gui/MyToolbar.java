package gui;
import controller.InputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class MyToolbar extends JToolBar implements ActionListener {
    private JButton btnExec;
    private String useless;
    private JButton clicked;
    private InputListener inputListener;


    //to do maknuti ovo jer krsi MVC
    public MyToolbar() {
        init();
    }

    public void init() {
        useless = new String("local value that the interface will overwrite anyway");
        btnExec = new JButton("EXECUTE");
        this.setBorder(BorderFactory.createEtchedBorder());
        btnExec.addActionListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalGlue());
        int rightMargin = 25;
        EmptyBorder emptyBorder = new EmptyBorder(7, 25, 7, rightMargin);
        btnExec.setBorder(emptyBorder);
        this.add(btnExec);
    }

    public JButton getBtnExec() {
        return btnExec;
    }

    public void setBtnExec(JButton btnExec) {
        this.btnExec = btnExec;
    }

    public InputListener getInputListener() {
        return inputListener;
    }

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    public JButton getClicked() {
        return clicked;
    }

    public void setClicked(JButton clicked) {
        this.clicked = clicked;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clicked = (JButton) e.getSource();
        if (clicked == btnExec) {
            System.out.println("Exec pressed");
            if(inputListener != null){
                inputListener.listenInput(useless);
            }
        }
    }
}
