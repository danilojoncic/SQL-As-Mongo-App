package third_perspective;
import data.Row;
import database.CustomMongoDatabase;
import database.settings.MySettings;
import database.settings.Settings;
import org.bson.Document;
import third_perspective.Clauses.*;
import utils.Credentials;

import javax.print.Doc;
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

    private Settings initSettings() {
        Settings settingsImplementation = new MySettings();
        settingsImplementation.addParameter("ip", Credentials.my_ip);
        settingsImplementation.addParameter("database", Credentials.my_db);
        settingsImplementation.addParameter("username", Credentials.my_username);
        settingsImplementation.addParameter("password", Credentials.my_password);
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
        StringBuilder sb = new StringBuilder();
        sb.append("db.");
        for (AbsClause absClause : query.getAllClauses()) {
            if(absClause instanceof From){
                docs.add(Document.parse(absClause.Jsonify()));
                sb.append(absClause.Jsonify());
            }
        }
        sb.append(".find(");
        sb.append("{");
        for(AbsClause absClause : query.getAllClauses()){
            if (absClause instanceof Select){
                if (((Select) absClause).getSelectedColumns().get(0).equalsIgnoreCase("*")) {
                    docs.add(Document.parse("{}"));
                    sb.append("");
                    break;
                }
                sb.append(",");
                docs.add(Document.parse(absClause.Jsonify()));
                sb.append(absClause.Jsonify());
            }
        }
        for(AbsClause absClause: query.getAllClauses()){
            if (absClause instanceof Where){
                System.out.println(absClause.Jsonify());
                docs.add(Document.parse(absClause.Jsonify()));
                //System.out.println(absClause.Jsonify());
                sb.append(absClause.Jsonify());
            }
        }

        for (AbsClause absClause : query.getAllClauses()) {
            if(absClause instanceof Order_By){
                docs.add(Document.parse(absClause.Jsonify()));
            }
        }
        sb.append(")");
        System.out.println(docs);
        return docs;
    }

    public List<Row> returnRows(Query query){
        return customMongoDatabase.getDataFromTable(generateMongoDBQuery(query));
    }

    private String generateSubQuery(Query query){
        return null;
    }

}
