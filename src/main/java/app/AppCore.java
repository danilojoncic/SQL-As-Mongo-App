package app;
import database.MyTableModel;


public class AppCore {
    private MyTableModel myTableModel;

    public AppCore() {
        this.myTableModel = new MyTableModel();
    }

    public MyTableModel getMyTableModel() {
        return myTableModel;
    }

    public void setMyTableModel(MyTableModel myTableModel) {
        this.myTableModel = myTableModel;
    }

}
