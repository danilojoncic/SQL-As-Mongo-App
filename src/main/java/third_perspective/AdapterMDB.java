package third_perspective;
import data.Row;
import database.CustomMongoDatabase;
import database.settings.MySettings;
import database.settings.Settings;
import org.bson.Document;
import third_perspective.Clauses.*;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AdapterMDB implements AdaptateMDB {
    public static AdapterMDB instance;
    private CustomMongoDatabase customMongoDatabase;
    private Settings mySettings;
    private AdapterMDB() {
    }


    public static AdapterMDB getInstance(){
        if(instance == null){
            instance = new AdapterMDB();
            instance.init();
        }
        return instance;
    }

    public static AdapterMDB getInstanceForTest(){
        if(instance == null){
            instance = new AdapterMDB();
        }
        return instance;
    }
    public static Settings initSettings() {
        Settings settingsImplementation = new MySettings();
        settingsImplementation.addParameter("ip", Constants.my_ip);
        settingsImplementation.addParameter("database", Constants.my_db);
        settingsImplementation.addParameter("username", Constants.my_username);
        settingsImplementation.addParameter("password", Constants.my_password);
        return settingsImplementation;
    }
    private void init(){
        mySettings = initSettings();
        customMongoDatabase = new CustomMongoDatabase(mySettings);
    }
    public CustomMongoDatabase getCustomMongoDatabase() {
        return customMongoDatabase;
    }
    public void setCustomMongoDatabase(CustomMongoDatabase customMongoDatabase) {
        this.customMongoDatabase = customMongoDatabase;
    }
    public List<Document> generateMongoDBQuery(Query query) {
        List<Document> docs = new ArrayList<>();
        for (Composite absClause : query.getAllClauses()) {
            if(absClause instanceof From){
                docs.add(Document.parse(((From) absClause).Jsonify()));

            }
        }
        for(Composite absClause : query.getAllClauses()){
            if (absClause instanceof Select){
                if (((Select) absClause).getSelectedColumns().get(0).equalsIgnoreCase("*")) {
                    docs.add(Document.parse("{}"));
                    break;
                }
                docs.add(Document.parse(((Select) absClause).Jsonify()));

            }
        }
        for(Composite absClause: query.getAllClauses()){
            if (absClause instanceof Where){
                docs.add(Document.parse(((Where) absClause).Jsonify()));
            }
        }

        for (Composite absClause : query.getAllClauses()) {
            if(absClause instanceof Order_By){
                docs.add(Document.parse(((Order_By) absClause).Jsonify()));
            }
        }
        return docs;
    }

    public List<Row> returnRows(Query query){
        return customMongoDatabase.getDataFromTable(generateMongoDBQuery(query));
    }

    private String generateSubQuery(Query query){
        return null;
    }

}
