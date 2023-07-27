package gui;
import app.AppCore;
import controller.UpdateController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private UpdateController updateController;
    private AppCore appCore;
    private MyToolbar myToolbar;
    private MyPanel myPanel;

    private static MainFrame instance = null;

    private MainFrame(){

    }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.init();
        }
        return instance;
    }

    private void init(){
        this.setTitle(" SAM: SQL As Mongo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myPanel = new MyPanel();
        myToolbar = new MyToolbar();
        this.setLayout(new BorderLayout());
        this.add(myToolbar,BorderLayout.NORTH);
        this.add(myPanel,BorderLayout.CENTER);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        updateController = new UpdateController(this);
    }
    public void setAppCore(AppCore appCore){
        this.appCore = appCore;
        this.myPanel.getTable().setModel(appCore.getMyTableModel());
    }

    public AppCore getAppCore() {
        return appCore;
    }

    public MyToolbar getMyToolbar() {
        return myToolbar;
    }

    public void setMyToolbar(MyToolbar myToolbar) {
        this.myToolbar = myToolbar;
    }

    public MyPanel getMyPanel() {
        return myPanel;
    }

    public void setMyPanel(MyPanel myPanel) {
        this.myPanel = myPanel;
    }
}


