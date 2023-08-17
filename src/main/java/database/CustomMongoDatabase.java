package database;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import data.Row;
import database.settings.Settings;
import org.bson.Document;
import utils.Constants;

import java.util.*;
public class CustomMongoDatabase implements Database{

    private HashMap<String,List<String>> tabKol;

    private MongoClient mongoClient;
    private Settings settings;

    public CustomMongoDatabase(Settings settings) {
        this.settings = settings;
        tabKol = formHashMap();
    }

    public void initConnection() throws Exception, ClassNotFoundException{
        String ip = Constants.my_ip;
        String database = Constants.my_db;
        String username = Constants.my_username;
        String password = Constants.my_password;
        //credentials are not used,because after passing the exam, database contents are local on mongo Compass
        mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        //to connect to an actual remote server or database change the host and port number,e.g.
        //MongoCredential cred = MongoCredential.createCredential(username,database,password.toCharArray());
        //mongoClient = new MongoClient(new ServerAddress("134.209.239.154",27017), Arrays.asList(cred));
        //also dont forget to check the authentication setting for the db, and change them in the Credentials class
        System.out.println("CONNECTION OPENED");
    }
    public void closeConnection(){
        try{
            mongoClient.close();
            System.out.println("CONNECTION CLOSED");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            mongoClient = null;
        }
    }
    @Override
    public List<Row> getDataFromTable(List<Document> docs) {
        List<Row> rows = new ArrayList<>();
        try {
            this.initConnection();
            MongoDatabase database = mongoClient.getDatabase("hr");
            Document fromDoc = docs.get(0);
            String cas = docs.get(0).toJson().toString();
            String useful[] = cas.split("\"");
            String tables[] = useful[0].split("\\{");
            String table = useful[1];


            Document column = docs.get(1);
            Document codition = Document.parse("{}");
            Document sort =  Document.parse("{}");

            for(Document doc : docs){
                if(doc.toJson().toString().contains("$")){
                    codition = doc;
                    break;
                }
            }
            for(Document doc: docs){
                if(!(doc.equals(column)) && !(doc.equals(codition)) && !(doc.equals(fromDoc))){
                    sort = doc;
                }
            }
            Document joinColumns = returnForJoinColumns(table,docs.get(1),"joinResult");
            MongoCursor<Document> cursor = null;
            if(docs.get(0).toJson().toString().contains("join")){
                System.out.println("JOIN DIO");
                String from = null;
                String to = null;
                String zajednicko = null;
                String glavnica = docs.get(0).toJson().toString();
                String navodnici[] = glavnica.split("\"");
                from = navodnici[1];
                to = navodnici[5];
                zajednicko = navodnici[9];
                Document lookup = Document.parse("{\n" +
                        "  $lookup: {\n" +
                        "    from: \"" + to + "\",\n" +
                        "    localField: \"" + zajednicko + "\",\n" +
                        "    foreignField: \"" + zajednicko + "\",\n" +
                        "    as: \"joinResult\"\n" +
                        "  }\n" +
                        "}");
                Document unwind = Document.parse("{ $unwind: \"$joinResult\" }");
                List<Document> joinDocs = new ArrayList<>();
                Document matchDoc = Document.parse("{}");
                if(docs.size() > 2 && !(docs.get(2).toJson().toString().equals("{}"))){
                    matchDoc = returnWhereMatchDocument(table,docs.get(2),"joinResult");
                    joinDocs.add(matchDoc);
                }
                joinDocs.add(lookup);
                joinDocs.add(unwind);
                Document sortDoc = Document.parse("{}");

                if(docs.size() == 3 && !sort.toJson().toString().equals("{}")){
                    sortDoc = returnOrderBySortDocument(table,docs.get(3),"joinResult");
                    joinDocs.add(sortDoc);
                }

                if(docs.get(1).toJson().toString().contains("{}")){
                    cursor = database.getCollection(table).aggregate(joinDocs).iterator();
                }else{
                    joinDocs.add(joinColumns);
                    cursor = database.getCollection(table).aggregate(joinDocs).iterator();
                }
            }else{
                cursor = database.getCollection(table).find(codition).projection(column).sort(sort).iterator();
            }
            while (cursor.hasNext()) {
                Document d = cursor.next();
                Row row = new Row();
                row.setName("Sve svuda u isto vrijeme");
                Set<String> mergedKeySet = new HashSet<>(d.keySet());
                Document joinResult = null;
                if (d.containsKey("joinResult")) {
                    joinResult = (Document) d.get("joinResult");
                    System.out.println(joinResult.toJson().toString());
                    mergedKeySet.addAll(joinResult.keySet());
                }
                for (String field : mergedKeySet) {
                    if ((!field.equals("_id")) && (!field.equals("joinResult"))) {
                        String value;
                        if (d.containsKey(field)) {
                            value = d.get(field).toString();
                        } else if (d.containsKey("joinResult") && joinResult.containsKey(field)) {
                            value = joinResult.get(field).toString();
                        } else {
                            value = "";
                        }
                        row.addField(field, value);
                    }
                }
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return rows;
    }

    private Document returnOrderBySortDocument(String table, Document orderByDocument,String alias){
        Document doc = null;
        String json = "{ $sort: " + orderByDocument.toJson() + " }";
        doc = Document.parse(json);
        System.out.println(doc.toJson());
        return doc;
    }


    private Document returnWhereMatchDocument(String table,Document whereDocument,String alias){
        //HashMap magic
        String json = "{ $match: " + whereDocument.toJson() + " }";
        Document matchDoc = Document.parse(json);
        return matchDoc;
    }
    private Document returnForJoinColumns(String fromTable,Document theDocument,String alias){
        //
        Document doc = null;
        StringBuilder sb = new StringBuilder();
        int totalKeys = theDocument.keySet().size();
        int index = 0;
        sb.append("{ $project: { ");
        for(String s : theDocument.keySet()){
            if(tabKol.containsKey(fromTable) && tabKol.get(fromTable).contains(s)) {
                sb.append("\"" + s + "\": 1");
            }else{
                sb.append("\"" + alias + "." + s + "\": 1");
            }
            if(index < totalKeys - 1){
                sb.append(",");
            }
            index++;
        }
        sb.append("} }");
        doc = Document.parse(sb.toString());
        return doc;
    }
    private HashMap<String,List<String>> formHashMap(){
        HashMap<String, List<String>> tabKol = new HashMap<>();
        try{
            System.out.println("CONNECTION TO FORM HASH MAP OPENED");
            this.initConnection();
            MongoDatabase database = mongoClient.getDatabase("hr");
            MongoCursor<String> collectionNames = database.listCollectionNames().iterator();
            while(collectionNames.hasNext()){
                String collectionName = collectionNames.next();
                List<String> columnNames = new ArrayList<>();
                MongoCursor<Document> cursor = database.getCollection(collectionName).find().limit(1).iterator();
                if(cursor.hasNext()){
                    Document document = cursor.next();
                    for(String field : document.keySet()){
                        columnNames.add(field);
                    }
                }
                tabKol.put(collectionName, columnNames);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            this.closeConnection();
        }
        return tabKol;
    }
}