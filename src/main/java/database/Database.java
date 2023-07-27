package database;

import data.Row;
import org.bson.Document;

import java.util.List;

public interface Database {
    List<Row> getDataFromTable(List<Document> docs);

    /*
        @Override
        public List<Row> getDataFromTable(String from, List<String> columns) {
            List<Row> rows = new ArrayList<>();
            try {
                this.initConnection();
                MongoDatabase database = mongoClient.getDatabase(Credentials.my_db);
                MongoCollection<Document> collection = database.getCollection(from, Document.class);
                FindIterable<Document> documents;

                if (columns.isEmpty()) {
                    documents = collection.find();
                } else {
                    Document projection = new Document();
                    for (String column : columns) {
                        projection.append(column, 1);
                    }
                    documents = collection.find().projection(projection);
                }

                for (Document document : documents) {
                    Row row = new Row();
                    row.setName(from);
                    for (String field : document.keySet()) {
                        row.addField(field, document.get(field).toString());
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
        */
}
